import java.awt.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.*;
/**
 * @version 2.0
 * @author Chunbo Cheng
 */
public class Hl7GenApp {
    private static int numOperations;
    private static String filePath = "";
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            createAndShowGUI();
        });
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("HL7 Sample Generator");
        frame.setSize(370, 380);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);

        JLabel label = new JLabel("Number of files to generate:");
        label.setBounds(20, 20, 200, 30);
        JLabel label2 = new JLabel("Path: select a destination...");
        label2.setBounds(20, 50, 500, 30);
        frame.add(label);
        frame.add(label2);

        JTextField textField = new JTextField();
        JTextField fNameField = new JTextField();
        JTextField lNameField = new JTextField();
        JTextField mrnField = new JTextField();
        JTextField accField = new JTextField();
        textField.setBounds(200, 20, 50, 30);
        fNameField.setBounds(170, 120, 160, 20);
        lNameField.setBounds(170, 150, 160, 20);
        mrnField.setBounds(170, 180, 160, 20);
        accField.setBounds(170, 210, 160, 20);
        frame.add(textField);
        frame.add(fNameField);
        frame.add(lNameField);
        frame.add(mrnField);
        frame.add(accField);

        JCheckBox incrementBox = new JCheckBox("auto increment for first name (different names)");
        JCheckBox fNameBox = new JCheckBox("change first name");
        JCheckBox lNameBox = new JCheckBox("change last name");
        JCheckBox mrnBox = new JCheckBox("change mrn");
        JCheckBox accBox = new JCheckBox("change accession");
        incrementBox.setBounds(20, 90, 300, 20);
        fNameBox.setBounds(20, 120, 130, 20);
        lNameBox.setBounds(20, 150, 130, 20);
        mrnBox.setBounds(20, 180, 130, 20);
        accBox.setBounds(20, 210, 130, 20);
        fNameBox.setSelected(false);
        lNameBox.setSelected(false);
        mrnBox.setSelected(false);
        accBox.setSelected(false);
        incrementBox.setSelected(true);
        frame.add(incrementBox);
        frame.add(fNameBox);
        frame.add(lNameBox);
        frame.add(mrnBox);
        frame.add(accBox);

        JButton selectFileButton = new JButton("Select File Destination");
        selectFileButton.setBounds(90, 250, 170, 30);
        selectFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent a) {
                selectFileDestination(frame);
                label2.setText("Path: " + filePath);
            }
        });
        frame.add(selectFileButton);

        JButton button = new JButton("Generate HL7 Files");
        button.setBounds(100, 290, 150, 30);
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String input = textField.getText();
                try {
                    numOperations = Integer.parseInt(input);
                    if (numOperations > 0) {
                        JOptionPane.showMessageDialog(frame, "Generating " + numOperations + " HL7 files!");
                        Hl7Gen temp = new Hl7Gen();
                        if (fNameBox.isSelected())
                            temp.setFirstName(fNameField.getText());
                        if (lNameBox.isSelected())
                            temp.setLastName(lNameField.getText());
                        if (mrnBox.isSelected())
                            temp.setMRN(Integer.parseInt(mrnField.getText()));
                        if (accBox.isSelected())
                            temp.setAccession(Integer.parseInt(accField.getText()));
                        String tempFirstName = temp.getFirstName();
                        String filename = "";
                        for (int i = 0; i < numOperations; i++) {
                            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
                            String timestamp = dateFormat.format(new Date());
                            if (incrementBox.isSelected())
                                filename = temp.getFirstName() + i + "_" + temp.getLastName() + "_" + timestamp + "_" + i + ".hl7";
                            else
                                filename = temp.getFirstName() + "_" + temp.getLastName() + "_" + timestamp + "_" + i + ".hl7";
                            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath + "\\" + filename))) {
                                if (incrementBox.isSelected())
                                    temp.incrementFirstName(i);
                                writer.write(temp.toString());
                                temp.setFirstName(tempFirstName);
                                //System.out.println("HL7 message has been written to the queue folder on desktop");
                            } catch (IOException ex1) {
                                System.err.println("Error writing to file: " + ex1.getMessage());
                            }
                        }
                    }
                    else
                        JOptionPane.showMessageDialog(frame, "Please enter a number larger than 0.");
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Please enter a valid number.");
                }
            }
        });
        frame.add(button);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private static void selectFileDestination(JFrame frame) {
        // Create a file chooser
        JFileChooser fileChooser = new JFileChooser();

        // Set dialog type to select a file or directory
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        // Show the file chooser dialog
        int result = fileChooser.showSaveDialog(frame);

        // If user selects a file/directory and clicks "Save"
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();

            // Get the path of the selected file
            filePath = selectedFile.getAbsolutePath();

            // Notify the user about the selected file path
            //JOptionPane.showMessageDialog(frame, "File destination selected: " + filePath);
        } else if (result == JFileChooser.CANCEL_OPTION) {
            JOptionPane.showMessageDialog(frame, "File selection canceled.");
        }
    }
}


