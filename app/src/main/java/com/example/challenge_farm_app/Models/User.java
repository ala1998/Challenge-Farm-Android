package com.example.challenge_farm_app.Models;

import com.google.gson.annotations.SerializedName;

public class User {
    @SerializedName("id")
    private Integer id;
    @SerializedName("username")
    private String username;
    @SerializedName("password")
    private String password;

    public User(Integer id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
