package com.tik.xpermission;

import android.app.Service;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

/**
 * @author tik
 **/
@Aspect
public class PermissionAspect {

    @Around("execution(@com.tik.xpermission.NeedPermission * *(..))")
    public void aroundJoinPoint(final ProceedingJoinPoint joinPoint) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        NeedPermission annotation = method.getAnnotation(NeedPermission.class);
        final String[] permissions = annotation.permissions();
        final int[] requestCodes = annotation.requestCodes();

        final List<String> permissionList = Arrays.asList(permissions);
        Object object = joinPoint.getThis();
        Context context = null;
        if (object instanceof FragmentActivity) {
            context = (FragmentActivity) object;
        } else if (object instanceof Fragment) {
            context = ((Fragment) object).getContext();
        } else if (object instanceof Service) {
            context = (Service) object;
        }

        XPermission.with(context)
                .permissions(permissions)
                .callback(new PermissionCallback() {
                    @Override
                    public void onPermissionGranted() {
                        try {
                            joinPoint.proceed();
                        } catch (Throwable throwable) {
                            throwable.printStackTrace();
                        }
                    }

                    @Override
                    public void shouldShowRational(String permission) {
                        int index = permissionList.indexOf(permission);
                        int requestCode = -1;
                        if (index < requestCodes.length) {
                            requestCode = requestCodes[index];
                        }
                        XPermission.getGlobalPermissionCallback().shouldShowRational(permission, requestCode);
                    }

                    @Override
                    public void onPermissionReject(String permission) {
                        int index = permissionList.indexOf(permission);
                        int requestCode = -1;
                        if (index < requestCodes.length) {
                            requestCode = requestCodes[index];
                        }
                        XPermission.getGlobalPermissionCallback().onPermissionReject(permission, requestCode);
                    }
                })
                .request();
    }

}
