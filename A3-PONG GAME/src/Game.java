import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.print.attribute.standard.Media;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.Timer;

import org.json.JSONException;
import org.json.JSONObject;

public class Game implements ActionListener{
	
	private final int render_delay = 10; // The time period of timer in ms 
	public static GameData data;
	private GUI UI;
	private Timer timer;
	private long prevTime,curTime;  
	public int size;
	private int localUser;
	private JLabel statusBar;
	private int send_counter=0;
	
	public Game(Main parent) {

		System.out.println("Constructing Game...");
		statusBar = parent._statusBar();
		data = new GameData();
		UI = new GUI(this);
		localUser = -1;
		System.out.println("Game Constructed!");
	}
	
	public GUI _UI(){return UI;}
	public GameData _data(){return data;}
	public JLabel _statusBar(){return statusBar;}
	public int _localUser(){return localUser;}
	public int _size(){return size;}
	
	/**
	 *  Setup the parameters of the game.
	 *  Forwards the window size to data and UI
	 *  Forwards Local User to UI and HumanPlayer(keyboard)
	 *  Size is available when AIs are instantiated
	 */

	public void setup(int numberOfPlayers) throws Exception {
		
		this.localUser = 0;
		
		//TODO : Verify size below
		this.size = UI.getWidth()-60;
		//UI.getToolkit().getScreenSize();
		
		System.out.println(size +" is the size of box");
		data.set_windowSize(size);
		UI.set_windowSize(size);
		
		for(int i=network.me.id-1,j=0;j<numberOfPlayers;i++,j++) {
			if(network.users[i%4].priority==-1){
				addBotPlayer(network.users[i%4].name,j);
			}else
				addHumanPlayer(network.users[i%4].name,j);
		}
		
		Ball ball = new Ball(10,200,0,600,600);
		ball.set_color(Color.DARK_GRAY);
		ball.set_id(0);
		data.addBall(ball);
		ball = new Ball(10,20,0,700,400);
		ball.set_color(Color.BLUE);
		ball.set_id(1);
		data.addBall(ball);
		UI.set_localUser(localUser);
	}
	
	/**
	 * Add a Human Player with given global Position. UI would still be
	 * rendered as if he is playing at Position.BOTTOM.
	 * If index of this Human player is same as local user, then 
	 * KeyListener is also instantiated for it.
	 * @param pos Integer corresponding to Position of the paddle
	 * @throws Exception Maximum Players Limit Reached Already. 
	 */
	public void addHumanPlayer(String name, int pos) throws Exception	{
		if(data._numberOfPlayers()==data.MAXPLAYERS)
			throw new Exception("Maximum limit reached.");
		System.out.println("Adding human player at index "+data._numberOfPlayers()+" as "+Position.values()[pos]);
		Player t = new Player();
		data.set_player(data._numberOfPlayers(),t);
		t.id=data._numberOfPlayers();
		t.set_name(name);
		t.set_isAlive(true);
		t.set_isBot(false);
		t.set_isHuman(true);
		t._paddle().set_positionID(pos);
		if(data._numberOfPlayers()==localUser)
			UI.addKeyListener(new HumanPlayer(t._paddle()));
		data.set_numberOfPlayers(1+data._numberOfPlayers());
	}
	
	public void addBotPlayer(String name, int pos) throws Exception	{
		if(data._numberOfPlayers()==data.MAXPLAYERS)
			throw new Exception("Maximum limit reached.");
		System.out.println("Adding bot player at index "+data._numberOfPlayers()+" as "+Position.values()[pos]);
		Player t = new Player();
		t.id=data._numberOfPlayers();
		data.set_player(data._numberOfPlayers(),t);	
		t.set_name(name);
		t.set_isAlive(true);
		t.set_isBot(true);
		t.set_isHuman(false);
		t._paddle().set_positionID(pos);
		t.set_AI(new AI(data,t._paddle()));
		data.set_numberOfPlayers(1+data._numberOfPlayers());
	}
	
	public static void convertToBot(int i)
	{
		Player t_player=data._player(i);
		t_player.isBot=true;
		t_player.isHuman=false;
		t_player.set_AI(new AI(data, t_player._paddle()));
	}
	
	public void play(){
		prevTime = System.currentTimeMillis();
		System.out.println("Starting play() at "+prevTime);
		timer = new Timer(render_delay,this);
		if(network.is_server){
			for (Ball ball:data._balls())
				BallMessage(ball._id());
		}
		timer.start();
	}
	
