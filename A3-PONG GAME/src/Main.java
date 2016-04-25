import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.Scanner;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

/**
 * @author YKB
 *
 */

public class Main{

	private boolean debug;
	private JFrame frame;
	static public JLabel statusbar,bar2;
	private Scanner scanner = new Scanner(System.in);
	private Game game;
	
	public Main() {
		
		System.out.println("Constructing Main...");
		frame = new JFrame();
		statusbar = new JLabel();
		bar2 = new JLabel();
		game = new Game(this);
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
		int minDim = (int)Math.min(width, height)-100;
		frame.setSize(minDim-15,minDim+15);
		frame.setResizable(false);
		System.out.println(minDim+" is the dim at "+System.currentTimeMillis());
		frame.setTitle("Ping Pong!");

		//frame.setBackground(Color.BLUE);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);	// center-aligned

		statusbar.setText("Size is : "+String.valueOf(width)+" x "+String.valueOf(height));
		frame.getContentPane().add(statusbar, BorderLayout.SOUTH);
		frame.getContentPane().add(bar2, BorderLayout.NORTH);

		frame.getContentPane().add(game._UI());
		frame.setVisible(true);

		System.out.println("Game window Created!");
	}
	
	public JLabel _statusBar()	{	return statusbar;	}
	public boolean get_debugMode()	{	return debug;		}
	
	public static void message(Boolean debug,String str){
		if(debug)
			System.out.println(str);
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() { 
			public void run() {
				try{
					Main obj = new Main();
					System.out.println("Calling game Play!");
					obj.game.setup(4);
					obj.game.play();
				//	game.setup();
				}
				catch(Exception e){
					System.out.println(e.toString());
				}
			}
		});
	}
}

/*import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;


public class Game extends JFrame {

    private JLabel statusbar;

    public Game() {
        
        initUI();
   }
    
   private void initUI() {

       
        setSize(200, 400);
        setTitle("Game");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);       
        statusbar = new JLabel(" 0");
        add(statusbar, BorderLayout.SOUTH);
       
   }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                Game game = new Game();
                game.setVisible(true);
            }
        });                
    } 
}*/