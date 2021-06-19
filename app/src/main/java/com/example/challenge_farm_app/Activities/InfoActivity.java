package com.example.challenge_farm_app.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.example.challenge_farm_app.Activities.AnimalsActivity;
import com.example.challenge_farm_app.Models.JawdaReq;
import com.example.challenge_farm_app.Models.MasdarReq;
import com.example.challenge_farm_app.Models.SolalaReq;
import com.example.challenge_farm_app.R;
import com.example.challenge_farm_app.Models.Animal;
import com.example.challenge_farm_app.Models.AnimalsList;
import com.example.challenge_farm_app.Models.UsersList;
import com.example.challenge_farm_app.Network.GetDataService;
import com.example.challenge_farm_app.Network.RetrofitClientInstance;
import com.example.challenge_farm_app.R;

import java.math.BigDecimal;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InfoActivity extends AppCompatActivity {

    private TextView nameTV, barcodeTV, motherNumTV, farmNumTV, sexTV, birthDateTV, solalaTV, jawdaTV, fetamDoneTV,
            tu3umDoneTV, idTV, ageTV, farmIDTV, childrenNumTV, weightTV, weladatNumTV,
            twinsAVGTV, sellPriceTV, costPriceTV, milkAmountTV, fetamWeightTV, status1TV, status2TV, statusDateTV, noteTV;

    private String name, barCode, motherNum, farmNum, sex, birthDate, masdar="", solala="", jawda="", fetamDone, tu3umDone, status1, status2, statusDate, note;
    private int id, ageDay, ageMonth, ageYear, farmID, solalaID, jawdaID, childrenNum;
    private double weight, weladatNum, twinsAVG, sellPrice, costPrice, milkAmount, fetamWeight;

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
            noteTV= findViewById(R.id.note);

        Animal animal = getIntent().getParcelableExtra("ANIMAL");
        String ip = getIntent().getStringExtra("IP");

        if (animal != null) {
            final GetDataService service = RetrofitClientInstance.getRetrofitInstance(ip).create(GetDataService.class);
//        Log.d("para", "" + tokens[0]+"."+tokens[1]+"."+tokens[2]+"."+last);

            name = animal.getName();
            barCode = animal.getParcode();
            motherNum = animal.getMother_num();
            farmNum = animal.getFarm_number();
            sex = animal.getSex();
            birthDate = animal.getDate_of_birth().substring(0,10);
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
            statusDate = animal.getStatus_date().substring(0,10);
            note = animal.getNote();

            Log.d("FARM ID", ""+farmID);
            Log.d("SOLALA ID", ""+solalaID);
            Log.d("JAWDA ID", ""+jawdaID);

            Call<MasdarReq> farmCall = service.getFarmByID(farmID);
            Call<SolalaReq> solalaCall = service.getSolalaByID(solalaID);
            Call<JawdaReq> jawdaCall = service.getJawdaByID(jawdaID);

            farmCall.enqueue(new Callback<MasdarReq>() {
                @Override
                public void onResponse(Call<MasdarReq> call, Response<MasdarReq> response) {
                    Log.d("BODY", String.valueOf(response));
                    if(response.body()!=null && response.body().getMasdarName()!=null && response.body().getMasdarName().size()>0)
                    masdar = response.body().getMasdarName().get(0).getName();
                    solalaCall.enqueue(new Callback<SolalaReq>() {
                        @Override
                        public void onResponse(Call<SolalaReq> call, Response<SolalaReq> response) {
                            if(response.body()!=null && response.body().getSolalaName()!=null && response.body().getSolalaName().size()>0)
                                solala = response.body().getSolalaName().get(0).getName();
                            jawdaCall.enqueue(new Callback<JawdaReq>() {
                                @Override
                                public void onResponse(Call<JawdaReq> call, Response<JawdaReq> response) {
                                    if(response.body()!=null && response.body().getJawdaName()!=null && response.body().getJawdaName().size()>0)
                                        jawda = response.body().getJawdaName().get(0).getName();
//                    Toast.makeText(InfoActivity.this,masdar+"\n"+solala+"\n"+jawda,Toast.LENGTH_LONG).show();
                                    nameTV.setText( "الحيوان " +"(" + name + ")");
                                    idTV.setText(""+id);
                                    barcodeTV.setText(barCode);
                                    motherNumTV.setText(motherNum);
                                    farmNumTV.setText(farmNum);
                                    sexTV.setText(sex);
                                    weightTV.setText(""+weight);
                                    birthDateTV.setText(birthDate);
                                    ageTV.setText(ageYear+" سنين و"+ageMonth+" أشهر و"+ageDay+" أيام");
                                    farmIDTV.setText(masdar);
                                    solalaTV.setText(solala);
                                    jawdaTV.setText(jawda);
                                    childrenNumTV.setText(""+childrenNum);
                                    weladatNumTV.setText(""+weladatNum);
                                    twinsAVGTV.setText(""+((new BigDecimal(Double.toString(twinsAVG)).setScale(2, BigDecimal.ROUND_HALF_UP)).doubleValue()));
                                    sellPriceTV.setText(""+sellPrice);
                                    costPriceTV.setText(""+costPrice);
                                    milkAmountTV.setText(""+milkAmount);
                                    fetamWeightTV.setText(""+fetamWeight);
                                    if(fetamDone.toLowerCase().equals("no"))
                                        fetamDoneTV.setText("لا");
                                    else if(fetamDone.toLowerCase().equals("yes"))
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



            //TODO: Icon for app

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