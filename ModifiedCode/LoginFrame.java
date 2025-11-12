import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.net.URI;
import java.util.*;

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

        // ----- Simple action listeners -----
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

    // ---------- LOGIN CHECK ----------
    public static void determine(JFrame login) {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());

        if (username.isEmpty() || password.isEmpty()) {
            messageLabel.setText("Please fill in both fields.");
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
                goToAppPage(login);
            } else {
                messageLabel.setForeground(Color.RED);
                messageLabel.setText("Incorrect username or password.");
            }
        } catch (IOException e) {
            messageLabel.setText("Error reading user file.");
        }
    }

    // ---------- CREATE ACCOUNT ----------
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
        goToAppPage(login);
    }

    //app page
    public static void goToAppPage(JFrame login) {
        // Close login window
        login.dispose();

        // Main app window
        JFrame appPage = new JFrame("App Catalogue");
        appPage.setSize(800, 600);
        appPage.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        appPage.setLocationRelativeTo(null);
        appPage.setResizable(false); // 🔹 prevents full-screen resizing
        appPage.getContentPane().setBackground(new Color(255, 240, 220));
        appPage.setLayout(null);

        // Add search bar at top
        SearchBar searchBar = new SearchBar(appPage);

        JPanel appIcon = new JPanel() {
            private Image image = new ImageIcon("sm_5b321c99945a2.jpg").getImage()
                    .getScaledInstance(120, 120, Image.SCALE_SMOOTH);

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Round the corners (20 = curve radius)
                Shape clip = new java.awt.geom.RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 20, 20);
                g2.setClip(clip);

                g2.drawImage(image, 0, 0, getWidth(), getHeight(), this);
                g2.dispose();
            }
        };
        appIcon.setBounds(55, 200, 120, 120);
        appIcon.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // 🔹 Clicking the app image opens description
        appIcon.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                openAppDescription(appPage, "WhatsApp");
            }
        });

        // Add everything
        appPage.add(appIcon);
        appPage.setVisible(true);
    }

    
   

    //description of app
    public static void openAppDescription(JFrame parentFrame, String appName) {
        JFrame descFrame = new JFrame(appName + " - Description");
        descFrame.setSize(400, 400);
        descFrame.setLocationRelativeTo(parentFrame);
        descFrame.getContentPane().setBackground(new Color(240, 230, 210));
        descFrame.setLayout(null);
        descFrame.setResizable(false);

        JLabel title = new JLabel(appName, SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 22));
        title.setBounds(50, 30, 300, 40);
        descFrame.add(title);

        JTextArea description = new JTextArea();
        description.setWrapStyleWord(true);
        description.setLineWrap(true);
        description.setEditable(false);
        description.setFont(new Font("Arial", Font.PLAIN, 14));
        description.setBounds(30, 90, 320, 150);

        // You can later load app-specific descriptions from a file.
        if (appName.equalsIgnoreCase("WhatsApp")) {
            description.setText("WhatsApp allows messaging, voice, and video calls across the world.");
        } else if (appName.equalsIgnoreCase("Kaigan")) {
            description.setText("Kaigan is an app catalogue platform developed by students for students.");
        } else {
            description.setText("Description not available for this app.");
        }

        descFrame.add(description);

        JButton linkButton = new JButton("Go to App Platform");
        linkButton.setBounds(100, 270, 180, 40);
        descFrame.add(linkButton);

        linkButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    if (appName.equalsIgnoreCase("WhatsApp")) {
                        Desktop.getDesktop().browse(new java.net.URI("https://www.whatsapp.com/"));
                    } else if (appName.equalsIgnoreCase("Kaigan")) {
                        Desktop.getDesktop().browse(new java.net.URI("https://example.com/kaigan"));
                    } else {
                        JOptionPane.showMessageDialog(descFrame, "No link available for this app.");
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(descFrame, "Unable to open link.");
                }
            }
        });

        descFrame.setVisible(true);
    }

}
