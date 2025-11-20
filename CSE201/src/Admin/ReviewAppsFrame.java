package Admin;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

public class ReviewAppsFrame extends JFrame {
    public ReviewAppsFrame() {
        setTitle("Submit a Review for an App");
        setSize(480, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        DefaultListModel<String> appList2 = new DefaultListModel<>();
        JList<String> appList = new JList<>(appList2);

        try (BufferedReader br = new BufferedReader(new FileReader("App Database.csv"))) {
            String header = br.readLine();
            String line;
            while ((line = br.readLine()) != null) {
                String[] fields = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
                appList2.addElement(fields[0].trim());
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Failed to load apps.");
        }

        JTextArea commentArea = new JTextArea();
        commentArea.setWrapStyleWord(true);
        commentArea.setLineWrap(true);
        commentArea.setFont(new Font("Arial", Font.PLAIN, 14));
        JScrollPane commentScroll = new JScrollPane(commentArea);

        JButton submitBtn = new JButton("Submit Review");
        submitBtn.addActionListener(e -> {
            String selectedApp = appList.getSelectedValue();
            String comment = commentArea.getText().trim();
            if (selectedApp == null || comment.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please select an app and enter a comment.");
                return;
            }
            try (FileWriter fw = new FileWriter("src/Moderator/reviewmod.txt", true)) {
                fw.write(selectedApp + ":\n");
                fw.write(comment + "\n");
                fw.write("~\n");
                JOptionPane.showMessageDialog(this, "Review submitted for moderation!");
                commentArea.setText("");
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error saving review.");
            }
        });

        JPanel top = new JPanel(new BorderLayout());
        top.add(new JLabel("Select an app:"), BorderLayout.NORTH);
        top.add(new JScrollPane(appList), BorderLayout.CENTER);

        JPanel bottom = new JPanel(new BorderLayout());
        bottom.add(new JLabel("Enter your comment:"), BorderLayout.NORTH);
        bottom.add(commentScroll, BorderLayout.CENTER);
        bottom.add(submitBtn, BorderLayout.SOUTH);

        add(top, BorderLayout.WEST);
        add(bottom, BorderLayout.CENTER);

        setVisible(true);
    }
}
