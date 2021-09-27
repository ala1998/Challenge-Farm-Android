package com.example.challenge_farm_app.Network;

import com.example.challenge_farm_app.Models.AllFarms;
import com.example.challenge_farm_app.Models.AllJawda;
import com.example.challenge_farm_app.Models.AllSolalat;
import com.example.challenge_farm_app.Models.Animal;
import com.example.challenge_farm_app.Models.AnimalsList;
import com.example.challenge_farm_app.Models.JawdaReq;
import com.example.challenge_farm_app.Models.MasdarReq;
import com.example.challenge_farm_app.Models.SolalaReq;
import com.example.challenge_farm_app.Models.User;
import com.example.challenge_farm_app.Models.UsersList;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface GetDataService {
    @GET("/users/list/")
    Call<UsersList> getAllUsers();

    @GET("/animals/list/")
    Call<AnimalsList> getAllAnimals();

    @GET("animals/animalInfoByID/")
    Call<AnimalsList> getAnimalByID(@Query("id") int id);

    @GET("animals/farmByID/{id}")
    Call<MasdarReq> getFarmByID(@Path("id") int id);

    @GET("animals/solalaByID/{id}")
    Call<SolalaReq> getSolalaByID(@Path("id") int id);

    @GET("animals/jawdaByID/{id}")
    Call<JawdaReq> getJawdaByID(@Path("id") int id);

    @GET("animals/farms/")
    Call<AllFarms> getFarms();

    @GET("animals/jawda/")
    Call<AllJawda> getAllJawda();

    @GET("animals/solalat/")
    Call<AllSolalat> getSolalat();
}
