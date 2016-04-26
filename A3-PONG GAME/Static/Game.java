import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.Timer;

public class Game implements ActionListener{
	
	private final int render_delay = 10; // The time period of timer in ms 
	private GameData data;
	private GUI UI;
	private Timer timer;
	private long prevTime,curTime;  
	public int size;
	private int localUser;
	private JLabel statusBar;
	
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
		this.size = UI.getWidth()-50;
		//UI.getToolkit().getScreenSize();
		
		System.out.println(size +" is the size of box");
		data.set_windowSize(size);
		UI.set_windowSize(size);
		
		for(int i=0;i<numberOfPlayers;i++) {
			if(network.users[i].priority==-1){
				addBotPlayer(network.users[i].name,(i-network.me.id+4)%4);
			}else
				addHumanPlayer(network.users[i].name,(i-network.me.id+4)%4);
		}
		
		Ball ball = new Ball(10,200,0,300,400);
		ball.set_color(Color.DARK_GRAY);
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
		data.set_player(data._numberOfPlayers(),t);	
		t.set_name(name);
		t.set_isAlive(true);
		t.set_isBot(true);
		t.set_isHuman(false);
		t._paddle().set_positionID(pos);
		t.set_AI(new AI(data,t._paddle()));
		data.set_numberOfPlayers(1+data._numberOfPlayers());
	}
	
	public void play(){
		prevTime = System.currentTimeMillis();
		System.out.println("Starting play() at "+prevTime);
		timer = new Timer(render_delay,this);
		timer.start();
	}
	
	public void actionPerformed(ActionEvent evt) {
		
		curTime = evt.getWhen();
	
		move();
		CollisionWithPaddles();
		CollisionWithWalls();
		UI.repaint();
		
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
				if(player._isBot())
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
					//statusBar.setText("WALL WALL WALL");
				}
			if(Math.abs(ball._y())+ball._rad()>=size/2.0)
				if(ball._y()*ball._vy()>0){
					ball.set_vy(-ball._vy());
					//statusBar.setText("WALL WALL WALL");
				}
		}
	}
	
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
				paddle=data._player(i)._paddle();
				res = Physics.Rotate(paddle._positionID(), ball._x(), ball._y());
				if(res[1]-ball._rad()<=paddle._y()+paddle._wdt()/2){
					if(Math.abs(res[0]-paddle._x())<=paddle._len()/2+ball._rad()) {
						if(paddle._dx()*ball._vy()<0){
							ball.set_vy(-ball._vy());
						    ball.set_vx(ball._vx()+data._restitution()*paddle._dx()*paddle._vx());
						    //statusBar.setText("PADDLE PADDLE PADDLE");
					    }
						if(paddle._dy()*ball._vx()>0){
							ball.set_vx(-ball._vx());
							ball.set_vy(ball._vy()+data._restitution()*paddle._dy()*paddle._vx());
							//statusBar.setText("PADDLE PADDLE PADDLE");
						}
					}
				}
			}
		}
	}
	
}
