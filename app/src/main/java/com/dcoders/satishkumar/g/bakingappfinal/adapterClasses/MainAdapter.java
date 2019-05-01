package com.dcoders.satishkumar.g.bakingappfinal.adapterClasses;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.dcoders.satishkumar.g.bakingappfinal.DataClasses.Ingredient;
import com.dcoders.satishkumar.g.bakingappfinal.DataClasses.Receipe;
import com.dcoders.satishkumar.g.bakingappfinal.DataClasses.Step;
import com.dcoders.satishkumar.g.bakingappfinal.R;
import com.dcoders.satishkumar.g.bakingappfinal.ReceipeDetailListActivity;

import java.io.Serializable;
import java.util.List;

import static com.dcoders.satishkumar.g.bakingappfinal.ReceipeDetailListActivity.INGRDIENT;
import static com.dcoders.satishkumar.g.bakingappfinal.ReceipeDetailListActivity.STEP;
import static com.dcoders.satishkumar.g.bakingappfinal.ReceipeDetailListActivity.TITLE;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {
    private Context context;

    public MainAdapter(Context context, List<Receipe> recipes) {
        this.context = context;
        this.recipes = recipes;
    }

    private List<Receipe> recipes;
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.main_activity_recycler_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txt.setText(recipes.get(position).getName());
        if(!TextUtils.isEmpty(recipes.get(position).getImage()))
        {
            Glide.with(context).load(recipes.get(position).getImage()).into(holder.image);
        }


    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView image;
        TextView txt;
        public ViewHolder(View itemView) {
            super(itemView);
            txt=itemView.findViewById(R.id.main_recycler_item_textView);
            image=itemView.findViewById(R.id.main_recycler_item_imageView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position=getAdapterPosition();
            Intent intent=new Intent(context, ReceipeDetailListActivity.class);
            intent.putExtra(TITLE,recipes.get(position).getName());
            intent.putExtra(INGRDIENT, (Serializable) recipes.get(position).getIngredients());
            intent.putExtra(STEP, (Serializable) recipes.get(position).getSteps());
            context.startActivity(intent);


        }
    }
}