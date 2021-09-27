package com.example.challenge_farm_app.Models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class AllSolalat {


    public ArrayList<Solala> getSolalat() {
        return solalat;
    }

    public void setSolalat(ArrayList<Solala> solalat) {
        this.solalat = solalat;
    }

    @SerializedName("data")
    private ArrayList<Solala> solalat;
}


