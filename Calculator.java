import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Calculator extends JFrame implements ActionListener {
    private JTextField textField;
    private JButton[] numberButtons;
    private JButton[] functionButtons;
    private JButton eqButton, clrButton, delButton, dotButton;
    private JPanel panel;
    private String expression = "";

    public Calculator() {
        setTitle("Calculator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 600);
        setLayout(null);
        setResizable(false);
        
        textField = new JTextField();
        textField.setBounds(10, 10, 370, 50);
        add(textField);

        numberButtons = new JButton[10];
        for (int i = 0; i < 10; i++) {
            numberButtons[i] = new JButton(String.valueOf(i));
            numberButtons[i].addActionListener(this);
        }

        functionButtons = new JButton[4];
        functionButtons[0] = new JButton("+");
        functionButtons[1] = new JButton("-");
        functionButtons[2] = new JButton("*");
        functionButtons[3] = new JButton("/");

        for (JButton button : functionButtons) {
            button.addActionListener(this);
        }

        eqButton = new JButton("=");
        clrButton = new JButton("C");
        delButton = new JButton("Del");
        dotButton = new JButton(".");

        eqButton.addActionListener(this);
        clrButton.addActionListener(this);
        delButton.addActionListener(this);
        dotButton.addActionListener(this);

        panel = new JPanel();
        panel.setBounds(10, 70, 370, 480);
        panel.setLayout(new GridLayout(5, 4, 10, 10));

        panel.add(numberButtons[1]);
        panel.add(numberButtons[2]);
        panel.add(numberButtons[3]);
        panel.add(functionButtons[0]);
        panel.add(numberButtons[4]);
        panel.add(numberButtons[5]);
        panel.add(numberButtons[6]);
        panel.add(functionButtons[1]);
        panel.add(numberButtons[7]);
        panel.add(numberButtons[8]);
        panel.add(numberButtons[9]);
        panel.add(functionButtons[2]);
        panel.add(dotButton);
        panel.add(numberButtons[0]);
        panel.add(eqButton);
        panel.add(functionButtons[3]);
        panel.add(clrButton);
        panel.add(delButton);

        add(panel);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        for (int i = 0; i < 10; i++) {
            if (e.getSource() == numberButtons[i]) {
                expression += i;
                textField.setText(expression);
            }
        }

        if (e.getSource() == dotButton) {
            if (!expression.endsWith(".") && !expression.contains(".")) {
                expression += ".";
                textField.setText(expression);
            }
        }

        if (e.getSource() == clrButton) {
            expression = "";
            textField.setText(expression);
        }

        if (e.getSource() == delButton) {
            if (!expression.isEmpty()) {
                expression = expression.substring(0, expression.length() - 1);
                textField.setText(expression);
            }
        }

        for (int i = 0; i < 4; i++) {
            if (e.getSource() == functionButtons[i]) {
                expression += " " + functionButtons[i].getText() + " ";
                textField.setText(expression);
            }
        }

        if (e.getSource() == eqButton) {
            try {
                double result = evaluateExpression(expression);
                textField.setText(expression + " = " + result);
                expression = String.valueOf(result);
            } catch (ArithmeticException | NumberFormatException ex) {
                textField.setText("Error");
                expression = "";
            }
        }
    }

    private double evaluateExpression(String expression) {
        String[] tokens = expression.split("\\s+");
        double result = Double.parseDouble(tokens[0]);
        char lastOperator = ' ';

        for (String token : tokens) {
            if (token.equals("+") || token.equals("-") || token.equals("*") || token.equals("/")) {
                lastOperator = token.charAt(0);
            } else {
                double operand = Double.parseDouble(token);

                switch (lastOperator) {
                    case '+':
                        result += operand;
                        break;
                    case '-':
                        result -= operand;
                        break;
                    case '*':
                        result *= operand;
                        break;
                    case '/':
                        if (operand != 0) {
                            result /= operand;
                        } else {
                            throw new ArithmeticException("Cannot divide by zero");
                        }
                        break;
                    default:
                        result = operand;
                }
            }
        }
        return result;
    }

    public static void main(String[] args) {
        new Calculator();
    }
}
