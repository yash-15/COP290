import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Scanner;
import javax.swing.JLabel;
import javax.swing.Timer;

/**
 * 
 */

/**
 * @author YKB
 *
 */
public class Game implements ActionListener{

	/**
	 * 
	 */
	private boolean debug=false;
	private final int render_delay = 50;
	private Scanner scanner = new Scanner(System.in);
	private GameData data;
	private GUI UI;
	private JLabel statusBar;
	private Timer timer;
	private long prevTime,curTime;
	private int size;
	
	public Game(Main parent) {

		System.out.println("Constructing Game...");
		
		statusBar = parent._statusBar();
		
		data = new GameData();
		UI = new GUI(this,data);
		
		System.out.println("Game Constructed!");
	}
	
	public void actionPerformed(ActionEvent evt) {
		message(evt.getWhen()+" Timer Triggered");
		//System.out.println("Hello2 "+statusBar.toString());
		//statusBar.setText("Action event : "+ evt.getWhen());//+evt.toString());
		curTime = evt.getWhen();
		
		//UI.clear();
		move();
		CollisionWithWalls();
		CollisionWithPaddles();
		UI.repaint();
		prevTime = curTime;
	}
		
	private void move() {
		
		double delta = (curTime-prevTime)/1000.0;
		
		//message("Move() with delta : "+delta);
		
		for(Ball ball : data._balls()){
			ball.set_x (ball._x()+delta*ball._vx());
			ball.set_y (ball._y()+delta*ball._vy());
			ball.set_vx(ball._vx()+delta*ball._ax());
			ball.set_vy(ball._vy()+delta*ball._ay());
			//statusBar.setText("X : "+String.valueOf(ball._x())+" Y : "+String.valueOf(ball._y()));
		}
		
		Player[] player = data._players();
		Paddle paddle;
		for(int i=0; i<data._numberOfPlayers();i++){
			if(player[i]._isAlive()){
				paddle=player[i]._paddle();
				paddle.set_x(paddle._x()+delta*paddle._vx());
				if(player[i]._isHuman()){
					if(!paddle.isKeyPressed && paddle._vx()*paddle._ax()>=0){
						paddle.set_vx(0);
						paddle.set_ax(0);
					}
					else
						paddle.set_vx(paddle._vx()+delta*paddle._ax());
				}
				else if(player[i]._isBot()) {
					player[i]._AI().playMove();
					paddle.set_vx(paddle._vx()+delta*paddle._ax());
				}
			}
		}
	}
  
	private void CollisionWithWalls() {
		for(Ball ball: data._balls()){
			if(Math.abs(ball._x())+ball._rad()>=size/2.0)
				if(ball._x()*ball._vx()>0)
					ball.set_vx(-ball._vx());
			if(Math.abs(ball._y())+ball._rad()>=size/2.0)
				if(ball._y()*ball._vy()>0)
					ball.set_vy(-ball._vy());
		}
	}
	
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
						if(paddle._dx()*ball._vy()<0)
							ball.set_vy(-ball._vy());
						if(paddle._dy()*ball._vx()>0)
							ball.set_vx(-ball._vx());
					}
			}
		}
	}
	/*
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
			//System.out.println("Enter Player "+(i+1) + " Type : ");
			//int val = scanner.nextInt();
			player = new Player(data);
			player.set_isAlive(true);
			player.set_isHuman(i==0);
			player.set_isBot(i>0);
			player._paddle().set_positionID(i);
			data.addPlayer(player);
		}
		Ball ball = new Ball(20,300,0,500,500);
		ball.set_color(Color.BLUE);
		data.addBall(ball);

	//	ball = new Ball(20,-30,-30,-20,-20);
	//	ball.set_color(Color.GREEN);
	//	data.addBall(ball);
		//ball = new Ball(20,100,100,-20,-20);
		//data.addBall(ball);
	//	ball.set_color(Color.CYAN);
		
	}
	
	private void message(String str){
		if(debug)
			System.out.println(str);
	}
	

	public GUI _UI(){return UI;}
	public GameData _data(){return data;}
	public JLabel _statusBar()	{	return statusBar;	}
	
	public void play(){
		prevTime = System.currentTimeMillis();
		System.out.println("Starting play() at "+prevTime);
		timer = new Timer(render_delay,this);
		timer.start();
	}

}
