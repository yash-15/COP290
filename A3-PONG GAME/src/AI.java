import java.awt.Color;


public class AI {
	Paddle paddle;
	GameData data;
	int size;
	int Cos[] = {1,0,-1,0}, Sin[] = {0,1,0,-1},count;
	double x0,y0,x,bvx,bx,bvy,by,len,wdt,rad,sz;
	
	
	public AI(GameData _data,Paddle _paddle) {
		data = _data;
		paddle = _paddle;
		size=_data._windowSize();
	}
	public void playMove(){
		
		x = paddle._x();
		count = paddle._positionID();
		len = paddle._len();
		
		double minReachTime=-1,temp_bx=0,temp_x=0;boolean is_hitting=false;
		Color color=null;
		// for storing the data of the ball scheduled to be arriving first
		
		for(Ball ball : data._balls()){
			
			//Ball ball=ball;
			x0= ball._x();
			y0= ball._y();

			bx=(x0*Cos[count]+y0*Sin[count]);//-p._len()/2)
			by=(y0*Cos[count]-x0*Sin[count]);
			
			x0=ball._vx();
			y0=ball._vy();
			
			bvx=(x0*Cos[count]+y0*Sin[count]);
			bvy=(y0*Cos[count]-x0*Sin[count]);
			
			
			//Tentative time for ball to reach the paddle
			sz=size;
			double reachTime=((sz/20.0-sz/2.0)-by)/bvy;
			
			//Tentative position of ball at time of landing
			bx+=(bvx* reachTime);
			boolean yes=false;
			//Check if the ball will hit your wall
			if(reachTime>0 && Math.abs(bx)<=(sz/2.0- sz/20))
			{
				if(!is_hitting || reachTime<minReachTime) {is_hitting=true;yes=true;}
			}
			//Check if it approaches in the vicinity of your wall
			else if (reachTime>0)
			{
				if(!is_hitting && Math.abs(bx)<Math.abs(temp_bx)) {yes=true;}
			}
			if(yes) {minReachTime=reachTime;temp_bx=bx;temp_x=x;
			}
		}
			
		
		if(minReachTime>0 && Math.abs(temp_bx-temp_x)>0.3*paddle._len()){
			if(temp_bx>temp_x)
				paddle.moveRight();
			else
				paddle.moveLeft();
		}
		else
			paddle.stop();
		
		return;
		
	}
}
