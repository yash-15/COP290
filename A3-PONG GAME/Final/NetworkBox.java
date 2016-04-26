import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetAddress;

import javax.swing.ButtonGroup;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;


@SuppressWarnings("serial")
public class NetworkBox extends JPanel implements ActionListener{

	ButtonGroup rButtonGroup;
	
	JRadioButton rButton1;
	JRadioButton rButton2;
	
	JLabel jLabelExposeIP;
	JLabel jLabelServerIP;
	JLabel jLabelServerPort;
	
	JTextField jTextFieldExposeIP;
	JTextField jTextFieldServerIP;
	JTextField jTextFieldServerPort;
	
	Network network;
	
	public NetworkBox(Network parent) {
		network = parent;
		
	}
	
	public void actionPerformed(ActionEvent e) {
		jLabelExposeIP.setEnabled(rButton1.isSelected());
		jTextFieldExposeIP.setEnabled(rButton1.isSelected());
		
		jLabelServerIP.setEnabled(rButton2.isSelected());
		jLabelServerPort.setEnabled(rButton2.isSelected());
		jTextFieldServerIP.setEnabled(rButton2.isSelected());
		jTextFieldServerPort.setEnabled(rButton2.isSelected());
	}
	
	/** 
	 * This dialog box will appear at the start of the application.
	 * This will allow the user to choose the mode of running:
	 * Whether he wants to start a new network or connect to a previous network
	 * If he wants to connect then IP and port of the network is asked.
	 */
	 void startDialogBox(){
		rButtonGroup =new ButtonGroup();
		
		rButton1=new JRadioButton("Run as Server");
		rButton2=new JRadioButton("Run as Client");
		
		jLabelExposeIP=new JLabel("Expose IP:");
		jLabelServerIP=new JLabel("Server IP:");
		jLabelServerPort=new JLabel("Server Port:");
		
		jTextFieldExposeIP=new JTextField();
		jTextFieldServerIP= new JTextField();
		jTextFieldServerPort=new JTextField();

		rButtonGroup.add(rButton1);
		rButtonGroup.add(rButton2);		
		
		rButton1.addActionListener(this);
		rButton2.addActionListener(this);
		
		this.actionPerformed(new ActionEvent(rButton1, 1001,"startDialogBox()"));
		
		JComponent[] comps=new JComponent[] {
				rButton1,
				jLabelExposeIP,
				jTextFieldExposeIP,
				rButton2,
				jLabelServerIP,
				jTextFieldServerIP,
				jLabelServerPort,
				jTextFieldServerPort
		};
		
		JOptionPane.showMessageDialog(null,comps,"How do you want to run this application?",JOptionPane.QUESTION_MESSAGE);
		
		if (rButtonGroup.getSelection()==null)
			System.exit(0);
		else {
			network.is_server=rButton1.isSelected();
			String temp1=jTextFieldExposeIP.getText(),
					temp2=jTextFieldServerIP.getText(),
					temp3=jTextFieldServerPort.getText();
			String Default="";
			try{
				Default=InetAddress.getLocalHost().getHostAddress();
			}
			catch(Exception e){
				network.restart();
			}
			if(network.is_server){
				network.serverAddress=((temp1.equals("")||temp1.equals("0.0.0.0"))?Default:temp1);
				network.port="";
			}
			else{
				network.serverAddress=((temp2.equals("")||temp2.equals("0.0.0.0"))?Default:temp2);
				network.port=(temp3.equals("")?"8000":temp3);
			}
			
		}
		
	}

}
