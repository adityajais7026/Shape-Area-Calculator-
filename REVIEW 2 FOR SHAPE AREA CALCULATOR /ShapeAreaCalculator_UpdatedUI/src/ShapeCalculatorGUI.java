import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

public class ShapeCalculatorGUI extends JFrame {
    private JComboBox<String> shapeComboBox;
    private JPanel inputPanel, resultPanel, historyPanel;
    private JLabel resultLabel;
    private JTextField field1, field2;
    private JTextArea historyArea;
    private boolean darkMode = false;
    private JButton toggleThemeButton;
    private static final String HISTORY_FILE = "history.txt";

    public ShapeCalculatorGUI() {
        setTitle("Shape Area Calculator");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JLabel title = new JLabel("Shape Area Calculator", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 22));
        add(title, BorderLayout.NORTH);

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        shapeComboBox = new JComboBox<>(new String[]{"Circle", "Rectangle", "Triangle"});
        shapeComboBox.addActionListener(e -> updateInputFields());
        topPanel.add(new JLabel("Select Shape:"));
        topPanel.add(shapeComboBox);

        toggleThemeButton = new JButton("🌙 Dark Mode");
        toggleThemeButton.addActionListener(e -> toggleDarkMode());
        topPanel.add(toggleThemeButton);
        add(topPanel, BorderLayout.PAGE_START);

        inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(4, 2, 10, 10));
        add(inputPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        JButton calculateBtn = new JButton("Calculate Area");
        calculateBtn.addActionListener(e -> calculateArea());
        buttonPanel.add(calculateBtn);

        JButton clearBtn = new JButton("Clear History");
        clearBtn.addActionListener(e -> clearHistory());
        buttonPanel.add(clearBtn);

        resultLabel = new JLabel("Area: ");
        buttonPanel.add(resultLabel);
        add(buttonPanel, BorderLayout.SOUTH);

        historyPanel = new JPanel(new BorderLayout());
        historyPanel.setPreferredSize(new Dimension(200, getHeight()));
        historyPanel.setBorder(BorderFactory.createTitledBorder("History"));

        historyArea = new JTextArea();
        historyArea.setEditable(false);
        historyPanel.add(new JScrollPane(historyArea), BorderLayout.CENTER);
        add(historyPanel, BorderLayout.EAST);

        loadHistoryFromFile();
        updateInputFields();

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                saveHistoryToFile();
            }
        });
    }

    private void updateInputFields() {
        String shape = (String) shapeComboBox.getSelectedItem();
        inputPanel.removeAll();

        if ("Circle".equals(shape)) {
            field1 = new JTextField();
            inputPanel.add(new JLabel("Radius:"));
            field1.setToolTipText("Enter the radius of the circle");
            inputPanel.add(field1);
        } else if ("Rectangle".equals(shape)) {
            field1 = new JTextField();
            field2 = new JTextField();
            inputPanel.add(new JLabel("Length:"));
            field1.setToolTipText("Enter the length of the rectangle");
            inputPanel.add(field1);
            inputPanel.add(new JLabel("Width:"));
            field2.setToolTipText("Enter the width of the rectangle");
            inputPanel.add(field2);
        } else if ("Triangle".equals(shape)) {
            field1 = new JTextField();
            field2 = new JTextField();
            inputPanel.add(new JLabel("Base:"));
            field1.setToolTipText("Enter the base of the triangle");
            inputPanel.add(field1);
            inputPanel.add(new JLabel("Height:"));
            field2.setToolTipText("Enter the height of the triangle");
            inputPanel.add(field2);
        }

        inputPanel.revalidate();
        inputPanel.repaint();
    }

    private void calculateArea() {
        String shape = (String) shapeComboBox.getSelectedItem();
        try {
            double area = 0;
            String entry = "";
            if ("Circle".equals(shape)) {
                double r = Double.parseDouble(field1.getText());
                area = Math.PI * r * r;
                entry = String.format("Circle (r=%.2f) ➜ Area = %.2f", r, area);
            } else if ("Rectangle".equals(shape)) {
                double l = Double.parseDouble(field1.getText());
                double w = Double.parseDouble(field2.getText());
                area = l * w;
                entry = String.format("Rectangle (l=%.2f, w=%.2f) ➜ Area = %.2f", l, w, area);
            } else if ("Triangle".equals(shape)) {
                double b = Double.parseDouble(field1.getText());
                double h = Double.parseDouble(field2.getText());
                area = 0.5 * b * h;
                entry = String.format("Triangle (b=%.2f, h=%.2f) ➜ Area = %.2f", b, h, area);
            }
            resultLabel.setText(String.format("Area: %.2f", area));
            historyArea.append(entry + "\n");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter valid numeric inputs!", "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clearHistory() {
        historyArea.setText("");
    }

    private void loadHistoryFromFile() {
        try (BufferedReader br = new BufferedReader(new FileReader(HISTORY_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                historyArea.append(line + "\n");
            }
        } catch (IOException e) {
            // File may not exist yet; ignore
        }
    }

    private void saveHistoryToFile() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(HISTORY_FILE))) {
            bw.write(historyArea.getText());
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Failed to save history to file.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void toggleDarkMode() {
        darkMode = !darkMode;
        Color bg = darkMode ? Color.DARK_GRAY : UIManager.getColor("Panel.background");
        Color fg = darkMode ? Color.WHITE : Color.BLACK;
        getContentPane().setBackground(bg);
        for (Component c : getContentPane().getComponents()) {
            updateComponentColors(c, bg, fg);
        }
        toggleThemeButton.setText(darkMode ? "☀ Light Mode" : "🌙 Dark Mode");
        repaint();
    }

    private void updateComponentColors(Component comp, Color bg, Color fg) {
        comp.setBackground(bg);
        comp.setForeground(fg);
        if (comp instanceof Container) {
            for (Component child : ((Container) comp).getComponents()) {
                updateComponentColors(child, bg, fg);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ShapeCalculatorGUI().setVisible(true));
    }
}