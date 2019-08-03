package com.example.assignment1.entry;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.assignment1.DiaryEntry;
import com.example.assignment1.R;
import com.example.assignment1.calculator.CalculatorActivity;
import com.example.assignment1.main.MainActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class EntryActivity extends AppCompatActivity {
    ArrayList<DiaryEntry> entryArrayList;
    DiaryEntry diaryEntry;
    Intent intent;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadData();
        setContentView(R.layout.activity_entry);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        intent = this.getIntent();

        if (intent.getExtras().getString("Uniqid").equals("From_calculator") || intent.getExtras().getString("Uniqid").equals("From_overview") || intent.getExtras().getString("Uniqid").equals("From_diary")) {
            diaryEntry = entryArrayList.get(Integer.parseInt(intent.getExtras().getString("index")));
        }
        displayData();

        final SimpleDateFormat formatter = new SimpleDateFormat("EEE d MMM yyyy");
        TextView dateText = findViewById(R.id.dateText);
        dateText.setText(formatter.format(diaryEntry.getDate().getTime()));

        if (intent.getExtras().getString("index").equals("0")) {
            Button prevButton = findViewById(R.id.previousButton);
            prevButton.setVisibility(View.INVISIBLE);
        } else if (intent.getExtras().getString("index").equals((entryArrayList.size() - 1) + "")) {
            Button nextButton = findViewById(R.id.nextButton);
            nextButton.setVisibility(View.INVISIBLE);
        }

        Button buttonOverview = findViewById(R.id.overviewButton);
        buttonOverview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToOverview();
            }
        });

        Button buttonEdit = findViewById(R.id.editButton);
        buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToCalculator("edit");
            }
        });

        Button buttonNext = findViewById(R.id.nextButton);
        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent viewNext = new Intent(getApplicationContext(), EntryActivity.class);
                viewNext.putExtra("Uniqid", "From_diary");
                String index = (entryArrayList.indexOf(diaryEntry) + 1) + "";
                viewNext.putExtra("index", index);

                startActivity(viewNext);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();
            }
        });

        Button buttonPrevious = findViewById(R.id.previousButton);
        buttonPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent viewPrevious = new Intent(getApplicationContext(), EntryActivity.class);
                viewPrevious.putExtra("Uniqid", "From_diary");
                String index = (entryArrayList.indexOf(diaryEntry) - 1) + "";
                viewPrevious.putExtra("index", index);

                startActivity(viewPrevious);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                finish();
            }
        });

    }

    @Override
    public void onBackPressed() {
        //do whatever you want the 'Back' button to do
        //as an example the 'Back' button is set to start a new Activity named 'NewActivity'
        goToOverview();
    }

    public void goToOverview() {
        Intent viewOverview = new Intent(getApplicationContext(), MainActivity.class);
        viewOverview.putExtra("Uniqid", "From_diary");

        startActivity(viewOverview);
        finish();
    }

    public void goToCalculator(String back) {
        Intent viewEdit = new Intent(getApplicationContext(), CalculatorActivity.class);
        viewEdit.putExtra("Uniqid", "From_diary");
        String index = entryArrayList.indexOf(diaryEntry) + "";
        if (back.equals("edit")) {
            viewEdit.putExtra("edit", back);
        }
        viewEdit.putExtra("index", index);

        startActivity(viewEdit);
        finish();
    }


    private void displayData() {
        ((TextView) findViewById(R.id.breakfastValueView)).setText(diaryEntry.getBreakfast());
        ((TextView) findViewById(R.id.lunchValueView)).setText(diaryEntry.getLunch());
        ((TextView) findViewById(R.id.dinnerValueView)).setText(diaryEntry.getDinner());
        ((TextView) findViewById(R.id.snacksValueView)).setText(diaryEntry.getSnacks());
        ((TextView) findViewById(R.id.foodTotalValueView)).setText(diaryEntry.getFoodKJ() + " kJ");
        ((TextView) findViewById(R.id.weightliftingValueView)).setText(diaryEntry.getWeightlifting());
        ((TextView) findViewById(R.id.cardioValueView)).setText(diaryEntry.getCardio());
        ((TextView) findViewById(R.id.mixedValueView)).setText(diaryEntry.getMixed());
        ((TextView) findViewById(R.id.exerciseTotalValueView)).setText(diaryEntry.getExerciseKJ() + " kJ");
        ((TextView) findViewById(R.id.foodTotalNKIView)).setText(diaryEntry.getFoodKJ());
        ((TextView) findViewById(R.id.exerciseTotalNKIView)).setText(diaryEntry.getExerciseKJ());
        ((TextView) findViewById(R.id.NKITotalView)).setText(diaryEntry.getNKI() + " kJ");

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
            }
        }).start();
    }

}
