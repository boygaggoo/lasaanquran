package com.lisanulquranapp.retrofit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data
{
    @SerializedName("imei_number")
    @Expose
    private String imeiNumber;
    @SerializedName("fcm_token")
    @Expose
    private String fcmToken;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    
    public String getImeiNumber() {
        return imeiNumber;
    }
    
    public void setImeiNumber(String imeiNumber) {
        this.imeiNumber = imeiNumber;
    }
    
    public String getFcmToken() {
        return fcmToken;
    }
    
    public void setFcmToken(String fcmToken) {
        this.fcmToken = fcmToken;
    }
    
    public String getUpdatedAt() {
        return updatedAt;
    }
    
    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }
    
    public String getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}
