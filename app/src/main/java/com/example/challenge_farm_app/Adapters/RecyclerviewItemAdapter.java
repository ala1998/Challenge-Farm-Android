package com.example.challenge_farm_app.Adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.challenge_farm_app.Models.Animal;
import com.example.challenge_farm_app.Activities.AnimalsActivity;
import com.example.challenge_farm_app.Activities.InfoActivity;
import com.example.challenge_farm_app.Models.Animal;
import com.example.challenge_farm_app.R;

import java.util.ArrayList;
import java.util.List;

public class RecyclerviewItemAdapter extends RecyclerView.Adapter<RecyclerviewItemAdapter.MyViewHolder>implements Filterable {

    private List<Animal> animalsList;
    private List<Animal> animalsListFiltered;
    private List<Animal> getAnimalModelListFiltered;
    private Context ctx;
   // private AnimalClickListener clickListener;

    public RecyclerviewItemAdapter(List<Animal> animalsList, Context ctx){
        this.animalsList = animalsList;
        this.getAnimalModelListFiltered = animalsList;
        this.ctx = ctx;
    }

    @Override
    public RecyclerviewItemAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerviewItemAdapter.MyViewHolder holder, final int position) {
        final Animal animal = animalsList.get(position);
        holder.name.setText(animal.getName());
        holder.id.setText(String.valueOf(animal.getId()));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(),InfoActivity.class);
                intent.putParcelableArrayListExtra("ANIMALS_LIST", (ArrayList<? extends Parcelable>) animalsList);
                intent.putExtra("POSITION", holder.getAdapterPosition());
                view.getContext().startActivity(intent);
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
}
