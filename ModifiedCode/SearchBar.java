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
        JLabel searchLabel = new JLabel("Search for an App:");
        searchLabel.setFont(new Font("Arial", Font.BOLD, 16));
        searchLabel.setBounds(150, 20, 200, 30);
        parentFrame.add(searchLabel);

        searchField = new JTextField();
        searchField.setBounds(300, 20, 150, 30);
        parentFrame.add(searchField);

        searchButton = new JButton("Search");
        searchButton.setBounds(460, 20, 80, 30);
        parentFrame.add(searchButton);

        resultLabel = new JLabel("");
        resultLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        resultLabel.setBounds(150, 60, 300, 30);
        parentFrame.add(resultLabel);

        // 🔹 Add listener for search button
        searchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String query = searchField.getText().trim().toLowerCase();
                if (query.isEmpty()) {
                    resultLabel.setText("Please enter an app name.");
                    return;
                }

                String foundApp = findAppInFile(query);
                if (foundApp != null) {
                    LoginFrame.openAppDescription(parentFrame, foundApp);
                } else {
                    resultLabel.setForeground(Color.RED);
                    resultLabel.setText("No app found with that name.");
                }
            }
        });
    }

    // 🔹 Reads apps.txt and checks if the query exists
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
                    return line; // Found app
                }
            }
            reader.close();
        } catch (IOException e) {
            System.out.println("Error reading apps.txt file.");
        }
        return null; // Not found
    }
}
