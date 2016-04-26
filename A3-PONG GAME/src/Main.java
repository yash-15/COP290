import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

public class Main{
	
	private JFrame frame;  //Main Frame
	private JLabel statusBar;
	private Game game;
	
	/**
	 * This initializes the components and calls createWindow()
	 */
	
	public Main() {
		
		System.out.println("Constructing Main...");
		frame = new JFrame();
		statusBar = new JLabel();
		game = new Game(this);
		//TODO: Add Network Object and must ensure that the minimum size is returned
		//TODO: Or you can consider re-scaling according to size.
		createWindow();
		System.out.println("Constructed Main!");
    }
    
   /**
    * Set size, title and position of game window 
    */
	
	private void createWindow(){
		

		System.out.println("Creating Game Window!");
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		double width = screenSize.getWidth();
		double height = screenSize.getHeight();
		int minDim = (int)Math.min(width, height)-100-15;
		//TODO: Please check the height
		frame.setSize(minDim,minDim+40);
		//frame.setResizable(false);
		//frame.setPreferredSize(new Dimension(400, 400));
		System.out.println(minDim+" is the dim at "+System.currentTimeMillis());
		frame.setTitle("Ping Pong!");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);	// center-aligned

		statusBar.setText("Size is : "+String.valueOf(width)+" x "+String.valueOf(height));
		BoxLayout bLayout=new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS);
		frame.getContentPane().setLayout(bLayout);
		
		frame.getContentPane().add(game._UI());
		frame.getContentPane().add(statusBar,BorderLayout.SOUTH);
		frame.setVisible(true);

		System.out.println("Game window Created!");
	}
	
	public JLabel _statusBar() {	return statusBar;	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() { 
			public void run() {
				try{
					Main obj = new Main();
					System.out.println("Calling game Play!");
					obj.game.setup(4);
					obj.game.play();
				}
				catch(Exception e){
					e.printStackTrace();
					System.out.println(e.toString());
				}
			}
		});
	}
}