package com.tik.xpermission;

/**
 * 权限申请回调
 * @author tik
 **/
public interface PermissionCallback {
    /** 权限申请通过 */
    void onPermissionGranted();
    /** 权限申请本次被拒绝，没有勾选不再提醒 */
    void shouldShowRational(String permission);
    /** 权限申请彻底被拒绝，勾选了不再提醒 */
    void onPermissionReject(String permission);
}
