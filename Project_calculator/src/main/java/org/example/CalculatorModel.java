package org.example;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public class CalculatorModel {

    private static final MathContext MC = new MathContext(30, RoundingMode.HALF_UP);

    public BigDecimal calculate(String a, String b, char operator) {
        BigDecimal x = new BigDecimal(a);
        BigDecimal y = new BigDecimal(b);

        switch (operator) {
            case '+': return x.add(y, MC);
            case '-': return x.subtract(y, MC);
            case '*': return x.multiply(y, MC);
            case '/':
                if (y.compareTo(BigDecimal.ZERO) == 0) {
                    throw new ArithmeticException("Division by zero");
                }
                return x.divide(y, MC);
            default:
                throw new IllegalArgumentException("Unknown operator: " + operator);
        }
    }
}
