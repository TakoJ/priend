package com.example.management;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.nhn.android.maps.NMapActivity;
import com.nhn.android.maps.NMapCompassManager;
import com.nhn.android.maps.NMapController;
import com.nhn.android.maps.NMapLocationManager;
import com.nhn.android.maps.NMapView;
import com.nhn.android.maps.NMapView.OnMapStateChangeListener;
import com.nhn.android.maps.NMapView.OnMapViewTouchEventListener;
import com.nhn.android.maps.maplib.NGeoPoint;
import com.nhn.android.maps.overlay.NMapPOIdata;
import com.nhn.android.maps.overlay.NMapPathData;
import com.nhn.android.maps.overlay.NMapPathLineStyle;
import com.nhn.android.mapviewer.overlay.NMapMyLocationOverlay;
import com.nhn.android.mapviewer.overlay.NMapOverlayManager;
import com.nhn.android.mapviewer.overlay.NMapPOIdataOverlay;
import com.nhn.android.mapviewer.overlay.NMapPOIdataOverlay.OnStateChangeListener;
import com.nhn.android.mapviewer.overlay.NMapPathDataOverlay;

//GPS에 필요한 라이브러리
//지도위에 오버레이 아이템 표시를 위한 라이브러리
/*import com.nhn.android.mapviewer.overlay.NMapPOIdataOverlay.OnStateChangeListener;*/
//지도 위에 경로 그리기를 위한 라이브러리
/*import com.nhn.android.mapviewer.overlay.NMapPOIdataOverlay;*/

/**
 * Created by Chaewon on 2017-05-18.
 * map code referred from Naver Developer Center
 * permission code referred from Google Developer Center
 */

public class mapActivity extends NMapActivity {
    MyApplicationPermission mapPermission;

    private NMapView mMapView;
    private final String CLIENT_ID = "HV8X2Nc9rkckC89MJH61";
    private LinearLayout MapContainer;
    OnMapStateChangeListener onMapStateChangeListener;
    OnMapViewTouchEventListener onMapViewTouchEventListener;
    NMapController mMapController;

    NMapViewerResourceProvider mMapViewerResourceProvider;
    //지도 위 오버레이 객체 드로잉에 필요한 리소스 데이터를 제공하는 클래스
    NMapOverlayManager mOverlayManager;
    //오버레이 객체 관리 클래스
    OnStateChangeListener onPOIdataStateChangeListener;
    //POI 아이템 선택 상태 변경 시 호출되는 콜백 인터페이스스

    NMapMyLocationOverlay mMyLocationOverlay;
    //지도 위에 현재 위치를 표시하는 오버레이 클래스
    NMapLocationManager mMapLocationManager;
    //단말기의 현재 위치 탐색 기능을 사용하는 클래스
    NMapCompassManager mMapCompassManager;
    //단말기의 나침반 기능을 사용하는 클래스스


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MapContainer = (LinearLayout) findViewById(R.id.mapLayout);

        mMapView = new NMapView(this);
        setContentView(mMapView);
        mMapView.setClientId(CLIENT_ID); // 클라이언트 아이디 값 설정

        mMapView.setClickable(true);
        mMapView.setEnabled(true);
        mMapView.setFocusable(true);
        mMapView.setFocusableInTouchMode(true);
        mMapView.requestFocus();
        mMapView.setBuiltInZoomControls(true, null);
        //줌 인아웃 버튼
        mMapView.setOnMapStateChangeListener(onMapStateChangeListener);
        //지도 상태 변경시 호출되는 콜백 인터페이스
        mMapView.setOnMapViewTouchEventListener(onMapViewTouchEventListener);
        //지도 터치 이벤트 처리 후 호출되는 콜백 인터페이스
        mMapController = mMapView.getMapController();
        //지도 컨트롤러 (줌인아웃 기능 등) 사용

