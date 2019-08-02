package com.example.assignment1.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.example.assignment1.DiaryEntry;
import com.example.assignment1.R;
import com.example.assignment1.entry.EntryActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;

public class CalculatorActivity extends AppCompatActivity {

    ArrayList<DiaryEntry> entryArrayList;
    DiaryEntry newEntry;
    int currentIndex = -1;
    EditText breakfastInput;
    EditText lunchInput;
    EditText dinnerInput;
    EditText snacksInput;
    EditText weightliftingInput;
    EditText cardioInput;
    EditText mixedInput;
    DatePickerDialog.OnDateSetListener mdateSetListener;
    Calendar currentDate;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);

        loadData();

         breakfastInput = findViewById(R.id.breakfastTextInput);
         lunchInput = findViewById(R.id.lunchTextInput);
         dinnerInput = findViewById(R.id.dinnerTextInput);
         snacksInput = findViewById(R.id.snacksTextInput);
         weightliftingInput = findViewById(R.id.weightliftingTextInput);
         cardioInput = findViewById(R.id.cardioTextInput);
         mixedInput = findViewById(R.id.mixedTextInput);

        final SimpleDateFormat formatter = new SimpleDateFormat("EEE d MMM yyyy");

        currentDate = Calendar.getInstance();
        final TextView dateText = findViewById(R.id.dateText);

        Button buttonSave = findViewById(R.id.saveButton);
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateEntry();
                if(currentIndex != -1){
                    entryArrayList.set(currentIndex,newEntry);
                }else{
                    entryArrayList.add(newEntry);
                }
                saveData();
                currentIndex = entryArrayList.indexOf(newEntry);
                Intent viewEntry = new Intent(getApplicationContext(), EntryActivity.class);
                viewEntry.putExtra("Uniqid", "From_calculator");
                viewEntry.putExtra("index", (currentIndex)+"");

                startActivity(viewEntry);
                finish();
            }
        });

        intent = this.getIntent();
        if(intent.getExtras().getString("Uniqid").equals("From_diary")){
            currentIndex = Integer.parseInt(intent.getExtras().getString("index"));
            newEntry = entryArrayList.get(currentIndex);
            dateText.setText(formatter.format(newEntry.getDate().getTime()));
            currentDate = newEntry.getDate();
            edit();
            updateTotals();
        }else{
            newEntry = new DiaryEntry();
            dateText.setText(formatter.format(currentDate.getTime()));
        }

        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                updateTotals();
            }
        };

        breakfastInput.addTextChangedListener(textWatcher);
        lunchInput.addTextChangedListener(textWatcher);
        dinnerInput.addTextChangedListener(textWatcher);
        snacksInput.addTextChangedListener(textWatcher);
        weightliftingInput.addTextChangedListener(textWatcher);
        cardioInput.addTextChangedListener(textWatcher);
        mixedInput.addTextChangedListener(textWatcher);

        dateText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int year = currentDate.get(Calendar.YEAR);
                int month = currentDate.get(Calendar.MONTH);
                int day = currentDate.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(CalculatorActivity.this, android.R.style.Theme_Material_Dialog_MinWidth, mdateSetListener, year, month, day);
//                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(getResources().getColor(android.R.color.transparent)));
                dialog.show();
            }
        });

        mdateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                currentDate = Calendar.getInstance();
                currentDate.set(Calendar.YEAR,year);
                currentDate.set(Calendar.MONTH,month);
                currentDate.set(Calendar.DAY_OF_MONTH, day);
                newEntry.setDate(currentDate);
                dateText.setText(formatter.format(currentDate.getTime()));
            }
        };


    }

    private void updateEntry(){
        EditText breakfastText = findViewById(R.id.breakfastTextInput);
        String breafastKJ = (breakfastText.getText().length() != 0) ? breakfastText.getText().toString() : 0 + "";
        newEntry.setBreakfast(breafastKJ);
        EditText lunchText = findViewById(R.id.lunchTextInput);
        String lunchKJ = (lunchText.getText().length() != 0) ? lunchText.getText().toString() : 0 + "";
        newEntry.setLunch(lunchKJ);
        EditText dinnerText = findViewById(R.id.dinnerTextInput);
        String dinnerKJ = (dinnerText.getText().length() != 0) ? dinnerText.getText().toString() : 0 + "";
        newEntry.setDinner(dinnerKJ);
        EditText snacksText = findViewById(R.id.snacksTextInput);
        String snacksKJ = (snacksText.getText().length() != 0) ? snacksText.getText().toString() : 0 + "";
        newEntry.setSnacks(snacksKJ);
        EditText weightliftingText = findViewById(R.id.weightliftingTextInput);
        String weightliftingKJ = (weightliftingText.getText().length() != 0) ? weightliftingText.getText().toString() : 0 + "";
        newEntry.setWeightlifting(weightliftingKJ);
        EditText cardioText = findViewById(R.id.cardioTextInput);
        String cardioKJ = (cardioText.getText().length() != 0) ? cardioText.getText().toString() : 0 + "";
        newEntry.setCardio(cardioKJ);
        EditText mixedText = findViewById(R.id.mixedTextInput);
        String mixedKJ = (mixedText.getText().length() != 0) ? mixedText.getText().toString() : 0 + "";
        newEntry.setMixed(mixedKJ);

    }

    private void updateTotals(){
        String foodTotal = (Integer.parseInt((breakfastInput.getText().length() != 0) ? breakfastInput.getText().toString() : 0 + "") + Integer.parseInt((lunchInput.getText().length() != 0) ? lunchInput.getText().toString() : 0 + "") + Integer.parseInt((dinnerInput.getText().length() != 0) ? dinnerInput.getText().toString() : 0 + "") + Integer.parseInt((snacksInput.getText().length() != 0) ? snacksInput.getText().toString() : 0 + "")+"");
        String exerciseTotal = (Integer.parseInt((weightliftingInput.getText().length() != 0) ? weightliftingInput.getText().toString() : 0 + "") + Integer.parseInt((cardioInput.getText().length() != 0) ? cardioInput.getText().toString() : 0 + "") + Integer.parseInt((mixedInput.getText().length() != 0) ? mixedInput.getText().toString() : 0 + "") + "");
        ((TextView) findViewById(R.id.foodTotalValueView)).setText(foodTotal);
        ((TextView) findViewById(R.id.exerciseTotalValueView)).setText(exerciseTotal);
        ((TextView) findViewById(R.id.foodTotalNKIView)).setText(foodTotal);
        ((TextView) findViewById(R.id.exerciseTotalNKIView)).setText(exerciseTotal);
        ((TextView) findViewById(R.id.NKITotalView)).setText((Integer.parseInt(foodTotal) - Integer.parseInt(exerciseTotal)) + "");

    }

    private void saveData() {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        Collections.sort(entryArrayList);
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

    private void edit() {
        breakfastInput.setText(newEntry.getBreakfast());
        lunchInput.setText(newEntry.getLunch());
        dinnerInput.setText(newEntry.getDinner());
        snacksInput.setText(newEntry.getSnacks());
        weightliftingInput.setText(newEntry.getWeightlifting());
        cardioInput.setText(newEntry.getCardio());
        mixedInput.setText(newEntry.getMixed());
    }

    @Override
    public void onBackPressed()
    {
        //do whatever you want the 'Back' button to do
        //as an example the 'Back' button is set to start a new Activity named 'NewActivity'
        if (intent.getExtras().getString("Uniqid").equals("From_overview")) {

        }else if(intent.getExtras().getString("Uniqid").equals("From_diary")){

        }
    }

    public void goToDiary(){

    }

    public void goToOverview(){

    }

}