	public void actionPerformed(ActionEvent evt) {
		
		curTime = evt.getWhen();
	
		move();
		CollisionWithCorners();
		CollisionWithPaddles();
		CollisionWithWalls();
		UI.repaint(); 
		send_counter+=render_delay;
		if(send_counter>=1000)
		{
			send_counter-=1000;
			if (network.is_server) for (Ball ball:data._balls()) BallMessage(ball._id());
		}
		prevTime = curTime;
	}
		
	Player player;
	Paddle paddle;
	
	/**
	 * Updates the parameters of the paddles and the balls when called.
	 * Called at every timer event
	 */
	private void move() {
		
		double delta = (curTime-prevTime)/1000.0;
		
		for(int i=0; i<data._numberOfPlayers();i++){
			player=data._player(i);
			if(player._isAlive()){
				paddle=player._paddle();
				paddle.set_x(paddle._x()+delta*paddle._vx());
				paddle.set_vx(paddle._vx()+delta*paddle._ax());
				if(player._isBot() && network.is_server)
					player._AI().playMove();
				if(!paddle._isKeyPressed() && paddle._vx()*paddle._ax()>=0){
					paddle.set_vx(0);
					paddle.set_ax(0);
				}
			}
		}
		
		for(Ball ball : data._balls()){
			ball.set_x (ball._x()+delta*ball._vx());
			ball.set_y (ball._y()+delta*ball._vy());
			ball.set_vx(ball._vx()+delta*ball._ax());
			ball.set_vy(ball._vy()+delta*ball._ay());
		}
	}
  
	/**
	 * Checks whether a ball has collided with any of the walls
	 * If yes, then appropriate changes in the ball's parameters are made
	 */
	private void CollisionWithWalls() {
		for(Ball ball: data._balls()){
			if(Math.abs(ball._x())+ball._rad()>=size/2.0)
				if(ball._x()*ball._vx()>0){
					ball.set_vx(-ball._vx());
					int t_id=det_wall(ball._x(),ball._y(), ball._rad());
					if (t_id==0 || (t_id!=-1 && network.is_server
							&& data._player(t_id)._isBot())){
						BallMessage(ball._id());
						if(data._player(t_id).isAlive) LostLife(t_id,true);}
					
					
					//statusBar.setText("WALL WALL WALL");
				}
			if(Math.abs(ball._y())+ball._rad()>=size/2.0)
				if(ball._y()*ball._vy()>0){
					ball.set_vy(-ball._vy());
					int t_id=det_wall(ball._x(),ball._y(), ball._rad());
					if (t_id==0 || (t_id!=-1 && network.is_server
							&& data._player(t_id)._isBot())){
						BallMessage(ball._id());
						if(data._player(t_id).isAlive) LostLife(t_id,true);
					}
					//statusBar.setText("WALL WALL WALL");
				}
		}
	}
	
	/*
	 * Check whether a ball is at a corner location 
	 * If yes, then trace back the ball's path
	 */
	private void CollisionWithCorners(){
		for(Ball ball: data._balls()){
			if(Math.abs(ball._x())+ball._rad()>=9*size/20.0 && Math.abs(ball._y())+ball._rad()>=9*size/20.0)
				if(ball._x()*ball._vx()>0 && ball._y()*ball._vy()>0){
					ball.set_vx(-ball._vx());ball.set_vy(-ball._vy());
					/*
					int t_id=det_wall(ball._x(),ball._y(), ball._rad());
					if (t_id==0 || (t_id!=-1 && network.is_server
							&& data._player(t_id)._isBot())) BallMessage(ball._id());
					*/
					//statusBar.setText("WALL WALL WALL");
				}
		}
	}
	/*
	private void CollisionWithBalls(){
		for(Ball ball1: data._balls()){
			for(Ball ball2:data._balls()){
				if (ball2._id()>ball1._id()){
					double x1=ball1._x(),x2=ball2._x(),y1=ball1._y(),y2=ball2._y();
					double dx=x2-x1,dy=y2-y1;
					//CHeck whether they are in contact
					if (Math.hypot(dx, dy)<=ball1._rad()+ball2._rad()){
						double theta;
						try{theta=Math.atan(dy/dx);}catch(Exception e){theta=1.570796*(dy>0?1:-1);}
						double vx1=ball1._vx(),vx2=ball2._vx(),vy1=ball1._vy(),vy2=ball2._vy();
						double[] v_1=Physics.RotateDouble(theta, vx1, vy1);
						double[] v_2=Physics.RotateDouble(theta, vx2, vy2);
						double m1=Math.pow(ball1._rad(),2),m2=Math.pow(ball2._rad(),2);
						v_1[0]=vx1*(m1-data._restitution())
					}
				}
			}
		}
	}
	*/
	/**
	 * Checks whether a ball has collided with any of the paddles
	 * If yes, then appropriate changes in the ball's parameters are made
	 */
	private void CollisionWithPaddles() {
		int numberOfPlayers = data._numberOfPlayers();
		double[] res = {0,0};
		Paddle paddle;
		for(Ball ball: data._balls()){
			for(int i=0;i<numberOfPlayers;i++) {
				if (data._player(i)._isAlive()){
					paddle=data._player(i)._paddle();
					res = Physics.Rotate(paddle._positionID(), ball._x(), ball._y());
					if(res[1]-ball._rad()<=paddle._y()+paddle._wdt()/2){
						if(Math.abs(res[0]-paddle._x())<=paddle._len()/2+ball._rad()) {
							if(paddle._dx()*ball._vy()<0){
								ball.set_vy(-ball._vy());
							    ball.set_vx(ball._vx()+data._restitution()*paddle._dx()*paddle._vx());
							    if (i==0 || (network.is_server && data._player(i).isBot)) BallMessage(ball._id());
							    //statusBar.setText("PADDLE PADDLE PADDLE");
						    }
							if(paddle._dy()*ball._vx()>0){
								ball.set_vx(-ball._vx());
								ball.set_vy(ball._vy()+data._restitution()*paddle._dy()*paddle._vx());
								if (i==0 || (network.is_server && data._player(i).isBot)) BallMessage(ball._id());
								//statusBar.setText("PADDLE PADDLE PADDLE");
							}
						}
					}
				}
			}
		}
	}
	
