package com.pacosignes.projecte.Api;


import okhttp3.ResponseBody;
import retrofit2.Call;

import retrofit2.http.GET;

import retrofit2.http.Path;

public interface APIService {


    @GET("/ApiRest/data/articulos")
    Call<ResponseBody> getArticulos();

    @GET("/ApiRest/data/articulos/{ref}")
    Call<ResponseBody> getArticuloReferencia(@Path("ref") String ref);

    @GET("/ApiRest/data/articulos/imagenes/{ref}")
    Call<ResponseBody> getImageArticulo(@Path("ref") String ref);

}
