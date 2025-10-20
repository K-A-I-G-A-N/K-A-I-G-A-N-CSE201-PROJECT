import java.awt.Color;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class LogInFrame{

	static JButton guestAccessButton;
	static JButton logInButton;


	public static void main(String[] args) {

		JFrame login = new JFrame();
		login.setTitle("K A I G A N App_Catalogue Log In");
		login.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		login.setSize(550,550);
		login.setResizable(false);
		login.setLocationRelativeTo(null);
		login.getContentPane().setBackground(new Color (255, 214, 178));
		login.setLocationRelativeTo(null);

		/**
		 * The Buttons function as intended but could use customization
		 * try to keep the buttons uniform
		 */
		guestAccessButton = new JButton("Guest");
		guestAccessButton.setBounds(275, 450, 275, 30);
		guestAccessButton.addActionListener(e -> goToGuest(login));
		
		logInButton = new JButton("Login");
		logInButton.setBounds(0, 450, 275, 30);
		logInButton.addActionListener(e -> determine(login));

		
		/**
		 * Feel free to customize this text field if you can.
		 */
		JLabel label = new JLabel();
		label.setText("Inset Admin/Mod login info or press guest button to continue");
		label.setFont(new Font("Arial", Font.ITALIC, 16));
		label.setHorizontalAlignment(JLabel.CENTER);
		label.setVerticalAlignment(JLabel.BOTTOM);

		JLabel wrong = new JLabel();
		wrong.setText("Incorrect Username or Password.");
		wrong.setFont(new Font("Arial", Font.BOLD, 16));
		wrong.setHorizontalAlignment(JLabel.CENTER);
		wrong.setVerticalAlignment(JLabel.TOP);
		
		login.add(guestAccessButton);
		login.add(logInButton);
		login.add(label);
		login.setVisible(true);
	}




	public static void determine(JFrame Login) {

		/**
		 * This area will require a username/passcode system
		 * should dtermine from 2 different fields (txt files, databases, etc)
		 * & direct to appropriate areas
		 */

		//goToMod(Login);
		//goToAdmin(Login);

		

	}

	public static void goToGuest(JFrame Login) {
		Guest.guestAccess();
		Login.setVisible(false);
	}

	public static void goToMod(JFrame Login) {
		Moderator.modAccess();
		Login.setVisible(false);
	}
	public static void goToAdmin(JFrame Login) {
		Admin.adminAccess();
		Login.setVisible(false);
	}

}