package com.example.individual3;

import android.content.Intent;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import android.widget.Toast;
import java.util.List;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class ListActivity extends AppCompatActivity {

    private ListView listViewMonths;
    private DBHelper dbHelper;
    private Button buttonBackToHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        listViewMonths = findViewById(R.id.listViewMonths);
        buttonBackToHome = findViewById(R.id.buttonBackToHome);
        dbHelper = new DBHelper(this);

        // Retrieve months from SQLite
        List<String> months = dbHelper.getAllMonths();

        if (months.isEmpty()) {
            Toast.makeText(this, "Tiada rekod bil ditemui!", Toast.LENGTH_SHORT).show();
        }

        // Create adapter with WHITE text for ListView items
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, months) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView textView = view.findViewById(android.R.id.text1);
                textView.setTextColor(getResources().getColor(android.R.color.white)); // Set text to white
                return view;
            }
        };

        listViewMonths.setAdapter(adapter);

        // List item click - navigate to BillDetailActivity
        listViewMonths.setOnItemClickListener((parent, view, position, id) -> {
            String selectedMonth = months.get(position);
            BillData billData = dbHelper.getBillDetails(selectedMonth);

            if (billData != null) {
                Intent intent = new Intent(ListActivity.this, BillDetailActivity.class);
                intent.putExtra("billData", billData);
                startActivity(intent);
            } else {
                Toast.makeText(ListActivity.this, "Data tidak ditemui untuk bulan ini!", Toast.LENGTH_SHORT).show();
            }
        });

        // Back button - return to MainActivity
        buttonBackToHome.setOnClickListener(v -> {
            Intent intent = new Intent(ListActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });
    }
}