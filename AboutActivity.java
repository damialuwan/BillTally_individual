package com.example.individual3;

import android.content.Intent;
import android.os.Bundle;
import android.net.Uri;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about); // Link to XML file

        // Set student information
        TextView textViewInfo = findViewById(R.id.textViewInfo);
        textViewInfo.setText("Full Name: NURNEESA DAMIA BINTI MOHD HILMI\n" +
                "Student ID: 2024745557\n" +
                "Course Code: ICT602 - MOBILE TECHNOLOGY AND DEVELOPMENT\n"
                );

        Button buttonGitHub = findViewById(R.id.buttonGitHub);
        buttonGitHub.setOnClickListener(v -> {
            String url = "https://github.com/damialuwan/BillTally_individual";
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(url));
            startActivity(intent);
        });
        // Initialize Back Button inside `onCreate()`
        Button buttonBack = findViewById(R.id.buttonBack);
        buttonBack.setOnClickListener(v -> {
            Intent intent = new Intent(AboutActivity.this, MainActivity.class);
            startActivity(intent);
            finish(); // Closes AboutActivity after navigating
        });
    }
}