	//i for ball_id
	private void BallMessage(int i)
	{
		JSONObject t_json=new JSONObject();
		Ball t_ball=GameData.balls.get(i);
		try {
			t_json.put("PROTOCOL", "BALL_UPDATE");
			t_json.put("USER_ID", network.me.id);
			t_json.put("BALL_ID", i);
			t_json.put("X",t_ball._x());
			t_json.put("Y",t_ball._y());
			t_json.put("VX",t_ball._vx());
			t_json.put("VY",t_ball._vy());
			t_json.put("AX",t_ball._ax());
			t_json.put("AY",t_ball._ay());
			t_json.put("THETA",t_ball._theta());
			t_json.put("OMEGA",t_ball._omega());
			t_json.put("ALPHA",t_ball._alpha());
			t_json.put("COLOR",t_ball._color());
			for(int j=0;j<4;j++)
			{
				if(network.users[j].id!=network.me.id && network.users[j].priority!=-1){
					network.users[j].conn.ptWriter.println(t_json.toString());
				}
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	/* Returns 0,1,2 ,3 as per the walls 
	 * Returns -1 if not applicable
	 */
	private int det_wall(double x,double y,double r)
	{
		if (x+r>=size/2) return 1;
		else if (x-r<=-size/2) return 3;
		else if(y+r>=size/2) return 2;
		else if (y-r<=-size/2) return 0;
		else return -1;
	}
	
	/**
	 * If a player loses a life then appr. things are done and 
	 * this info is send to others
	 * i is the id of the user
	 */
	public void LostLife(int i,boolean send)
	{
		data._player(i).set_health(data._player(i)._health()-1);
		if(data._player(i)._health()==0)
		{
			data._player(i).isAlive=false;
			data._player(i).isBot=false;
			data._player(i).isHuman=false;
			data.set_num_alive(data._num_alive()-1);
		}
		if(send){
			JSONObject t_json=new JSONObject();
			try {
				t_json.put("PROTOCOL", "LOST_LIFE");
				int t_id=network.me.id+i;
				t_json.put("USER_ID", t_id>4?t_id-4:t_id);		
				for(int j=0;j<4;j++)
				{
					if(network.users[j].id!=network.me.id && network.users[j].priority!=-1){
						network.users[j].conn.ptWriter.println(t_json.toString());
					}
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		UI.repaint();  //Update the screen info of the player
		//Check whether only one player is alive.
		if (data._num_alive()==1)
		{
			timer.stop();
			for (int j=0;j<4;j++)
			{
				if(data._player(j).isAlive)
				{
					//data.GameOn=false;
					JOptionPane.showMessageDialog(Main.frame, "Game Over: Winner is "+data._player(j)._name());
					network.window.setVisible(true);
					Main.frame.dispose();
				}
			}
		}
		
	}
}
