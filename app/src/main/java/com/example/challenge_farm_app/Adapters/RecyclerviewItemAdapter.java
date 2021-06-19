package com.example.challenge_farm_app.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.challenge_farm_app.Models.Animal;
import com.example.challenge_farm_app.Activities.AnimalsActivity;
import com.example.challenge_farm_app.Activities.InfoActivity;
import com.example.challenge_farm_app.Models.Animal;
import com.example.challenge_farm_app.Models.JawdaReq;
import com.example.challenge_farm_app.Models.MasdarReq;
import com.example.challenge_farm_app.Models.SolalaReq;
import com.example.challenge_farm_app.Models.UsersList;
import com.example.challenge_farm_app.Network.GetDataService;
import com.example.challenge_farm_app.Network.RetrofitClientInstance;
import com.example.challenge_farm_app.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;

public class RecyclerviewItemAdapter extends RecyclerView.Adapter<RecyclerviewItemAdapter.MyViewHolder>implements Filterable {

    private List<Animal> animalsList;
    private List<Animal> animalsListFiltered;
    private List<Animal> getAnimalModelListFiltered;
    private Context ctx;
    private String ip;
    private Activity activity;
   // private AnimalClickListener clickListener;

    public RecyclerviewItemAdapter(List<Animal> animalsList, Context ctx, String ip, Activity activity){
        this.animalsList = animalsList;
        this.getAnimalModelListFiltered = animalsList;
        this.ctx = ctx;
        this.ip = ip;
        this.activity = activity;
    }

