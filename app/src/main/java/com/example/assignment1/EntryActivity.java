package com.example.assignment1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class EntryActivity extends AppCompatActivity {
    ArrayList<DiaryEntry> entryArrayList;
    DiaryEntry diaryEntry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Intent intent = this.getIntent();
        loadData();

        if(intent.getExtras().getString("Uniqid").equals("From_calculator")){
            diaryEntry = entryArrayList.get(Integer.parseInt(intent.getExtras().getString("index")));
        }
        displayData();

        Button buttonOverview = findViewById(R.id.overviewButton);
        buttonOverview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent viewOverview = new Intent(getApplicationContext(), MainActivity.class);
                viewOverview.putExtra("Uniqid", "From_diary");

                startActivity(viewOverview);
                finish();
            }
        });

        Button buttonEdit = findViewById(R.id.editButton);
        buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent viewEdit = new Intent(getApplicationContext(), CalculatorActivity.class);
                viewEdit.putExtra("Uniqid", "From_diary");
                String index = entryArrayList.indexOf(diaryEntry)+"";
                viewEdit.putExtra("index", index);

                startActivity(viewEdit);
                finish();
            }
        });

    }

    private void displayData() {
        ((TextView) findViewById(R.id.breakfastValueView)).setText(diaryEntry.getBreakfast());
        ((TextView) findViewById(R.id.lunchValueView)).setText(diaryEntry.getLunch());
        ((TextView) findViewById(R.id.dinnerValueView)).setText(diaryEntry.getDinner());
        ((TextView) findViewById(R.id.snacksValueView)).setText(diaryEntry.getSnacks());
        ((TextView) findViewById(R.id.foodTotalValueView)).setText(diaryEntry.getFoodKJ());
        ((TextView) findViewById(R.id.weightliftingValueView)).setText(diaryEntry.getWeightlifting());
        ((TextView) findViewById(R.id.cardioValueView)).setText(diaryEntry.getCardio());
        ((TextView) findViewById(R.id.mixedValueView)).setText(diaryEntry.getMixed());
        ((TextView) findViewById(R.id.exerciseTotalValueView)).setText(diaryEntry.getExerciseKJ());
        ((TextView) findViewById(R.id.foodTotalNKIView)).setText(diaryEntry.getFoodKJ());
        ((TextView) findViewById(R.id.exerciseTotalNKIView)).setText(diaryEntry.getExerciseKJ());
        ((TextView) findViewById(R.id.NKITotalView)).setText(diaryEntry.getNKI());

    }

    private void saveData() {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(entryArrayList);
        editor.putString("task list", json);
        editor.apply();
    }

    private void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("task list", null);
        Type type = new TypeToken<ArrayList<DiaryEntry>>() {}.getType();
        entryArrayList = gson.fromJson(json, type);

        if (entryArrayList == null) {
            entryArrayList = new ArrayList<>();
        }
    }

}
