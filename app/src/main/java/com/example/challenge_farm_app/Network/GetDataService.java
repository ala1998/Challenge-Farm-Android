package com.example.challenge_farm_app.Network;

import com.example.challenge_farm_app.Models.Animal;
import com.example.challenge_farm_app.Models.AnimalsList;
import com.example.challenge_farm_app.Models.User;
import com.example.challenge_farm_app.Models.UsersList;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface GetDataService {
    @GET("/users/list/")
    Call<UsersList> getAllUsers();
    @GET("/animals/list/")
    Call<AnimalsList> getAllAnimals();
    @POST("animals/animalInfoByID/")
    Call<AnimalsList> getAnimalByID(@Query("id") int id);

}
