package com.example.challenge_farm_app.Models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UsersList {
    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    @SerializedName("data")
    private List<User> users;
}
