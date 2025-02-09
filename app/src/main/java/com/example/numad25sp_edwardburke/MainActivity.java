package com.example.numad25sp_edwardburke;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

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

        // Find the button by ID
        Button button1 = findViewById(R.id.button1);
        Button button2 = findViewById(R.id.button2);

        // Set click listener for the button
        button1.setOnClickListener(v -> {
            // Show a toast message
            Toast.makeText(MainActivity.this, "Edward Burke burke.e@northeastern.edu", Toast.LENGTH_SHORT).show();
        });

        // Set click listener for the button
        button2.setOnClickListener(new View.OnClickListener(){
            // Go to QuickCalc
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, QuickCalc.class);
                startActivity(intent);
            }
        });
    }
}