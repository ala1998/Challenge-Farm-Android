package com.example.challenge_farm_app.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.example.challenge_farm_app.Activities.AnimalsActivity;
import com.example.challenge_farm_app.R;
import com.example.challenge_farm_app.Models.Animal;
import com.example.challenge_farm_app.Models.AnimalsList;
import com.example.challenge_farm_app.Models.UsersList;
import com.example.challenge_farm_app.Network.GetDataService;
import com.example.challenge_farm_app.Network.RetrofitClientInstance;
import com.example.challenge_farm_app.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InfoActivity extends AppCompatActivity {

    private TextView farmNoTV, animalName, idTV, barcodeTV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        getSupportActionBar().hide();
        int pos = getIntent().getIntExtra("POSITION",1);
      //  ArrayList<Animal> animals = getIntent().getParcelableArrayListExtra("ANIMALS_LIST");

    //    int id = animals.get(pos).getId();
       // Toast.makeText(this,""+id,Toast.LENGTH_LONG).show();
        if(AnimalsActivity.animalArrayList != null) {
            animalName = findViewById(R.id.animalName);
            idTV = findViewById(R.id.id);
            barcodeTV = findViewById(R.id.barcode);
            farmNoTV = findViewById(R.id.farmNo);
            animalName.setText( "الحيوان " +"(" + AnimalsActivity.animalArrayList.get(pos).getName() + ")");
            idTV.setText(AnimalsActivity.animalArrayList.get(pos).getId().toString());
            barcodeTV.setText(AnimalsActivity.animalArrayList.get(pos).getParcode());
            farmNoTV.setText(AnimalsActivity.animalArrayList.get(pos).getFarm_number());
           // infoTV.setText(AnimalsActivity.animalArrayList.get(pos).toString());
         //   Toast.makeText(this, AnimalsActivity.animalArrayList.get(pos).getId() + "", Toast.LENGTH_LONG).show();
        }
       /* String ip = "10.0.2.2";
        final GetDataService service = RetrofitClientInstance.getRetrofitInstance(ip).create(GetDataService.class);
        Call<AnimalsList> animalCall = service.getAnimalByID(id);
        animalCall.enqueue(new Callback<AnimalsList>() {
            @Override
            public void onResponse(Call<AnimalsList> call, Response<AnimalsList> response) {
                AnimalsList animal = response.body();
            Toast.makeText(InfoActivity.this, animal.getAnimals().get(0).getDate_of_birth(),Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<AnimalsList> call, Throwable t) {

            }
        });*/
    }
}