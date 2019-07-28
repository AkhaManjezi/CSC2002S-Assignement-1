package com.example.assignment1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class CalculatorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);
    }

    public void newEntry(View view){
        EditText breakfastText = findViewById(R.id.breakfastTextInput);
        double breafastKJ = (breakfastText.getText().length() != 0) ? Double.parseDouble(breakfastText.getText().toString()) : 0;
        EditText lunchText = findViewById(R.id.breakfastTextInput);
        double lunchKJ = Double.parseDouble(lunchText.getText().toString());
        EditText dinnerText = findViewById(R.id.breakfastTextInput);
        double dinnerKJ = Double.parseDouble(dinnerText.getText().toString());
        EditText snacksText = findViewById(R.id.breakfastTextInput);
        double snacksKJ = Double.parseDouble(snacksText.getText().toString());
        EditText weightliftingText = findViewById(R.id.breakfastTextInput);
        double weightliftingKJ = Double.parseDouble(weightliftingText.getText().toString());
        EditText cardioText = findViewById(R.id.breakfastTextInput);
        double cardioKJ = Double.parseDouble(cardioText.getText().toString());
        EditText mixedText = findViewById(R.id.breakfastTextInput);
        double mixedKJ = Double.parseDouble(mixedText.getText().toString());


    }


}
