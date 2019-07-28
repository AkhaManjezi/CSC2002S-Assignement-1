package com.example.assignment1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

public class CalculatorActivity extends AppCompatActivity {

    ArrayList<DiaryEntry> entryArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);

        loadData();

        Button buttonSave = findViewById(R.id.saveButton);
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newEntry();
                saveData();
                Intent viewEntry = new Intent(getApplicationContext(), EntryActivity.class);

                startActivity(viewEntry);
            }
        });
    }

    private void newEntry(){
        EditText breakfastText = findViewById(R.id.breakfastTextInput);
        String breafastKJ = (breakfastText.getText().length() != 0) ? breakfastText.getText().toString() : 0 + "";
        EditText lunchText = findViewById(R.id.lunchTextInput);
        String lunchKJ = (lunchText.getText().length() != 0) ? lunchText.getText().toString() : 0 + "";
        EditText dinnerText = findViewById(R.id.dinnerTextInput);
        String dinnerKJ = (dinnerText.getText().length() != 0) ? dinnerText.getText().toString() : 0 + "";
        EditText snacksText = findViewById(R.id.snacksTextInput);
        String snacksKJ = (snacksText.getText().length() != 0) ? snacksText.getText().toString() : 0 + "";
        EditText weightliftingText = findViewById(R.id.weightliftingTextInput);
        String weightliftingKJ = (weightliftingText.getText().length() != 0) ? weightliftingText.getText().toString() : 0 + "";
        EditText cardioText = findViewById(R.id.cardioTextInput);
        String cardioKJ = (cardioText.getText().length() != 0) ? cardioText.getText().toString() : 0 + "";
        EditText mixedText = findViewById(R.id.mixedTextInput);
        String mixedKJ = (mixedText.getText().length() != 0) ? mixedText.getText().toString() : 0 + "";
        Date pubDate = new Date();

        DiaryEntry newEntry  = new DiaryEntry(breafastKJ, lunchKJ, dinnerKJ, snacksKJ, weightliftingKJ, cardioKJ, mixedKJ, pubDate);
        entryArrayList.add(newEntry);

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
