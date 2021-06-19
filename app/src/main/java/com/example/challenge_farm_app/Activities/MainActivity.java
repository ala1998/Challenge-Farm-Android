package com.example.challenge_farm_app.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.challenge_farm_app.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void goToAnimals(View view) {
        String ip = getIntent().getStringExtra("IP");
        Intent intent = new Intent(MainActivity.this, AnimalsActivity.class);
        intent.putExtra("IP", ip);
        startActivity(intent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

    }
}