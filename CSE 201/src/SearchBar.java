import javax.swing.*;

import Admin.ReviewAppsFrame;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import java.util.List;

public class SearchBar {

    private JTextField searchField;
    private JButton searchButton;
    private JLabel resultLabel;

    public SearchBar(JFrame parentFrame, boolean isGuest) {
        parentFrame.getContentPane().setBackground(new Color(236, 239, 250)); 

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

        resultLabel = new JLabel("");
        resultLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        resultLabel.setBounds(20, 50, 350, 30);
        resultLabel.setForeground(new Color(70, 130, 180));
        searchCard.add(resultLabel);
        parentFrame.add(searchCard);
        
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
                LoginFrame.main(new String[]{});
            }
        });
        
        JButton appFormButton = new JButton("Request New App");
        appFormButton.setBounds(320, 500, 180, 35); // adjust to your layout
        appFormButton.setBackground(new Color(34, 167, 240));
        appFormButton.setForeground(Color.WHITE);
        appFormButton.setFont(new Font("Arial", Font.BOLD, 15));
        appFormButton.setFocusPainted(false);

        parentFrame.add(appFormButton);

        if (isGuest) {
            appFormButton.setEnabled(true); 
            appFormButton.addActionListener(e -> {
                JOptionPane.showMessageDialog(parentFrame,
                    "Please create an account to request a new app.",
                    "Guest Access Restricted",
                    JOptionPane.WARNING_MESSAGE);
            });
        } else {
            appFormButton.addActionListener(e -> {
                App_Form_Appearance.main(new String[]{});
            });
        }
        
        JButton reviewAppsButton = new JButton("Review Apps");
        reviewAppsButton.setBounds(300, 540, 140, 35);
        parentFrame.add(reviewAppsButton);

        reviewAppsButton.addActionListener(e -> {
            new ReviewAppsFrame();
        });
        

            
        JButton filterButton = new JButton("Filter");
        filterButton.setBounds(550, 100, 80, 30); 
        filterButton.setBackground(new Color(100, 180, 220));
        filterButton.setForeground(Color.WHITE);
        filterButton.setFont(new Font("Arial", Font.BOLD, 15));
        filterButton.setFocusPainted(false);
        parentFrame.add(filterButton);

        filterButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                FilterGUI.main(new String[]{});
            }
        });

        searchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String query = searchField.getText().trim().toLowerCase();
                if (query.isEmpty()) {
                    resultLabel.setForeground(new Color(231, 76, 60));
                    resultLabel.setText("Please enter an app name.");
                    return;
                }

                String foundApp = findAppInDatabase(query);

                if (foundApp != null) {
                    resultLabel.setForeground(new Color(46, 204, 113));
                    resultLabel.setText("Opening app description for: " + foundApp);
                    LoginFrame.openAppDescription(parentFrame, foundApp); 
                } else {
                    resultLabel.setForeground(new Color(231, 76, 60));
                    resultLabel.setText("No app found!");
                }
            }
        });
    }
    
    private String findAppInDatabase(String query) {
        try {
            File file = new File("App Database.csv");
            if (!file.exists()) {
                return null;
            }
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String header = reader.readLine();
            String line;
            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
                String appName = fields[0].trim().toLowerCase();
                if (appName.equals(query)) {
                    reader.close();
                    return fields[0].trim();
                }
            }
            reader.close();
        } catch (IOException e) {
            System.out.println("Error reading App Database.csv.");
        }
        return null;
    }

	public List<String> searchApps(String string) {
		return null;
	}
}
