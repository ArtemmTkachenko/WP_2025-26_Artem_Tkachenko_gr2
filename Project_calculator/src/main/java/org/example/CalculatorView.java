package org.example;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class CalculatorView extends JFrame {

    public final JTextPane textPane;

    public final JButton[] numberButtons = new JButton[10];
    public final JButton addButton, subButton, mulButton, divButton;
    public final JButton decButton, equButton, delButton, clrButton, negButton;

    private final JButton[] functionButtons = new JButton[9];
    private final JPanel panel;

    Font myFont = new Font("Ink Free", Font.BOLD, 30);

    public CalculatorView() {
        super("Calculator MVC");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(420, 550);
        setLayout(null);

        textPane = new JTextPane();
        textPane.setBounds(50, 25, 300, 50);
        textPane.setFont(myFont);
        textPane.setEditable(false);
        textPane.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        addButton = new JButton("+");
        subButton = new JButton("-");
        mulButton = new JButton("*");
        divButton = new JButton("/");
        decButton = new JButton(".");
        equButton = new JButton("=");
        delButton = new JButton("Del");
        clrButton = new JButton("Clr");
        negButton = new JButton("(-)");

        functionButtons[0] = addButton;
        functionButtons[1] = subButton;
        functionButtons[2] = mulButton;
        functionButtons[3] = divButton;
        functionButtons[4] = decButton;
        functionButtons[5] = equButton;
        functionButtons[6] = delButton;
        functionButtons[7] = clrButton;
        functionButtons[8] = negButton;

        for (JButton button : functionButtons) {
            button.setFont(myFont);
            button.setFocusable(false);
        }

        for (int i = 0; i < 10; i++) {
            numberButtons[i] = new JButton(String.valueOf(i));
            numberButtons[i].setFont(myFont);
            numberButtons[i].setFocusable(false);
        }

        negButton.setBounds(50, 430, 100, 50);
        delButton.setBounds(150, 430, 100, 50);
        clrButton.setBounds(250, 430, 100, 50);

        panel = new JPanel();
        panel.setBounds(50, 100, 300, 300);
        panel.setLayout(new GridLayout(4, 4, 10, 10));

        panel.add(numberButtons[1]);
        panel.add(numberButtons[2]);
        panel.add(numberButtons[3]);
        panel.add(addButton);

        panel.add(numberButtons[4]);
        panel.add(numberButtons[5]);
        panel.add(numberButtons[6]);
        panel.add(subButton);

        panel.add(numberButtons[7]);
        panel.add(numberButtons[8]);
        panel.add(numberButtons[9]);
        panel.add(mulButton);

        panel.add(decButton);
        panel.add(numberButtons[0]);
        panel.add(equButton);
        panel.add(divButton);

        add(panel);
        add(negButton);
        add(delButton);
        add(clrButton);
        add(textPane);
    }


    public void setDisplayPlainBlack(String text) {
        StyledDocument doc = textPane.getStyledDocument();
        try {
            doc.remove(0, doc.getLength());

            Style normal = textPane.addStyle("normalBlack", null);
            StyleConstants.setForeground(normal, Color.BLACK);

            doc.insertString(doc.getLength(), text, normal);
        } catch (BadLocationException e) {
            textPane.setText("Error");
        }
    }

    public void setDisplayWithGreenRemainder(String integerPart, String remainderPart) {
        StyledDocument doc = textPane.getStyledDocument();
        try {
            doc.remove(0, doc.getLength());

            Style normal = textPane.addStyle("normal", null);
            StyleConstants.setForeground(normal, Color.BLACK);

            Style green = textPane.addStyle("green", null);
            StyleConstants.setForeground(green, Color.GREEN.darker());

            doc.insertString(doc.getLength(), integerPart, normal);

            if (remainderPart != null && !remainderPart.isEmpty()) {
                doc.insertString(doc.getLength(), remainderPart, green);
            }
        } catch (BadLocationException e) {
            textPane.setText("Error");
        }
    }

    public void addActionListener(ActionListener listener) {
        for (JButton button : functionButtons)
            button.addActionListener(listener);

        for (JButton button : numberButtons)
            button.addActionListener(listener);
    }
}
