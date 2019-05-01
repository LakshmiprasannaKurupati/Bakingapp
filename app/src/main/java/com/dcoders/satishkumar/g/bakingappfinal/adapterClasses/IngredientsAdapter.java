package com.dcoders.satishkumar.g.bakingappfinal.adapterClasses;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dcoders.satishkumar.g.bakingappfinal.DataClasses.Ingredient;
import com.dcoders.satishkumar.g.bakingappfinal.R;

import java.util.List;

public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.ViewHolder> {
    private Context context;

    public IngredientsAdapter(Context context, List<Ingredient> ingredients) {
        this.context = context;
        this.ingredients = ingredients;
    }

    private List<Ingredient> ingredients;
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.ingredients_recycler_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.ingred.setText(ingredients.get(position).getIngredient());
        holder.quantity.setText(String.valueOf(ingredients.get(position).getQuantity()));
        holder.measure.setText(ingredients.get(position).getMeasure());
        holder.count.setText(String.valueOf(position+1));

    }
    @Override
    public int getItemCount() {
        return ingredients.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView ingred,quantity,measure,count;
        public ViewHolder(View itemView) {
            super(itemView);
            ingred=itemView.findViewById(R.id.ingredient_text_view);
            quantity=itemView.findViewById(R.id.quantity_text_view);
            measure=itemView.findViewById(R.id.measure_text_view);
            count=itemView.findViewById(R.id.count_text_view);
        }
    }
}
