import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
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

    // checking login info
    public static void determine(JFrame login) {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());

        if (username.isEmpty() || password.isEmpty()) {
            messageLabel.setText("Please fill in both fields.");
            return;
        }
        
        boolean found = false;
        try {  //Reads the text file for saved username and password
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

    // Creating account
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

    // Giving access to Guess
    public static void goToGuest(JFrame login) {
        goToAppPage(login);
    }

    // page to app display
    public static void goToAppPage(JFrame login) {
        login.dispose();

        JFrame appPage = new JFrame("App Catalogue");
        appPage.setSize(550, 550);
        appPage.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        appPage.setLocationRelativeTo(null);
        appPage.getContentPane().setBackground(new Color(255, 240, 220));

        JLabel label = new JLabel("Welcome! Apps will be displayed here soon.", SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.PLAIN, 18));
        appPage.add(label);

        appPage.setVisible(true);
    }
}
