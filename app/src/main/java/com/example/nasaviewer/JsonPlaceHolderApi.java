package com.example.nasaviewer;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface JsonPlaceHolderApi {

    @GET("apod")
    Call<info> getinfo(@Query("api_key") String api_key, @Query("date") String date);

   // Call<info> getinfo(String api_key, String date);
}
