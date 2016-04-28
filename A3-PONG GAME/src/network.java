//package netpong;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;  

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;
import java.util.Vector;

public class network {
	static JFrame window;
	static JPanel jPanel;
	static JTextArea display;
	static JTextArea status;
	static JTextField chatmsg;
	static JButton playButton;
	static ServerSocket s_socket;
	static user me;
	static user[] users;
	static Vector<Socket> sockets;
	static boolean expose_server,is_server,well_connected;
	static int num_users=1,max_priority=1;
	static int secretKey=0;
	static int goodClient=0;
	static String serverAddress,port;//Stores the IP address and port of the server
	
	/**
	 * @param
	 * @return
	 * This dialog box will appear at the start of the application.
	 * This will allow the user to choose the mode of running:
	 * Whether he wants to start a new network or connect to a previous network
	 * If he wants to connect then IP and port of the network is asked.
	 */
	static void startDialogBox(){
		ButtonGroup rButtonGroup =new ButtonGroup();
		
		final JRadioButton rButton1=new JRadioButton("Run as Server");
		final JRadioButton rButton2=new JRadioButton("Run as Client");
		
		final JLabel jLabelExposeIP=new JLabel("Expose IP:");
		final JLabel jLabelServerIP=new JLabel("Server IP:");
		final JLabel jLabelServerPort=new JLabel("Server Port:");
		
		final JTextField jTextFieldExposeIP=new JTextField();
		final JTextField jTextFieldServerIP= new JTextField();
		final JTextField jTextFieldServerPort=new JTextField();

		rButtonGroup.add(rButton1);
		rButtonGroup.add(rButton2);
		ActionListener radio_ActionListener=new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				jLabelExposeIP.setEnabled(rButton1.isSelected());
				jTextFieldExposeIP.setEnabled(rButton1.isSelected());
				
				jLabelServerIP.setEnabled(rButton2.isSelected());
				jLabelServerPort.setEnabled(rButton2.isSelected());
				jTextFieldServerIP.setEnabled(rButton2.isSelected());
				jTextFieldServerPort.setEnabled(rButton2.isSelected());
			}
		};
		rButton1.addActionListener(radio_ActionListener);
		rButton2.addActionListener(radio_ActionListener);
		
		//rButton1.setSelected(true);
		//rButton2.setSelected(true);
		radio_ActionListener.actionPerformed(new ActionEvent(rButton1, 1001,""));
		final JComponent[] comps=new JComponent[] {
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
		{
			System.exit(0);
		}
		else {
			is_server=rButton1.isSelected();
			String temp1=jTextFieldExposeIP.getText(),
					temp2=jTextFieldServerIP.getText(),
					temp3=jTextFieldServerPort.getText();
			String Default="";
			try{Default=InetAddress.getLocalHost().getHostAddress();}
			catch(Exception e){restart();}
			serverAddress=is_server?((temp1.equals("")||temp1.equals("0.0.0.0"))?Default:temp1)
					:((temp2.equals("")||temp2.equals("0.0.0.0"))?Default:temp2);
			port=is_server?"":(temp3.equals("")?"8000":temp3);
		}
		
	}
	
	
	/**
	 * @param
	 * @return
	 * Setting up the main window of the peer-to-peer chat application
	 */
	static void initWindow()
	{
		window= new JFrame("Peer Chat");
		jPanel=new JPanel();
		BoxLayout bl=new BoxLayout(jPanel,BoxLayout.Y_AXIS);
		jPanel.setLayout(bl);
		status=new JTextArea("Status Bar");
		status.setEditable(false);
		display = new JTextArea(8,40);
		display.setEditable(false);
		chatmsg=new JTextField();
		chatmsg.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if (well_connected)
				{
					for(int j=0;j<4;j++)
					{
						if(users[j].priority!=-1 && me.id!=j+1)
						{
							try{
								System.out.println("Writing to user "+users[j].conn.socket.getRemoteSocketAddress().toString());
							PrintWriter prTemp=users[j].conn.ptWriter;
							prTemp.println(me.name+":"+chatmsg.getText());
							}catch(Exception e){display.append("Could not sent to "+users[j].name+"\n");}
						}
					}
					display.append(me.name+":"+chatmsg.getText()+"\n");
					chatmsg.setText("");
					testprint();
				}
				else {
					display.append("You are not properly connected to the network!\n");
					chatmsg.select(0, chatmsg.getText().length());
				}
			}
		});
		
		playButton = new JButton();
		playButton.setText("PLAY >>");
		playButton.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				System.out.println(arg0.getActionCommand());
				Main.main(null);
				window.setVisible(false);
				expose_server=false;
				for(int i=0;i<4;i++)
				{
					if (users[i].id!=me.id && users[i].priority!=-1)
					{
						JSONObject t_json =new JSONObject();
						try {
							t_json.put("PROTOCOL", "GAMESTART");
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						users[i].conn.ptWriter.println(t_json.toString());
						//JOptionPane.showMessageDialog(window, "DONE");
					}
				}
			}
		});
		jPanel.add(status);
		jPanel.add(new JScrollPane(display));
		jPanel.add(chatmsg);
		jPanel.add(playButton);
		window.getContentPane().add(jPanel,"East");
		window.pack();
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	
	/**
	 * @param sockAddr
	 * @param sep
	 * @return ip
	 * The sockAddr is of the form ip/ip or ip:port etc.
	 * This function extracts the ip form that
	 */
	static String SocketAddress2LocalAddress(String sockAddr,char sep)
	{
		int index=0;
		while(true)
		{
			if (sockAddr.charAt(index)==sep) break;
			index++;
		}
		return sockAddr.substring(0,index);
	}
	
	
	/**
	 * @param
	 * @return
	 * Just equivalent to restarting the application
	 */
	static void restart()
	{
		if (window!=null){window.dispose();}
		for(int j=0;j<sockets.size();j++)
		{
			try{
			sockets.elementAt(j).close();
			}catch(Exception e){}
		}
		sockets.clear();
		String[] argStrings={};main(argStrings);
	}
	
	
	///Some of the tasks performed for disconnecting user[i]
	///Assumes that the variables used are initialized
	static void disconnect(int i)
	{
		users[i].priority=-1;
		status.setText("Number of Users: "+(--num_users));expose_server=window.isVisible()
				&& is_server && num_users<4;
		if (!window.isVisible()) {
			Main.game.data.plString();
			System.out.println("our test"+(i+5-me.id)%4 + users[(i+5-me.id)%4].name);
			Main.statusBar.setText(Main.game.data._player((i+5-me.id)%4)._name()+" is now disconnected");
			Game.convertToBot((i+5-me.id)%4);}
	}
	
	
	/**
	 * @param t_name
	 * @param i
	 * @return name
	 * 
	 * t_name is the name chosen by the user
	 * But it may already be in use.
	 * So this function returns some variant of t_name
	 */
	static String giveName(String t_name,int i)
	{
		if (t_name.equals("")||t_name.equals(null)) t_name="NO_NAME";
		t_name=t_name.trim();
		
		//Check whether this name exists.
		//If it does then add indices 1,2 or 3
		int t_ln=t_name.length();
		boolean[] found={false,false,false,false};
		for (int j=0;j<4;j++)
		{
			if (j!=i)
			{
				int t_ln1=users[j].name.length();
				if (t_ln1>=t_ln)
				{
					if (t_name.equals(users[j].name.substring(0,t_ln)))
					{
						if (t_ln==t_ln1){found[0]=true;}
						else if (t_ln1==t_ln+1)
						{
							int t_c=users[j].name.charAt(t_ln);
							if (t_c>=49 && t_c<=51) found[t_c-48]=true;
						}
					}
				}
			}
		}
		
		for(int j=0;j<4;j++)
		{
			if (!found[j]) {t_name=(j==0)?t_name:t_name+String.valueOf(j);break;}
		}
		return t_name;
	}
	
	
	/**
	 * This function is called after a new user is connected to the server and 
	 * needs to connect to other clients
	 */
	public static void connectAll() throws IOException
	{System.out.println("ip: "+serverAddress+" port:"+port);
		num_users=2;
		int i1=-1,i2=-1;
		for (int i=0;i<4;i++)
		{
			if (!(me.id==i+1 ||(users[i].priority==-1)||
					(users[i].ip.equals(serverAddress)
					&& users[i].s_port==Integer.valueOf(port))))
			{
				 
				if (++num_users==3)
				{
					i1=i;
				}
				else {
					i2=i;
				}	
			}
		 }
		well_connected=num_users==2;
		if (well_connected)
		{
			display.append("Successfully connected to the peer-to-peer network\n");
			window.setTitle("Pong Game @IP: "+me.ip);
		}
		goodClient=0;
		int[] toConnect={i1,i2};
		for(int i=0;i<2;i++)
		{
			int j=toConnect[i];
			if (j>=0)
			{
				  Socket c_socket= new Socket(users[j].ip,users[j].s_port);
				  sockets.add(c_socket);
				  users[j].conn=new conPkg(c_socket,
						  new BufferedReader(new InputStreamReader(c_socket.getInputStream())),
						  new PrintWriter(c_socket.getOutputStream(),true));
				  new cl_thread(users[j].conn).start();
			}
		}
	}
	
	/**
	 * When the current pseudo-server goes down 
	 * a new pseudo-server needs to be chosen
	 * This function does that and the other necessities
	 */
	static void newServer()
	{
		int min=-1;
		for (int j=0;j<4;j++)
		{
			if (users[j].priority>0)
			{
				min=(min>=0)?(users[j].priority<users[min].priority?j:min):j;
			}
		}
		serverAddress=users[min].ip;port=String.valueOf(users[min].s_port);
		if (min+1==me.id){is_server=true;expose_server=window.isVisible() 
				&& is_server&&num_users<4;
		window.setTitle("Pong Game -- Listening @ "+me.ip+":"+me.s_port);}
		else{is_server=false;expose_server=false;}
		display.append("New Server is "+users[min].name+" @ "+serverAddress
				+":"+port+"\n");
	}
	
	
	/**
	 * Just for debugging purposes
	 */
	static void testprint()
	{
		for (int i=0;i<4;i++)
		{
			System.out.println(users[i].toString());
		}
	}
	
	
	public static void main(String[] args) {	
		
		users=new user[4];
		sockets=new Vector<Socket>();
		
		startDialogBox(); ///Get the mode of running
		
		initWindow();	///Initialize the GUI
		
		
		
		for (int i=0;i<4;i++)
		{
			users[i]=new user();
			users[i].id=i+1;
			users[i].goodClient=false;
		}
		
		me=new user();
		testprint();
		///Setting the server socket but it is not open to clients till now
		try{
			s_socket=new ServerSocket(0);
			me.s_port=s_socket.getLocalPort();
			System.out.println(s_socket.getInetAddress().toString());
			System.out.println(me.ip);
			window.setTitle("Initializing....");
			}
		catch(Exception e)
		{System.out.println("Can't have server socket");}
			
		///Connecting to an existing network if client mode 
		expose_server=is_server;
		if (!expose_server)
		{
			try{
			  Socket c_socket= new Socket(serverAddress,Integer.valueOf(port));
			  sockets.add(c_socket);
			  well_connected=false;
			  new cl_thread(new conPkg(c_socket,
					  new BufferedReader(new InputStreamReader(c_socket.getInputStream())),
							  new PrintWriter(c_socket.getOutputStream(),true))).start();
			}catch(Exception e)
			{System.out.println("No Connection or Incorrect Address");
			restart();}
		}
		
		///If you are the starting server add your data to the users array 
		else{
			users[0].name=giveName(JOptionPane.showInputDialog("Choose a Name"), 0);
			users[0].ip=serverAddress;
			users[0].priority=1;
			users[0].s_port=me.s_port;
			me=users[0];
			port=String.valueOf(me.s_port);
			window.setTitle("Pong Game -- Listening @ "+me.ip+":"+me.s_port);
			Random t_rand=new Random();
			secretKey=(int) ((1E8) *t_rand.nextDouble());
			well_connected=true;
		}
		
		///Display window
		window.setVisible(true);
		if (expose_server)
		{
			display.append("Server Started by "+users[0].name+"\n");
		}
		
		
		///Make yourself open to clients
		try{
			while(true)
			{   
				Socket s_socket1=s_socket.accept();
				sockets.add(s_socket1);
				new sr_thread(new conPkg(s_socket1,
						  new BufferedReader(new InputStreamReader(s_socket1.getInputStream())),
								  new PrintWriter(s_socket1.getOutputStream(),true))).start();
			}
		}catch(Exception e){e.printStackTrace();}
	
	}
	
	///Client Thread
	private static class cl_thread extends Thread{
		private Socket socket;
		private BufferedReader from;
		private PrintWriter to;
		private JSONObject msgJsonObjectTo;
		private JSONObject msgJsonObjectFrom;
		private String addr;
		public cl_thread(conPkg conn)
		{
			this.socket=conn.socket;
			this.from=conn.bfReader;
			this.to=conn.ptWriter;
		}
		
		public void run()
		{
			try{
				//from=new BufferedReader(new InputStreamReader(socket.getInputStream()));
				//to=new PrintWriter(socket.getOutputStream(),true);
				addr=socket.getRemoteSocketAddress().toString().substring(1);
				display.append("Connected to Server @ "+addr+"\n");
				
				///Initialize your databases from the server
				while(true)
				{	
					msgJsonObjectFrom=new JSONObject(from.readLine());
					String t_prot=msgJsonObjectFrom.optString("PROTOCOL");
					if (t_prot.equals("SUBMITKEYNDATA")){
						display.append("Asked for Secret Key and User Info @ "+addr+"\n");
						msgJsonObjectTo=new JSONObject();
						msgJsonObjectTo.put("PROTOCOL", "KEY");
						msgJsonObjectTo.put("SECRET_KEY", secretKey);
						msgJsonObjectTo.put("USER", new JSONObject(me.toString()));
						to.println(msgJsonObjectTo.toString());
						display.append("Sent Secret Key and User Info @ "+addr+"\n");
					}
					else if (t_prot.equals("CONNECTED"))
					{
						display.append("Successfully connected to "+addr+"\n");
						well_connected=++goodClient+2==num_users;
						if (well_connected)
						{
							display.append("Successfully connected to the peer-to-peer network\n");
							window.setTitle("Pong Game @IP: "+me.ip);
						}
						break;
					}
					else if (t_prot.equals("SUBMITNAMEPORT")){
						display.append("Asked for Name and Port by Server\n");
						msgJsonObjectTo=new JSONObject();
						msgJsonObjectTo.put("PROTOCOL", "NAMEPORT");
						msgJsonObjectTo.put("NAME",JOptionPane.showInputDialog("Choose a name"));
						msgJsonObjectTo.put("PORT", me.s_port);
						to.println(msgJsonObjectTo.toString());
						display.append("Sent name and port to server\n");
					}
					else if (t_prot.equals("USERDATA")){
						 display.append("Receiving User Data from Server\n");
						 JSONArray t_array=msgJsonObjectFrom.optJSONArray("USERS");
						 for(int j=0;j<4;j++)
						 {
							 JSONObject t_json=t_array.optJSONObject(j);
							 users[j].name=t_json.optString("NAME");
							 users[j].ip=t_json.optString("IP");
							 users[j].priority=t_json.optInt("PRIORITY");
							 users[j].s_port=t_json.optInt("PORT");
							 if (users[j].ip.equals(serverAddress)&&
									 users[j].s_port==Integer.valueOf(port)) users[j].conn=new conPkg(socket,from,to);
						 }
						 me=users[msgJsonObjectFrom.optInt("YOUR_ID")-1];
						 max_priority=msgJsonObjectFrom.optInt("MAX_PRIORITY");
						 secretKey=msgJsonObjectFrom.optInt("SECRET_KEY");
						 display.append("Received User Data from Server\n");
						 display.append("I am "+me.name+"\n");
						 
						 
						 ///Once you have received all the user data connect to other users
						 connectAll();
						 ///Update the status bar
						 status.setText("Number of Users: "+(num_users));
						 break;
					}
				}
				//Waits for messages from peers
				while(true){
					String x=from.readLine();
					if(x.equals(null)){
						socket.close();
					}
					else{
						
							
								try{
								msgJsonObjectFrom=new JSONObject(x);
					}catch(Exception e){}
						
								if(msgJsonObjectFrom.optString("PROTOCOL").equals("PADDLE_UPDATE"))
								{
									int t_p_index=((msgJsonObjectFrom.optInt("USER_ID")-me.id)+4) %4;
									System.out.println("Paddle Index: "+t_p_index);
									Paddle t_paddle=GameData.players[t_p_index]._paddle();
									t_paddle.set_x(msgJsonObjectFrom.optDouble("X"));
									t_paddle.set_vx(msgJsonObjectFrom.optDouble("VX"));
									t_paddle.set_ax(msgJsonObjectFrom.optDouble("AX"));
									t_paddle.isKeyPressed=msgJsonObjectFrom.optBoolean("IS_KEY_PRESSED");
								}
								else if (msgJsonObjectFrom.optString("PROTOCOL").equals("GAMESTART")){
									Main.main(null);
									window.setVisible(false);
							}
								else if (msgJsonObjectFrom.optString("PROTOCOL").equals("LOST_LIFE")){
									int t_id=msgJsonObjectFrom.optInt("USER_ID");
									Main.game.LostLife((4+t_id-me.id)%4,false);
								}
								else{
									
								}
							
						
						}}
				
			}catch(Exception e)
			{
				e.printStackTrace();
				display.append("Server @ "+socket.getRemoteSocketAddress().toString()+"is unavailable.\n");
				
				
				///If the connection is not established properly that means you must restart
				if (!well_connected)
				{ JOptionPane.showMessageDialog(null,"The server is unavailable");restart();}
				///Otherwise you assume that the server is down and continue with that
				else {
					///Set the priority to -1 for the server
					for(int j=0;j<4;j++)
					{
						String t_ip=SocketAddress2LocalAddress(addr,':');
						String t_port=addr.substring(t_ip.length()+1,addr.length());
						///Check whether it is the server
						if (users[j].ip.equals(t_ip)
								&& users[j].s_port==Integer.valueOf
								(t_port))
						{
							System.out.println("Disconnecting id "+(j+1)+users[j].name);
							disconnect(j);
							if (t_ip.equals(serverAddress) &&
									t_port.equals(port)) newServer(); ///Update for new server
							break;
						}
					}
					
				}
			}
		}
	}
	
	///Server Thread
	private static class sr_thread extends Thread{
		private Socket socket;
		private BufferedReader from;
		private PrintWriter to;
		private JSONObject msgJsonObjectTo;
		private JSONObject msgJsonObjectFrom;
		private int i;
		private boolean connect;//Tells whether the connection was authenticated or not
		public sr_thread(conPkg conn)
		{
			this.socket=conn.socket;
			this.from=conn.bfReader;
			this.to=conn.ptWriter;
		}
		
		public void run()
		{
			i=-1; //This will store the array address where the new client will be stored
			connect=false;// This tells whether we want this connection or not
		
			String remoteAddress=socket.getRemoteSocketAddress().toString();
			try{
				if(!SocketAddress2LocalAddress(socket.getLocalSocketAddress()
						.toString().substring(1), ':').equals(me.ip)){
					throw new Exception();
				}
				//from=new BufferedReader(new InputStreamReader(socket.getInputStream()));
				//to=new PrintWriter(socket.getOutputStream(),true);
				///This implies you a new connection request has been sent to the current server
				///and there are frees slots.
				if (expose_server){
					connect=true;
					display.append("Connected to "+remoteAddress+"\n");
					status.setText("Number of Users: "+(++num_users));
					expose_server=window.isVisible() && is_server &&num_users<4;
					
					msgJsonObjectTo=new JSONObject();
					
						
					///get the index of an empty location in the users array
					i=0;
					for (i=0;i<4;i++)
					{
						if (users[i].priority==-1) break;
					}
					users[i].ip=SocketAddress2LocalAddress(remoteAddress.substring(1),':');
					users[i].priority=++max_priority;
					users[i].conn=new conPkg(socket,from,to);
					///attempt to receive name and port in the next exchange
					msgJsonObjectTo.put("PROTOCOL", "SUBMITNAMEPORT");
					to.println(msgJsonObjectTo.toString());
					display.append("Asked "+remoteAddress+" for name and port\n");

					///Retrieves the name and the port
					msgJsonObjectFrom=new JSONObject(from.readLine());
					if (msgJsonObjectFrom.optString("PROTOCOL").equals("NAMEPORT"))
					{
						users[i].s_port=msgJsonObjectFrom.optInt("PORT");
						String t_name=msgJsonObjectFrom.optString("NAME");
						
						///After properly setting t_name
						users[i].name=giveName(t_name, i);
						
						display.append(remoteAddress+" --> "+users[i].name+"\n");
						display.append("Sending UserData to "+users[i].name+"\n");
						
						///Now the new user is properly set
						///We need to send the info of all users to this person
						msgJsonObjectTo =new JSONObject();
						msgJsonObjectTo.put("PROTOCOL", "USERDATA");
						JSONArray jsonUsersArray=new JSONArray();
						for (int j=0;j<4;j++)
						{
							JSONObject jsonObject=new JSONObject(users[j].toString());
							jsonUsersArray.put(j,jsonObject);
						}
						msgJsonObjectTo.put("USERS", jsonUsersArray);
						msgJsonObjectTo.put("YOUR_ID",i+1);
						msgJsonObjectTo.put("MAX_PRIORITY", max_priority);
						msgJsonObjectTo.put("SECRET_KEY", secretKey);
						to.println(msgJsonObjectTo);
						display.append("Sent UserData to "+users[i].name+"\n");
						testprint();
					}
					else {
						///Disconnects if any other info is sent
						display.append("Incorrect Info! Connection cut @ "+remoteAddress+"\n");
						socket.close();
						
					}
					
				}
				
				/**
				 * This handles the case when there are no frees slots 
				 * or request is sent to a peer other than the server
				 */
				else{
					

					/**
					 *  If the request is sent to a non-server peer
					 *  We need to check if it is connected to the server
					 *  We do this through the secretKey
					 *  So we at first request for the secret key and then verify
					 *  
					 */
					connect=false;
					display.append("Attempt to connect from "+remoteAddress+"\n");
					if (num_users<4){
					msgJsonObjectTo=new JSONObject();
					msgJsonObjectTo.put("PROTOCOL", "SUBMITKEYNDATA");
					to.println(msgJsonObjectTo.toString());
					display.append("Asking for secret Key @ "+remoteAddress+"\n");
					
					///Wait for the response
					msgJsonObjectFrom=new JSONObject(from.readLine());
					display.append("Verifying Key @ "+remoteAddress+"\n");
					try{
					if (msgJsonObjectFrom.optString("PROTOCOL").equals("KEY"))
					{
						connect=msgJsonObjectFrom.optInt("SECRET_KEY")==secretKey;
					}}catch(Exception e){connect=false;}
					
					/**
					 * If verification is successful i.e. connect =true
					 * Then proceed with the connection
					 */
					
					if (connect)
					{
						JSONObject tjson_user=msgJsonObjectFrom.optJSONObject("USER");
						i=tjson_user.optInt("ID")-1;
						user t_user=users[i];
						t_user.name=tjson_user.optString("NAME");
						t_user.ip=tjson_user.optString("IP");
						t_user.s_port=tjson_user.optInt("PORT");
						t_user.priority=tjson_user.optInt("PRIORITY");
						t_user.conn=new conPkg(socket,from,to);
						max_priority=t_user.priority>max_priority?t_user.priority:max_priority;
						display.append("Connected to "+remoteAddress+"\n");
						status.setText("Number of Users: "+(++num_users));
						expose_server=window.isVisible()&&is_server &&num_users<4;
						msgJsonObjectTo=new JSONObject();
						msgJsonObjectTo.put("PROTOCOL","CONNECTED");
						to.println(msgJsonObjectTo);
					}
					
					}
					
					
					/**
					 * If there are already 4 players then close the connection 
					 * If incorrect secret Key we close the connection
					 * Any one of the above happens if connect=false
					 */
					if (!connect){
					display.append("Connection Refused @ "+remoteAddress+"\n");
					socket.close(); //Close the connection 
					
					}
				}
				while(true){
					String x=from.readLine();
					if(x.equals(null)){
						socket.close();
					}
					else{
						
						try{
							msgJsonObjectFrom=new JSONObject(x);
				}catch(Exception e){}
								if(msgJsonObjectFrom.optString("PROTOCOL").equals("PADDLE_UPDATE"))
								{
									int t_p_index=((msgJsonObjectFrom.optInt("USER_ID")-me.id)+4) %4;
									Paddle t_paddle=GameData.players[t_p_index]._paddle();
									t_paddle.set_x(msgJsonObjectFrom.optDouble("X"));
									t_paddle.set_vx(msgJsonObjectFrom.optDouble("VX"));
									t_paddle.set_ax(msgJsonObjectFrom.optDouble("AX"));
									t_paddle.isKeyPressed=msgJsonObjectFrom.optBoolean("IS_KEY_PRESSED");
								}
								else if (msgJsonObjectFrom.optString("PROTOCOL").equals("GAMESTART")){
									Main.main(null);
									window.setVisible(false);
							}
								else if (msgJsonObjectFrom.optString("PROTOCOL").equals("BALL_UPDATE")){
									int num90rot=0,s_id=msgJsonObjectFrom.optInt("USER_ID");
									double xco,yco;double[] x1=new double[2];
									num90rot=((me.id-s_id)+4)%4;
									
									Ball t_ball=GameData.balls.get(msgJsonObjectFrom.optInt("BALL_ID"));
									
									xco=msgJsonObjectFrom.optDouble("X");
									yco=msgJsonObjectFrom.optDouble("Y");
									x1=Physics.Rotate(num90rot, xco,yco);
									t_ball.set_x(x1[0]);t_ball.set_y(x1[1]);
									
									xco=msgJsonObjectFrom.optDouble("VX");
									yco=msgJsonObjectFrom.optDouble("VY");
									x1=Physics.Rotate(num90rot, xco,yco);
									t_ball.set_vx(x1[0]);t_ball.set_vy(x1[1]);
									
									xco=msgJsonObjectFrom.optDouble("AX");
									yco=msgJsonObjectFrom.optDouble("AY");
									x1=Physics.Rotate(num90rot, xco,yco);
									t_ball.set_ax(x1[0]);t_ball.set_ay(x1[1]);
									
									t_ball.set_theta(msgJsonObjectFrom.optDouble("THETA")-num90rot*3.1415926);
									t_ball.set_omega(msgJsonObjectFrom.optDouble("OMEGA"));
									t_ball.set_alpha(msgJsonObjectFrom.optDouble("ALPHA"));
									
								}
								else if (msgJsonObjectFrom.optString("PROTOCOL").equals("LOST_LIFE")){
									int t_id=msgJsonObjectFrom.optInt("USER_ID");
									Main.game.LostLife((4+t_id-me.id)%4,false);
								}
								else {
									
								}
						
						}}
			}catch(Exception e)
			{e.printStackTrace();
				try{socket.close();}catch(Exception e1){}
				finally{
					if(connect){
					disconnect(i);
					display.append("Disconnected @ "+remoteAddress+"\n");
					}}}
		}


	}
}








