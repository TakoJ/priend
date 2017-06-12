package com.example.management;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.nhn.android.maps.NMapActivity;
import com.nhn.android.maps.NMapController;
import com.nhn.android.maps.NMapView;
import com.nhn.android.maps.maplib.NGeoPoint;
import com.nhn.android.maps.NMapView.OnMapStateChangeListener;
import com.nhn.android.maps.NMapView.OnMapViewTouchEventListener;

//GPS에 필요한 라이브러리
import com.nhn.android.maps.NMapLocationManager;
import com.nhn.android.maps.NMapCompassManager;
import com.nhn.android.mapviewer.overlay.NMapMyLocationOverlay;
import com.nhn.android.mapviewer.overlay.NMapPOIdataOverlay.OnStateChangeListener;

//지도위에 오버레이 아이템 표시를 위한 라이브러리
import com.nhn.android.maps.overlay.NMapPOIdata;
/*import com.nhn.android.mapviewer.overlay.NMapPOIdataOverlay.OnStateChangeListener;*/
import com.nhn.android.mapviewer.overlay.NMapOverlayManager;
import com.nhn.android.mapviewer.overlay.NMapPOIdataOverlay;

//지도 위에 경로 그리기를 위한 라이브러리
import com.nhn.android.maps.overlay.NMapPathData;
import com.nhn.android.maps.overlay.NMapPathLineStyle;
/*import com.nhn.android.mapviewer.overlay.NMapPOIdataOverlay;*/
import com.nhn.android.mapviewer.overlay.NMapPathDataOverlay;

/**
 * Created by Chaewon on 2017-05-18.
 * map code referred from Naver Developer Center
 * permission code referred from Google Developer Center
 */

public class mapActivity extends NMapActivity {

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

        Intent intent= getIntent();
        int var= intent.getIntExtra("var", 0);

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


        //누르는 위치에 따라서 실행되는 함수가 다르다
        if (var==1){
            startMyLocation();//내 위치 찾기 함수 호출
            testOverlayMaker();
        }
        else
            testOverlayPath(); //경로 그리기 함수 시행
    }

    private void testOverlayMaker() { //오버레이 아이템 추가 함수 정의
        int markerId = NMapPOIflagType.PIN; //마커 아이디 설정
        //POI 아이템 관리 클래스 생성: 전체 아이템 수, NMapResourceProvider 상속 클래스
        NMapPOIdata poiData = new NMapPOIdata(3, mMapViewerResourceProvider);
        poiData.beginPOIdata(3);//POI 아이템 추가 시작: 세개를 추가할 것이다
        poiData.addPOIitem(127.0339685, 37.5560677, "오렌지동물병원", markerId, 0);
        poiData.addPOIitem(127.0370298, 37.5596499, "한양동물메디컬센터", markerId, 0);
        poiData.addPOIitem(127.0428056, 37.5657035, "기린동물병원", markerId, 0);
        poiData.endPOIdata();//POI 아이템 추가 종료
        //POI data overlay 객체 생성: 여러 개의 오버레이 아이템을 포함할 수 있는 오버레이 클래스
        NMapPOIdataOverlay poiDataOverlay = mOverlayManager.createPOIdataOverlay(poiData, null);
        poiDataOverlay.showAllPOIdata(0); //모든 POI 데이터를 /화면에 표시한다 (괄호 안은 zoom level)
        poiDataOverlay.setOnStateChangeListener(onPOIdataStateChangeListener);
        //POI 아이템이 선택 상태 변경 시 호출되는 콜백 인터페이스
    }
//GPS
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
//경로
    private void testOverlayPath() {
        NMapPOIdata poiData = new NMapPOIdata(2, mMapViewerResourceProvider);
        poiData.beginPOIdata(2);
        poiData.addPOIitem(127.0492429, 37.5559484, "출발", NMapPOIflagType.FROM, 0);
        poiData.addPOIitem(127.0431163, 37.5658500, "도착", NMapPOIflagType.TO, 0);
        poiData.endPOIdata();

        //POI 데이터 오버레이 객체 생성: 여러개의 오버레이 아이템을 포함할 수 있는 오버레이 클래스
        NMapPOIdataOverlay poiDataOverlay = mOverlayManager.createPOIdataOverlay(poiData, null);
        poiDataOverlay.showAllPOIdata(0); //모든 POI 데이터를 화면에 표시
        poiDataOverlay.setOnStateChangeListener(onPOIdataStateChangeListener); //콜백 인터페이스 설정

        NMapPathData pathData = new NMapPathData(9); //경로 관리 클래스 생성: 괄호 안은 경로 데이터의 개수
        pathData.initPathData(); //경로데이터 추가 시작

        //경로 데이터의 보간점 좌표 추가- 좌표, 선 타입 설정 (0으로 설정 시 이전 값을 그대로 사용한다)
        //from ITBT to 기린동물병원
        pathData.addPathPoint(127.0492429, 37.5559484, NMapPathLineStyle.TYPE_DASH);
        pathData.addPathPoint(127.0492429, 37.5555484, 0);
        pathData.addPathPoint(127.0488429, 37.5549052, 0);
        pathData.addPathPoint(127.0470199, 37.5549052, 0);
        pathData.addPathPoint(127.0430681, 37.5565076, NMapPathLineStyle.TYPE_SOLID);
        pathData.addPathPoint(127.0430681, 37.5569076, 0);
        pathData.addPathPoint(127.0408010, 37.5577510, 0);
        pathData.addPathPoint(127.0414112, 37.5620135, 0);
        pathData.addPathPoint(127.0416603, 37.5653466, 0);
        pathData.addPathPoint(127.0425078, 37.5653466, NMapPathLineStyle.TYPE_DASH);
        pathData.addPathPoint(127.0427542, 37.5655640, 0);
        pathData.addPathPoint(127.0431163, 37.5658500, 0);
        pathData.endPathData();//경로 데이터 추가 종료

        //경로 데이터를 표시하는 오버레이 객체 생성
        NMapPathDataOverlay pathDataOverlay = mOverlayManager.createPathDataOverlay(pathData);
    }
}
