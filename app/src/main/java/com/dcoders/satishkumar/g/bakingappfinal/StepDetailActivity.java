package com.dcoders.satishkumar.g.bakingappfinal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.dcoders.satishkumar.g.bakingappfinal.DataClasses.Step;

import java.io.Serializable;
import java.util.List;

import static com.dcoders.satishkumar.g.bakingappfinal.ReceipeDetailListActivity.STEP;

public class StepDetailActivity extends AppCompatActivity {
    List<Step> steps;
    int pos;
    public static final String BUNDLE_KEY="bundlekey";
    public static final String POSITIONKEY="positionkey";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_detail);
        if (savedInstanceState == null) {
            Intent intent=getIntent();
            Bundle arguments =intent.getBundleExtra(BUNDLE_KEY);
            steps= (List<Step>) arguments.getSerializable(STEP);
            pos=arguments.getInt(POSITIONKEY,0);
            arguments.putSerializable(STEP,
                    (Serializable) steps);
            arguments.putInt(POSITIONKEY,pos);
            DetailFragment fragment = new DetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.main_container, fragment)
                    .commit();
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {

            finish();
            return true;
        }
        return true;
    }
}
