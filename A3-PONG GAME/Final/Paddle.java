import java.awt.Color;

class Paddle{
	
	private Position pos;
	private double len,wdt,x,vx,ax; 
	private Color color=Color.BLUE;
	static double def_len, def_wdt, max_speed, max_x;
	boolean isKeyPressed;
	
	public Paddle() {
		pos = Position.DOWN;
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

enum Position{
	DOWN,RIGHT,UP,LEFT;
}
