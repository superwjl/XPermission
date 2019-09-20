package com.tik.demo.xpermission;

import android.Manifest;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.tik.xpermission.PermissionCallback;
import com.tik.xpermission.XPermission;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void locationPermission(final View view) {
        XPermission.with(this)
                .permissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION})
                .callback(new PermissionCallback() {
                    @Override
                    public void onPermissionGranted() {
                        Log.i("xx", "onPermissionGranted: " + Manifest.permission.ACCESS_FINE_LOCATION);
                    }

                    @Override
                    public void shouldShowRational(String permission) {
                        XPermission.showRationaleDialog(MainActivity.this, getString(R.string.location_permission_rational));
                    }

                    @Override
                    public void onPermissionReject(String permission) {
                        XPermission.showRejectDialog(MainActivity.this, getString(R.string.location_permission_reject));
                    }
                })
                .request();

    }


    public void storagePermission(View view) {
        XPermission.with(this)
                .permissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
                .callback(new PermissionCallback() {
                    @Override
                    public void onPermissionGranted() {
                        Log.i("xx", "onPermissionGranted: " + Manifest.permission.READ_EXTERNAL_STORAGE + " | " + Manifest.permission.WRITE_EXTERNAL_STORAGE);
                    }

                    @Override
                    public void shouldShowRational(String permission) {
                        XPermission.showRationaleDialog(MainActivity.this, getString(R.string.storage_permission_rational));
                    }

                    @Override
                    public void onPermissionReject(String permission) {
                        XPermission.showRejectDialog(MainActivity.this, getString(R.string.storage_permission_reject));
                    }
                })
                .request();
    }
}
