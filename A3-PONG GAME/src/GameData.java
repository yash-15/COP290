import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

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
 * 		0 : DOWN  ;	 1 : RIGHT  ;  2 : UP  ;  3 : LEFT
 */


public class GameData {
	
	/** 
	 * MAXPLAYERS		: maximum number of players in the game
	 * numberOfPlayers 	: number of players in the game : 2,3,4 are acceptable
	 * centralPlayer	: player performing the computations : should be a Human Player
	 * players			: array of Player objects
	 * balls			: Set of all active balls in the game
	 */
	
	public final int MAXPLAYERS = 4;
	private int numberOfPlayers;
	private int centralPlayer;
	private int num_alive=MAXPLAYERS;
	public static Player[] players;
	public static ArrayList<Ball> balls;
	private int windowSize;
	private double restitution;

	//public boolean GameOn=false;
	
	public GameData() {
		System.out.println("Constructing GameData...");
		numberOfPlayers = centralPlayer = 0;
		players = new Player[MAXPLAYERS];
		balls = new ArrayList<Ball>();
		num_alive=MAXPLAYERS;
		restitution=0.3;// for paddle only not to be confused with ball collisons
		System.out.println("GameData Constructed!");
		//GameOn=true;//Assuming the new GameData is made only when a new game is started
	}
	
	public void set_windowSize(int t){	
		windowSize=t;
		Paddle.def_len = windowSize/5.0;
		Paddle.def_wdt = windowSize/20.0;
		Paddle.max_speed = windowSize;
		Paddle.max_x = 0.35*windowSize;
	}

	public int _numberOfPlayers()	{	return numberOfPlayers;	}
	public int _centralPlayer()		{	return centralPlayer;	}
	public Player[] _players()		{	return players;			}
	public Player _player(int i)	{	return players[i];		}
	public ArrayList<Ball> _balls()	{	return balls;			}
	public double _restitution()    {	return restitution;		}
	public int _windowSize() 		{	return windowSize;		}
	public int _num_alive()			{	return num_alive;		}

	public void set_numberOfPlayers(int t)	{	numberOfPlayers=t;	}
	public void set_num_alive(int i)		{	num_alive=i;		}
	public void set_centralPlayer(int t)	{
		if(t>=0 && t<numberOfPlayers && players[t]._isHuman())
			centralPlayer=t;
		else
			throw new IllegalArgumentException();
	}
	
	public void set_player(int id, Player t) {
		players[id]=t;
		System.out.println("Added player "+t._name()+"to players");
	}
	
	public void addBall(Ball ball) {
		balls.add(ball);
		System.out.println("Number of balls:"+balls.size());
	}
	
	public void  plString()	{ 
		try {
			for (int i=0;i<4;i++)
			{
				System.out.println("Printing player "+i);
				System.out.println("PLAYER 1: "+players[i]._name());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	}