    @Override
    public RecyclerviewItemAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerviewItemAdapter.MyViewHolder holder, final int position) {
        long days = 0;
        final Animal animal = animalsList.get(position);
        holder.name.setText(animal.getName());
        holder.id.setText(String.valueOf(animal.getId()));
//        Log.d("Status2", animal.getStatus2());
        try {
            days = daysDiff(animal.getStatus_date(),position, animal.getId());
//            Log.d("days", ""+daysDiff(animal.getStatus_date()));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if(animal.getStatus2().equals("حالة ولادة") && days>=45) {
            holder.name.setBackground(ContextCompat.getDrawable(ctx, R.drawable.yellow_border));
            holder.id.setBackground(ContextCompat.getDrawable(ctx, R.drawable.yellow_border));
            holder.name.setTextColor(Color.BLACK);
            holder.id.setTextColor(Color.BLACK);
        }

        else if(animal.getStatus2().equals("هرمون") && days>=30) {
            holder.name.setBackground(ContextCompat.getDrawable(ctx, R.drawable.green_border));
            holder.id.setBackground(ContextCompat.getDrawable(ctx, R.drawable.green_border));
            holder.name.setTextColor(Color.BLACK);
            holder.id.setTextColor(Color.BLACK);
        }

        else if(animal.getStatus2().equals("فلين") && days>=12) {
            holder.name.setBackground(ContextCompat.getDrawable(ctx, R.drawable.black_border));
            holder.id.setBackground(ContextCompat.getDrawable(ctx, R.drawable.black_border));
            holder.name.setTextColor(Color.WHITE);
            holder.id.setTextColor(Color.WHITE);
        }
        else if(animal.getStatus2().equals("عشار") && days>90 && days<120) {
            holder.name.setBackground(ContextCompat.getDrawable(ctx, R.drawable.dark_blue_border));
            holder.id.setBackground(ContextCompat.getDrawable(ctx, R.drawable.dark_blue_border));
            holder.name.setTextColor(Color.WHITE);
            holder.id.setTextColor(Color.WHITE);
        }

        else if(animal.getStatus2().equals("عشار") && days>=120) {
            holder.name.setBackground(ContextCompat.getDrawable(ctx, R.drawable.red_border));
            holder.id.setBackground(ContextCompat.getDrawable(ctx, R.drawable.red_border));
            holder.name.setTextColor(Color.WHITE);
            holder.id.setTextColor(Color.WHITE);
        }
        else if(animal.getStatus2().equals("محذوف")) {
            holder.name.setBackground(ContextCompat.getDrawable(ctx, R.drawable.light_blue_border));
            holder.id.setBackground(ContextCompat.getDrawable(ctx, R.drawable.light_blue_border));
            holder.name.setTextColor(Color.BLACK);
            holder.id.setTextColor(Color.BLACK);
        }
        else if(animal.getStatus2().equals("مشكلة")) {
            holder.name.setBackground(ContextCompat.getDrawable(ctx, R.drawable.pink_border));
            holder.id.setBackground(ContextCompat.getDrawable(ctx, R.drawable.pink_border));
            holder.name.setTextColor(Color.WHITE);
            holder.id.setTextColor(Color.WHITE);
        }
        else
        {
            holder.name.setBackground(ContextCompat.getDrawable(ctx, R.drawable.border));
            holder.id.setBackground(ContextCompat.getDrawable(ctx, R.drawable.border));
            holder.name.setTextColor(Color.BLACK);
            holder.id.setTextColor(Color.BLACK);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(),InfoActivity.class);
//                intent.putParcelableArrayListExtra("ANIMALS_LIST", (ArrayList<? extends Parcelable>) animalsList);
                intent.putExtra("ANIMAL", animalsList.get(holder.getAdapterPosition()));
                intent.putExtra("IP", ip);

                view.getContext().startActivity(intent);
                activity.overridePendingTransition(R.anim.bottom_up, R.anim.bottom_down);

                // Toast.makeText(view.getContext(),"You clicked on animal "+holder.getAdapterPosition(),Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return animalsList.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {

                FilterResults filterResults = new FilterResults();

                if(charSequence == null | charSequence.length() == 0){
           //         Toast.makeText(ctx,"EMPTY",Toast.LENGTH_LONG).show();

                    filterResults.count = getAnimalModelListFiltered.size();
                    filterResults.values = getAnimalModelListFiltered;


                }else{
                    String searchChr = charSequence.toString().toLowerCase();
                    List<Animal> resultData = new ArrayList<>();
                 //   Toast.makeText(ctx,"EXIST",Toast.LENGTH_LONG).show();

                    for(Animal animalModel: getAnimalModelListFiltered){
                        if(animalModel.getName().toLowerCase().equals(searchChr) || animalModel.getId().toString().equals(searchChr)){
                      //Toast.makeText(ctx,"MATCH",Toast.LENGTH_LONG).show();
                            resultData.add(animalModel);
                        }
                    }

                    filterResults.count = resultData.size();
                    filterResults.values = resultData;

                }

                return filterResults;
           /*     String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    animalsListFiltered = animalsList;
                } else {
                    List<Animal> filteredList = new ArrayList<>();
                    for (Animal row : animalsList) {
                            // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getName().toLowerCase().contains(charString.toLowerCase()) || row.getId().toString()
                                .contains(charSequence)) {
                            filteredList.add(row);
                        }
                    }

                    animalsListFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = animalsListFiltered;
                return filterResults;*/
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                animalsList = (ArrayList<Animal>) filterResults.values;
              //Toast.makeText(ctx, "SIZE"+animalsList.size(), Toast.LENGTH_LONG).show();
                notifyDataSetChanged();
            }
        };
    }

    /* public void setOnItemClickListener(AnimalClickListener clickListener) {
         this.clickListener = clickListener;
     }*/
    class MyViewHolder extends RecyclerView.ViewHolder{

        public TextView name,id;
       // private LinearLayout itemLayout;

        public MyViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            id = itemView.findViewById(R.id.id);
            // itemLayout =  itemView.findViewById(R.id.itemLayout);
        }
    }


    public long daysDiff(String status_date, int position, int id)
            throws ParseException {
        if(status_date==null || status_date.equals('0')) {
            Log.d("NULL", "" + position+"    "+id);
            return 0;
        }
        else{
//            Log.d("STATUS DATE", status_date);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
            Date firstDate = sdf.parse(status_date);
            Date current = new Date();
            Date secondDate = sdf.parse(sdf.format(current));
            long diffInMillies = Math.abs(secondDate.getTime() - firstDate.getTime());
            long days = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);

            return days;
        }

    }
}

