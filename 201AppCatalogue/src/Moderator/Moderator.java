package Moderator;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;

public class Moderator {

	static String displayedContent;
	static String content;

	public static void modAccess() {

		JFrame mod = new JFrame();
		mod.setTitle("K A I G A N App_Catalogue MOD");
		mod.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mod.setSize(1400, 800);
		mod.setResizable(false);
		mod.setLocationRelativeTo(null);
		mod.getContentPane().setBackground(new Color(178, 205, 255));
		mod.setVisible(true);

	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		File modFile = FileMaker("reviewMod.txt");

		File modApprovedFile = FileMaker("reviewModApproved.txt");

		JFrame reviewForm = new JFrame();
		reviewForm.setTitle("K A I G A N Review Moderation");
		reviewForm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		reviewForm.setSize(550, 600);
		reviewForm.setResizable(false);
		reviewForm.setLocationRelativeTo(null);
		reviewForm.getContentPane().setBackground(new Color(255, 214, 178));
		reviewForm.setLayout(null);

		// Title

		JLabel titleLabel = new JLabel("Reviews to Approve/Deny");
		titleLabel.setFont(new Font("Arial", Font.BOLD, 22));
		titleLabel.setBounds(130, 20, 400, 40);
		reviewForm.add(titleLabel);

		// Review

		JTextArea reviewArea = new JTextArea();
		reviewArea.setEditable(false);
		reviewArea.setBounds(20, 100, 500, 200);
		reviewArea.setFont(new Font("Arial", Font.BOLD, 12));
		reviewArea.setLineWrap(true);
		reviewArea.setWrapStyleWord(true);

		content = FileReader()[0];

		displayedContent = FileReader()[1];

		reviewArea.setText(displayedContent);
		reviewForm.add(reviewArea);

		// Approve Button

		JButton approveButton = new JButton("Approve");
		approveButton.setBounds(100, 400, 100, 40);
		reviewForm.add(approveButton);

		approveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {

				if (displayedContent.equals("Nothing left to approve")) {
					return;
				}

				try (FileWriter fw = new FileWriter(modApprovedFile, true)) {

					fw.write(content + System.lineSeparator());
					fw.write("~" + System.lineSeparator());
					System.out.println("Text added successfully!");
				} catch (IOException e) {
					e.printStackTrace();
				}

				try {
					DeleteEntry(content, modFile);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				displayedContent = FileReader()[1];

				content = FileReader()[0];

				reviewForm.revalidate();
				reviewForm.repaint();

				reviewArea.setText(displayedContent);

			}
		});

		// Deny Button

		JButton denyButton = new JButton("Deny");
		denyButton.setBounds(300, 400, 100, 40);
		reviewForm.add(denyButton);

		denyButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {

				if (displayedContent.equals("Nothing left to approve")) {
					return;
				}

				try {
					DeleteEntry(content, modFile);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				displayedContent = FileReader()[1];

				content = FileReader()[0];

				reviewForm.revalidate();
				reviewForm.repaint();

				reviewArea.setText(displayedContent);
			}
		});

		reviewForm.setVisible(true);

	}

	public static File FileMaker(String fileName) {

		File modFile = new File("src/Moderator/" + fileName);

		return modFile;
	}

	public static String[] FileReader() {

	    String filePath = "src/Moderator/reviewMod.txt";
	    String[] result = new String[2];

	    try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {

	        String line;
	        StringBuilder fullEntry = new StringBuilder();
	        StringBuilder reviewOnly = new StringBuilder();

	        boolean readingReview = false;

	        while ((line = reader.readLine()) != null) {
	        	
	        	System.out.println("Lines:" + line);

	            if (line.trim().equals("~")) break;   
	            fullEntry.append(line).append("\n");

	            if (readingReview) {
	                reviewOnly.append(line).append("\n");
	            }

	          
	            if (line.endsWith(":")) {
	                readingReview = true;
	            }
	        }

	        if (fullEntry.toString().trim().isEmpty()) {
	            result[0] = "";
	            result[1] = "Nothing left to approve";
	            return result;
	        }

	        result[0] = fullEntry.toString().trim();
	        result[1] = reviewOnly.toString().trim();

	    } catch (IOException e) {
	        e.printStackTrace();
	    }

	    return result;
	}


	public static void DeleteEntry(String toDelete, File txtFile) throws IOException {

	    List<String> lines = Files.readAllLines(txtFile.toPath());
	    
	    StringBuilder onesKept = new StringBuilder();

	    boolean pastFirstEntry = false;

	    for (String line : lines) {


	    	System.out.println(line);
	        if (!pastFirstEntry) {
	            if (line.trim().equals("~")) {
	            	
	                pastFirstEntry = true;
	            }
	            continue;
	        }

	        // Copy the remainder of the file
	        onesKept.append(line).append(System.lineSeparator());
	    }

	    try (FileWriter writer = new FileWriter(txtFile)) { // overwrites the file
	    	writer.write(onesKept.toString());
	    }

	    System.out.println("Top entry deleted.");
	}


}
