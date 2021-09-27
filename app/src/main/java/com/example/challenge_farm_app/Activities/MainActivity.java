package com.example.challenge_farm_app.Activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.challenge_farm_app.Adapters.RecyclerviewItemAdapter;
import com.example.challenge_farm_app.Models.AllFarms;
import com.example.challenge_farm_app.Models.AllJawda;
import com.example.challenge_farm_app.Models.AllSolalat;
import com.example.challenge_farm_app.Models.Animal;
import com.example.challenge_farm_app.Models.AnimalsList;
import com.example.challenge_farm_app.Models.Jawda;
import com.example.challenge_farm_app.Models.JawdaReq;
import com.example.challenge_farm_app.Models.Masdar;
import com.example.challenge_farm_app.Models.MasdarReq;
import com.example.challenge_farm_app.Models.Solala;
import com.example.challenge_farm_app.Models.SolalaReq;
import com.example.challenge_farm_app.Network.GetDataService;
import com.example.challenge_farm_app.Network.RetrofitClientInstance;
import com.example.challenge_farm_app.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;

import pl.droidsonroids.gif.GifImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    String ip;
    public static ArrayList<Animal> animalArrayList;
    String JSONPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath();
    Button animalsCard;
    GetDataService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        activity = (Activity) this;

        animalsCard = findViewById(R.id.animals_card);
        File file = new File(JSONPath + "/animals.json");
        if (file.exists()) {
            animalsCard.setEnabled(true);
            animalsCard.setAlpha(1.0f);
            animalsCard.setClickable(true);
            //   Log.d("json file", "Yessssssssssss");
        }
        ip = getIntent().getStringExtra("IP");
        service = RetrofitClientInstance.getRetrofitInstance(ip).create(GetDataService.class);

        animalsCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToAnimals();
            }
        });

    }

    public void goToAnimals() {
        Intent intent = new Intent(MainActivity.this, AnimalsActivity.class);
        intent.putExtra("IP", ip);
        startActivity(intent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

    }


    public void fetchAnimals(View view) {
        fetchAnimalsFromServer();
    }

    private void fetchAnimalsFromServer() {

        Call<AnimalsList> animalsCall = service.getAllAnimals();
        animalsCall.enqueue(new Callback<AnimalsList>() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
            @Override
            public void onResponse(Call<AnimalsList> call, Response<AnimalsList> response) {
                AnimalsList animals = response.body();
                animalArrayList = animals.getAnimals();
                if (animalArrayList != null) {
                    try {
                        writeFileOnInternalStorage("animals.json", prepareAnimalsJSON(animalArrayList));
                        fetchFarmsSolalatJawda();
                        animalsCard.setEnabled(true);
                        animalsCard.setAlpha(1.0f);
                        animalsCard.setClickable(true);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    //goToAnimals();

                }
                /*else
                    showGIF();*/
            }

            @Override
            public void onFailure(Call<AnimalsList> call, Throwable t) {
                Log.d("ANIMALS", t.getLocalizedMessage());
                Toast.makeText(MainActivity.this, "يرجى التأكد من تشغيل السيرفر!", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void fetchFarmsSolalatJawda() {
        Call<AllFarms> farmsCall = service.getFarms();
        farmsCall.enqueue(new Callback<AllFarms>() {
            @Override
            public void onResponse(Call<AllFarms> call, Response<AllFarms> response) {
                AllFarms farms = response.body();
                if (farms.getFarms() != null) {
                    try {
                        writeFileOnInternalStorage("farms.json", prepareJSON(farms.getFarms(), null, null));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }

            @Override
            public void onFailure(Call<AllFarms> call, Throwable t) {
                Log.d("FARMS", t.getLocalizedMessage());

                Toast.makeText(MainActivity.this, "يرجى التأكد من تشغيل السيرفر!", Toast.LENGTH_LONG).show();
            }
        });

        Call<AllSolalat> solalatCall = service.getSolalat();
        solalatCall.enqueue(new Callback<AllSolalat>() {
            @Override
            public void onResponse(Call<AllSolalat> call, Response<AllSolalat> response) {
                AllSolalat solalat = response.body();
                if (solalat.getSolalat() != null) {
                    try {
                        writeFileOnInternalStorage("solalat.json", prepareJSON(null, null, solalat.getSolalat()));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }

            @Override
            public void onFailure(Call<AllSolalat> call, Throwable t) {
                Log.d("SOLALAT", t.getLocalizedMessage());

                Toast.makeText(MainActivity.this, "يرجى التأكد من تشغيل السيرفر!", Toast.LENGTH_LONG).show();
            }
        });

        Call<AllJawda> allJawdaCall = service.getAllJawda();
        allJawdaCall.enqueue(new Callback<AllJawda>() {
            @Override
            public void onResponse(Call<AllJawda> call, Response<AllJawda> response) {
                AllJawda jawdas = response.body();
                if (jawdas.getAllJawda() != null) {
                    try {
                        writeFileOnInternalStorage("jawda.json", prepareJSON(null, jawdas.getAllJawda(), null));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }

            public void onFailure(Call<AllJawda> call, Throwable t) {
                Log.d("JAWDAS", t.getLocalizedMessage());

                Toast.makeText(MainActivity.this, "يرجى التأكد من تشغيل السيرفر!", Toast.LENGTH_LONG).show();
            }
        });

    }

    private String prepareJSON(ArrayList<Masdar> farms, ArrayList<Jawda> jawdas, ArrayList<Solala> solalat) throws JSONException {

        JSONObject result = new JSONObject();

        try {
            result.put("status", 200);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JSONArray jsonArray = new JSONArray();

        if (farms != null)
            for (int i = 0; i < farms.size(); i++) {
                JSONObject farm = new JSONObject();
                farm.put("id", farms.get(i).getId());
                farm.put("name", farms.get(i).getName());
                jsonArray.put(farm);
            }
        else if (jawdas != null)
            for (int i = 0; i < jawdas.size(); i++) {
                JSONObject jawda = new JSONObject();
                jawda.put("id", jawdas.get(i).getId());
                jawda.put("name", jawdas.get(i).getName());
                jsonArray.put(jawda);
            }
        else if (solalat != null)
            for (int i = 0; i < solalat.size(); i++) {
                JSONObject solala = new JSONObject();
                solala.put("id", solalat.get(i).getId());
                solala.put("name", solalat.get(i).getName());
                jsonArray.put(solala);
            }
        result.put("data", jsonArray);

//        Log.d("PREPARE_JSON", result.toString());
//        Toast.makeText(AnimalsActivity.this, result.toString(), Toast.LENGTH_LONG).show();
        return result.toString();
    }

    public void writeFileOnInternalStorage(String sFileName, String sBody) {
        File dir = new File(JSONPath);
//        File dir = new File(String.valueOf(mContext.getFilesDir()));
        if (!dir.exists()) {
            dir.mkdir();
        }

        try {
            File file = new File(dir, sFileName);
            FileWriter writer = new FileWriter(file);
            writer.append(sBody);
            writer.flush();
            writer.close();
//            Log.d("Write file", "wwwwwwwwwww");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String prepareAnimalsJSON(ArrayList<Animal> animalArrayList) throws JSONException {

        JSONObject result = new JSONObject();

        try {
            result.put("status", 200);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JSONArray jsonAnimalsArray = new JSONArray();

        for (int i = 0; i < animalArrayList.size(); i++) {
            JSONObject animal = new JSONObject();
            animal.put("id", animalArrayList.get(i).getId());
            animal.put("name", animalArrayList.get(i).getName());
            animal.put("parcode", animalArrayList.get(i).getParcode());
            animal.put("farm_number", animalArrayList.get(i).getFarm_number());
            animal.put("date_of_birth", animalArrayList.get(i).getDate_of_birth());
            animal.put("age_y", animalArrayList.get(i).getAge_y());
            animal.put("age_m", animalArrayList.get(i).getAge_m());
            animal.put("age_d", animalArrayList.get(i).getAge_d());
            animal.put("weight", animalArrayList.get(i).getWeight());
            animal.put("sex", animalArrayList.get(i).getSex());
            animal.put("mother_num", animalArrayList.get(i).getMother_num());
            animal.put("childern_num", animalArrayList.get(i).getChildern_num());
            animal.put("farm_id", animalArrayList.get(i).getFarm_id());
            animal.put("solala_id", animalArrayList.get(i).getSolala_id());
            animal.put("jawda_id", animalArrayList.get(i).getJawda_id());
            animal.put("sell_price", animalArrayList.get(i).getSell_price());
            animal.put("cost_price", animalArrayList.get(i).getCost_price());
            animal.put("fetam_weight", animalArrayList.get(i).getFetam_weight());
            animal.put("milk_amount", animalArrayList.get(i).getMilk_amount());
            animal.put("weladat_num", animalArrayList.get(i).getWeladat_num());
            animal.put("twins_avarage", animalArrayList.get(i).getTwins_avarage());
            animal.put("status1", animalArrayList.get(i).getStatus1());
            animal.put("status2", animalArrayList.get(i).getStatus2());
            animal.put("note", animalArrayList.get(i).getNote());
            animal.put("status_date", animalArrayList.get(i).getStatus_date());
            animal.put("fetam_done", animalArrayList.get(i).getFetam_done());
            animal.put("to3om_done", animalArrayList.get(i).getTo3om_done());
            jsonAnimalsArray.put(animal);
        }

        result.put("data", jsonAnimalsArray);

//        Log.d("PREPARE_JSON", result.toString());
//        Toast.makeText(AnimalsActivity.this, result.toString(), Toast.LENGTH_LONG).show();
        return result.toString();
    }


}