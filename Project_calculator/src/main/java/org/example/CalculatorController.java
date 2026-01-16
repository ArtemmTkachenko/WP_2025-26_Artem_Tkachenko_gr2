package org.example;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;

public class CalculatorController implements ActionListener {

    private final CalculatorModel model;
    private final CalculatorView view;

    private String num1 = null;
    private char operator = 0;

    private String currentInput = "";


    private boolean resultFrozen = false;

    public CalculatorController(CalculatorModel model, CalculatorView view) {
        this.model = model;
        this.view = view;
        view.addActionListener(this);
        updateDisplayPlain();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();

        for (int i = 0; i < 10; i++) {
            if (src == view.numberButtons[i]) {
                appendDigit(i);
                return;
            }
        }

        if (src == view.decButton) { appendDot(); return; }

        if (src == view.addButton) { setOperator('+'); return; }
        if (src == view.subButton) { setOperator('-'); return; }
        if (src == view.mulButton) { setOperator('*'); return; }
        if (src == view.divButton) { setOperator('/'); return; }

        if (src == view.equButton) { calculateAndShow(); return; }

        if (src == view.clrButton) { clearAll(); return; }

        if (src == view.delButton) { deleteLast(); return; }

        if (src == view.negButton) { toggleNegative(); }
    }

    private void updateDisplayPlain() {

        view.setDisplayPlainBlack(currentInput);
    }

    private void appendDigit(int digit) {
        if (resultFrozen) return;

        if (currentInput.equals("0")) currentInput = "";

        if (wouldBreakDecimalLimit()) return;

        currentInput += digit;
        updateDisplayPlain();
    }

    private void appendDot() {
        if (resultFrozen) return;

        if (currentInput.isEmpty()) {
            currentInput = "0.";
            updateDisplayPlain();
            return;
        }

        if (!currentInput.contains(".")) {
            currentInput += ".";
            updateDisplayPlain();
        }
    }

    private boolean wouldBreakDecimalLimit() {
        if (!currentInput.contains(".")) return false;
        int dotIndex = currentInput.indexOf('.');
        int decimals = currentInput.length() - dotIndex - 1;
        return decimals >= 5;
    }

    private void setOperator(char op) {

        if (resultFrozen) {
            num1 = currentInput;
            operator = op;

            currentInput = "";
            resultFrozen = false;

            updateDisplayPlain(); // ✅ black
            return;
        }

        if (currentInput.isEmpty() || currentInput.equals(".")) return;

        num1 = currentInput;
        operator = op;

        currentInput = "";
        updateDisplayPlain();
    }

    private void calculateAndShow() {
        try {
            if (num1 == null || operator == 0) return;
            if (currentInput.isEmpty() || currentInput.equals(".")) return;

            String num2 = currentInput;

            BigDecimal result = model.calculate(num1, num2, operator).stripTrailingZeros();

            if (result.scale() > 5) {
                view.setDisplayPlainBlack("Error");
                currentInput = "";
                num1 = null;
                operator = 0;
                resultFrozen = false;
                return;
            }

            String s = result.toPlainString();


            if (s.contains(".")) {
                String[] parts = s.split("\\.");
                String intPart = parts[0] + ".";
                String remainder = (parts.length > 1) ? parts[1] : "";
                view.setDisplayWithGreenRemainder(intPart, remainder);
            } else {
                view.setDisplayPlainBlack(s);
            }

            currentInput = s;
            num1 = null;
            operator = 0;
            resultFrozen = true;

        } catch (Exception ex) {
            view.setDisplayPlainBlack("Error");
            currentInput = "";
            num1 = null;
            operator = 0;
            resultFrozen = false;
        }
    }

    private void clearAll() {
        currentInput = "";
        num1 = null;
        operator = 0;
        resultFrozen = false;
        updateDisplayPlain();
    }

    private void deleteLast() {
        if (resultFrozen) return;
        if (currentInput.isEmpty()) return;
        currentInput = currentInput.substring(0, currentInput.length() - 1);
        updateDisplayPlain();
    }

    private void toggleNegative() {
        if (resultFrozen) return;
        if (currentInput.isEmpty()) return;

        if (currentInput.startsWith("-")) currentInput = currentInput.substring(1);
        else currentInput = "-" + currentInput;

        updateDisplayPlain();
    }

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            CalculatorModel model = new CalculatorModel();
            CalculatorView view = new CalculatorView();
            new CalculatorController(model, view);
            view.setVisible(true);
        });
    }
}
