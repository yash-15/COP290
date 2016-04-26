import java.awt.Color;

import org.json.JSONException;
import org.json.JSONObject;

class Paddle{
	
	private Position pos;
	private double len,wdt,x,vx,ax; 
	private Color color=Color.BLUE;
	static double def_len, def_wdt, max_speed, max_x;
	boolean isKeyPressed;
	private Player parent;
	public Paddle(Player player) {
		pos = Position.DOWN;
		parent=player;
		x=vx=ax=0;
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
	public boolean  _isKeyPressed()	{	return isKeyPressed;	}
	
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
		sendMessage();
		
	}
	public void moveRight(){
		ax=1000;
		isKeyPressed=true;
		sendMessage();
	}
	public void stop(){
		if(isKeyPressed)
			ax=ax*-2;
		isKeyPressed=false;
		sendMessage();
	}
	
	public void sendMessage()
	{
		JSONObject msgJsonObject=new JSONObject();
		try {
			msgJsonObject.put("PROTOCOL","PADDLE_UPDATE");
			int t_id=parent.id+network.me.id;
			msgJsonObject.put("USER_ID", t_id>4?t_id-4:t_id);
			msgJsonObject.put("X", _x());
			msgJsonObject.put("VX", _vx());
			msgJsonObject.put("AX", _ax());
			msgJsonObject.put("IS_KEY_PRESSED", isKeyPressed);
			for(int i=0;i<4;i++)
			{
				if(network.users[i].id!=network.me.id && network.users[i].priority!=-1)
				{
					network.users[i].conn.ptWriter.println(msgJsonObject.toString());
					//System.out.println("SEnd paddle dat");
				}
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
}

enum Position{
	DOWN,RIGHT,UP,LEFT;
}
