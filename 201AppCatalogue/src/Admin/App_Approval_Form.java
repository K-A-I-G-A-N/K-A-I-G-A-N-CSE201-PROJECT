package Admin;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class App_Approval_Form {

	static String Requests = "";
	static String Name;
	static String Publisher;
	static String Platfroms;

	//public static void Form(string AppName, String DeveloperName, String Platforms, String URL, String Price, file RequestDoc) {
	public static void main(String[] args) throws IOException{

		File request = new File("Request.txt");
		requestCheck(request);

	}

	public static void requestCheck(File request) throws FileNotFoundException, IOException {

		File filereq = request;

		Scanner reader = new Scanner(filereq);

		String line = reader.nextLine();

		while (reader.hasNextLine()) {
			Requests = Requests + reader.nextLine() + "\n";
		}

		String[] parts = line.split("~");
		Name=parts[0];
		Publisher=parts[1];
		Platfroms=parts[2];
		reader.close();


		PrintWriter Writer = new PrintWriter(filereq);
		Writer.print(Requests);
		Writer.close();


		JFrame admin = new JFrame();
		admin.setTitle("ADMIN_App Approval Form");
		admin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		admin.setSize(1400,800);
		admin.setResizable(false);
		admin.setLocationRelativeTo(null);
		admin.getContentPane().setBackground(new Color (255, 143, 143));
		admin.setLayout(null);

		JButton approveButton = new JButton("Approve");
		approveButton.setBounds(0,700,650,50);
		approveButton.setFont(new Font("Arial", Font.BOLD, 22));
		admin.add(approveButton);

		JButton rejectButton = new JButton("Reject");
		rejectButton.setBounds(750,700,640,50);
		admin.add(rejectButton);
		rejectButton.setFont(new Font("Arial", Font.BOLD, 22));
		admin.setVisible(true);

		JLabel AppName = new JLabel("App Name: " + Name);
		AppName.setBounds(50,50,650,50);
		AppName.setFont(new Font("Arial", Font.BOLD, 22));
		admin.add(AppName);

		JLabel DeveloperName = new JLabel("Publisher Name: " + Publisher);
		DeveloperName.setBounds(50,100,650,50);
		DeveloperName.setFont(new Font("Arial", Font.BOLD, 22));
		admin.add(DeveloperName);

		JLabel Platforms = new JLabel("Platforms: " + Platfroms);
		Platforms.setBounds(50,150,650,50);
		Platforms.setFont(new Font("Arial", Font.BOLD, 22));
		admin.add(Platforms);

		rejectButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				admin.setVisible(false);
				//does nothing as prompt is rejected


				//perhaps do If statement for if you want to continue
				Admin.adminAccess();

			}});

		approveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {

				//ADD App to app document, same if statement as before.
				admin.setVisible(false);
				Admin.adminAccess();


			}});

	}
}
