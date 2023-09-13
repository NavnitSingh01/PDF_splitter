package Navnit;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import org.apache.pdfbox.multipdf.Splitter;
import org.apache.pdfbox.pdmodel.PDDocument;
import java.util.List;
import java.util.Iterator;

public class First {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            createAndShowGUI();
        });
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("PDF Splitter");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());

        JLabel label = new JLabel("Select PDF File:");
        panel.add(label);

        JTextField textField = new JTextField(20);
        panel.add(textField);

        JButton browseButton = new JButton("Browse");
        panel.add(browseButton);

        JButton splitButton = new JButton("Split PDF");
        panel.add(splitButton);

        JTextArea logTextArea = new JTextArea(10, 30);
        JScrollPane scrollPane = new JScrollPane(logTextArea);
        panel.add(scrollPane);

        browseButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int result = fileChooser.showOpenDialog(frame);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    textField.setText(selectedFile.getAbsolutePath());
                }
            }
        });

        splitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String inputFilePath = textField.getText();

                try {
                    PDDocument document = PDDocument.load(new File(inputFilePath));

                    // Splitter Class
                    Splitter splitting = new Splitter();

                    // Splitting the pages into multiple PDFs
                    List<PDDocument> pages = splitting.split(document);

                    // Using an iterator to traverse all pages
                    Iterator<PDDocument> iterator = pages.iterator();

                    int pageCount = 1;
                    while (iterator.hasNext()) {
                        PDDocument page = iterator.next();
                        String outputPath = "output_page_" + pageCount + ".pdf";
                        page.save(outputPath);
                        page.close();
                        logTextArea.append("Page " + pageCount + " saved as " + outputPath + "\n");
                        pageCount++;
                    }

                    logTextArea.append("PDF Split Successfully.\n");

                    // Close the main document
                    document.close();
                } catch (IOException ex) {
                    logTextArea.append("Error: " + ex.getMessage() + "\n");
                }
            }
        });

        frame.getContentPane().add(panel);
        frame.pack();
        frame.setVisible(true);
    }
}
