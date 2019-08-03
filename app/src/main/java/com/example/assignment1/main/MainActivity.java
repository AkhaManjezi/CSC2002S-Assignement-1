package com.example.assignment1.main;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.assignment1.DiaryEntry;
import com.example.assignment1.DiaryEntryAdapter;
import com.example.assignment1.R;
import com.example.assignment1.calculator.CalculatorActivity;
import com.example.assignment1.entry.EntryActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private DiaryEntryAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    ArrayList<DiaryEntry> entryArrayList;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadData();
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        buildRecyclerView();

        ((TextView) findViewById(R.id.NKIAverageValue)).setText(NKIAverage());


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent newCalculation = new Intent(getApplicationContext(), CalculatorActivity.class);
                newCalculation.putExtra("Uniqid", "From_overview");

                startActivity(newCalculation);
            }
        });
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

    private String NKIAverage() {
        double total = 0;
        for (int i = 0; i < entryArrayList.size(); i++) {
            total += Double.parseDouble(entryArrayList.get(i).getNKI());
        }
        String output = (entryArrayList.size() == 0) ? "0.0 kJ" : String.format("%.2f kJ", total / entryArrayList.size());
        return output;
    }

    private void buildRecyclerView() {
        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new DiaryEntryAdapter(entryArrayList);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new DiaryEntryAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent viewEntry = new Intent(getApplicationContext(), EntryActivity.class);
                viewEntry.putExtra("Uniqid", "From_overview");
                viewEntry.putExtra("index", (position) + "");

                startActivity(viewEntry);
                finish();
            }
        });

    }
}
