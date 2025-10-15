import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class User implements ActionListener{

	static User u = new User();
	static JFrame LOGINframe = new JFrame();
	static JFrame BROWSERframe = new JFrame();
	static JTextField user = new JTextField();
	static JTextField pass =new JTextField();

	String username;
	String password;
	int role;

	public static void main(String[] args) {
		login();
	}

	public static void login() {

		JLabel title = new JLabel();
		JLabel titleB = new JLabel();
		JLabel info = new JLabel();
		JButton guest = new JButton("Guest");
		JButton login = new JButton("Login");
		JLabel userLabel = new JLabel();
		JLabel passLabel = new JLabel();

		LOGINframe.setTitle("K A I G A N App_Catalogue Log In");
		LOGINframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		LOGINframe.setSize(550,550);
		LOGINframe.setResizable(false);
		LOGINframe.setLocationRelativeTo(null);
		LOGINframe.getContentPane().setBackground(new Color (255, 214, 178));
		LOGINframe.setLocationRelativeTo(null);
		LOGINframe.setLayout(null);

		userLabel.setText("Username");
		userLabel.setFont(new Font("Arial", Font.BOLD, 24));
		userLabel.setBounds(137, 105, 275, 60);

		user.setBounds(137, 150, 275, 60);
		user.setFont(new Font("Arial", Font.ITALIC, 24));

		passLabel.setText("Password");
		passLabel.setFont(new Font("Arial", Font.BOLD, 24));
		passLabel.setBounds(137, 205, 275, 60);

		pass.setBounds(137, 250, 275, 60);
		pass.setFont(new Font("Arial", Font.ITALIC, 24));

		guest.setBounds(275, 450, 275, 30);
		guest.addActionListener(u);

		login.setBounds(0, 450, 275, 30);
		login.addActionListener(u);

		info.setText("Insert Admin/Mod login info or press Guest button to continue.");
		info.setFont(new Font("Arial", Font.ITALIC, 16));
		info.setBounds(50, 475, 450, 30);

		title.setText("K-A-I-G-A-N");
		title.setFont(new Font("Arial", Font.BOLD, 48));
		title.setBounds(136, 20, 450, 60);
		
		titleB.setText("App Catalogue");
		titleB.setFont(new Font("Arial", Font.BOLD, 24));
		titleB.setBounds(190, 60, 450, 60);

		LOGINframe.add(guest);
		LOGINframe.add(login);
		LOGINframe.add(user);
		LOGINframe.add(userLabel);
		LOGINframe.add(pass);
		LOGINframe.add(passLabel);
		LOGINframe.add(info);
		LOGINframe.add(title);
		LOGINframe.add(titleB);
		LOGINframe.setVisible(true);
	}


	public static void browser(int role) {
		JButton logout = new JButton("Logout");

		BROWSERframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		BROWSERframe.setSize(1400,800);
		BROWSERframe.setResizable(false);
		BROWSERframe.setLocationRelativeTo(null);
		BROWSERframe.setLayout(null);

		if(role==1) {
			BROWSERframe.setTitle("K A I G A N App_Catalogue MOD");
			BROWSERframe.getContentPane().setBackground(new Color (178, 205, 255));
		}
		else if(role==2) {
			BROWSERframe.setTitle("K A I G A N App_Catalogue ADMIN");
			BROWSERframe.getContentPane().setBackground(new Color (255, 143, 143));}
		else {
			BROWSERframe.setTitle("K A I G A N App_Catalogue");
			BROWSERframe.getContentPane().setBackground(new Color (255, 214, 178));
		}

		logout.setBounds(1290, 0, 100, 40);
		logout.addActionListener(u);

		BROWSERframe.add(logout);
		BROWSERframe.setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {
		String s = e.getActionCommand();
		if (s.equals("Login")) {
			username = (user.getText());
			password = (pass.getText());

			//Mess with excel database for thisj
			if(username.equals("Maikeru")) {
				if(password.equals("PikoPiko")) {
					role=2;
					LOGINframe.setVisible(false);
					browser(role);
				}
			}
			if(username.equals("Niko")) {
				if(password.equals("Momiji")) {
					role=1;
					LOGINframe.setVisible(false);
					browser(role);
				}
			}
			//above code is DUMMY VALUES	

		} else if (s.equals("Guest")) {
			LOGINframe.setVisible(false);
			role=0;
			browser(role);
		}
		else if (s.equals("Logout")) {
			BROWSERframe.setVisible(false);
			login();

		}
	}
}

