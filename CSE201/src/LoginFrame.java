import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import Admin.Admin;
import Admin.App_Approval_Form;
import Moderator.Moderator;
import java.io.*;
import java.net.URI;
import java.util.*;
import java.util.List;
 
public class LoginFrame {
    private static JButton guestAccessButton;
    private static JButton logInButton;
    private static JButton createAccountButton;
    private static JTextField usernameField;
    private static JPasswordField passwordField;
    private static JLabel messageLabel;

    public static void main(String[] args) {
        JFrame login = new JFrame();
        login.setTitle("K A I G A N App Catalogue Log In");
        login.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        login.setSize(550, 550);
        login.setResizable(false);
        login.getContentPane().setBackground(new Color(255, 214, 178));
        login.setLayout(null);

        JLabel titleLabel = new JLabel("Log In or Create Account");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));
        titleLabel.setBounds(130, 40, 400, 40);
        login.add(titleLabel);

        JLabel userLabel = new JLabel("Username:");
        userLabel.setBounds(100, 150, 100, 30);
        login.add(userLabel);

        usernameField = new JTextField();
        usernameField.setBounds(200, 150, 200, 30);
        login.add(usernameField);

        JLabel passLabel = new JLabel("Password:");
        passLabel.setBounds(100, 200, 100, 30);
        login.add(passLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(200, 200, 200, 30);
        login.add(passwordField);

        messageLabel = new JLabel("");
        messageLabel.setBounds(100, 250, 400, 30);
        messageLabel.setForeground(Color.RED);
        login.add(messageLabel);

        logInButton = new JButton("Login");
        logInButton.setBounds(100, 300, 100, 30);
        login.add(logInButton);

        createAccountButton = new JButton("Create Account");
        createAccountButton.setBounds(210, 300, 150, 30);
        login.add(createAccountButton);

        guestAccessButton = new JButton("Guest");
        guestAccessButton.setBounds(370, 300, 100, 30);
        login.add(guestAccessButton);
       
        logInButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                determine(login);
            }
        });

        createAccountButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                createAccount();
            }
        });

        guestAccessButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                goToGuest(login);
            }
        });

        login.setVisible(true);
    }

    // App data class
    static class App {
        String name, company, platforms, link, price, genre, description;

        public App(String[] fields) {
            name = fields[0];
            company = fields[1];
            platforms = fields[2];
            link = fields[3];
            price = fields[4];
            genre = fields.length > 5 ? fields[5] : "";
            description = fields.length > 6 ? fields[6] : "";
        }
    }

    // Load apps from App Database.csv
    public static List<App> loadAppsFromCSV(String filename) {
        List<App> apps = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String header = br.readLine(); // skip header line
            String line;
            while ((line = br.readLine()) != null) {
                // Split CSV, careful for commas inside quotes
                String[] fields = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
                if (fields.length < 7) fields = Arrays.copyOf(fields, 7);
                apps.add(new App(fields));
            }
        } catch (Exception ex) { ex.printStackTrace(); }
        return apps;
    }

    //Checking for Username and Password
    public static void determine(JFrame login) {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());

        if (username.isEmpty() || password.isEmpty()) {
            messageLabel.setText("Please fill in both fields.");
            return;
        }

        if (username.equals("admin") && password.equals("admin123")) {
            messageLabel.setForeground(Color.GREEN);
            messageLabel.setText("Admin login successful!");
            login.dispose();
            Admin.adminAccess();
            return;
        }
        
        if (username.equals("moderator") && password.equals("mod123")) {
            messageLabel.setForeground(Color.GREEN);
            messageLabel.setText("Moderator login successful!");
            login.dispose();
            Moderator.main(null);
            return;
        }

        boolean found = false;
        try {
            File file = new File("accounts.txt");
            if (!file.exists()) {
                messageLabel.setText("No users found. Please create an account.");
                return;
            }
            Scanner reader = new Scanner(file);
            while (reader.hasNextLine()) {
                String line = reader.nextLine();
                String[] parts = line.split(",");
                if (parts.length == 2 && parts[0].equals(username) && parts[1].equals(password)) {
                    found = true;
                    break;
                }
            }
            reader.close();

            if (found) {
                messageLabel.setForeground(Color.GREEN);
                messageLabel.setText("Login successful!");
                goToAppPage(login, false);
            } else {
                messageLabel.setForeground(Color.RED);
                messageLabel.setText("Incorrect username or password.");
            }
        } catch (IOException e) {
            messageLabel.setText("Error reading user file.");
        }
    }

    public static void createAccount() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());

        if (username.isEmpty() || password.isEmpty()) {
            messageLabel.setText("Please enter both username and password.");
            return;
        }

        try {
            File file = new File("accounts.txt");
            if (!file.exists()) file.createNewFile();

            Scanner reader = new Scanner(file);
            while (reader.hasNextLine()) {
                String[] parts = reader.nextLine().split(",");
                if (parts.length > 0 && parts[0].equals(username)) {
                    messageLabel.setText("Username already exists.");
                    reader.close();
                    return;
                }
            }
            reader.close();

            FileWriter writer = new FileWriter(file, true);
            writer.write(username + "," + password + "\n");
            writer.close();

            messageLabel.setForeground(Color.GREEN);
            messageLabel.setText("Account created successfully!");
        } catch (IOException e) {
            messageLabel.setText("Error saving account.");
        }
    }

    public static void goToGuest(JFrame login) {
        goToAppPage(login, true);

        
    }

    public static void goToAppPage(JFrame login, boolean isGuest) {
        login.dispose();

        JFrame appPage = new JFrame("App Catalogue");
        appPage.setSize(900, 630);
        appPage.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        appPage.setLocationRelativeTo(null);
        appPage.setResizable(false);
        appPage.getContentPane().setBackground(new Color(255, 240, 220));
        appPage.setLayout(null);

        // Load database and select top apps
        List<App> allApps = loadAppsFromCSV("App Database.csv");
        List<App> topApps = new ArrayList<>();
        for (App app : allApps) {
            String appLower = app.name.toLowerCase();
            if (appLower.contains("whatsapp") || appLower.contains("instagram") || appLower.contains("tiktok")) {
                topApps.add(app);
            }
        }

        int y = 170;
        int gap = 290;
        int x = 36;
        for (int i = 0; i < topApps.size(); i++) {
            App app = topApps.get(i);

            JPanel card = new JPanel(null);
            card.setBackground(Color.WHITE);
            card.setBounds(x + i * gap, y, 260, 240); // Increased card height for icon

            card.setBorder(BorderFactory.createLineBorder(new Color(70, 130, 180), 2));

            // App icon
            String iconFile = app.name.toLowerCase().contains("whatsapp") ? "sm_5b321c99945a2.jpg" :
                              app.name.toLowerCase().contains("instagram") ? "2227.jpg" :
                              app.name.toLowerCase().contains("tiktok") ? "10464230.png" : "default.png";
            // Make sure your icons are at the project root or use a full path if needed!
            ImageIcon icon = new ImageIcon(iconFile);
            JLabel iconLabel = new JLabel();
            iconLabel.setIcon(new ImageIcon(icon.getImage().getScaledInstance(48, 48, Image.SCALE_SMOOTH)));
            iconLabel.setBounds(10, 14, 48, 48);
            card.add(iconLabel);

            JLabel nameLabel = new JLabel(app.name, SwingConstants.LEFT);
            nameLabel.setFont(new Font("Arial", Font.BOLD, 17));
            nameLabel.setBounds(68, 14, 180, 22);
            card.add(nameLabel);

            JLabel genreLabel = new JLabel("Category: " + app.genre, SwingConstants.LEFT);
            genreLabel.setFont(new Font("Arial", Font.PLAIN, 13));
            genreLabel.setBounds(68, 38, 180, 18);
            card.add(genreLabel);

            JTextArea descLabel = new JTextArea(app.description);
            descLabel.setEditable(false);
            descLabel.setWrapStyleWord(true);
            descLabel.setLineWrap(true);
            descLabel.setFont(new Font("Arial", Font.PLAIN, 12));
            descLabel.setBounds(10, 70, 240, 70);
            descLabel.setOpaque(false);
            card.add(descLabel);

            JButton openBtn = new JButton("View Details");
            openBtn.setBounds(65, 155, 130, 32);
            openBtn.addActionListener(e -> openAppDescription(appPage, app.name));
            card.add(openBtn);

            appPage.add(card);
        }

        SearchBar searchBar = new SearchBar(appPage, isGuest);

        appPage.setVisible(true);
    }

    public static void openAppDescription(JFrame parentFrame, String appName) {
        List<App> allApps = loadAppsFromCSV("App Database.csv");
        App found = null;
        for (App app : allApps) {
            if (app.name.equalsIgnoreCase(appName)) {
                found = app;
                break;
            }
        }

        if (found == null) return;

        JFrame descFrame = new JFrame(appName + " - Description");
        descFrame.setSize(380, 280);
        descFrame.setLocationRelativeTo(parentFrame);
        descFrame.setLayout(null);

        JLabel nameLabel = new JLabel(found.name, SwingConstants.CENTER);
        nameLabel.setFont(new Font("Arial", Font.BOLD, 16));
        nameLabel.setBounds(30, 10, 300, 25);
        descFrame.add(nameLabel);

        JTextArea info = new JTextArea();
        info.setEditable(false);
        info.setLineWrap(true);
        info.setWrapStyleWord(true);
        info.setFont(new Font("Arial", Font.PLAIN, 12));
        info.setBounds(20, 45, 330, 150);

        StringBuilder text = new StringBuilder();

        String company = "";
        if (found.company != null) { 
            company = found.company;
            text.append("Company: ").append(company);
        }

        String platforms = "";
        if (found.platforms != null) {
            platforms = found.platforms;
            text.append("\nPlatforms: ").append(platforms);
        }

        String price = "";
        if (found.price != null) { 
        	price = found.price;
            text.append("\nPrice: ").append(price);
        }

        String genre = "";
        if (found.genre != null) { 
        	genre = found.genre;
            text.append("\nCategory: ").append(genre);
        }
        
        String description = "";
        if (found.description != null) {
        	description = found.description;
            text.append("\n\nDescription: ").append(description);
        }
        
        String link = "";
        if (found.link != null) { 
            link = found.link;
            text.append("\n\nApp Store: [").append(link).append("]");
        }
        info.setText(text.toString());
        descFrame.add(info);


        JButton openButton = new JButton("Open App Platform");
        openButton.setBounds(85, 210, 180, 30);
        descFrame.add(openButton);

        final String urlText = found.link;
        openButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    String url = urlText;
                    if (url == null || url.isEmpty()) {
                        JOptionPane.showMessageDialog(descFrame, "No link available.");
                        return;
                    }
                    if (!url.startsWith("http")) url = "https://" + url;
                    java.awt.Desktop.getDesktop().browse(new URI(url));
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(descFrame, "Unable to open link.");
                }
            }
        });

        descFrame.setVisible(true);
    }


    class User {
        private String username;
        private String password;

        public User(String username, String password) {
            this.username = username;
            this.password = password;
        }
        public String getUsername() {
            return username;
        }
        public String getPassword() {
            return password;
        }
    }
}
