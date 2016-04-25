import java.awt.Color;
import java.util.ArrayList;

/**
 * @author YKB
 *
 */

/*** Board Configuration :
 * 					 (2) 
 * 	   //===========================\\
 * 	   ||							||
 * 	   ||							||
 * 	   ||							||
 * 	   ||							||
 * 	   ||							||
 * 	(3)||			(0,0)			||(1)
 * 	   ||							||
 * 	   ||							||
 * 	   ||							||
 * 	   ||							||
 * 	   ||							||
 * 	   \\===========================//
 * 					 (0)
 * 
 * 		0 : DOWN  ;	 1 : RIGHT  ;  2 : UP  ;  3 : RIGHT  
 */


public class GameData {
	
	/** 
	 * MAXPLAYERS		: maximum number of players in the game
	 * numberOfPlayers 	: number of players in the game : 2,3,4 are acceptable
	 * centralPlayer	: player performing the computations : should be a Human Player
	 * localUser		: ID of user running on local machine
	 * players			: array of Player objects
	 * balls			: Set of all active balls in the game
	 */
	
	public static final int MAXPLAYERS = 4;
	private int numberOfPlayers;
	private int centralPlayer;
	private int localUser;
	private Player[] players;
	private ArrayList<Ball> balls;
	private int windowSize;
	private double restitution;
	
	public GameData() {

		System.out.println("Constructing GameData...");
		numberOfPlayers = centralPlayer = 0;
		players = new Player[MAXPLAYERS];
		balls = new ArrayList<Ball>();

		localUser=0;
		restitution=0.3;
		System.out.println("GameData Constructed!");
	}

	public int _numberOfPlayers()	{	return numberOfPlayers;	}
	public int _centralPlayer()		{	return centralPlayer;	}
	public Player[] _players()		{	return players;			}
	public Player _player(int i)	{	return players[i];		}
	public ArrayList<Ball> _balls()	{	return balls;			}
	public double _restitution()    {	return restitution;		}
	
	public void set_centralPlayer(int t)	{
		if(t>=0 && t<numberOfPlayers && players[t]._isHuman())
			centralPlayer=t;
		else
			throw new IllegalArgumentException();
	}
	
	public void addPlayer(Player t) throws Exception	{
		if(numberOfPlayers==MAXPLAYERS)
			throw new Exception("Maximum limit reached.");
		else{
			players[numberOfPlayers]=t;
			numberOfPlayers++;
		}	
	}

	public int _localUser()	{	return localUser;	}
	public int _windowSize(){	return windowSize;	}
	
	public void set_windowSize(int t){	
		windowSize=t;
		Paddle.def_len = windowSize/5.0;
		Paddle.def_wdt = windowSize/20.0;
		Paddle.max_speed = windowSize;
		Paddle.max_x = 0.35*windowSize;
	}
	
	public void addBall(Ball ball) {
		balls.add(ball);
	}
}

class Ball{
	
	private	double x=0,y=0,rad=0,vx=0,vy=0,ax=0,ay=0,omega=0,alpha=0;
	private Color color=Color.RED;
	
	public Ball(){
	}
	public Ball(double _rad){
		rad=_rad;
	}
	public Ball(double _rad, double _x, double _y){
		x=_x;y=_y;
		rad=_rad;
	}
	public Ball(double _rad, double _x, double _y, double _vx, double _vy){
		x=_x;y=_y;
		vx=_vx;vy=_vy;
		rad=_rad;
	}
	
	public double _x()		{	return x;		}
	public double _y()		{	return y;		}
	public double _rad()	{	return rad;		}
	public double _vx()		{	return vx;		}
	public double _vy()		{	return vy;		}
	public double _ax()		{	return ax;		}
	public double _ay()		{	return ay;		}
	public double _omega()	{	return omega;	}
	public double _alpha()	{	return alpha;	}
	public Color  _color()	{	return color;	}

	public void set_x(double t)		{	x=t;	}
	public void set_y(double t)		{	y=t;	}
	public void set_rad(double t)	{	rad=t;	}
	public void set_vx(double t)	{	vx=t;	}
	public void set_vy(double t)	{	vy=t;	}
	public void set_ax(double t)	{	ax=t;	}
	public void set_ay(double t)	{	ay=t;	}
	public void set_omega(double t)	{	omega=t;}
	public void set_alpha(double t)	{	alpha=t;}
	public void set_color(Color t)	{	color=t;}
	
}

enum Position{
	LEFT,RIGHT,UP,DOWN;
}

class Paddle{
	
	private Position pos;
	private double len,wdt,x,y,vx,ax; 
	private Color color=Color.BLUE;
	static double def_len, def_wdt, max_speed, max_x;
	boolean isKeyPressed;
	
