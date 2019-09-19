package com.tik.xpermission;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;

/**
 * @author tik
 **/
public class XPermission {
    private PermissionCallback callback;
    private String[] permissions;
    private Context context;

    public static XPermission with(Context context) {
        XPermission permission = Holder.INSTANCE;
        permission.context = context;
        return permission;
    }

    public XPermission permissions(String[] permissions) {
        this.permissions = permissions;
        return this;
    }

    public XPermission callback(PermissionCallback callback) {
        this.callback = callback;
        return this;
    }

    public void request() {
        if (permissions == null || permissions.length == 0) {
            return;
        }
        PermissionActivity.starter(context, permissions, callback);
    }

    public static void showRationaleDialog(Context context, String message) {
        new AlertDialog.Builder(context)
                .setTitle(R.string.permission_title)
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton(R.string.confirm, null)
                .show();
    }

    public static void showRejectDialog(final Context context, String message) {
        new AlertDialog.Builder(context)
                .setTitle(R.string.permission_title)
                .setMessage(message)
                .setPositiveButton(R.string.to_setting, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startSettingsActivity(context);
                    }
                })
                .setNegativeButton(R.string.cancel, null)
                .show();
    }

    /**
     * Jump to Settings page of your application
     * @param context
     */
    public static void startSettingsActivity(Context context) {
        Uri packageURI = Uri.parse("package:" + context.getPackageName());
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, packageURI);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    private static class Holder {
        private static XPermission INSTANCE = new XPermission();
    }
}
