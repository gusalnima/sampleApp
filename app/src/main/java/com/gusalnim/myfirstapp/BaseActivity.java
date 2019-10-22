package com.gusalnim.myfirstapp;

import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.gusalnim.myfirstapp.util.PermissionHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gusalnim on 26/04/2019.
 */

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void finish() {
        super.finish();
    }

    private static final int PERMISSION_REQUEST = 1024;

    private PermissionHelper.onPermissionListener onPermissionListener;

    public final void checkPermission(PermissionHelper.onPermissionListener onPermissionListener, String... permission) {
        this.onPermissionListener = onPermissionListener;
        if (null == onPermissionListener) return;

        if (PermissionHelper.checkPermission(this, PERMISSION_REQUEST, permission)) {
            onPermissionListener.onPermission(permission);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        if (null == onPermissionListener) return;

        if (requestCode == PERMISSION_REQUEST) {
            List<String> successList = new ArrayList<>();
            List<String> failList = new ArrayList<>();
            for (int index = 0; index < permissions.length; index++) {
                if (grantResults[index] == PackageManager.PERMISSION_GRANTED) successList.add(permissions[index]);
                else failList.add(permissions[index]);
            }

            if (grantResults.length == successList.size()) {
                onPermissionListener.onPermission(permissions);
            } else {
                if (!successList.isEmpty()) {
                    onPermissionListener.onPermission(successList.toArray(new String[successList.size()]));
                }

                List<String> deniedList = new ArrayList<>();
                List<String> rejectList = new ArrayList<>();
                for (String permission : failList) {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) deniedList.add(permission);
                    else rejectList.add(permission);
                }

                if (!deniedList.isEmpty()) onPermissionListener.onPermissionDenied(deniedList.toArray(new String[deniedList.size()]));
                if (!rejectList.isEmpty()) onPermissionListener.onPermissionReject(rejectList.toArray(new String[rejectList.size()]));
            }
        }
    }
}
