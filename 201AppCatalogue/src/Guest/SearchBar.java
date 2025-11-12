package Guest;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

public class SearchBar {

    private JTextField searchField;
    private JButton searchButton;
    private JLabel resultLabel;

    public SearchBar(JFrame parentFrame) {
        // App Store background
        parentFrame.getContentPane().setBackground(new Color(236, 239, 250)); // soft purple/blue

        // Header Bar
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(null);
        headerPanel.setBackground(new Color(57, 95, 201));
        headerPanel.setBounds(0, 0, 600, 60);
        JLabel headerLabel = new JLabel("App Store");
        headerLabel.setFont(new Font("Segoe UI", Font.BOLD, 30));
        headerLabel.setForeground(Color.WHITE);
        headerLabel.setBounds(20, 8, 250, 40);
        headerPanel.add(headerLabel);
        parentFrame.add(headerPanel);

        // Search "Card"
        JPanel searchCard = new JPanel();
        searchCard.setLayout(null);
        searchCard.setBackground(Color.WHITE);
        searchCard.setBounds(100, 90, 400, 80);
        searchCard.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(100, 200, 180), 2),
            BorderFactory.createEmptyBorder(8, 8, 8, 8)
        ));

        JLabel searchLabel = new JLabel("Search:");
        searchLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        searchLabel.setForeground(new Color(70, 130, 180));
        searchLabel.setBounds(20, 10, 80, 30);
        searchCard.add(searchLabel);

        searchField = new JTextField();
        searchField.setBounds(100, 10, 170, 30);
        searchField.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        searchField.setBorder(BorderFactory.createLineBorder(new Color(173, 216, 230), 2));
        searchCard.add(searchField);

        searchButton = new JButton("Enter");
        searchButton.setBounds(280, 10, 90, 30);
        searchButton.setBackground(new Color(57, 95, 201));
        searchButton.setForeground(Color.WHITE);
        searchButton.setFocusPainted(false);
        searchButton.setFont(new Font("Segoe UI", Font.BOLD, 15));
        searchButton.setBorder(BorderFactory.createLineBorder(new Color(90, 160, 230), 1, true));
        searchCard.add(searchButton);

        parentFrame.add(searchCard);

        // Large bordered panel
        JPanel borderPanel = new JPanel();
        borderPanel.setLayout(null);
        borderPanel.setBackground(Color.WHITE); 
        borderPanel.setBounds(40, 170, 600, 200); // x=40 (left), y=170 (top), width=520, height=160
        borderPanel.setBorder(BorderFactory.createLineBorder(new Color(57, 95, 201), 4));
        parentFrame.add(borderPanel);

        resultLabel = new JLabel("");
        resultLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        resultLabel.setBounds(20, 20, 420, 40);
        borderPanel.add(resultLabel);

        // Go Back button
        JButton goBackButton = new JButton("← Go Back");
        goBackButton.setBounds(20, 500, 120, 35);
        goBackButton.setBackground(new Color(231, 76, 60));
        goBackButton.setForeground(Color.WHITE);
        goBackButton.setFont(new Font("Segoe UI", Font.BOLD, 15));
        goBackButton.setFocusPainted(false);
        goBackButton.setBorder(BorderFactory.createLineBorder(new Color(210, 70, 50), 1, true));
        parentFrame.add(goBackButton);

        goBackButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                parentFrame.dispose();
                Guest.guestAccess();
            }
        });

        // Search Button logic (shows description page if found)
        searchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String query = searchField.getText().trim().toLowerCase();
                if (query.isEmpty()) {
                    resultLabel.setForeground(new Color(231, 76, 60));
                    resultLabel.setText("Please enter an app name.");
                    return;
                }

                String foundApp = findAppInFile(query);

                if (foundApp != null) {
                    resultLabel.setForeground(new Color(46, 204, 113));
                    resultLabel.setText("Opening app description...");
                    Guest.openAppDescription(parentFrame, foundApp); // Opens app description page
                } else {
                    resultLabel.setForeground(new Color(231, 76, 60));
                    resultLabel.setText("No app found!");
                }
            }
        });
    }

    private String findAppInFile(String query) {
        try {
            File file = new File("apps.txt");
            if (!file.exists()) {
                return null;
            }
            Scanner reader = new Scanner(file);
            while (reader.hasNextLine()) {
                String line = reader.nextLine().trim();
                if (line.equalsIgnoreCase(query)) {
                    reader.close();
                    return line;
                }
            }
            reader.close();
        } catch (IOException e) {
            System.out.println("Error reading file.");
        }
        return null;
    }
}