	public Paddle() {
		pos = Position.DOWN;
		x=y=vx=ax=0;
		len=def_len;
		wdt=def_wdt;
		isKeyPressed = false;
	}

	public double _x()		{	return x;		}
	public double _y()		{	return -9.5*def_wdt;	}
	public double _vx()		{	return vx;		}
	public double _ax()		{	return ax;		}
	public double _len()	{	return len;		}
	public double _wdt()	{	return wdt;		}
	public Position _pos()	{	return pos;		}
	public Color  _color()	{	return color;	}
	
	public int _positionID() {
		switch (pos) {
			case DOWN:	return 0;
			case RIGHT:	return 1;
			case UP:	return 2;
			case LEFT:	return 3;
		}
		return 0;	//should not reach here!
	}
	
	public int _dx(){
		switch (pos) {
			case DOWN:	return 1;
			case RIGHT:	return 0;
			case UP:	return -1;
			case LEFT:	return 0;
		}
		return 0;	//should not reach here!
	}
	
	public int _dy(){
		switch (pos) {
		case DOWN:	return 0;
		case RIGHT:	return 1;
		case UP:	return 0;
		case LEFT:	return -1;
	}
	return 0;	//should not reach here!
}
	
	
	public void set_x(double t)	{
		x=Math.max(-max_x,Math.min(max_x, t));
	}
	public void set_y(double t)		{	y=t;	}
	public void set_vx(double t) {
		vx=Math.max(-max_speed,Math.min(max_speed,t));
	}
	public void set_ax(double t)	{	ax=t;	}
	public void set_len(double t)	{	len=t;	}
	public void set_wdt(double t)	{	wdt=t;	}
	public void set_pos(Position t)	{	pos=t;	}	
	public void set_color(Color t)	{	color=t;}
	
	public void set_positionID(int t) throws Exception{
		switch(t) {
			case 0:	pos = Position.DOWN;	break;
			case 1:	pos = Position.RIGHT;	break;
			case 2:	pos = Position.UP;		break;
			case 3:	pos = Position.LEFT;	break;
			default:throw new Exception("Invalid Position for the paddle!");
		}		
	}
	
	public void moveLeft(){
		ax=-1000;
		isKeyPressed=true;
	}
	public void moveRight(){
		ax=1000;
		isKeyPressed=true;
	}
	public void stop(){
		if(isKeyPressed)
			ax=ax*-2;
		isKeyPressed=false;
	}
	
}

class Player{
	
	/**
	 * 	name 		: name of Player
	 *  IPAddress 	: IP Address of player ( "-1" for bots)
	 *  serverPort	: port for connecting to player as server
	 *  clientPort	: port for connecting to player as client
	 *  isAlive 	: whether the player is alive or dead
	 *  isHuman 	: whether the player is a human player
	 *  isBot   	: whether the player is a bot
	 *  botLevel	: difficulty level of the bot (if isBot is True)
	 *  health		: number of lives remaining for the player
	 *  paddle		: Paddle assigned to the player
	 */
	
	private String name,IPAddress;
	private int serverPort,clientPort;
	private boolean isAlive,isHuman,isBot;
	private int botLevel,health;
	private Paddle paddle;
	private AI ai;
	private GameData parent;
	public enum State{DEAD,HUMAN,BOT;}
	
	private State state;

	public Player(GameData data){
		name=IPAddress="";
		isAlive=isBot=isHuman=false;
		health=botLevel=0;
		parent = data;
		paddle = new Paddle();
		ai = new AI(parent, this);
	}
	
	public String _name()		{	return name;		}
	public String _IPAddress()	{	return IPAddress;	}
	public int _clientPort() 	{	return clientPort;	}
	public int _serverPort() 	{	return serverPort;	}	
	public boolean _isAlive()	{	return isAlive;		}
	public boolean _isHuman()	{	return isHuman;		}
	public boolean _isBot()		{	return isBot;		}
	public int _botLevel()		{	return botLevel;	}
	public int _health()		{	return health;		}
	public Paddle _paddle()		{	return paddle;		}
	public State _state() 		{	return state;		}
	public AI _AI() 			{	return ai;			}
	
	public void set_name(String t)		{	name=t;			}
	public void set_IPAddress(String t)	{	IPAddress=t;	}
	public void set_isAlive(boolean t)	{	isAlive=t;		}
	public void set_isHuman(boolean t)	{	isHuman=t;		}
	public void set_isBot(boolean t)	{	isBot=t;		}
	public void set_botLevel(int t)		{	botLevel=t;		}
	public void set_health(int t)		{	health=t;		}
	public void set_paddle(Paddle t)	{	paddle=t;		}
	public void set_clientPort(int t) 	{	clientPort=t;	}
	public void set_serverPort(int t) 	{	serverPort=t;	}
	public void set_state(State t)	 	{	state=t;		}

}