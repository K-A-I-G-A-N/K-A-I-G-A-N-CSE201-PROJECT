import static org.junit.Assert.*;
import javax.swing.*;

import org.junit.Test;



public class loginFrameTest {

	private static JTextField usernameField;
	private static JPasswordField passwordField;

	@Test
	public void TEST1() {
		JFrame login = new JFrame();
		usernameField = new JTextField("ToShort");
		passwordField = new JPasswordField("Password$");
		assertFalse(determine(login));
	}

	@Test
	public void TEST2() {
		JFrame login = new JFrame();
		usernameField = new JTextField("TooLongtoread");
		passwordField = new JPasswordField("Password$");
		assertFalse(determine(login));
	}

	@Test
	public void TEST3() {  
		JFrame login = new JFrame();
		usernameField = new JTextField("JustRight");
		passwordField = new JPasswordField("password");
		assertFalse(determine(login));
	}
	
	@Test
	public void TEST4() {  
		JFrame login = new JFrame();
		usernameField = new JTextField("JustRight");
		passwordField = new JPasswordField("PassWord");
		assertFalse(determine(login));
	}
	
	@Test
	public void TEST5() {  
		JFrame login = new JFrame();
		usernameField = new JTextField("JustRight");
		passwordField = new JPasswordField("!@#$");
		assertFalse(determine(login));
	}
	
	@Test
	public void TEST6() {  
		JFrame login = new JFrame();
		usernameField = new JTextField("JustRight");
		passwordField = new JPasswordField("PassWord!");
		assertTrue(determine(login));
	}
	
	@Test
	public void TEST7() {  
		JFrame login = new JFrame();
		usernameField = new JTextField("moderator");
		passwordField = new JPasswordField("mod123");
		assertTrue(determine(login));
	}
	
		@Test
		public void TEST8() {  
			JFrame login = new JFrame();
			usernameField = new JTextField("admin");
			passwordField = new JPasswordField("admin123");
			assertTrue(determine(login));
	}

	public static boolean determine(JFrame login) {
		String username = usernameField.getText().trim();
		String password = new String(passwordField.getPassword());

		if (username.isEmpty() || password.isEmpty()) {
			return false;
		}

		if (username.equals("admin") && password.equals("admin123")||username.equals("moderator") && password.equals("mod123")) {
			return true;
		}

		if (username.isEmpty() || password.isEmpty()) {
			return false;
		} else if (username.length()<8||username.length()>10) {
			return false;
		} else if (!password.chars().anyMatch(ch->!Character.isLetterOrDigit(ch)&&!Character.isWhitespace(ch)) || !password.matches(".*[A-z].*")) {
			return false;
		} else {
			return true;
		}
	}

}