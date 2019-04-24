package com.example.admin.networking;

import com.example.admin.objects.Admin;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface RetrofitService {

//    @GET("{users_phone_no}/AUTOGEN")
//    Call<OTPMessageResponse> sentOTP(@Path("users_phone_no")String phone_no);

    @POST("/admin")
    Call<String> addAdmin(@Body Admin adminData);

    @GET("/login/{email}/{password}")
    Call<Admin> login(@Path("email")String email,@Path("password")String password);
}