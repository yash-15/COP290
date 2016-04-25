import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

/**
 * @author YKB
 *
 */

public class Main{

	
	private JFrame frame;  //Main Frame
	static public JLabel statusBar;
	private Game game;
	
	
	/**
	 * This initializes the components and calls createWindow()
	 */
	public Main() {
		
		System.out.println("Constructing Main...");
		frame = new JFrame();
		statusBar = new JLabel();
		game = new Game();
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
		System.out.println(minDim+" is the dim at "+System.currentTimeMillis());
		frame.setTitle("Ping Pong!");
		frame.addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e){
            	network.window.setVisible(true);
            	frame.dispose();
            }
        });
		frame.setLocationRelativeTo(null);	// center-aligned

		statusBar.setText("Size is : "+String.valueOf(width)+" x "+String.valueOf(height));
		BoxLayout bLayout=new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS);
		frame.getContentPane().setLayout(bLayout);
		
		frame.getContentPane().add(game._UI());
		frame.getContentPane().add(statusBar,BorderLayout.SOUTH);
		frame.setVisible(true);

		System.out.println("Game window Created!");
	}
	
	
	
		
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() { 
			public void run() {
				try{
	
					Main obj = new Main();
					System.out.println("Calling game Play!");
					obj.game.setup(4);
					//wait(10000);
					obj.game.play();
				
				}
				catch(Exception e){
					System.out.println(e.toString());
				}
			}
		});
	}
}

