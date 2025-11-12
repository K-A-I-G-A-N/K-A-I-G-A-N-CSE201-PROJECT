package Admin;
import Login.LoginFrame;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

//NEED TO RESIZE SOME ELEMENTS FIRST


public class Admin {
	static String request = "";
	private static JButton fullButton;
	private static JButton windowButton;

	//For KEIRA TO TEST METHODS WITH
	public static void main(String[] args){
		adminAccess();
	}

	public static void adminAccess(){

		JFrame admin = new JFrame();
		admin.setTitle("K A I G A N App_Catalogue ADMIN");
		admin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		admin.setSize(550,450);
		admin.setResizable(false);
		admin.setLocationRelativeTo(null);
		admin.getContentPane().setBackground(new Color (255, 143, 143));
		admin.setLayout(null);

		JLabel Title = new JLabel("Admin MENU");
		Title.setBounds(195, 40, 355, 40);
		Title.setFont(new Font("Arial", Font.BOLD, 22));
		admin.add(Title);

		JLabel message = new JLabel();
		message.setBounds(145, 80, 400, 40);
		message.setFont(new Font("Arial", Font.BOLD, 18));
		admin.add(message);

		JButton Logout = new JButton("Logout");
		Logout.setBounds(0, 300, 550, 35);
		admin.add(Logout);
		
		JButton Review = new JButton("Reveiw");
		Review.setBounds(0, 250, 550, 35);
		admin.add(Review);
		
		fullButton = new JButton("Fullscreen");
		fullButton.setBounds(0, 390, 95, 25);
		admin.add(fullButton);

		windowButton = new JButton("Window");
		windowButton.setBounds(0, 750, 95, 65);
		admin.add(windowButton);
		windowButton.setVisible(false);
		
		File File = new File("request.txt");
		Scanner reader = null;
		try {
			reader = new Scanner(File);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		while (reader.hasNextLine()) {
			request = request + reader.nextLine() + "\n";
		}
		if(request=="") {
			message.setText("There are no new requests");
		} else {
			message.setText("   There are new requests");
		}
		admin.setVisible(true);
		//Add logout button & maybe resize buttons


		Logout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				admin.setVisible(false); 
				LoginFrame.Login();
			}
		});

		Review.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				admin.setVisible(false); 
				try {
					App_Approval_Form.Form(File);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});

		windowButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {				
				admin.setSize(550, 450);
				admin.setLocationRelativeTo(null);

				Title.setBounds(195, 40, 355, 40);
				Title.setFont(new Font("Arial", Font.BOLD, 22));

				message.setBounds(145, 80, 400, 40);
				message.setFont(new Font("Arial", Font.BOLD, 18));



				windowButton.setVisible(false);
				fullButton.setVisible(true);				
			}
		});

	}

}
