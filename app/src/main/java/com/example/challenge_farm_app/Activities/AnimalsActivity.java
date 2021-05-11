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
import android.app.SearchManager;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
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
    String ip = "10.0.2.2";
    View include;

    // boolean searchFlag = false;
    @SuppressLint("ResourceType")
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animals);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
    //    getSupportActionBar().hide();
        Configuration configuration = getResources().getConfiguration();
        configuration.setLayoutDirection(new Locale("ar"));
        getResources().updateConfiguration(configuration, getResources().getDisplayMetrics());

        gifImageView = findViewById(R.id.goatGIF);
        loadingTV = findViewById(R.id.loading_tv);
//        appBarLayout = findViewById(R.id.appbar_layout);
//        toolbar = findViewById(R.id.toolbar);
        include = findViewById(R.id.includeView);

        // String ip = getLocalIpAddress();
        // String ip = "192.168.1.106";
       //animalArrayList = getIntent().getParcelableArrayListExtra("ANIMALS_LIST");

        if(animalArrayList == null) {
         fetchAnimals();
        }
        else
        {
           showGIF();
        }
/*
            TableLayout tableLayout = findViewById(R.id.table_layout);
            TableRow titlesRow = findViewById(R.id.titles_row);
            titlesRow.setVisibility(View.VISIBLE);*/

        /*    TextView id_tv, name_tv;
            for (int i = 0; i < animals_num; i++) {
                TableRow row = new TableRow(this);
                TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,TableRow.LayoutParams.WRAP_CONTENT);
                row.setLayoutParams(lp);
       //         row.setBackgroundColor(Color.parseColor("#DAE8FC"));
                row.setPadding(5, 5, 5, 5);
                id_tv = new TextView(this);
                id_tv.setTag("id"+i);
                id_tv.setGravity(Gravity.CENTER);
                id_tv.setBackgroundResource(R.drawable.table_border);
                id_tv.setTextColor(Color.parseColor("#ffffff"));
                name_tv = new TextView(this);
                name_tv.setTag("name"+i);
                name_tv.setGravity(Gravity.CENTER);
                name_tv.setBackgroundResource(R.drawable.table_border);
                name_tv.setTextColor(Color.parseColor("#ffffff"));

                TableRow.LayoutParams params = new TableRow.LayoutParams(
                        TableRow.LayoutParams.WRAP_CONTENT,
                        TableRow.LayoutParams.WRAP_CONTENT,
                        1.0f
                );
                id_tv.setLayoutParams(params);
                name_tv.setLayoutParams(params);
                id_tv.setPadding(5,5,5,5);
                name_tv.setPadding(5,5,5,5);
                row.addView(name_tv);
                row.addView(id_tv);
          //      tableLayout.addView(row);
            }

            AppCompatButton displayBTN = new AppCompatButton(this);

            TableRow.LayoutParams params = new TableRow.LayoutParams(
                    TableRow.LayoutParams.WRAP_CONTENT,
                    TableRow.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(25,50,25,50);
            params.gravity = Gravity.CENTER_HORIZONTAL;
            params.topMargin = 100;
            displayBTN.setLayoutParams(params);
            displayBTN.setText("عرض");
            displayBTN.setTextColor(Color.parseColor("#ffffff"));
            displayBTN.setTextSize(25);
            displayBTN.setPadding(12,12,12,12);
            displayBTN.setBackgroundResource(R.color.colorAccent);
            displayBTN.setTypeface(null, Typeface.BOLD);

            displayBTN.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

         */
               /*     for(int i=0; i<animalArrayList.size(); i++) {
                        TextView id_tv = findViewById(R.id.table_layout).findViewWithTag("id"+i);
                        id_tv.setText(""+animalArrayList.get(i).getId());
                        TextView name_tv = findViewById(R.id.table_layout).findViewWithTag("name"+i);
                        name_tv.setText(animalArrayList.get(i).getName());
                    }
                }
            });
            LinearLayout linearLayout = findViewById(R.id.main_layout);
            linearLayout.addView(displayBTN);*/

    }

    private void showGIF() {
        include.setVisibility(View.GONE);
        gifImageView.setVisibility(View.VISIBLE);
        loadingTV.setVisibility(View.VISIBLE);
//        appBarLayout.setVisibility(View.GONE);
//        toolbar.setVisibility(View.GONE);
    }

    private void fetchAnimals() {

        final GetDataService service = RetrofitClientInstance.getRetrofitInstance(ip).create(GetDataService.class);
        Call<AnimalsList> animalsCall = service.getAllAnimals();
        animalsCall.enqueue(new Callback<AnimalsList>() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
            @Override
            public void onResponse(Call<AnimalsList> call, Response<AnimalsList> response) {
                AnimalsList animals = response.body();
                animalArrayList = animals.getAnimals();
                if(animalArrayList != null)
                {

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
                    recyclerviewItemAdapter = new RecyclerviewItemAdapter(animalArrayList, AnimalsActivity.this);

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
                else
                    showGIF();
            }

            @Override
            public void onFailure(Call<AnimalsList> call, Throwable t) {
                Toast.makeText(AnimalsActivity.this, t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            }
        });
        /*
        if(animalArrayList == null)
            Toast.makeText(this,"Error in displaying animals!",Toast.LENGTH_LONG).show();
        else {
            int animals_num = animalArrayList.size();*/
//          Toast.makeText(this, "" + animals_num, Toast.LENGTH_LONG).show();


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
        fetchAnimals();
        super.onResume();
    }
}
