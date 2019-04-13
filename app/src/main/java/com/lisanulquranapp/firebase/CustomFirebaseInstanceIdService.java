package com.lisanulquranapp.firebase;


import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.lisanulquranapp.permissions.PermissionUtility;
import com.lisanulquranapp.retrofit.CallingWebServices;
import com.lisanulquranapp.retrofit.ServiceResponse;

/**
 * Created by ZeeZee on 11/5/2017.
 */

public class CustomFirebaseInstanceIdService extends FirebaseInstanceIdService
{
    @Override
    public void onTokenRefresh()
    {
        if (PermissionUtility.isPermissionGranted(Manifest.permission.READ_PHONE_STATE, getApplication()))
        {
            String refreshedToken = FirebaseInstanceId.getInstance().getToken();
            TelephonyManager tel = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED)
            {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            insertFirebaseToken(tel.getDeviceId(), refreshedToken);
        }
       /* // Get updated InstanceID token.
        if (PreferenceUtility.getPreferenceUtility().isAdmin())
        {
            String refreshedToken = FirebaseInstanceId.getInstance().getToken();
            if(!TextUtils.equals(refreshedToken, PreferenceUtility.getPreferenceUtility().getStringPreference(Constants.LAST_SAVED_TOKEN)))
            {
                PreferenceUtility.getPreferenceUtility().setPreference(Constants.LAST_SAVED_TOKEN, refreshedToken);
                insertFirebaseToken(refreshedToken);
            }
        }*/
        
        
    }
    
    private void insertFirebaseToken(String imeiNumber, String refreshedToken)
    {
        CallingWebServices.getInstance().sendFirebaseToken("", refreshedToken, new ServiceResponse()
        {
            @Override
            public void onSuccess(Object object)
            {
            
            }
            
            @Override
            public void onFail(Object object)
            {
            
            }
            
            @Override
            public void onError(Object object)
            {
            
            }
        });
    }
    
    
}
