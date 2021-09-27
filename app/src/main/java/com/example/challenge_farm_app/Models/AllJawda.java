package com.example.challenge_farm_app.Models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class AllJawda {


    public ArrayList<Jawda> getAllJawda() {
        return allJawda;
    }

    public void setAllJawda(ArrayList<Jawda> allJawda) {
        this.allJawda = allJawda;
    }

    @SerializedName("data")
    private ArrayList<Jawda> allJawda;
}
