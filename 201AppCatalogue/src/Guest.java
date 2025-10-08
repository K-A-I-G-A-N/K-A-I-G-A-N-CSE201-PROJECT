import java.awt.Color;
import javax.swing.JFrame;

public class Guest {

	public static void guestAccess() {

		JFrame guest = new JFrame();
		guest.setTitle("K A I G A N App_Catalogue");
		guest.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		guest.setSize(1400,800);
		guest.setResizable(false);
		guest.setLocationRelativeTo(null);
		guest.getContentPane().setBackground(new Color (255, 214, 178));
		guest.setVisible(true);
		
	}

}
