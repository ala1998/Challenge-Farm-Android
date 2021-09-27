package com.example.challenge_farm_app.Models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

    public class AllFarms {


        public ArrayList<Masdar> getFarms() {
            return farms;
        }

        public void setFarms(ArrayList<Masdar> farms) {
            this.farms = farms;
        }

        @SerializedName("data")
        private ArrayList<Masdar> farms;
    }


