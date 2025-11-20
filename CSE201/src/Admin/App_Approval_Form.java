package Admin;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;

import javax.imageio.ImageIO;
import javax.swing.*;

public class App_Approval_Form {

	static String Requests = "";
	static String Name;
	static String Genre;
	static String Description;
	static String Platforms;
	static String Publisher;
	static String Price;
	static String URI;
	static String initialImage;
	static File filereq;
	static Boolean judged = false;

	public static void main(String[] args) throws IOException {
		File request = new File("Request.txt");
		Form(request);
	}

	public static void Form(File request) throws IOException {
		filereq = request;
		BufferedReader reader = new BufferedReader(new FileReader(filereq));
		String line = reader.readLine();
		Requests = "";
		String next;
		while ((next = reader.readLine()) != null)
			Requests += next + "\n";
		reader.close();

		// If no request, return to Admin panel
		if (line == null) {
			JOptionPane.showMessageDialog(null, "No requests to review.");
			Admin.adminAccess();
			return;
		}

		String[] parts = line.split("~");
		Name = parts.length > 0 ? parts[0] : "";
		Genre = parts.length > 1 ? parts[1] : "";
		Publisher = parts.length > 2 ? parts[2] : "";
		Price = parts.length > 3 ? parts[3] : "";
		URI = parts.length > 4 ? parts[4] : "";
		Description = parts.length > 5 ? parts[5] : "";
		Platforms = parts.length > 6 ? parts[6] : "";
		initialImage = "tmp\\" +(parts.length > 7 ? parts[7] : "");

		// Remove processed request from file
		PrintWriter Writer = new PrintWriter(filereq);
		Writer.print(Requests);
		Writer.close();

		JFrame admin = new JFrame();
		admin.setTitle("ADMIN_App Approval Form");
		admin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		admin.setSize(1000,600);
		admin.setResizable(false);
		admin.setLocationRelativeTo(null);
		admin.getContentPane().setBackground(new Color (255, 143, 143));
		admin.setLayout(null);

		JLabel AppNameL = new JLabel("App Name: " + Name);
		AppNameL.setBounds(40,40,500,36);
		AppNameL.setFont(new Font("Arial", Font.BOLD, 22));
		admin.add(AppNameL);

		JLabel GenreL = new JLabel("Genre: " + Genre);
		GenreL.setBounds(40,90,500,28);
		GenreL.setFont(new Font("Arial", Font.BOLD, 17));
		admin.add(GenreL);

		JLabel DescriptionL = new JLabel("<html>Description:<br>" + Description + "</html>");
		DescriptionL.setBounds(40,130,500,50);
		DescriptionL.setFont(new Font("Arial", Font.BOLD, 15));
		admin.add(DescriptionL);

		JLabel PublisherL = new JLabel("Publisher: " + Publisher);
		PublisherL.setBounds(40,190,500,27);
		PublisherL.setFont(new Font("Arial", Font.BOLD, 15));
		admin.add(PublisherL);

		JLabel PlatformsL = new JLabel("Platforms: " + Platforms);
		PlatformsL.setBounds(40,220,500,27);
		PlatformsL.setFont(new Font("Arial", Font.BOLD, 15));
		admin.add(PlatformsL);

		JLabel URL = new JLabel("URL: " + URI);
		URL.setBounds(40,250,500,27);
		URL.setFont(new Font("Arial", Font.BOLD, 15));
		admin.add(URL);

		JLabel PriceL = new JLabel("Price: " +Price);
		PriceL.setBounds(40,280,500,27);
		PriceL.setFont(new Font("Arial", Font.BOLD, 15));
		admin.add(PriceL);

		JLabel ImageL = new JLabel();
		ImageL.setBounds(600,60,256,256);
		try {
			BufferedImage ImageScaled = ImageIO.read(new File(initialImage));
		    Image resultingImage = ImageScaled.getScaledInstance(256, 256, Image.SCALE_DEFAULT);
		    BufferedImage outputImage = new BufferedImage(256, 256, BufferedImage.TYPE_INT_RGB);
		    outputImage.getGraphics().drawImage(resultingImage, 0, 0, null);
			ImageIcon icon = new ImageIcon(outputImage);
			
			if (icon.getIconWidth() > 0) {
				ImageL.setIcon(icon); 
			} else {
				ImageL.setText("No Image");
			}
		} catch (Exception e) {
			ImageL.setText("No Image");
		}
		admin.add(ImageL);

		JButton approveButton = new JButton("Approve");
		approveButton.setBounds(40,340,180,40);
		approveButton.setFont(new Font("Arial", Font.BOLD, 18));
		admin.add(approveButton);

		JButton rejectButton = new JButton("Reject");
		rejectButton.setBounds(240,340,180,40);
		rejectButton.setFont(new Font("Arial", Font.BOLD, 18));
		admin.add(rejectButton);

		JButton nextButton = new JButton("Next Request");
		nextButton.setBounds(440,340,180,40);
		nextButton.setFont(new Font("Arial", Font.BOLD, 18));
		admin.add(nextButton);

		JButton returnButton = new JButton("Return to Admin");
		returnButton.setBounds(640,340,180,40);
		returnButton.setFont(new Font("Arial", Font.BOLD, 18));
		admin.add(returnButton);

		admin.setVisible(true);

		approveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				// Save to App-Database
				try (BufferedWriter dbWriter = new BufferedWriter(new FileWriter("App Database.csv", true))) {
					dbWriter.write(Name + "," + Genre + "," + Publisher + "," + Price + "," +
							URI + "," + Description + "," + Platforms + "," + initialImage);
					dbWriter.newLine();
				} catch (IOException e) { e.printStackTrace(); }
				admin.dispose();
				nextRequest();
			}
		});

		rejectButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				admin.dispose();
				nextRequest();
			}
		});

		nextButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				if(!judged){
					try {
						PrintWriter Writer;
						Writer = new PrintWriter(filereq);
						Writer.print(Requests+line+"\n");
						Writer.close();
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					}
				}
				admin.dispose();
				nextRequest();
			}
		});

		returnButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {

				if(!judged){
					try {
						PrintWriter Writer;
						Writer = new PrintWriter(filereq);
						Writer.print(Requests+line+"\n");
						Writer.close();
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					}
				}

				admin.dispose();
				Admin.adminAccess();
			}
		});
	}

	// Helper to move to next unprocessed request
	private static void nextRequest() {
		try {
			Form(new File("Request.txt"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
