package com.example.admin.networking;

import com.example.admin.objects.*;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

import java.util.ArrayList;

public interface RetrofitService {


    @POST("/admin")
    Call<String> addAdmin(@Body Admin adminData);

    @POST("/admin/verify")
    Call<String> verifyUser(@Body RollNo rollNo);

    @POST("/rebate/verify")
    Call<String> verifyRebate(@Body Rebate rebate);

    @POST("/poll/{mess}")
    Call<String> makePoll(@Body Poll poll,@Path("mess") String mess);

    @POST("/add/menu/{mess}")
    Call<String> updateMenu(@Body MessMenu menu,@Path("mess") String mess);

    @GET("/login/{email}/{password}")
    Call<Admin> login(@Path("email")String email,@Path("password")String password);

    @GET("/feedbacks/{mess}")
    Call<ArrayList<Feedback>> getFeedback(@Path("mess")String mess);

    @GET("/rebate/{mess}")
    Call<ArrayList<Rebate>> getRebate(@Path("mess")String mess);

    @GET("/unverified/{mess}")
    Call<ArrayList<Student>> getUnverifiedStudents(@Path("mess") String mess);

    @GET("/menu/{mess}")
    Call<ArrayList<MessMenu>> getMenu(@Path("mess") String mess);
}