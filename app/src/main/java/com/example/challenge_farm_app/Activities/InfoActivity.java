package com.example.challenge_farm_app.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.example.challenge_farm_app.Activities.AnimalsActivity;
import com.example.challenge_farm_app.Models.AllFarms;
import com.example.challenge_farm_app.Models.AllJawda;
import com.example.challenge_farm_app.Models.AllSolalat;
import com.example.challenge_farm_app.Models.Jawda;
import com.example.challenge_farm_app.Models.JawdaReq;
import com.example.challenge_farm_app.Models.Masdar;
import com.example.challenge_farm_app.Models.MasdarReq;
import com.example.challenge_farm_app.Models.Solala;
import com.example.challenge_farm_app.Models.SolalaReq;
import com.example.challenge_farm_app.R;
import com.example.challenge_farm_app.Models.Animal;
import com.example.challenge_farm_app.Models.AnimalsList;
import com.example.challenge_farm_app.Models.UsersList;
import com.example.challenge_farm_app.Network.GetDataService;
import com.example.challenge_farm_app.Network.RetrofitClientInstance;
import com.example.challenge_farm_app.R;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InfoActivity extends AppCompatActivity {

    private TextView nameTV, barcodeTV, motherNumTV, farmNumTV, sexTV, birthDateTV, solalaTV, jawdaTV, fetamDoneTV,
            tu3umDoneTV, idTV, ageTV, farmIDTV, childrenNumTV, weightTV, weladatNumTV,
            twinsAVGTV, sellPriceTV, costPriceTV, milkAmountTV, fetamWeightTV, status1TV, status2TV, statusDateTV, noteTV;

    private String name, barCode, motherNum, farmNum, sex, birthDate, masdar = "", solala = "", jawda = "", fetamDone, tu3umDone, status1, status2, statusDate, note;
    private int id, ageDay, ageMonth, ageYear, farmID, solalaID, jawdaID, childrenNum;
    private double weight, weladatNum, twinsAVG, sellPrice, costPrice, milkAmount, fetamWeight;

    String JSONPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath();
    GetDataService service;
    public static ArrayList<Masdar> farmsList;
    public static ArrayList<Solala> solalatList;
    public static ArrayList<Jawda> jawdasList;

    //FarmNum : رقم المزرعة
    //FarmID : المصدر
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        getSupportActionBar().hide();

        nameTV = findViewById(R.id.name);
        idTV = findViewById(R.id.id);
        barcodeTV = findViewById(R.id.barcode);
        motherNumTV = findViewById(R.id.motherNum);
        farmNumTV = findViewById(R.id.farmNum);
        sexTV = findViewById(R.id.sex);
        weightTV = findViewById(R.id.weight);
        birthDateTV = findViewById(R.id.birth_date);
        ageTV = findViewById(R.id.age);
        farmIDTV = findViewById(R.id.masdar);
        solalaTV = findViewById(R.id.solala);
        jawdaTV = findViewById(R.id.jawda);
        childrenNumTV = findViewById(R.id.children_num);
        weladatNumTV = findViewById(R.id.weladat_num);
        twinsAVGTV = findViewById(R.id.twins_avg);
        sellPriceTV = findViewById(R.id.sell_price);
        costPriceTV = findViewById(R.id.cost_price);
        milkAmountTV = findViewById(R.id.milk_amount);
        fetamWeightTV = findViewById(R.id.fetam_weight);
        fetamDoneTV = findViewById(R.id.fetam_done);
        tu3umDoneTV = findViewById(R.id.tu3um_done);
        status1TV = findViewById(R.id.status1);
        status2TV = findViewById(R.id.status2);
        statusDateTV = findViewById(R.id.status_date);
        noteTV = findViewById(R.id.note);

        Animal animal = getIntent().getParcelableExtra("ANIMAL");
        String ip = getIntent().getStringExtra("IP");
        service = RetrofitClientInstance.getRetrofitInstance(ip).create(GetDataService.class);

        Log.d("ANIMAL", animal.getName());

        if (animal != null) {
//        Log.d("para", "" + tokens[0]+"."+tokens[1]+"."+tokens[2]+"."+last);

            name = animal.getName();
            barCode = animal.getParcode();
            motherNum = animal.getMother_num();
            farmNum = animal.getFarm_number();
            sex = animal.getSex();
            birthDate = animal.getDate_of_birth().substring(0, 10);
            solalaID = animal.getSolala_id();
            jawdaID = animal.getJawda_id();
            fetamDone = animal.getFetam_done();
            tu3umDone = animal.getTo3om_done();
            id = animal.getId();
            ageDay = animal.getAge_d();
            ageMonth = animal.getAge_m();
            ageYear = animal.getAge_y();
            farmID = animal.getFarm_id();
            childrenNum = animal.getChildern_num();
            weight = animal.getWeight();
            weladatNum = animal.getWeladat_num();
            twinsAVG = animal.getTwins_avarage();
            sellPrice = animal.getSell_price();
            costPrice = animal.getCost_price();
            milkAmount = animal.getMilk_amount();
            fetamWeight = animal.getFetam_weight();
            status1 = animal.getStatus1();
            status2 = animal.getStatus2();
            statusDate = animal.getStatus_date().substring(0, 10);
            note = animal.getNote();

//            Log.d("FARM ID", ""+farmID);
//            Log.d("SOLALA ID", ""+solalaID);
//            Log.d("JAWDA ID", ""+jawdaID);


            File farmsFile = new File(JSONPath + "/farms.json");
            File solalatFile = new File(JSONPath + "/solalat.json");
            File jawdaFile = new File(JSONPath + "/jawda.json");

            if (farmsFile.exists() && solalatFile.exists() && jawdaFile.exists()) {
                fetchLocalInfo();
            } else {
                fetchServerInfo();
            }


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

    private void fetchServerInfo() {


        Call<MasdarReq> farmCall = service.getFarmByID(farmID);
        Call<SolalaReq> solalaCall = service.getSolalaByID(solalaID);
        Call<JawdaReq> jawdaCall = service.getJawdaByID(jawdaID);

        farmCall.enqueue(new Callback<MasdarReq>() {
            @Override
            public void onResponse(Call<MasdarReq> call, Response<MasdarReq> response) {
//                        Log.d("BODY", String.valueOf(response));
                if (response.body() != null && response.body().getMasdarName() != null && response.body().getMasdarName().size() > 0) {
                    if(id == 752 && farmID == 0)
                        masdar = "المزرعة - محلي";
                    else
                        masdar = response.body().getMasdarName().get(0).getName();
                }
                solalaCall.enqueue(new Callback<SolalaReq>() {
                    @Override
                    public void onResponse(Call<SolalaReq> call, Response<SolalaReq> response) {
                        if (response.body() != null && response.body().getSolalaName() != null && response.body().getSolalaName().size() > 0) {
                            if(id == 13 && solalaID == 0)
                                solala = "عساف";
                            else
                                solala = response.body().getSolalaName().get(0).getName();
                        }
                        jawdaCall.enqueue(new Callback<JawdaReq>() {
                            @Override
                            public void onResponse(Call<JawdaReq> call, Response<JawdaReq> response) {
                                if (response.body() != null && response.body().getJawdaName() != null && response.body().getJawdaName().size() > 0){
                                    if(id == 752 && jawdaID == 0)
                                        jawda = "عادي";
                                    else
                                        jawda = response.body().getJawdaName().get(0).getName();
                                }
//                    Toast.makeText(InfoActivity.this,masdar+"\n"+solala+"\n"+jawda,Toast.LENGTH_LONG).show();
                                displayInfo();
                            }

                            @Override
                            public void onFailure(Call<JawdaReq> call, Throwable t) {
                                jawda = "";
                            }
                        });
                    }

                    @Override
                    public void onFailure(Call<SolalaReq> call, Throwable t) {
                        solala = "";
                    }
                });

            }

            @Override
            public void onFailure(Call<MasdarReq> call, Throwable t) {
                masdar = "";
            }
        });

    }

    private void displayInfo() {
        nameTV.setText("الحيوان " + "(" + name + ")");
        idTV.setText("" + id);
        barcodeTV.setText(barCode);
        motherNumTV.setText(motherNum);
        farmNumTV.setText(farmNum);
        sexTV.setText(sex);
        weightTV.setText("" + weight);
        birthDateTV.setText(birthDate);
        ageTV.setText(ageYear + " سنين و" + ageMonth + " أشهر و" + ageDay + " أيام");
        farmIDTV.setText(masdar);
        solalaTV.setText(solala);
        jawdaTV.setText(jawda);
        childrenNumTV.setText("" + childrenNum);
        weladatNumTV.setText("" + weladatNum);
        twinsAVGTV.setText("" + ((new BigDecimal(Double.toString(twinsAVG)).setScale(2, BigDecimal.ROUND_HALF_UP)).doubleValue()));
        sellPriceTV.setText("" + sellPrice);
        costPriceTV.setText("" + costPrice);
        milkAmountTV.setText("" + milkAmount);
        fetamWeightTV.setText("" + fetamWeight);
        if (fetamDone.toLowerCase().equals("no"))
            fetamDoneTV.setText("لا");
        else if (fetamDone.toLowerCase().equals("yes"))
            fetamDoneTV.setText("نعم");
        if (tu3umDone.toLowerCase().equals("no"))
            tu3umDoneTV.setText("لا");
        else if (tu3umDone.toLowerCase().equals("yes"))
            tu3umDoneTV.setText("نعم");
        status1TV.setText(status1);
        status2TV.setText(status2);
        statusDateTV.setText(statusDate);
        noteTV.setText(note);
    }

    private void fetchLocalInfo() {
        if(id == 752 && farmID == 0)
            masdar = "المزرعة - محلي";
        else {
            if ((farmsList = readFarmsFromInternalStorage("farms.json").getFarms()) != null)
                for (int i = 0; i < farmsList.size(); i++)
                    if (farmID == farmsList.get(i).getId())
                        masdar = farmsList.get(i).getName();
        }

        if(id == 13 && solalaID == 0)
            solala = "عساف";
        else {
            if ((solalatList = readSolalatFromInternalStorage("solalat.json").getSolalat()) != null)
                for (int i = 0; i < solalatList.size(); i++)
                    if (solalaID == solalatList.get(i).getId())
                        solala = solalatList.get(i).getName();
        }
        
        if(id == 752 && jawdaID == 0)
            jawda = "عادي";
        else {
            if ((jawdasList = readJawdaFromInternalStorage("jawda.json").getAllJawda()) != null)
                for (int i = 0; i < jawdasList.size(); i++)
                    if (jawdaID == jawdasList.get(i).getId())
                        jawda = jawdasList.get(i).getName();
        }
        displayInfo();

    }

    public AllFarms readFarmsFromInternalStorage(String sFileName) {
        File file = new File(JSONPath, sFileName);
        FileReader fileReader = null;
        try {
            fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            StringBuilder stringBuilder = new StringBuilder();
            String line = bufferedReader.readLine();
            while (line != null) {
                stringBuilder.append(line).append("\n");
                line = bufferedReader.readLine();
            }
            bufferedReader.close();

            String response = stringBuilder.toString();
//            Log.d("READ_FILE", response);
            JSONObject JSONResponse = new JSONObject(response);
            AllFarms localList = new Gson().fromJson(String.valueOf(JSONResponse), AllFarms.class);
//            Log.d("NUM_OF_FARMS", "" + localList.getFarms().size());
            return localList;

        } catch (FileNotFoundException e) {
            Log.d("FileNotFoundException", "FileNotFoundException: ");
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            Log.d("IOException", "IOException: ");

            e.printStackTrace();
            return null;
        } catch (JSONException e) {
            Log.d("JSONException", "JSONException: ");

            e.printStackTrace();
            return null;
        }
    }

    public AllSolalat readSolalatFromInternalStorage(String sFileName) {
        File file = new File(JSONPath, sFileName);
        FileReader fileReader = null;
        try {
            fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            StringBuilder stringBuilder = new StringBuilder();
            String line = bufferedReader.readLine();
            while (line != null) {
                stringBuilder.append(line).append("\n");
                line = bufferedReader.readLine();
            }
            bufferedReader.close();

            String response = stringBuilder.toString();
//            Log.d("READ_FILE", response);
            JSONObject JSONResponse = new JSONObject(response);
            AllSolalat localList = new Gson().fromJson(String.valueOf(JSONResponse), AllSolalat.class);
//            Log.d("NUM_OF_FARMS", "" + localList.getFarms().size());
            return localList;

        } catch (FileNotFoundException e) {
            Log.d("FileNotFoundException", "FileNotFoundException: ");
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            Log.d("IOException", "IOException: ");

            e.printStackTrace();
            return null;
        } catch (JSONException e) {
            Log.d("JSONException", "JSONException: ");

            e.printStackTrace();
            return null;
        }
    }

    public AllJawda readJawdaFromInternalStorage(String sFileName) {
        File file = new File(JSONPath, sFileName);
        FileReader fileReader = null;
        try {
            fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            StringBuilder stringBuilder = new StringBuilder();
            String line = bufferedReader.readLine();
            while (line != null) {
                stringBuilder.append(line).append("\n");
                line = bufferedReader.readLine();
            }
            bufferedReader.close();

            String response = stringBuilder.toString();
//            Log.d("READ_FILE", response);
            JSONObject JSONResponse = new JSONObject(response);
            AllJawda localList = new Gson().fromJson(String.valueOf(JSONResponse), AllJawda.class);
//            Log.d("NUM_OF_FARMS", "" + localList.getFarms().size());
            return localList;

        } catch (FileNotFoundException e) {
            Log.d("FileNotFoundException", "FileNotFoundException: ");
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            Log.d("IOException", "IOException: ");

            e.printStackTrace();
            return null;
        } catch (JSONException e) {
            Log.d("JSONException", "JSONException: ");

            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void onBackPressed() {
//        startActivity( new Intent(this, AnimalsActivity.class) );
        finish();
    }

}