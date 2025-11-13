package Admin;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class App_Approval_Form {

	static String Requests = "";
	static String Name;
	static String Genre;
	static String Description;
	static String Platfroms;
	static String Publisher;
	static String Price;
	static String URI;
	static String Image = "tmp\\";

	static File filereq;

	public static void main(String[] args) throws IOException{
		File request = new File("Request.txt");
		Form(request);
	}

	public static void Form(File request) throws FileNotFoundException, IOException {

		filereq = request;
		Scanner reader = new Scanner(filereq);
		String line = reader.nextLine();
		while (reader.hasNextLine()) {
			Requests = Requests + reader.nextLine() + "\n";
		}

		String[] parts = line.split("~");
		Name=parts[0];
		Genre=parts[1];
		Publisher=parts[2];
		Price=parts[3];
		URI=parts[4];
		Description=parts[5];
		Platfroms=parts[6];
		Image=Image+parts[7];
		File ImageICO = new File(Image);
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

		JLabel AppNameL = new JLabel("App Name: " + Name);
		AppNameL.setBounds(50,50,650,50);
		AppNameL.setFont(new Font("Arial", Font.BOLD, 22));
		admin.add(AppNameL);

		JLabel GenreL = new JLabel("Genre: " + Genre);
		GenreL.setBounds(50,100,650,50);
		GenreL.setFont(new Font("Arial", Font.BOLD, 22));
		admin.add(GenreL);

		JLabel DescriptionL = new JLabel("Description: " + Description);
		DescriptionL.setBounds(50,150,650,50);
		DescriptionL.setFont(new Font("Arial", Font.BOLD, 22));
		admin.add(DescriptionL);

		JLabel PublisherL = new JLabel("Publisher: " + Publisher);
		PublisherL.setBounds(50,200,650,50);
		PublisherL.setFont(new Font("Arial", Font.BOLD, 22));
		admin.add(PublisherL);

		JLabel PlatformsL = new JLabel("Compatable Platforms: " + Platfroms);
		PlatformsL.setBounds(50,250,650,50);
		PlatformsL.setFont(new Font("Arial", Font.BOLD, 22));
		admin.add(PlatformsL);

		JLabel URL = new JLabel ("URL: " + URI);
		URL.setBounds(50,300,650,50);
		URL.setFont(new Font("Arial", Font.BOLD, 22));
		admin.add(URL);

		JLabel PriceL = new JLabel ("Price: " +Price);
		PriceL.setBounds(50,350,650,50);
		PriceL.setFont(new Font("Arial", Font.BOLD, 22));
		admin.add(PriceL);

		JLabel ImageL = new JLabel (new ImageIcon(Image));
		ImageL.setBounds(400,0,1100,500);
		admin.add(ImageL);

		JButton approveButton = new JButton("Approve");
		approveButton.setBounds(0,700,650,50);
		approveButton.setFont(new Font("Arial", Font.BOLD, 22));
		admin.add(approveButton);

		JButton rejectButton = new JButton("Reject");
		rejectButton.setBounds(750,700,640,50);
		admin.add(rejectButton);
		rejectButton.setFont(new Font("Arial", Font.BOLD, 22));

		JButton continueButton = new JButton("Next request");
		continueButton.setBounds(0,700,650,50);
		continueButton.setFont(new Font("Arial", Font.BOLD, 22));
		admin.add(continueButton);
		continueButton.setVisible(true);

		JButton returnButton = new JButton("Return to Admin frame");
		returnButton.setBounds(750,700,640,50);
		admin.add(returnButton);
		returnButton.setFont(new Font("Arial", Font.BOLD, 22));
		returnButton.setVisible(true);


		admin.setVisible(true);

		rejectButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {

				if(Requests!="") {
					
					System.out.print(ImageICO.delete());
					
					continueButton.setVisible(true);
					returnButton.setVisible(true);
					rejectButton.setVisible(false);
					approveButton.setVisible(false);
				}else {
					admin.dispose();
					Admin.adminAccess();
				}

			}});

		approveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {

				try (FileWriter fileWriter = new FileWriter("App Database.csv", true);
						BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)) {
					bufferedWriter.newLine();
					bufferedWriter.write(Name+","+Publisher+","+Platfroms+","+URI+","+Price+","+Genre+","+Description+","+Image);
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				if(Requests!="") {
					continueButton.setVisible(true);
					returnButton.setVisible(true);
					rejectButton.setVisible(false);
					approveButton.setVisible(false);
				}else {
					admin.dispose();
					Admin.adminAccess();
				}
				
			}});

		returnButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				admin.dispose();
				Admin.adminAccess();
			}});

		continueButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {

				try {
					admin.dispose();
					Form(filereq);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}});

	}

}
