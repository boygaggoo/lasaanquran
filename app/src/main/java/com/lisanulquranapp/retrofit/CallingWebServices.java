package com.lisanulquranapp.retrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by ZeeZee on 11/16/2017.
 */

public class CallingWebServices
{
    Retrofit retrofit;
    WebServices webServices;
    Gson gson;

    public static final String BASE_URL = "http://portal.lisanulquranapp.com/api/sync/";
    
//    public static final String BASE_URL = "http://192.168.43.147/FastBBQ/";
    private static CallingWebServices callingWebServices;

    public static CallingWebServices getInstance()
    {
        if (callingWebServices == null)
        {
            callingWebServices = new CallingWebServices();
        }
        return callingWebServices;
    }

    private CallingWebServices()
    {
        initRetrofit();
    }

    private void initRetrofit()
    {

        OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder();
        okHttpClient.readTimeout(15, TimeUnit.SECONDS);
        okHttpClient.connectTimeout(10, TimeUnit.SECONDS);

        gson = new GsonBuilder().setLenient().create();
        retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create(gson)).client(okHttpClient.build()).build();
        webServices = retrofit.create(WebServices.class);
    }
    
    public void sendFirebaseToken(String imeiNumber, String firebaseToken, final ServiceResponse serviceResponse)
    {
        Call<ResponseModel> sendFirebaseToken = webServices.sendFirebaseToken(imeiNumber, firebaseToken);
        sendFirebaseToken.enqueue(new Callback<ResponseModel>()
        {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response)
            {
                if(response.code() == 200 || response.code() == 201)
                {
                    serviceResponse.onSuccess(response.body());
                }
                else
                {
                    serviceResponse.onFail("Failed to send firebase token");
                }
            }
    
            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t)
            {
                serviceResponse.onError("Server not responding");
            }
        });
    }
}
