package com.dcoders.satishkumar.g.bakingappfinal.adapterClasses;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dcoders.satishkumar.g.bakingappfinal.DataClasses.Ingredient;
import com.dcoders.satishkumar.g.bakingappfinal.DataClasses.Step;
import com.dcoders.satishkumar.g.bakingappfinal.DetailFragment;
import com.dcoders.satishkumar.g.bakingappfinal.R;
import com.dcoders.satishkumar.g.bakingappfinal.ReceipeDetailListActivity;
import com.dcoders.satishkumar.g.bakingappfinal.StepDetailActivity;

import java.io.Serializable;
import java.util.List;

import static com.dcoders.satishkumar.g.bakingappfinal.ReceipeDetailListActivity.INGRDIENT;
import static com.dcoders.satishkumar.g.bakingappfinal.ReceipeDetailListActivity.STEP;
import static com.dcoders.satishkumar.g.bakingappfinal.StepDetailActivity.BUNDLE_KEY;
import static com.dcoders.satishkumar.g.bakingappfinal.StepDetailActivity.POSITIONKEY;

public class StepsAdapter extends RecyclerView.Adapter<StepsAdapter.ViewHolder> {
    private ReceipeDetailListActivity context;
    private List<Step> steps;
    private List<Ingredient> ingredients;

    public StepsAdapter(ReceipeDetailListActivity context, List<Step> steps, boolean pane, List<Ingredient> ingredients) {
        this.context = context;
        this.steps = steps;
        this.pane = pane;
        this.ingredients=ingredients;
    }

    private boolean pane;
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.steps_recycler_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {
        holder.tv.setText(steps.get(position).getShortDescription());

    }

    @Override
    public int getItemCount() {

        return steps.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tv;
        public ViewHolder(View itemView) {
            super(itemView);
            tv=itemView.findViewById(R.id.steps_textView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position=getAdapterPosition();
            Bundle bundle=new Bundle();
            bundle.putInt(POSITIONKEY, position);
            bundle.putSerializable(STEP, (Serializable) steps);
            bundle.putSerializable(INGRDIENT, (Serializable) ingredients);
            if (pane) {
                 DetailFragment detailFragment= new DetailFragment();
                detailFragment.setArguments(bundle);
                context.getSupportFragmentManager().beginTransaction()
                        .replace(R.id.step_detail_container,detailFragment)
                        .commit();

            } else {

                Intent intent = new Intent(context, StepDetailActivity.class);
                intent.putExtra(BUNDLE_KEY, bundle);
                context.startActivity(intent);
            }


        }
    }
}
