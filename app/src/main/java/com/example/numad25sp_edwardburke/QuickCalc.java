package com.example.numad25sp_edwardburke;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class QuickCalc extends AppCompatActivity {

    private TextView calcDisplay;
    private StringBuilder expression;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quick_calc);

        calcDisplay = findViewById(R.id.calc_display);
        expression = new StringBuilder();

        // Set up button click listeners
        int[] buttonIds = {
                R.id.btn_0, R.id.btn_1, R.id.btn_2, R.id.btn_3, R.id.btn_4,
                R.id.btn_5, R.id.btn_6, R.id.btn_7, R.id.btn_8, R.id.btn_9,
                R.id.btn_add, R.id.btn_subtract, R.id.btn_equals, R.id.btn_backspace
        };

        for (int id : buttonIds) {
            Button button = findViewById(id);
            button.setOnClickListener(this::onButtonClick);
        }

    }

    private void onButtonClick(View view) {
        Button clickedButton = (Button) view;
        String buttonText = clickedButton.getText().toString();

        if (buttonText.equals("=")) {
            evaluateExpression();
        } else if (buttonText.equals("x")) {
            // Handle backspace (removes last character)
            if (expression.length() > 0) {
                expression.deleteCharAt(expression.length() - 1);
            }
        } else {
            // Append clicked character
            expression.append(buttonText);
        }

        // Update the display
        calcDisplay.setText(expression.length() > 0 ? expression.toString() : "CALC");
    }

    @SuppressLint("SetTextI18n")
    private void evaluateExpression() {
        try {
            int result = ExpressionEvaluator.evaluate(expression.toString());
            expression.setLength(0);
            expression.append(result);
            calcDisplay.setText(expression.toString());
        } catch (Exception e) {
            calcDisplay.setText("Error");
            expression.setLength(0);
        }
    }
}

