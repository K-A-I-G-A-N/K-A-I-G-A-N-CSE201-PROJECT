import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JFileChooser;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class App_Form_Appearance {

	private static JButton uploadButton;

	private static JButton submitButton;

	private static JTextField appNameField;

	private static JTextField appCategoryField;

	private static JTextField appPublisherField;

	private static JTextField appPriceField;

	private static JTextField appLinkField;

	private static JTextArea appDescriptionField;

	private static JTextField appPlatField;

	private static File imageIcon;

	private static String appInfo;

	public static void main(String[] args) {

		JFrame appForm = new JFrame();
		appForm.setTitle("K A I G A N App Request Form");
		appForm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		appForm.setSize(600, 700);
		appForm.setResizable(false);
		appForm.setLocationRelativeTo(null);
		appForm.getContentPane().setBackground(new Color(57, 95, 201)); // Dark blue color
		// Light blue color: 70,130,180
		appForm.setLayout(null);

		// Title

		JLabel titleLabel = new JLabel("App Post Request Form");
		titleLabel.setFont(new Font("Arial", Font.BOLD, 22));
		titleLabel.setBounds(180, 20, 400, 40);

		titleLabel.setForeground(Color.WHITE);

		appForm.add(titleLabel);

		// App Name Field

		JLabel appNameLabel = new JLabel("App Name:");
		appNameLabel.setBounds(100, 100, 100, 30);
		appNameLabel.setFont(new Font("Arial", Font.BOLD, 16));
		appForm.add(appNameLabel);

		appNameLabel.setForeground(Color.WHITE);

		appNameField = new JTextField();
		appNameField.setBounds(200, 100, 200, 30);
		appNameField.setFont(new Font("Arial", Font.BOLD, 16));

		appForm.add(appNameField);

		// App Category Field

		JLabel catLabel = new JLabel("App Category:");
		catLabel.setBounds(75, 150, 200, 30);
		catLabel.setFont(new Font("Arial", Font.BOLD, 16));

		catLabel.setForeground(Color.WHITE);

		appForm.add(catLabel);

		appCategoryField = new JTextField();
		appCategoryField.setBounds(200, 150, 200, 30);
		appCategoryField.setFont(new Font("Arial", Font.BOLD, 16));
		appForm.add(appCategoryField);

		// Publisher

		JLabel pubLabel = new JLabel("App Publisher:");
		pubLabel.setBounds(75, 200, 200, 30);
		pubLabel.setFont(new Font("Arial", Font.BOLD, 16));

		pubLabel.setForeground(Color.WHITE);

		appForm.add(pubLabel);

		appPublisherField = new JTextField();
		appPublisherField.setBounds(200, 200, 200, 30);
		appPublisherField.setFont(new Font("Arial", Font.BOLD, 16));
		appForm.add(appPublisherField);

		// Price

		JLabel priceLabel = new JLabel("App Price:");
		priceLabel.setBounds(100, 250, 200, 30);
		priceLabel.setFont(new Font("Arial", Font.BOLD, 16));

		priceLabel.setForeground(Color.WHITE);

		appForm.add(priceLabel);

		appPriceField = new JTextField();
		appPriceField.setBounds(200, 250, 200, 30);
		appPriceField.setFont(new Font("Arial", Font.BOLD, 16));

		Numvalidator(appPriceField);

		appForm.add(appPriceField);

		// Link to external App Place

		JLabel appLinkLabel = new JLabel("Link to App Store:");
		appLinkLabel.setBounds(50, 300, 200, 30);
		appLinkLabel.setFont(new Font("Arial", Font.BOLD, 16));

		appLinkLabel.setForeground(Color.WHITE);

		appForm.add(appLinkLabel);

		appLinkField = new JTextField();
		appLinkField.setBounds(200, 300, 200, 30);
		appLinkField.setFont(new Font("Arial", Font.BOLD, 16));
		appForm.add(appLinkField);

		// App Description Field

		JLabel descLabel = new JLabel("App Description:");
		descLabel.setBounds(58, 350, 300, 30);
		descLabel.setFont(new Font("Arial", Font.BOLD, 16));

		descLabel.setForeground(Color.WHITE);

		appForm.add(descLabel);

		appDescriptionField = new JTextArea();

		appDescriptionField.setFont(new Font("Arial", Font.BOLD, 12));
		appDescriptionField.setLineWrap(true);
		appDescriptionField.setWrapStyleWord(true);

		JScrollPane scrollpane = new JScrollPane(appDescriptionField);
		scrollpane.setBounds(200, 350, 300, 75);

		appForm.add(scrollpane);

		// App Platform Field

		JLabel platLabel = new JLabel("App Platform(s):");
		platLabel.setBounds(65, 450, 200, 30);
		platLabel.setFont(new Font("Arial", Font.BOLD, 16));

		platLabel.setForeground(Color.WHITE);

		appForm.add(platLabel);

		appPlatField = new JTextField();
		appPlatField.setBounds(200, 450, 200, 30);
		appPlatField.setFont(new Font("Arial", Font.BOLD, 16));
		appForm.add(appPlatField);

		// Image Upload Button

		JLabel uploadLabel = new JLabel("App Icon:");
		uploadLabel.setBounds(110, 500, 200, 30);
		uploadLabel.setFont(new Font("Arial", Font.BOLD, 16));

		uploadLabel.setForeground(Color.WHITE);

		appForm.add(uploadLabel);

		uploadButton = new JButton("Upload");
		uploadButton.setBounds(200, 500, 100, 40);
		appForm.add(uploadButton);

		uploadButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				imageIcon = fileGet();

			}
		});

		// Submit Button

		submitButton = new JButton("Submit");
		submitButton.setBounds(200, 575, 200, 40);
		appForm.add(submitButton);

		submitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {

				// Checks if all fields have been filled out
				if (!appNameField.getText().trim().isEmpty() && !appCategoryField.getText().trim().isEmpty()
						&& !appPublisherField.getText().trim().isEmpty() && !appPriceField.getText().trim().isEmpty()
						&& !appLinkField.getText().trim().isEmpty() && !appDescriptionField.getText().trim().isEmpty()
						&& !appPlatField.getText().trim().isEmpty() && imageIcon != null) {

					// Gets and formats the text information
					appInfo = appNameField.getText().trim();

					appInfo += "~" + appCategoryField.getText().trim() + "~" + appPublisherField.getText().trim() + "~"
							+ appPriceField.getText().trim() + "~" + appLinkField.getText().trim() + "~"
							+ appDescriptionField.getText().trim() + '~' + appPlatField.getText().trim() + '~'
							+ imageIcon.getName();

					File destFolder = new File("src/newAppInfo/Img_Temp");

					if (!destFolder.exists()) {
						destFolder.mkdirs();
					}

					File destFile = new File(destFolder, imageIcon.getName());

					Path source = imageIcon.toPath();
					Path dest = destFile.toPath();

					try {
						Files.copy(source, dest, StandardCopyOption.REPLACE_EXISTING);
						System.out.println("Image successfully copied!");
					} catch (IOException e) {
						System.out.println("Image copy failed: " + e.getMessage());
						e.printStackTrace();
					}

					try (FileWriter fw = new FileWriter("Request.txt", true)) {
						fw.write(appInfo + System.lineSeparator());
					} catch (IOException e) {
						e.printStackTrace();
					}

					System.out.println("Destination File at: " + destFile.getAbsolutePath());

					appForm.dispose();

				}
			}
		});

		appForm.setVisible(true);
	}

	public static File fileGet() {
		JFileChooser fileChooser = new JFileChooser();
		int returnValue = fileChooser.showOpenDialog(null);

		if (returnValue == JFileChooser.APPROVE_OPTION) {
			File selectedFile = fileChooser.getSelectedFile();
			System.out.println("Selected file: " + selectedFile.getAbsolutePath());

			imageIcon = selectedFile;
		} else {
			System.out.println("File selection cancelled.");
		}
		return imageIcon;
	}

	public static void Numvalidator(JTextField txtField) {
		txtField.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				if (!(Character.isDigit(c) || (c == KeyEvent.VK_PERIOD) || (c == KeyEvent.VK_BACK_SPACE)
						|| (c == KeyEvent.VK_DELETE))) {
					e.consume();
					JOptionPane.showMessageDialog(null, "Only numbers and periods are allowed", "Attention",
							JOptionPane.PLAIN_MESSAGE);
				}
			}
		});
	}

}
