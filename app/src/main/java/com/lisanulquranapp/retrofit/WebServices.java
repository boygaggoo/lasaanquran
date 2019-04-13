package com.lisanulquranapp.retrofit;


import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by khalidz on 1/30/2018.
 */

public interface WebServices
{
    @POST("device")
    @FormUrlEncoded
    Call<ResponseModel> sendFirebaseToken(@Field("imei_number") String imei_number, @Field("fcm_token") String fcm_token);
}
