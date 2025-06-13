package com.example.individual3;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.graphics.Color;
import android.widget.AdapterView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private Spinner spinnerMonth;
    private EditText editTextUsage;
    private TextView textViewResult;
    private Button buttonCalculate;
    private Button buttonRebate0, buttonRebate2, buttonRebate5;
    private int selectedRebate = 0; // Default rebate
    private Button buttonSave;
    private DBHelper dbHelper;

    private Button buttonAbout;
    private Button buttonListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DBHelper(this);

        // Hubungkan komponen UI
        spinnerMonth = findViewById(R.id.spinnerMonth);
        editTextUsage = findViewById(R.id.editTextUsage);
        textViewResult = findViewById(R.id.textViewResult);
        buttonCalculate = findViewById(R.id.buttonCalculate);
        buttonRebate0 = findViewById(R.id.buttonRebate0);
        buttonRebate2 = findViewById(R.id.buttonRebate2);
        buttonRebate5 = findViewById(R.id.buttonRebate5);
        buttonSave = findViewById(R.id.buttonSave);
        buttonListView = findViewById(R.id.buttonListView);
        buttonAbout = findViewById(R.id.buttonAbout); // Initialize button

        // Setup Spinner with White Text
        String[] months = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, months);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMonth.setAdapter(adapter);

        spinnerMonth.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView) parent.getChildAt(0)).setTextColor(Color.WHITE); // Set selected text color to white
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        // Set listener untuk rebate buttons
        buttonRebate0.setOnClickListener(v -> selectRebate(0));
        buttonRebate2.setOnClickListener(v -> selectRebate(2));
        buttonRebate5.setOnClickListener(v -> selectRebate(5));

        // Apabila pengguna tekan "Kira Bil"
        buttonCalculate.setOnClickListener(v -> calculateBill());

        // Apabila pengguna tekan "Simpan Data"
        buttonSave.setOnClickListener(v -> saveBillData());

        buttonListView.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ListActivity.class);
            startActivity(intent);
        });

        buttonAbout.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AboutActivity.class);
            startActivity(intent);
        });
    }

    private void selectRebate(int rebate) {
        selectedRebate = rebate;

        // Change background and text color dynamically
        buttonRebate0.setBackgroundColor(rebate == 0 ? 0xFFFFD700 : 0xFF00008B);
        buttonRebate0.setTextColor(rebate == 0 ? Color.BLACK : Color.WHITE);

        buttonRebate2.setBackgroundColor(rebate == 2 ? 0xFFFFD700 : 0xFF00008B);
        buttonRebate2.setTextColor(rebate == 2 ? Color.BLACK : Color.WHITE);

        buttonRebate5.setBackgroundColor(rebate == 5 ? 0xFFFFD700 : 0xFF00008B);
        buttonRebate5.setTextColor(rebate == 5 ? Color.BLACK : Color.WHITE);
    }

    private void calculateBill() {
        String usageText = editTextUsage.getText().toString();
        if (usageText.isEmpty()) {
            textViewResult.setText("Enter Electricity Unit kWh.");
            return;
        }

        int kWhUsed = Integer.parseInt(usageText);
        double totalCost = calculateTotalCost(kWhUsed);

        // Kiraan rebat
        double rebateAmount = (selectedRebate / 100.0) * totalCost;
        double finalBill = totalCost - rebateAmount;

        // Paparkan keputusan
        textViewResult.setText("TOTAL ELECTRICITY BILL: RM " + String.format("%.2f", finalBill));
    }

    private double calculateTotalCost(int kWhUsed) {
        double totalCost = 0.0;

        if (kWhUsed <= 200) {
            totalCost = kWhUsed * 0.218;
        } else if (kWhUsed <= 300) {
            totalCost = (200 * 0.218) + ((kWhUsed - 200) * 0.334);
        } else if (kWhUsed <= 600) {
            totalCost = (200 * 0.218) + (100 * 0.334) + ((kWhUsed - 300) * 0.516);
        } else {
            totalCost = (200 * 0.218) + (100 * 0.334) + (300 * 0.516) + ((kWhUsed - 600) * 0.571);
        }

        return totalCost;
    }

    private void saveBillData() {
        String month = spinnerMonth.getSelectedItem().toString();
        String usageText = editTextUsage.getText().toString();

        if (usageText.isEmpty()) {
            textViewResult.setText("Enter Electricity Unit kWh.");
            return;
        }

        int kWhUsed = Integer.parseInt(usageText);
        double totalCost = calculateTotalCost(kWhUsed);
        double rebateAmount = (selectedRebate / 100.0) * totalCost;
        double finalBill = totalCost - rebateAmount;

        dbHelper.saveBill(month, kWhUsed, totalCost, selectedRebate, finalBill);

        textViewResult.setText("Save: RM " + String.format("%.2f", finalBill));
    }
}