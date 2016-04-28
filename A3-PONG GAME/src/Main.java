import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

public class Main{
	
	public static JFrame frame;  //Main Frame
	public static JLabel statusBar;
	public static Game game;
	public static boolean ready;
	
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
		//frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);	// center-aligned

		statusBar.setText("Size is : "+String.valueOf(width)+" x "+String.valueOf(height));
		BoxLayout bLayout=new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS);
		frame.getContentPane().setLayout(bLayout);
		
		frame.getContentPane().add(game._UI());
		frame.getContentPane().add(statusBar,BorderLayout.SOUTH);
		statusBar.setText("status");
		//TODO on Game Over
		
				frame.addWindowListener(new WindowAdapter(){
		            public void windowClosing(WindowEvent e){
		               game.timer.stop();
		            	for(int i=0;i<4;i++)
		               {
		            	   try{
		            		   network.users[i].conn.socket.close();
		            	   }catch(Exception e1){}//DO nothing if sockets do not exist}
		               }
		               network.window.setVisible(true);
		               network.expose_server=(network.is_server && network.num_users<4);
		               network.initialized=true;
		               frame.dispose();
		            }
		        });
			
		
		frame.setVisible(true);

		System.out.println("Game window Created!");
	}
	
	public JLabel _statusBar() {	return statusBar;	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() { 
			public void run() {
				try{
					network.initialized=false;
					ready=false;
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