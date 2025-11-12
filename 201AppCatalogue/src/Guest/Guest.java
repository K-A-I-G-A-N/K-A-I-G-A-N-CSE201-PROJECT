package Guest;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

public class Guest {

    //app page
    public static void guestAccess() {
       

        // Main app window
        JFrame appPage = new JFrame("App Catalogue");
        appPage.setSize(800, 600);
        appPage.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        appPage.setLocationRelativeTo(null);
        appPage.setResizable(false); // 🔹 prevents full-screen resizing
        appPage.getContentPane().setBackground(new Color(255, 240, 220));
        appPage.setLayout(null);

        // Add search bar at top
        @SuppressWarnings("unused")
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
