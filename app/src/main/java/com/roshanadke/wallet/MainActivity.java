package com.roshanadke.wallet;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    LinearLayout btn_add_exp, btn_show_exp;
    private DatabaseHelper databaseHelper;
    List<PieEntry> pieEntries;
    private Cursor c;
    float tot = 0f;

    public static final int[] colors = {
            Color.rgb(193, 37, 82), Color.rgb(255, 102, 0), Color.rgb(245, 199, 0),
            Color.rgb(106, 150, 31), Color.rgb(179, 100, 53), Color.rgb(179, 48, 80)
    };


    final int[] MY_COLORS = {Color.rgb(192,0,0), Color.rgb(255,0,0), Color.rgb(255,192,0),
            Color.rgb(127,127,127), Color.rgb(146,208,80), Color.rgb(0,176,80), Color.rgb(79,129,189)};
    ArrayList<Integer> colors1 = new ArrayList<Integer>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setIcon(R.drawable.wallet_logo);
        btn_add_exp = findViewById(R.id.btn_add_expense);
        btn_show_exp = findViewById(R.id.btn_show_expense);

        databaseHelper =new DatabaseHelper(this);

        btn_add_exp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), add_expense.class);
                startActivity(i);
            }
        });

        btn_show_exp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), show_expense.class);
                startActivity(i);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpPieData();
    }

    private void setUpPieData() {

        pieEntries = new ArrayList<>();
        c = databaseHelper.showData();
        if (c.getCount() != 0) {

            counterMethod("Food");
            counterMethod("Travel");
            counterMethod("Clothes");
            counterMethod("Mobile");
            counterMethod("Others");

            showPieChart();

        } else {
            Toast.makeText(getApplicationContext(), "No Data found for list ...", Toast.LENGTH_SHORT).show();
        }

    }

    private void counterMethod(String strCat){
        tot= 0f;
        c.moveToFirst();
        do {

            String category = c.getString(2);
            String amt = c.getString(4);
            if(category.equals(strCat)){
                tot += Float.parseFloat(amt);
            }
        } while (c.moveToNext());
        pieEntries.add(new PieEntry(tot, strCat));
    }

    private void showPieChart() {
        for(int c: MY_COLORS) colors1.add(c);

        PieDataSet pieDataSet = new PieDataSet(pieEntries, "Example Pie Chart");
        pieDataSet.setColors(colors1);
        pieDataSet.setValueTextSize(6f);            // changes percentage's text size(y-axis value)
        PieData data = new PieData(pieDataSet);

        //get the chart
        PieChart chart = findViewById(R.id.pieChart);
        chart.setData(data);
        chart.animateXY(1500, 1500);
        //chart.setDrawEntryLabels(false);
        chart.setEntryLabelColor(Color.WHITE);
        chart.setEntryLabelTextSize(8f);       //chnages label's text size
        chart.getDescription().setEnabled(false);  //hides description label
        chart.invalidate();
    }
}
