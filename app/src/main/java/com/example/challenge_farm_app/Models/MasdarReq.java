package com.example.challenge_farm_app.Models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class MasdarReq {


    @SerializedName("data")
    private ArrayList<Masdar> masdarName;

    public ArrayList<Masdar> getMasdarName() {
        return masdarName;
    }

    public void setMasdarName(ArrayList<Masdar> masdarName) {
        this.masdarName = masdarName;
    }
}
