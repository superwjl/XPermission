package com.tik.demo.xpermission;

import android.Manifest;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.tik.xpermission.GlobalPermissionCallback;
import com.tik.xpermission.NeedPermission;
import com.tik.xpermission.XPermission;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        XPermission.initGlobalCallback(new GlobalPermissionCallback() {
            @Override
            public void shouldShowRational(String permission, int requestCode) {
                if (requestCode == Permissions.LOCATION_REQUEST) {
                    XPermission.showRationaleDialog(MainActivity.this, getString(R.string.location_permission_rational));
                } else if (requestCode == Permissions.READ_EXTERNAL_STORAGE_REQUEST) {
                    XPermission.showRationaleDialog(MainActivity.this, getString(R.string.storage_permission_rational));
                }
            }

            @Override
            public void onPermissionReject(String permission, int requestCode) {
                if (requestCode == Permissions.LOCATION_REQUEST) {
                    XPermission.showRejectDialog(MainActivity.this, getString(R.string.location_permission_reject));
                } else if (requestCode == Permissions.READ_EXTERNAL_STORAGE_REQUEST) {
                    XPermission.showRejectDialog(MainActivity.this, getString(R.string.storage_permission_reject));
                }
            }
        });
    }

    @NeedPermission(permissions = {Manifest.permission.ACCESS_FINE_LOCATION},
            requestCodes = {Permissions.LOCATION_REQUEST})
    public void locationPermission(final View view) {
//        XPermission.with(this)
//                .permissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION})
//                .callback(new PermissionCallback() {
//                    @Override
//                    public void onPermissionGranted() {
//                        Log.i("xx", "onPermissionGranted: " + Manifest.permission.ACCESS_FINE_LOCATION);
//                    }
//
//                    @Override
//                    public void shouldShowRational(String permission) {
//                        XPermission.showRationaleDialog(MainActivity.this, getString(R.string.location_permission_rational));
//                    }
//
//                    @Override
//                    public void onPermissionReject(String permission) {
//                        XPermission.showRejectDialog(MainActivity.this, getString(R.string.location_permission_reject));
//                    }
//                })
//                .request();

    }


    @NeedPermission(permissions = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
            requestCodes = {Permissions.READ_EXTERNAL_STORAGE_REQUEST})
    public void storagePermission(View view) {
//        XPermission.with(this)
//                .permissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
//                .callback(new PermissionCallback() {
//                    @Override
//                    public void onPermissionGranted() {
//                        Log.i("xx", "onPermissionGranted: " + Manifest.permission.READ_EXTERNAL_STORAGE + " | " + Manifest.permission.WRITE_EXTERNAL_STORAGE);
//                    }
//
//                    @Override
//                    public void shouldShowRational(String permission) {
//                        XPermission.showRationaleDialog(MainActivity.this, getString(R.string.storage_permission_rational));
//                    }
//
//                    @Override
//                    public void onPermissionReject(String permission) {
//                        XPermission.showRejectDialog(MainActivity.this, getString(R.string.storage_permission_reject));
//                    }
//                })
//                .request();
    }
}
