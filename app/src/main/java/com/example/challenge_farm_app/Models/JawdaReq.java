package com.example.challenge_farm_app.Models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class JawdaReq {

    public ArrayList<Masdar> getJawdaName() {
        return jawdaName;
    }

    public void setJawdaName(ArrayList<Masdar> jawdaName) {
        this.jawdaName = jawdaName;
    }

    @SerializedName("data")
    private ArrayList<Masdar> jawdaName;
}
