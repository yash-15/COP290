import org.json.JSONException;
import org.json.JSONObject;

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
	
	private String name="",IPAddress="";
	private int serverPort=0,clientPort=0;
	public boolean isAlive=true,isHuman=false,isBot=false;
	private int botLevel=0,health=3;
	private Paddle paddle;
	private AI ai;
	public int id;
	
	public Player(){
		paddle = new Paddle(this);
		health = Main.lifeCount;
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
	public void set_AI(AI t) 			{	ai=t;			}

}