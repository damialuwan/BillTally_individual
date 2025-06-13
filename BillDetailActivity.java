package com.example.individual3;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class BillDetailActivity extends AppCompatActivity {

    private TextView textMonth, textUnit, textTotalCharges, textRebate, textFinalCost;
    private DBHelper dbHelper;

    Button buttonBackToList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_detail);

        textMonth = findViewById(R.id.textMonth);
        textUnit = findViewById(R.id.textUnit);
        textTotalCharges = findViewById(R.id.textTotalCharges);
        textRebate = findViewById(R.id.textRebate);
        textFinalCost = findViewById(R.id.textFinalCost);
        buttonBackToList = findViewById(R.id.buttonBackToList);

        // Retrieve the passed bill data
        BillData billData = (BillData) getIntent().getSerializableExtra("billData");

        if (billData != null) {
            textMonth.setText("Month: " + billData.month);
            textUnit.setText("Units: " + billData.kWhUsed + " kWh");
            textTotalCharges.setText("Total Charges: RM " + String.format("%.2f", billData.totalCharges));
            textRebate.setText("Rebate: " + billData.rebatePercentage + "%");
            textFinalCost.setText("Final Cost: RM " + String.format("%.2f", billData.finalCost));
        } else {
            textMonth.setText("Tiada data ditemui!");
        }

        buttonBackToList.setOnClickListener(v -> {
            Intent intent = new Intent(BillDetailActivity.this, ListActivity.class);
            startActivity(intent);
            finish(); // Closes BillDetailActivity after navigating
        });

    }
}
