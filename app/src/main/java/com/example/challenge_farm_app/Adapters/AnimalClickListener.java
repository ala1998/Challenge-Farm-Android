package com.example.challenge_farm_app.Adapters;

import android.view.View;

public interface AnimalClickListener<T> {

    void onClick(View view, T data, int position);

}
