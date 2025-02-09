package com.example.numad25sp_edwardburke;

import java.util.Stack;

public class ExpressionEvaluator {

    public static int evaluate(String expression) {
        Stack<Integer> numbers = new Stack<>();
        Stack<Character> operations = new Stack<>();

        for (int i = 0; i < expression.length(); i++) {
            char c = expression.charAt(i);

            if (Character.isDigit(c)) {
                int num = 0;
                while (i < expression.length() && Character.isDigit(expression.charAt(i))) {
                    num = num * 10 + (expression.charAt(i) - '0');
                    i++;
                }
                i--; // Step back since loop moves one extra step
                numbers.push(num);
            } else if (c == '+' || c == '-') {
                while (!operations.isEmpty()) {
                    numbers.push(applyOperation(operations.pop(), numbers.pop(), numbers.pop()));
                }
                operations.push(c);
            }
        }

        while (!operations.isEmpty()) {
            numbers.push(applyOperation(operations.pop(), numbers.pop(), numbers.pop()));
        }

        return numbers.pop();
    }

    private static int applyOperation(char op, int b, int a) {
        switch (op) {
            case '+': return a + b;
            case '-': return a - b;
            default: return 0;
        }
    }
}