        mMapLocationManager = new NMapLocationManager(this);
        //위치 관리 매니저 객체 생성
        mMapLocationManager.setOnLocationChangeListener(onMyLocationChangeListener);
        //현재 위치 변경 시 호출되는 콜백 인터페이스
        mMapViewerResourceProvider = new NMapViewerResourceProvider(this);
        mOverlayManager = new NMapOverlayManager(this, mMapView, mMapViewerResourceProvider);

        mMyLocationOverlay = new NMapMyLocationOverlay(this, mMapView, mMapLocationManager, mMapCompassManager, mMapViewerResourceProvider);
        mMyLocationOverlay = mOverlayManager.createMyLocationOverlay(mMapLocationManager, mMapCompassManager);
        //NMapMyLocationOverlay 객체를 생성
        startMyLocation();//내 위치 찾기 함수 호출
        testOverlayMaker();
        testOverlayPath(); //경로 그리기 함수 시행

        //permission code for android version 6.0
        mapPermission = (MyApplicationPermission) getApplicationContext();

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            //조건문이 true일 경우, permission허가된 것, if문이 실행된다.
            MyApplicationPermission.mapPermission = true;
        }
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.ACCESS_FINE_LOCATION)) {}
        if (!MyApplicationPermission.mapPermission) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 10);
        }
    }

    //permission 설정
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 10 && grantResults.length > 0) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                MyApplicationPermission.mapPermission = true;
            }
            else {}
            return;
        }
    }

    private void testOverlayMaker() { //오버레이 아이템 추가 함수 정의
        int markerId = NMapPOIflagType.PIN; //마커 아이디 설정
        //POI 아이템 관리 클래스 생성: 전체 아이템 수, NMapResourceProvider 상속 클래스
        NMapPOIdata poiData = new NMapPOIdata(2, mMapViewerResourceProvider);
        poiData.beginPOIdata(2);//POI 아이템 추가 시작: 두개를 추가할 것이다
        poiData.addPOIitem(128.3925046, 36.1454420, "marker1", markerId, 0); //이거 좌표 금오공대, 이 좌표를 내 위치 기반 가까운 동물병원으로 바꿀 수 이써야 한다.
        poiData.addPOIitem(128.3915046, 36.1354420, "marker2", markerId, 0);
        poiData.endPOIdata();//POI 아이템 추가 종료
        //POI data overlay 객체 생성: 여러 개의 오버레이 아이템을 포함할 수 있는 오버레이 클래스
        NMapPOIdataOverlay poiDataOverlay = mOverlayManager.createPOIdataOverlay(poiData, null);
        poiDataOverlay.showAllPOIdata(0); //모든 POI 데이터를 화면에 표시한다 (괄호 안은 zoom level)
        poiDataOverlay.setOnStateChangeListener(onPOIdataStateChangeListener);
        //POI 아이템이 선택 상태 변경 시 호출되는 콜백 인터페이스
    }

    private final NMapLocationManager.OnLocationChangeListener onMyLocationChangeListener = new NMapLocationManager.OnLocationChangeListener() {
        //위치 변경 콜백 인터페이스의 정의
        //아래 코드는 위치가 변경될 경우 호출된다
        @Override
        public boolean onLocationChanged(NMapLocationManager locationManager, NGeoPoint myLocation) {
            if (mMapController != null) {
                mMapController.animateTo(myLocation); //지도 중심을 현재 위치로 이동
            }
            return true;
        }

        //정해진 시간 내에 위치 탐색 실패 시 호출
        @Override
        public void onLocationUpdateTimeout(NMapLocationManager locationManager) {

        }

        //현재 위치가 지도 상에 표시할 수 있는 범위를 벗어나는 경우 호출
        @Override
        public void onLocationUnavailableArea(NMapLocationManager locationManager, NGeoPoint myLocation) {
            stopMyLocation(); //내 위치찾기 중지 함수 호출출
        }
    };

    private void startMyLocation() {
        if (mMapLocationManager.isMyLocationEnabled()) {//현재 위치를 탐색중인지 확인
            if (!mMapView.isAutoRotateEnabled()) {//지도 회전기능 활성화 상태 여부 확인
                mMyLocationOverlay.setCompassHeadingVisible(true);//나침반 각도 표시
                mMapCompassManager.enableCompass();//나침반 모니터링 시작
                mMapView.setAutoRotateEnabled(true, false); //지도 회전 기능 활성화
            }
            mMapView.invalidate();
        } else {//현재 위치를 탐색중이지 않은 경우
            Boolean isMyLocationEnabled = mMapLocationManager.enableMyLocation(false);//현재 위치 탐색시작
            if (!isMyLocationEnabled) {//위치 탐색이 불가능한 경우
                Toast.makeText(mapActivity.this, "시스템 셋팅에서 위치 정보를 허용해주세요", Toast.LENGTH_LONG).show();

                Intent goToSettings = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(goToSettings); //위치 설정 창으로 이동
                return;
            }
        }
    }

    private void stopMyLocation() {
        mMapLocationManager.disableMyLocation();//현재 위치 탐색 종료
        if (mMapView.isAutoRotateEnabled()) {//지도 회전기능이 활성화 상태일때
            mMyLocationOverlay.setCompassHeadingVisible(false);//나침반 각도 표시 제거
            mMapCompassManager.disableCompass();//나침반 모니터링 종료
            mMapView.setAutoRotateEnabled(false, false);//지도 회전기능 중지
        }
    }

    private void testOverlayPath() {
        NMapPOIdata poiData = new NMapPOIdata(2, mMapViewerResourceProvider);
        poiData.beginPOIdata(2);
        poiData.addPOIitem(127.108099, 37.366034, "begin", NMapPOIflagType.FROM, 0); //이거 좌표 금오공대, 이 좌표를 내 위치 기반 가까운 동물병원으로 바꿀 수 이써야 한다.
        poiData.addPOIitem(127.106279, 37.366380, "end", NMapPOIflagType.TO, 0);
        poiData.endPOIdata();

        //POI 데이터 오버레이 객체 생성: 여러개의 오버레이 아이템을 포함할 수 있는 오버레이 클래스
        NMapPOIdataOverlay poiDataOverlay = mOverlayManager.createPOIdataOverlay(poiData, null);
        poiDataOverlay.showAllPOIdata(0); //모든 POI 데이터를 화면에 표시
        poiDataOverlay.setOnStateChangeListener(onPOIdataStateChangeListener); //콜백 인터페이스 설정

        NMapPathData pathData = new NMapPathData(9); //경로 관리 클래스 생성: 괄호 안은 경로 데이터의 개수
        pathData.initPathData(); //경로데이터 추가 시작

        //경로 데이터의 보간점 좌표 추가- 좌표, 선 타입 설정 (0으로 설정 시 이전 값을 그대로 사용한다)
        pathData.addPathPoint(127.108099, 37.366034, NMapPathLineStyle.TYPE_SOLID);
        pathData.addPathPoint(127.108088, 37.366043, 0);
        pathData.addPathPoint(127.108079, 37.365619, 0);
        pathData.addPathPoint(127.107458, 37.365608, 0);
        pathData.addPathPoint(127.107232, 37.365608, 0);
        pathData.addPathPoint(127.106904, 37.365624, 0);
        pathData.addPathPoint(127.105933, 37.365621, NMapPathLineStyle.TYPE_DASH);
        pathData.addPathPoint(127.105929, 37.366378, 0);
        pathData.addPathPoint(127.106279, 37.366380, 0);
        pathData.endPathData();//경로 데이터 추가 종료

        //경로 데이터를 표시하는 오버레이 객체 생성
        NMapPathDataOverlay pathDataOverlay = mOverlayManager.createPathDataOverlay(pathData);
    }
}