import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
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

	private static JTextArea appDescriptionField;

	private static File imageIcon;

	private static String appInfo;

	public static void main(String[] args) {

		JFrame appForm = new JFrame();
		appForm.setTitle("K A I G A N App Request Form");
		appForm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		appForm.setSize(550, 450);
		appForm.setResizable(false);
		appForm.setLocationRelativeTo(null);
		appForm.getContentPane().setBackground(new Color(255, 214, 178));
		appForm.setLayout(null);

		// Title

		JLabel titleLabel = new JLabel("App Post Request Form");
		titleLabel.setFont(new Font("Arial", Font.BOLD, 22));
		titleLabel.setBounds(130, 20, 400, 40);
		appForm.add(titleLabel);

		// App Name Field

		JLabel appNameLabel = new JLabel("App Name:");
		appNameLabel.setBounds(100, 100, 100, 30);
		appNameLabel.setFont(new Font("Arial", Font.BOLD, 16));
		appForm.add(appNameLabel);

		appNameField = new JTextField();
		appNameField.setBounds(200, 100, 200, 30);
		appNameField.setFont(new Font("Arial", Font.BOLD, 16));
		appForm.add(appNameField);

		// App Category Field

		JLabel catLabel = new JLabel("App Category:");
		catLabel.setBounds(75, 150, 200, 30);
		catLabel.setFont(new Font("Arial", Font.BOLD, 16));
		appForm.add(catLabel);

		appCategoryField = new JTextField();
		appCategoryField.setBounds(200, 150, 200, 30);
		appCategoryField.setFont(new Font("Arial", Font.BOLD, 16));
		appForm.add(appCategoryField);

		// App Description Field

		JLabel descLabel = new JLabel("App Description:");
		descLabel.setBounds(58, 200, 200, 30);
		descLabel.setFont(new Font("Arial", Font.BOLD, 16));
		appForm.add(descLabel);

		appDescriptionField = new JTextArea();
		appDescriptionField.setBounds(200, 200, 200, 75);
		appDescriptionField.setFont(new Font("Arial", Font.BOLD, 12));
		appDescriptionField.setLineWrap(true);
		appDescriptionField.setWrapStyleWord(true);
		appForm.add(appDescriptionField);

		// Image Upload Button

		JLabel uploadLabel = new JLabel("App Icon:");
		uploadLabel.setBounds(110, 300, 200, 30);
		uploadLabel.setFont(new Font("Arial", Font.BOLD, 16));
		appForm.add(uploadLabel);

		uploadButton = new JButton("Upload");
		uploadButton.setBounds(200, 300, 100, 40);
		appForm.add(uploadButton);

		uploadButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				imageIcon = fileGet();

			}
		});

		// Submit Button

		submitButton = new JButton("Submit");
		submitButton.setBounds(200, 350, 100, 40);
		appForm.add(submitButton);

		submitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {

				// Checks if all fields have been filled out
				if (!appNameField.getText().trim().isEmpty() && !appCategoryField.getText().trim().isEmpty()
						&& !appDescriptionField.getText().trim().isEmpty() && imageIcon != null) {

					// Gets and formats the text information
					appInfo = appNameField.getText().trim();

					appInfo += "~" + appCategoryField.getText().trim() + "~" + appDescriptionField.getText().trim();

					

					

					File destFolder = new File("src/newAppInfo/Img_Temp");

					if (!destFolder.exists()) {
						destFolder.mkdirs(); // create if missing
					}
					
					//Adds Image to Img_Temp. Wont show in eclipse without refreshing 
					//but will show in the project folder

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
					
					// Creates Text File
					
					try (FileWriter fw = new FileWriter("src/newAppInfo/newAppInfo.txt", true)) { // true = append mode

						fw.write(appInfo + System.lineSeparator());
						System.out.println("Text added successfully!");
					} catch (IOException e) {
						e.printStackTrace();
					}

					System.out.println("Destination File at: " + destFile.getAbsolutePath());

					//Closes Window
					appForm.dispose();

				}
			}
		});

		appForm.setVisible(true);
	}

	// File Chooser for the User

	public static File fileGet() {
		JFileChooser fileChooser = new JFileChooser();
		int returnValue = fileChooser.showOpenDialog(null); // Show the "Open" dialog

		if (returnValue == JFileChooser.APPROVE_OPTION) {
			File selectedFile = fileChooser.getSelectedFile();
			System.out.println("Selected file: " + selectedFile.getAbsolutePath());

			imageIcon = selectedFile;
		} else {
			System.out.println("File selection cancelled.");
		}
		return imageIcon;
	}

}
