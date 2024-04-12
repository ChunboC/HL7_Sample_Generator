import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import javax.swing.*;
import java.awt.event.*;
/**
 * @author Chunbo Cheng
 */
public class Hl7GenApp {
    private static int numOperations;
    private static String desktopPath = System.getProperty("user.home") + "/Desktop/";
    private static String folderName = "queue/";
    private static String folderPath = desktopPath + folderName;
    private static File folder = new File(folderPath);

    public static void main(String[] args) {

        JFrame frame = new JFrame("HL7 Sample Generator");
        frame.setSize(450, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);

        JLabel label = new JLabel("Number of files to generate:");
        label.setBounds(20, 20, 200, 30);
        JLabel label2 = new JLabel("Customized path:");
        label2.setBounds(20, 50, 200, 30);
        frame.add(label);
        frame.add(label2);

        JTextField textField = new JTextField();
        JTextField textField1 = new JTextField();
        textField.setBounds(200, 20, 50, 30);
        textField1.setBounds(200, 50, 200, 30);
        frame.add(textField);
        frame.add(textField1);

        JButton button = new JButton("Generate HL7 Files");
        button.setBounds(80, 100, 150, 30);
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String input = textField.getText();
                String path = folderPath;
                try {
                    numOperations = Integer.parseInt(input);
                    if (numOperations > 0) {
                        JOptionPane.showMessageDialog(frame, "Generating " + numOperations + " HL7 files!\nAll sample files will be saved in queue folder on desktop.");

                        if (!folder.exists()) {
                            if (folder.mkdir()) {
                                System.out.println("queue folder created: " + folderPath);
                            } else {
                                System.out.println("Failed to create folder: " + folderPath);
                            }
                        } else {
                            System.out.println("Folder already exists: " + folderPath);
                        }

                        for (int i = 0; i < numOperations; i++) {
                            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
                            String timestamp = dateFormat.format(new Date());
                            String filename = "example_" + timestamp + "_" + i + ".hl7";
                            try (BufferedWriter writer = new BufferedWriter(new FileWriter(folderPath + filename))) {
                                Hl7Gen temp = new Hl7Gen();
                                temp.setName(i);
                                writer.write(temp.toString());
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
        frame.setVisible(true);
    }
}


