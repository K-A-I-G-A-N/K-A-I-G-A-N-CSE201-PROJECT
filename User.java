import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class User implements ActionListener{

	static JButton guest;
	static JButton login;
	static JTextField user = new JTextField("username");
	static JTextField pass =new JTextField("password");
	static JFrame frame = new JFrame();

	String username;
	String password;
	int role;

	public static void main(String[] args) {
		login();
	}

	public static void login() {
		User u = new User();

		frame.setTitle("K A I G A N App_Catalogue Log In");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(550,550);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.getContentPane().setBackground(new Color (255, 214, 178));
		frame.setLocationRelativeTo(null);

		user.setBounds(137, 150, 275, 60);
		user.setFont(new Font("Arial", Font.ITALIC, 24));

		pass.setBounds(137, 250, 275, 60);
		pass.setFont(new Font("Arial", Font.ITALIC, 24));

		guest = new JButton("Guest");
		guest.setBounds(275, 450, 275, 30);
		guest.addActionListener(u);

		login = new JButton("Login");
		login.setBounds(0, 450, 275, 30);
		login.addActionListener(u);

		JLabel label = new JLabel();
		label.setText("Inset Admin/Mod login info or press guest button to continue");
		label.setFont(new Font("Arial", Font.ITALIC, 16));
		label.setHorizontalAlignment(JLabel.CENTER);
		label.setVerticalAlignment(JLabel.BOTTOM);

		JLabel title = new JLabel();
		label.setText("K-A-I-G-A-N");
		label.setFont(new Font("Arial", Font.BOLD, 48));
		label.setHorizontalAlignment(JLabel.CENTER);
		label.setVerticalAlignment(JLabel.TOP);

		JLabel wrong = new JLabel();
		wrong.setText("Incorrect Username or Password.");
		wrong.setFont(new Font("Arial", Font.BOLD, 16));
		wrong.setHorizontalAlignment(JLabel.CENTER);
		wrong.setVerticalAlignment(JLabel.TOP);

		frame.add(guest);
		frame.add(login);
		frame.add(user);
		frame.add(pass);
		frame.add(label);
		frame.setVisible(true);
	}


	public void actionPerformed(ActionEvent e) {
		String s = e.getActionCommand();
		if (s.equals("Login")) {
			username = (user.getText());
			password = (pass.getText());

			//Mess with excel database for this
			if(username.equals("Maikeru")) {
				if(password.equals("PikoPiko")) {
					role=2;
					frame.setVisible(false);
					browser(role);
				}
			}
			if(username.equals("Niko")) {
				if(password.equals("Momiji")) {
					role=1;
					frame.setVisible(false);
					browser(role);
				}
			}
			//above code is DUMMY VALUES	

		} else {
			frame.setVisible(false);
			role=0;
			browser(role);
		}
	}


	public static void browser(int role) {
		JFrame browserFrame = new JFrame();
		browserFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		browserFrame.setSize(1400,800);
		browserFrame.setResizable(false);
		browserFrame.setLocationRelativeTo(null);

		if(role==1) {
			browserFrame.setTitle("K A I G A N App_Catalogue MOD");
			browserFrame.getContentPane().setBackground(new Color (178, 205, 255));
		}
		else if(role==2) {
			browserFrame.setTitle("K A I G A N App_Catalogue ADMIN");
			browserFrame.getContentPane().setBackground(new Color (255, 143, 143));}
		else {
			browserFrame.setTitle("K A I G A N App_Catalogue");
			browserFrame.getContentPane().setBackground(new Color (255, 214, 178));
		}
		browserFrame.setVisible(true);
	}
}

