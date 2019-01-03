package com.lisanulquranapp.retrofit;

/**
 * Created by ZeeZee on 11/16/2017.
 */

public interface ServiceResponse
{
    void onSuccess(Object object);
    void onFail(Object object);
    void onError(Object object);
}
