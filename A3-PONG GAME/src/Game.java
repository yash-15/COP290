import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

/**
 * @author YKB
 *
 */

public class Game implements ActionListener{
	
	private final int render_delay = 10; // The time period of timer in ms 
	private GameData data;
	private GUI UI;
	private Timer timer;
	private long prevTime,curTime;  
	public static int size;
	
	public Game() {

		System.out.println("Constructing Game...");
		data = new GameData();
		UI = new GUI(data);
		System.out.println("Game Constructed!");
	}
	
	public GUI _UI(){return UI;}
	public GameData _data(){return data;}
	
	/**
	 *  Setup the parameters of the game
	 */
	public void setup(int numberOfPlayers) throws Exception {
		size = UI.getWidth()-3;
		System.out.println(size +" is the size of box");
		data.set_windowSize(size);
		UI.set_windowSize(size);
		UI.setFocusable(true);
		UI.addKeyListener(new HumanPlayer(this));
	       
		Player player;
		
		for(int i=0;i<numberOfPlayers;i++) {
			player = new Player(data);
			player.set_isAlive(true);
			player.set_isHuman(i==0);
			player.set_isBot(i>0);
			player._paddle().set_positionID(i);
			data.addPlayer(player);
		}
		Ball ball = new Ball(10,300,0,150,200);
		ball.set_color(Color.DARK_GRAY);
		data.addBall(ball);
	
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
		
	/**
	 * Updates the parameters of the paddles and the balls when called.
	 * Called @ every timer event
	 */
	
	private void move() {
		
		double delta = (curTime-prevTime)/1000.0;
		
		//TODO: Can be a better way so that you need not make references every time
		Player[] player = data._players();
		Paddle paddle;
		
		for(int i=0; i<data._numberOfPlayers();i++){
			if(player[i]._isAlive()){
				paddle=player[i]._paddle();
				paddle.set_x(paddle._x()+delta*paddle._vx());
				if(player[i]._isBot()) {
					player[i]._AI().playMove();
				}
				if(!paddle.isKeyPressed && paddle._vx()*paddle._ax()>=0){
					paddle.set_vx(0);
					paddle.set_ax(0);
				}
				else
					{paddle.set_vx(paddle._vx()+delta*paddle._ax());}
				
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
					Main.statusBar.setText("WALL WALL WALL");
				}
			if(Math.abs(ball._y())+ball._rad()>=size/2.0)
				if(ball._y()*ball._vy()>0){
					ball.set_vy(-ball._vy());
					Main.statusBar.setText("WALL WALL WALL");
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
				if(res[1]-ball._rad()<=paddle._y()+paddle._wdt()/2)
					if(Math.abs(res[0]-paddle._x())<=paddle._len()/2+ball._rad()) {
						if(paddle._dx()*ball._vy()<0){
							ball.set_vy(-ball._vy());
						    ball.set_vx(ball._vx()+data._restitution()*paddle._dx()*paddle._vx());
						    Main.statusBar.setText("PADDLE PADDLE PADDLE");
					    }
						if(paddle._dy()*ball._vx()>0){
							ball.set_vx(-ball._vx());
							ball.set_vy(ball._vy()+data._restitution()*paddle._dy()*paddle._vx());
							Main.statusBar.setText("PADDLE PADDLE PADDLE");
						}
					}
			}
		}
	}
	
	

}
