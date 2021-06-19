package com.example.challenge_farm_app.Models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class SolalaReq {

    public ArrayList<Masdar> getSolalaName() {
        return solalaName;
    }

    public void setSolalaName(ArrayList<Masdar> solalaName) {
        this.solalaName = solalaName;
    }

    @SerializedName("data")
    private ArrayList<Masdar> solalaName;
}
