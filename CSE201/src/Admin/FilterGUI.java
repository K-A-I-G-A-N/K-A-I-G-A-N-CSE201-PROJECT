package Admin;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;
import java.util.List;

public class FilterGUI extends JFrame {

    private JComboBox<String> categoryBox;
    private JButton searchButton;
    private JList<String> appNameList;
    private DefaultListModel<String> nameListModel;
    private List<App> allApps;

    // App class to hold app information
    static class App {
        String name, company, platforms, link, price, genre, description;

        App(String[] fields) {
            name = "";
            company = "";
            platforms = "";
            link = "";
            price = "";
            genre = "";
            description = "";

            if (fields.length > 0) name = fields[0];
            if (fields.length > 1) company = fields[1];
            if (fields.length > 2) platforms = fields[2];
            if (fields.length > 3) link = fields[3];
            if (fields.length > 4) price = fields[4];
            if (fields.length > 5) genre = fields[5];
            if (fields.length > 6) description = fields[6];
        }
    }


    public FilterGUI() {
        setTitle("App Category Filter");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(650, 420);
        setLayout(new BorderLayout());

        allApps = loadAppsFromCSV("App Database.csv");

        Set<String> genres = new TreeSet<>();
        for (App app : allApps) {
            if (app.genre != null && !app.genre.trim().isEmpty())
                genres.add(app.genre.trim());
        }

        JPanel topPanel = new JPanel(new FlowLayout());
        categoryBox = new JComboBox<>();
        categoryBox.addItem(""); 
        for (String genre : genres)
            categoryBox.addItem(genre);

        searchButton = new JButton("Search Category");
        topPanel.add(new JLabel("Category:"));
        topPanel.add(categoryBox);
        topPanel.add(searchButton);
        add(topPanel, BorderLayout.NORTH);

        nameListModel = new DefaultListModel<>();
        appNameList = new JList<>(nameListModel);
        appNameList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        appNameList.setVisibleRowCount(12);
        add(new JScrollPane(appNameList), BorderLayout.CENTER);

        searchButton.addActionListener(e -> {
            String cat = (String) categoryBox.getSelectedItem();
            nameListModel.clear();
            for (App app : allApps) {
                if (cat == null || cat.isEmpty() ||
                    (app.genre != null && app.genre.trim().equalsIgnoreCase(cat.trim()))) {
                    nameListModel.addElement(app.name);
                }
            }
            if (nameListModel.isEmpty()) {
                nameListModel.addElement("No apps found for category: " + cat);
            }
        });

        appNameList.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                if (evt.getClickCount() == 2) { 
                    int index = appNameList.locationToIndex(evt.getPoint());
                    if (index >= 0) {
                        String appName = nameListModel.getElementAt(index);
                        App found = null;
                        for (App app : allApps) {
                            if (app.name.equalsIgnoreCase(appName)) {
                                found = app;
                                break;
                            }
                        }
                        showAppDescription(FilterGUI.this, found);
                    }
                }
            }
        });

        setVisible(true);
    }

    // Loads apps from a CSV file
    private List<App> loadAppsFromCSV(String filename) {
        List<App> list = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String header = br.readLine(); // skip header
            String line;
            while ((line = br.readLine()) != null) {
                String[] fields = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
                if (fields.length < 7) fields = Arrays.copyOf(fields, 7);
                list.add(new App(fields));
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Failed to load database file!\n" + ex.getMessage());
        }
        return list;
    }

    // Shows all info in description format
    public static void showAppDescription(JFrame parent, App app) {
        if (app == null) return;

        JFrame frame = new JFrame(app.name);
        frame.setSize(380, 280);
        frame.setLocationRelativeTo(parent);
        frame.setLayout(null);

        JLabel nameLabel = new JLabel(app.name, SwingConstants.CENTER);
        nameLabel.setFont(new Font("Arial", Font.BOLD, 16));
        nameLabel.setBounds(30, 10, 300, 25);
        frame.add(nameLabel);

        JTextArea info = new JTextArea();
        info.setEditable(false);
        info.setLineWrap(true);
        info.setWrapStyleWord(true);
        info.setFont(new Font("Arial", Font.PLAIN, 12));
        info.setBounds(20, 45, 330, 150);

        StringBuilder text = new StringBuilder();
        text.append("Company: ").append(app.company == null ? "" : app.company)
            .append("\nPlatforms: ").append(app.platforms == null ? "" : app.platforms)
            .append("\nPrice: ").append(app.price == null ? "" : app.price)
            .append("\nCategory: ").append(app.genre == null ? "" : app.genre)
            .append("\n\nDescription: ").append(app.description == null ? "" : app.description)
            .append("\n\nApp Store: [").append(app.link == null ? "" : app.link).append("]");
        info.setText(text.toString());
        frame.add(info);

        JButton openButton = new JButton("Open App Platform");
        openButton.setBounds(85, 210, 180, 30);
        frame.add(openButton);

        final String urlText = app.link;
        openButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    String url = urlText;
                    if (url == null || url.isEmpty()) {
                        JOptionPane.showMessageDialog(frame, "No link available.");
                        return;
                    }
                    if (!url.startsWith("http")) url = "https://" + url;
                    java.awt.Desktop.getDesktop().browse(new java.net.URI(url));
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frame, "Unable to open link.");
                }
            }
        });

        frame.setVisible(true);
    }

    // Main method for running this frame
    public static void main(String[] args) {
        SwingUtilities.invokeLater(FilterGUI::new);
    }
}
