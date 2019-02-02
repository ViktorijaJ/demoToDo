package com.example.demotodo.model;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ItemsRepository {
    //get
    @GET("/")
    Call<List<ItemVO>> getAll();

    //post
    @POST("/")
    Call<ItemVO> post(@Body ItemVO itemVO);

    //put
    @PUT("/")
    Call<ItemVO> put(@Body ItemVO itemVO);

    //delete
    @DELETE("/{id}")
    Call<ResponseBody> delete(@Path("id") long id);
}
