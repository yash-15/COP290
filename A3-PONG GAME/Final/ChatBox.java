import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintWriter;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;


@SuppressWarnings("serial")
public class ChatBox extends JPanel implements ActionListener{

	JTextArea display;
	JTextArea status;
	JTextField chatmsg;
	Network network;
	
	public void actionPerformed(ActionEvent arg0) {
		if (network.well_connected) {
			for(int j=0;j<4;j++) {
				if(network.users[j].priority!=-1 && network.me.id!=j+1)
				{
					try{
					PrintWriter prTemp=network.users[j].conn.ptWriter;
					prTemp.println(network.me.name+":"+chatmsg.getText());
					}catch(Exception e){display.append("Could not sent to "+network.users[j].name+"\n");}
				}
			}
			display.append(network.me.name+":"+chatmsg.getText()+"\n");
			chatmsg.setText("");
		}
		else {
			display.append("You are not properly connected to the network!\n");
			chatmsg.select(0, chatmsg.getText().length());
		}
	}

	public ChatBox(){
		status=new JTextArea("Status Bar");
		status.setEditable(false);
		display = new JTextArea(8,40);
		display.setEditable(false);
		chatmsg=new JTextField();
		chatmsg.addActionListener(this);
		this.add(status);
		this.add(new JScrollPane(display));
		this.add(chatmsg);
	}
}
