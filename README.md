# XPermission

1. 定义权限请求回调
```java
public interface PermissionCallback {
    /** 权限申请通过 */
    void onPermissionGranted();
    /** 权限申请本次被拒绝，没有勾选不再提醒 */
    void shouldShowRational(String permission);
    /** 权限申请彻底被拒绝，勾选了不再提醒 */
    void onPermissionReject(String permission);
}
```

2. 创建请求权限透明页PermissionActivity
```java
public class PermissionActivity extends AppCompatActivity {
    public static final String PERMISSIONS_KEY = "permissions";
    private static final int PERMISSION_REQUEST_CODE = 1;
    private static PermissionCallback callback;
}
```
在清单文件中声明页面主题为透明
```xml
<activity android:name="com.tik.xpermission.PermissionActivity"
            android:theme="@style/Translucent"/>
```
添加跳转入口，传入上下文、权限数组、回调
```java
public static void starter(Context context, String[] permissions, PermissionCallback callBack) {
    callback = callBack;
    Intent intent = new Intent(context, PermissionActivity.class);
    intent.putExtra(PERMISSIONS_KEY, permissions);
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    context.startActivity(intent);
}
```
在onCreate方法中，发起权限请求
```java
if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            String[] permissions = getIntent().getStringArrayExtra(PERMISSIONS_KEY);
            requestPermissions(permissions, PERMISSION_REQUEST_CODE);
}
```
处理请求结果
```java
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
```
3. 定义对外暴露的工具类XPermission
```java
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
```
4. 调用示例：
```java
XPermission.with(this)
        .permissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION})
        .callback(new PermissionCallback() {
            @Override
            public void onPermissionGranted() {}

            @Override
            public void shouldShowRational(String permission) {}

            @Override
            public void onPermissionReject(String permission) {}
        })
        .request();
```
