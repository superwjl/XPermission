package com.tik.xpermission;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * @author tik
 **/
public class PermissionActivity extends AppCompatActivity {

    public static final String PERMISSIONS_KEY = "permissions";
    private static final int PERMISSION_REQUEST_CODE = 1;
    private static PermissionCallback callback;

    public static void starter(Context context, String[] permissions, PermissionCallback callBack) {
        callback = callBack;
        Intent intent = new Intent(context, PermissionActivity.class);
        intent.putExtra(PERMISSIONS_KEY, permissions);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!getIntent().hasExtra(PERMISSIONS_KEY)) {
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            String[] permissions = getIntent().getStringArrayExtra(PERMISSIONS_KEY);
            requestPermissions(permissions, PERMISSION_REQUEST_CODE);
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode != PERMISSION_REQUEST_CODE) {
            return;
        }
        if (null == callback) {
            return;
        }
        int length = permissions.length;
        int granted = 0;
        for (int i = 0; i < length; i++) {
            if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                granted++;
            } else {
                if (shouldShowRequestPermissionRationale(permissions[i])) {
                    callback.shouldShowRational(permissions[i]);
                } else {
                    callback.onPermissionReject(permissions[i]);
                }
            }
        }
        if (granted == length) {
            callback.onPermissionGranted();
        }
        finish();

    }
}
