package com.example.numad25sp_edwardburke;

import android.os.Bundle;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.widget.Button;
import android.content.Intent;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Find buttons by ID
        Button button1 = findViewById(R.id.button1);
        Button button2 = findViewById(R.id.button2);
        Button button3 = findViewById(R.id.button3);
        Button buttonPrimeFinder = findViewById(R.id.button_prime_finder);

        // Set click listener for the buttons
        button1.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, BioInfo.class);
            startActivity(intent);
        });

        button2.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, QuickCalc.class);
            startActivity(intent);
        });

        button3.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, Contacts.class);
            startActivity(intent);
        });

        buttonPrimeFinder.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, PrimeFinder.class);
            startActivity(intent);
        });
    }
}
