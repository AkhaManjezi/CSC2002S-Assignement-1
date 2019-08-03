package com.example.assignment1.calculator;

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

import androidx.appcompat.app.AppCompatActivity;

import com.example.assignment1.DiaryEntry;
import com.example.assignment1.R;
import com.example.assignment1.entry.EntryActivity;
import com.example.assignment1.main.MainActivity;
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
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadData();
        setContentView(R.layout.activity_calculator);

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
                saveData();
                currentIndex = entryArrayList.indexOf(newEntry);
                goToDiary();
            }
        });

        intent = this.getIntent();
        if (intent.getExtras().getString("Uniqid").equals("From_diary")) {
            currentIndex = Integer.parseInt(intent.getExtras().getString("index"));
            newEntry = entryArrayList.get(currentIndex);
            dateText.setText(formatter.format(newEntry.getDate().getTime()));
            currentDate = newEntry.getDate();
            edit();
            updateTotals();
        } else {
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
                dialog.show();
            }
        });

        mdateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                currentDate = Calendar.getInstance();
                currentDate.set(Calendar.YEAR, year);
                currentDate.set(Calendar.MONTH, month);
                currentDate.set(Calendar.DAY_OF_MONTH, day);
                newEntry.setDate(currentDate);
                dateText.setText(formatter.format(currentDate.getTime()));
            }
        };


    }

    private void updateEntry() {
        String breafastKJ = (breakfastInput.getText().length() != 0) ? breakfastInput.getText().toString() : 0 + "";
        newEntry.setBreakfast(breafastKJ);
        String lunchKJ = (lunchInput.getText().length() != 0) ? lunchInput.getText().toString() : 0 + "";
        newEntry.setLunch(lunchKJ);
        String dinnerKJ = (dinnerInput.getText().length() != 0) ? dinnerInput.getText().toString() : 0 + "";
        newEntry.setDinner(dinnerKJ);
        String snacksKJ = (snacksInput.getText().length() != 0) ? snacksInput.getText().toString() : 0 + "";
        newEntry.setSnacks(snacksKJ);
        String weightliftingKJ = (weightliftingInput.getText().length() != 0) ? weightliftingInput.getText().toString() : 0 + "";
        newEntry.setWeightlifting(weightliftingKJ);
        String cardioKJ = (cardioInput.getText().length() != 0) ? cardioInput.getText().toString() : 0 + "";
        newEntry.setCardio(cardioKJ);
        String mixedKJ = (mixedInput.getText().length() != 0) ? mixedInput.getText().toString() : 0 + "";
        newEntry.setMixed(mixedKJ);
        if (currentIndex != -1) {
            entryArrayList.set(currentIndex, newEntry);
        } else {
            entryArrayList.add(newEntry);
        }
        Collections.sort(entryArrayList);

    }

    private void updateTotals() {
        new Thread(new Runnable() {
            public void run() {
                // a potentially time consuming task
                final String foodTotal = (Integer.parseInt((breakfastInput.getText().length() != 0) ? breakfastInput.getText().toString() : 0 + "") + Integer.parseInt((lunchInput.getText().length() != 0) ? lunchInput.getText().toString() : 0 + "") + Integer.parseInt((dinnerInput.getText().length() != 0) ? dinnerInput.getText().toString() : 0 + "") + Integer.parseInt((snacksInput.getText().length() != 0) ? snacksInput.getText().toString() : 0 + "") + "");
                final String exerciseTotal = (Integer.parseInt((weightliftingInput.getText().length() != 0) ? weightliftingInput.getText().toString() : 0 + "") + Integer.parseInt((cardioInput.getText().length() != 0) ? cardioInput.getText().toString() : 0 + "") + Integer.parseInt((mixedInput.getText().length() != 0) ? mixedInput.getText().toString() : 0 + "") + "");
                final String NKITotal = (Integer.parseInt(foodTotal) - Integer.parseInt(exerciseTotal)) + "";
                final TextView foodTotalValueView = findViewById(R.id.foodTotalValueView);
                final TextView exerciseTotalValueView = findViewById(R.id.exerciseTotalValueView);
                final TextView foodTotalNKIView = findViewById(R.id.foodTotalNKIView);
                final TextView exerciseTotalNKIView = findViewById(R.id.exerciseTotalNKIView);
                final TextView NKITotalView = findViewById(R.id.NKITotalView);

                foodTotalValueView.post(new Runnable() {
                    public void run() {
                        foodTotalValueView.setText(foodTotal + " kJ");
                    }
                });
                exerciseTotalValueView.post(new Runnable() {
                    public void run() {
                        exerciseTotalValueView.setText(exerciseTotal + " kJ");
                    }
                });
                foodTotalNKIView.post(new Runnable() {
                    public void run() {
                        foodTotalNKIView.setText(foodTotal + " kJ");
                    }
                });
                exerciseTotalNKIView.post(new Runnable() {
                    public void run() {
                        exerciseTotalNKIView.setText(exerciseTotal + " kJ");
                    }
                });
                NKITotalView.post(new Runnable() {
                    public void run() {
                        NKITotalView.setText(NKITotal + " kJ");
                    }
                });
            }
        }).start();

    }

    private void saveData() {
        new Thread(new Runnable() {
            public void run() {
                Gson gson = new Gson();
                String json = gson.toJson(entryArrayList);
                editor.putString("task list", json);
                editor.apply();
            }
        }).start();
    }

    private void loadData() {
        new Thread(new Runnable() {
            public void run() {
                sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
                Gson gson = new Gson();
                String json = sharedPreferences.getString("task list", null);
                Type type = new TypeToken<ArrayList<DiaryEntry>>() {
                }.getType();
                entryArrayList = gson.fromJson(json, type);

                if (entryArrayList == null) {
                    entryArrayList = new ArrayList<>();
                }
                editor = sharedPreferences.edit();
            }
        }).start();
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
    public void onBackPressed() {
        //do whatever you want the 'Back' button to do
        //as an example the 'Back' button is set to start a new Activity named 'NewActivity'
        if (intent.getExtras().getString("Uniqid").equals("From_overview")) {
            goToOverview();
        } else if (intent.getExtras().getString("Uniqid").equals("From_diary")) {
            if (intent.getExtras().getString("edit").equals("back")) {
                goToOverview();
            } else if (intent.getExtras().getString("edit").equals("edit")) {
                goToDiary();
            }
        }
    }

    public void goToDiary() {
        Intent viewEntry = new Intent(getApplicationContext(), EntryActivity.class);
        viewEntry.putExtra("Uniqid", "From_calculator");
        viewEntry.putExtra("index", (currentIndex) + "");

        startActivity(viewEntry);
        finish();
    }

    public void goToOverview() {
        Intent viewOverview = new Intent(getApplicationContext(), MainActivity.class);
        viewOverview.putExtra("Uniqid", "From_calculator");

        startActivity(viewOverview);
        finish();

    }

}
