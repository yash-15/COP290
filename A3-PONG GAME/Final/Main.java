import javax.swing.JFrame;

public class Main {

	 JFrame window;
	 Network network;
	 
	 public Main() {
		network = new Network(this);
		///Get the mode of running
		network.networkBox.startDialogBox();
		///Initialize the GUI
		initWindow();
		///Setup network configurations
		network.setup();
	}
	
	/**
	 * 
	 * Setting up the main window of the peer-to-peer chat application
	 * @param
	 * @return
	 */
	 void initWindow() {
		window= new JFrame("Peer Chat");
		window.getContentPane().add(network.chatBox,"East");
		window.pack();
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public static void main(String[] args) {			
		new Main();
	}
			
}