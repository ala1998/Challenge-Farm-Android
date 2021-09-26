package com.example.challenge_farm_app.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.example.challenge_farm_app.Adapters.RecyclerviewItemAdapter;
import com.example.challenge_farm_app.Models.Animal;
import com.example.challenge_farm_app.Models.AnimalsList;
import com.example.challenge_farm_app.Network.GetDataService;
import com.example.challenge_farm_app.Network.RetrofitClientInstance;
import com.example.challenge_farm_app.R;
import com.google.android.material.appbar.AppBarLayout;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;

import pl.droidsonroids.gif.GifImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AnimalsActivity extends AppCompatActivity {

    public static ArrayList<Animal> animalArrayList;
    RecyclerviewItemAdapter recyclerviewItemAdapter;
    SearchView searchView;
    RecyclerView recyclerView;
    GifImageView gifImageView;
    TextView loadingTV;
    AppBarLayout appBarLayout;
    Toolbar toolbar;
    String ip;
    View include;
    Activity activity;
    String JSONPath = "/storage/emulated/legacy/Download";

    // boolean searchFlag = false;
    @SuppressLint("ResourceType")
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animals);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        activity = (Activity) this;

        //    getSupportActionBar().hide();
        Configuration configuration = getResources().getConfiguration();
        configuration.setLayoutDirection(new Locale("ar"));
        getResources().updateConfiguration(configuration, getResources().getDisplayMetrics());

        gifImageView = findViewById(R.id.goatGIF);
        loadingTV = findViewById(R.id.loading_tv);
//        appBarLayout = findViewById(R.id.appbar_layout);
//        toolbar = findViewById(R.id.toolbar);
        include = findViewById(R.id.includeView);

        ip = getIntent().getStringExtra("IP");

        //animalArrayList = getIntent().getParcelableArrayListExtra("ANIMALS_LIST");

        if (animalArrayList == null) {
            showGIF();
            fetchAnimalsFromLocal();
        }

    }

    private void showGIF() {
        include.setVisibility(View.GONE);
        gifImageView.setVisibility(View.VISIBLE);
        loadingTV.setVisibility(View.VISIBLE);
//        appBarLayout.setVisibility(View.GONE);
//        toolbar.setVisibility(View.GONE);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void displayRecyclerView(ArrayList<Animal> animalArrayList) {
        gifImageView.setVisibility(View.GONE);
        loadingTV.setVisibility(View.GONE);
//                    appBarLayout.setVisibility(View.VISIBLE);
//                    toolbar.setVisibility(View.VISIBLE);
        include.setVisibility(View.VISIBLE);

//                    setSupportActionBar(toolbar);
//                   // getSupportActionBar().setTitle("ابحث");
//                    getSupportActionBar().setDisplayOptions(androidx.appcompat.app.ActionBar.DISPLAY_SHOW_CUSTOM);
//                    getSupportActionBar().setCustomView(R.layout.customactionbar);


        recyclerView = findViewById(R.id.recycleView);
        recyclerviewItemAdapter = new RecyclerviewItemAdapter(animalArrayList, AnimalsActivity.this, ip, activity);

        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(recyclerviewItemAdapter);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);

        searchView = findViewById(R.id.searchView);
        searchView.setSearchableInfo(searchManager
                .getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);
//                    searchView.setLayoutParams(new ActionBar.LayoutParams((Gravity.RIGHT)));
        searchView.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);
        searchView.setTextDirection(View.TEXT_DIRECTION_ANY_RTL);

        // listening to search query text change
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //Toast.makeText(AnimalsActivity.this,"SUBMIT\n"+query,Toast.LENGTH_LONG).show();

                // filter recycler view when query submitted
                recyclerviewItemAdapter.getFilter().filter(query);
                // Toast.makeText(AnimalsActivity.this,, Toast.LENGTH_LONG).show();
                //   searchFlag = true;
                recyclerviewItemAdapter.notifyDataSetChanged();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                //  Toast.makeText(AnimalsActivity.this,"Change\n"+query,Toast.LENGTH_LONG).show();

                // filter recycler view when text is changed
                recyclerviewItemAdapter.getFilter().filter(query);
                recyclerviewItemAdapter.notifyDataSetChanged();
                return false;
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void fetchAnimalsFromLocal() {

        if ((animalArrayList = readFileOnInternalStorage("animals.json").getAnimals()) != null) {
//            animalArrayList = readFileOnInternalStorage("animals.json").getAnimals();

            displayRecyclerView(animalArrayList);

        } else
            showGIF();
    }


    private String prepareJSON(ArrayList<Animal> animalArrayList) throws JSONException {

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

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_main, menu);

        // Associate searchable configuration with the SearchView

//        searchView = (SearchView) menu.findItem(R.id.action_search)
        //        .getActionView();


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

       /* if(id == R.id.home){
            if (!searchView.isIconified())
                searchView.setIconified(true);
            }*/
       /* if (id == R.id.action_search) {
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        // close search view on back button pressed
        if (!searchView.isIconified()) {
            searchView.setIconified(true);
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onResume() {
//        fetchAnimals();
        super.onResume();
    }


    public AnimalsList readFileOnInternalStorage(String sFileName) {
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
            AnimalsList localList = new Gson().fromJson(String.valueOf(JSONResponse), AnimalsList.class);
            Log.d("NUM_OF_ANIMALS", "" + localList.getAnimals().size());
            return localList;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}
