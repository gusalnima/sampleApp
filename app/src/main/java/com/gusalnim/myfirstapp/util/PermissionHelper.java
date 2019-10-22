package com.gusalnim.myfirstapp.util;

import android.app.Activity;
import android.content.pm.PackageManager;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

/**
 * Created by gusalnim on 26/04/2019.
 */
public class PermissionHelper {
    public static boolean checkPermission(final Activity activity, final int requestCode, final String... permissions) {
        int count = 0;
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(activity, permission) == PackageManager.PERMISSION_GRANTED) count++;
        }

        if (count == permissions.length) return true;

        ActivityCompat.requestPermissions(activity, permissions, requestCode);
        return false;
    }

    public interface onPermissionListener {
        void onPermission(String... permissions);

        void onPermissionDenied(String... permissions);

        void onPermissionReject(String... permissions);
    }
}
