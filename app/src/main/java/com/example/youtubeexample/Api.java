package com.example.youtubeexample;


import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Api {



    @GET("country.php")
    Call<List<youtube>> getdata(@Query("D_ID") String searchQuery, @Query("v")String b);

//    @GET("delete_data.php")
//    Call<List<Profile>> delete_data(@Query("D_ID") String searchQuery);
//
//    @GET("get_all_mod.php")
//    Call<List<ModModel>> getAllMods(@Query("v") String str4);
//
//    @Multipart
//    @POST("image_upload.php")
//    Call<FileModel> callUploadApi(@Part MultipartBody.Part images, @Part("D_ID") RequestBody serial_wise_img);



}
