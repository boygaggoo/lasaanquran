package com.lisanulquranapp.permissions;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

/**
 * Created by khalidz on 3/8/2018.
 */

public class PermissionUtility
{
    public static void askForStorageWritePermission(Activity activity)
    {
        if (ContextCompat.checkSelfPermission(activity.getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
        {
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE))
            {
                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PermissionsConstants.WRITE_EXTERNAL_STORAGE);
            }
            else
            {
                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PermissionsConstants.WRITE_EXTERNAL_STORAGE);
            }
        }
    }

    public static void askForStorageReadPermission(Activity activity)
    {
        if (ContextCompat.checkSelfPermission(activity.getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
        {
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.READ_EXTERNAL_STORAGE))
            {
                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PermissionsConstants.READ_EXTERNAL_STORAGE);
            }
            else
            {
                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PermissionsConstants.READ_EXTERNAL_STORAGE);
            }
        }
    }
    
    public static void askForStateReadPermission(Activity activity)
    {
        if (ContextCompat.checkSelfPermission(activity.getApplicationContext(), Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED)
        {
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.READ_PHONE_STATE))
            {
                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.READ_PHONE_STATE}, PermissionsConstants.READ_PHONE_STATE);
            }
            else
            {
                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.READ_PHONE_STATE}, PermissionsConstants.READ_PHONE_STATE);
            }
        }
    }

    public static void askForCameraPermission(Activity activity)
    {
        if (ContextCompat.checkSelfPermission(activity.getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
        {
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.CAMERA))
            {
                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.CAMERA}, PermissionsConstants.CAMERA);
            }
            else
            {
                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.CAMERA}, PermissionsConstants.CAMERA);
            }
        }
    }
    
    public static void askForSMSPermission(Activity activity)
    {
        if (ContextCompat.checkSelfPermission(activity.getApplicationContext(), Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED)
        {
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.SEND_SMS))
            {
                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.SEND_SMS}, PermissionsConstants.SEND_SMS);
            }
            else
            {
                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.SEND_SMS}, PermissionsConstants.SEND_SMS);
            }
        }
    }
    

    public static void askForLocationPermission(Activity activity)
    {
        if (ContextCompat.checkSelfPermission(activity.getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.ACCESS_FINE_LOCATION))
            {
                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PermissionsConstants.ACCESS_FINE_LOCATION);
            }
            else
            {
                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PermissionsConstants.ACCESS_FINE_LOCATION);
            }
        }
    }

    public static boolean isPermissionGranted(String permission, Activity activity)
    {
        return ContextCompat.checkSelfPermission(activity.getApplicationContext(), permission) == PackageManager.PERMISSION_GRANTED;
    }
    
    public static boolean isPermissionGranted(String permission, Context activity)
    {
        return ContextCompat.checkSelfPermission(activity.getApplicationContext(), permission) == PackageManager.PERMISSION_GRANTED;
    }

    public static void showPermissionDenialDialog(final String androidPermission, String permission, final int permissionRequestCode, final Activity context, final PermissionDenialListener permissionDenialListener)
    {
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(context);
        builder.setTitle(permission + " Denied!")
                .setMessage("This permission is required to provide the proper functionality to this application. Kindly allow this permission to make this application fully functional")
                .setPositiveButton("OK", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        if (ActivityCompat.shouldShowRequestPermissionRationale(context, androidPermission))
                        {
                            ActivityCompat.requestPermissions(context, new String[]{androidPermission}, permissionRequestCode);
                        }
                        else
                        {
                            Intent intent = new Intent();
                            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                            Uri uri = Uri.fromParts("package", context.getPackageName(), null);
                            intent.setData(uri);
                            context.startActivity(intent);
                        }
                    }
                })
                .setNegativeButton("CANCEL", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        dialog.dismiss();
                        if (permissionDenialListener != null)
                            permissionDenialListener.onPermissionDenied();
                    }
                }).setCancelable(false).create().show();
    }


}
