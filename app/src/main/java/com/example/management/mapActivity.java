package com.example.management;

import android.os.Bundle;
import android.widget.LinearLayout;
import com.nhn.android.maps.NMapActivity;
import com.nhn.android.maps.NMapView;

/**
 * Created by Chaewon on 2017-05-18.
 */

public class mapActivity extends NMapActivity {
    private NMapView mMapView;
    private final String CLIENT_ID = "HV8X2Nc9rkckC89MJH61";
    //API 키 재발급 받아야함.
    private LinearLayout MapContainer;

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

    }
}