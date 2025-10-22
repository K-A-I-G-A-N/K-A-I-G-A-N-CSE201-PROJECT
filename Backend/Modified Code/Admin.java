import java.awt.Color;
import javax.swing.JFrame;

public class Admin {

	public static void adminAccess() {

		JFrame admin = new JFrame();
		admin.setTitle("K A I G A N App_Catalogue ADMIN");
		admin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		admin.setSize(1400,800);
		admin.setResizable(false);
		admin.setLocationRelativeTo(null);
		admin.getContentPane().setBackground(new Color (255, 143, 143));
		admin.setVisible(true);
		
	}

}
