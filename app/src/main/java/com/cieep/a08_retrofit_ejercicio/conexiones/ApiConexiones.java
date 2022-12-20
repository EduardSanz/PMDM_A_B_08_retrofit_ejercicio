package com.cieep.a08_retrofit_ejercicio.conexiones;

import com.cieep.a08_retrofit_ejercicio.modelos.Response;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiConexiones {

    @GET("/api/users?")
    Call<Response> getUsers(@Query("page") String pagina);

}
