import javax.swing.JPanel;

/**
 * 
 */

/**
 * @author YKB
 *
 */
public class WelcomeScreen extends JPanel{

	/**
	 * 
	 */
	private int height, width, x0,y0;
	
	public WelcomeScreen() {
		height = getHeight();
		width = getWidth();
		x0 = getX();
		y0 = getY();
		System.out.println(String.valueOf(height)+" x "+String.valueOf(width));
		System.out.println(String.valueOf(x0)+" x "+String.valueOf(y0));
	}

}
