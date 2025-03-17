package com.example.numad25sp_edwardburke;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class PrimeFinder extends AppCompatActivity {
    private volatile boolean isSearching = false;
    private Thread primeThread;
    private int currentNumber = 3;
    private int latestPrime = 2;
    private TextView currentNumberText;
    private TextView latestPrimeText;
    private Switch pacifierSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prime_finder);

        Button findPrimesButton = findViewById(R.id.button_find_primes);
        Button terminateButton = findViewById(R.id.button_terminate_search);
        currentNumberText = findViewById(R.id.text_current_number);
        latestPrimeText = findViewById(R.id.text_latest_prime);
        pacifierSwitch = findViewById(R.id.switch_pacifier);

        if (savedInstanceState != null) {
            currentNumber = savedInstanceState.getInt("currentNumber", 3);
            latestPrime = savedInstanceState.getInt("latestPrime", 2);
            pacifierSwitch.setChecked(savedInstanceState.getBoolean("pacifierState", false));
            isSearching = savedInstanceState.getBoolean("isSearching", false);
        }

        updateUI();

        findPrimesButton.setOnClickListener(v -> startPrimeSearch());
        terminateButton.setOnClickListener(v -> stopPrimeSearch());

        if (isSearching) {
            startPrimeSearch();
        }
    }

    private void startPrimeSearch() {
        if (isSearching && primeThread != null && primeThread.isAlive()) return;
        isSearching = true;
        primeThread = new Thread(() -> {
            while (isSearching) {
                if (isPrime(currentNumber)) {
                    latestPrime = currentNumber;
                    runOnUiThread(this::updateUI);
                }
                currentNumber += 2;
                runOnUiThread(this::updateUI);
            }
        });
        primeThread.start();
    }

    private void stopPrimeSearch() {
        isSearching = false;
        if (primeThread != null) {
            primeThread.interrupt();
        }
    }

    private boolean isPrime(int num) {
        if (num < 2) return false;
        for (int i = 2; i * i <= num; i++) {
            if (num % i == 0) return false;
        }
        return true;
    }

    private void updateUI() {
        currentNumberText.setText("Checking: " + currentNumber);
        latestPrimeText.setText("Latest Prime: " + latestPrime);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("currentNumber", currentNumber);
        outState.putInt("latestPrime", latestPrime);
        outState.putBoolean("pacifierState", pacifierSwitch.isChecked());
        outState.putBoolean("isSearching", isSearching);
    }

    @Override
    public void onBackPressed() {
        if (isSearching) {
            new AlertDialog.Builder(this)
                    .setTitle("Confirm Exit")
                    .setMessage("Search is running. Do you want to terminate it?")
                    .setPositiveButton("Yes", (dialog, which) -> {
                        stopPrimeSearch();
                        super.onBackPressed();
                        finish();
                    })
                    .setNegativeButton("No", null)
                    .show();
        } else {
            super.onBackPressed();
        }
    }
}
