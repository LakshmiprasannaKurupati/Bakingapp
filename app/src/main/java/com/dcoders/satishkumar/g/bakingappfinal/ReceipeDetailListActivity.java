package com.dcoders.satishkumar.g.bakingappfinal;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.Toast;

import com.dcoders.satishkumar.g.bakingappfinal.DataClasses.Ingredient;
import com.dcoders.satishkumar.g.bakingappfinal.DataClasses.Step;
import com.dcoders.satishkumar.g.bakingappfinal.adapterClasses.IngredientsAdapter;
import com.dcoders.satishkumar.g.bakingappfinal.adapterClasses.StepsAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ReceipeDetailListActivity extends AppCompatActivity {
    public static final String STEP="stepskey";
    public static final String INGRDIENT="ingredoientskey";
    public static final String TITLE="titleKey";
    //
    List<Ingredient> ingredients;
    List<Step> steps;
    String title;
    boolean mPane;
    //

    RecyclerView ingredientsRecycler;
    RecyclerView stepsRecycler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipe_detail_list);
        //
       ingredientsRecycler=findViewById(R.id.ingredients_list);
        stepsRecycler=findViewById(R.id.steps_list);
        //
        ingredients=new ArrayList<>();
steps=new ArrayList<>();
        if (findViewById(R.id.step_detail_container)!=null)
       {
           mPane=true;
       }

        title=getIntent().getStringExtra(TITLE);
        setTitle(title);
        assert ingredients!=null;
        ingredients= (List<Ingredient>) getIntent().getSerializableExtra(INGRDIENT);
        if (ingredients==null) {
            Toast.makeText(this, "Null Object", Toast.LENGTH_SHORT).show();
             }
             else
        {
            ingredientsRecycler.setAdapter(new IngredientsAdapter(this, ingredients));
            ingredientsRecycler.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));

        }
        steps= (List<Step>) getIntent().getSerializableExtra(STEP);
        if (steps!=null) {
            stepsRecycler.setAdapter(new StepsAdapter(this, steps, mPane,ingredients));
            stepsRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        }


        //
        updateWidget();

    }

    private void updateWidget() {

        SharedPreferences sharedPreferences=getSharedPreferences("SATISH",MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        StringBuilder stringBuilder=new StringBuilder();
        for (int i=0;i<ingredients.size();i++)
        {
            int j=i+1;
            stringBuilder.append(j+"."+ingredients.get(i).getIngredient()+"\n");
        }
        String totString=stringBuilder.toString();
        editor.putString("KEY",title);
        editor.putString(INGRDIENT,totString);
        editor.apply();
        Intent intent=new Intent(this,MainActivity.class);
        intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        int[] ids=AppWidgetManager.getInstance(getApplicationContext())
                .getAppWidgetIds(new ComponentName(getApplication(),Widget.class));
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS,ids);
        sendBroadcast(intent);

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == android.R.id.home)
        {
            navigateUpTo(new Intent(this,MainActivity.class));
        }
        return true;
    }

}
