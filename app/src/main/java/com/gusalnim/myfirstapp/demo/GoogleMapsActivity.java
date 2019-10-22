package com.gusalnim.myfirstapp.demo;

import android.Manifest;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.gusalnim.myfirstapp.BaseActivity;
import com.gusalnim.myfirstapp.R;
import com.gusalnim.myfirstapp.util.AlertHelper;
import com.gusalnim.myfirstapp.util.PermissionHelper;

import java.io.File;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

/**
 * Created by gusalnim on 21/10/2019.
 */
public class GoogleMapsActivity extends BaseActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    private RelativeLayout hiddenPanel; // 하단 메뉴

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        checkPermission(new PermissionHelper.onPermissionListener() {
            @Override
            public void onPermission(String... permission) {
                setContentView(R.layout.activity_google_maps);

                SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.map);
                mapFragment.getMapAsync(GoogleMapsActivity.this);
            }

            @Override
            public void onPermissionDenied(String... permission) {
                finish();
            }

            @Override
            public void onPermissionReject(String... permission) {
                AlertHelper.showMessage(
                        GoogleMapsActivity.this,
                        null,
                        String.format(getString(R.string.permission_reject), "위치 서비스", "위치 서비스"),
                        new AlertHelper.Button(ContextCompat.getColor(GoogleMapsActivity.this, R.color.colorPrimary), getString(R.string.label_enter), new AlertHelper.OnClickListener() {
                            @Override
                            public void onClick(AlertDialog alertDialog) {
                                alertDialog.cancel();
                                finish();
                            }
                        })
                );
            }
        }, Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION);

        hiddenPanel = findViewById(R.id.hidden_panel);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;

        LatLng SEOUL = new LatLng(37.56, 126.97);

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(SEOUL);
        markerOptions.title("서울");
        markerOptions.snippet("한국의 수도");
        mMap.addMarker(markerOptions);

        mMap.moveCamera(CameraUpdateFactory.newLatLng(SEOUL));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(10));
    }

    // 하단 메뉴 슬라이드
    public void slideUpDown(final View view) {
        if (!isPanelShown()) {
            // Show the panel
            Animation bottomUp = AnimationUtils.loadAnimation(this,
                    R.anim.bottom_up);

            hiddenPanel.startAnimation(bottomUp);
            hiddenPanel.setVisibility(View.VISIBLE);
        }
        else {
            // Hide the Panel
            Animation bottomDown = AnimationUtils.loadAnimation(this,
                    R.anim.bottom_down);

            hiddenPanel.startAnimation(bottomDown);
            hiddenPanel.setVisibility(View.GONE);
        }
    }

    private boolean isPanelShown() {
        return hiddenPanel.getVisibility() == View.VISIBLE;
    }
}
