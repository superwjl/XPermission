package com.tik.xpermission;

public abstract class GlobalPermissionCallback {
        public abstract void shouldShowRational(String permission, int requestCode);
        public abstract void onPermissionReject(String permission, int requestCode);
    }