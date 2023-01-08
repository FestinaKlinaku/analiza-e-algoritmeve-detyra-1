import java.io.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Driver extends JFrame {

    // Algorithm related fields
    private int noItems;
    private double[] values;
    private double[] weights;
    private double knapsackSize;

    // GUI related fields
    private String START_TEXT = "0";
    private JTextField noItemsInput = new JTextField(START_TEXT, 15);
    private JTextField valuesInput = new JTextField(START_TEXT, 15);
    private JTextField weightsInput = new JTextField(START_TEXT, 15);
    private JTextField knapsackSizeInput = new JTextField(START_TEXT, 15);
    private ButtonGroup buttonGroup;
    private JButton geneticButton;
    private JButton greedyButton;
    private JRadioButton radioButton1;
    private JRadioButton radioButton2;
    private JTextArea textarea;
    public boolean isDivided;

    public Driver() {
        Container contentPane = getContentPane();
        contentPane.setLayout(new GridLayout(9, 1));
        JPanel blank = new JPanel(new FlowLayout());
        blank.add(new JLabel(""));
        contentPane.add(blank);
        JPanel p = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, -5));
        Font labelFont = new Font("Arial", Font.BOLD, 20);
        JLabel title = new JLabel("Knapsack Problem");
        title.setFont(labelFont);
        p.add(title);
        contentPane.add(p);
        JPanel p1 = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 0));
        p1.add(new JLabel("Number of items: "));
        p1.add(noItemsInput);
        contentPane.add(p1);
        JPanel p2 = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 0));
        p2.add(new JLabel("Values: "));
        p2.add(valuesInput);
        contentPane.add(p2);
        JPanel p3 = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 0));
        p3.add(new JLabel("Weights: "));
        p3.add(weightsInput);
        contentPane.add(p3);
        JPanel p4 = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 0));
        p4.add(new JLabel("Knapsack size: "));
        p4.add(knapsackSizeInput);
        contentPane.add(p4);
        JPanel radioContainer = new JPanel(new FlowLayout());
        radioButton1 = new JRadioButton("Are not divided");
        radioButton2 = new JRadioButton("Are divided");
        radioButton1.addActionListener(sliceActionListener);
        radioButton2.addActionListener(sliceActionListener);
        buttonGroup = new ButtonGroup();
        buttonGroup.add(radioButton1);
        buttonGroup.add(radioButton2);
        radioContainer.add(radioButton1);
        radioContainer.add(radioButton2);
        contentPane.add(radioContainer);
        JPanel p9 = new JPanel(new FlowLayout());
        greedyButton = new JButton("Greedy solution");
        greedyButton.addActionListener(e1 -> {
            writeInit("try.txt");
            readInit("try.txt");
            OpenGreedyAlgorithm();
        });
        p9.add(greedyButton);
        contentPane.add(p9);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 600);
        setResizable(false);
        setLocationRelativeTo(null);
        setTitle("Knapsack Problem");
        setVisible(true);
    }

    public static void main(String[] args) {
        new Driver();
    }

    public void readInit(String filename) {
        try {
            Scanner input = new Scanner(new File(filename));
            noItems = input.nextInt();
            values = new double[noItems];
            weights = new double[noItems];
            for (int i = 0; i < noItems; i++)
                values[i] = input.nextDouble();
            for (int i = 0; i < noItems; i++)
                weights[i] = input.nextDouble();
            knapsackSize = input.nextDouble();
        } catch (FileNotFoundException ex) {
            System.err.println("File not found!");
            System.exit(1);
        }
    }

    public void writeInit(String filename) {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(filename));
            bw.write(noItemsInput.getText());
            bw.newLine();
            bw.write(valuesInput.getText());
            bw.newLine();
            bw.write(weightsInput.getText());
            bw.newLine();
            bw.write(knapsackSizeInput.getText());
            bw.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void OpenGreedyAlgorithm() {
        openStat();
        Greedy knapsack = new Greedy(noItems, values, weights, knapsackSize, isDivided, textarea);
        this.textarea.append("Time spent on nonoseconds: " + knapsack.timeSpent);
    }

    public void openStat() {
        JFrame frame = new JFrame("Algorithm");
        this.textarea = new JTextArea(17, 40);
        final JScrollPane comp = new JScrollPane(this.textarea);
        final JPanel comp6 = new JPanel(new BorderLayout());
        comp6.add(comp, "Center");
        frame.setVisible(true);
        frame.setSize(500, 500);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.getContentPane().add(comp6);
    }

    ActionListener sliceActionListener = new ActionListener() {
        public void actionPerformed(ActionEvent actionEvent) {
            AbstractButton aButton = (AbstractButton) actionEvent.getSource();
            if (aButton.getText() == "Are divided") {
                isDivided = true;
            } else {
                isDivided = false;
            }
        }
    };
}
