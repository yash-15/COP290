import javax.xml.xpath.XPath;




public class AI {
	Player player;
	Paddle paddle;
	GameData data;
	int Cos[] = {1,0,-1,0}, Sin[] = {0,1,0,-1},count;
	double x0,y0,x,bvx,bx,bvy,by,len,wdt,rad,sz;
	
	
	public AI(GameData _data,Player _player) {
		player = _player;
		data = _data;
		paddle = _player._paddle();
	}
	public void playMove(){
		
		x=(int)paddle._x();
		count = paddle._positionID();
		len=(int) paddle._len();
		
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
			sz=Game.size;
			double reachTime=((sz/20.0-sz/2.0)-by)/bvy;
			
			//Tentative position of ball at time of landing
			bx+=(bvx* reachTime);
			
	//		if(paddle._pos()==Position.RIGHT)
		//		Main.statusbar.setText(count+" "+(int)ball._x()+" "+(int)ball._y()+" :: "+bx+" Yo");
			if(player._paddle()._pos()==Position.UP) 
				{//Main.statusbar.setText("Tentative : "+(int)bx+"\nPaddle at :"+(int)x+"\nReachTime: "+reachTime);
				Main.statusbar.setText("Paddle Velocity: "+paddle._vx()+" Accn: "+paddle._ax());}
				
			if(reachTime>=0 && Math.abs(bx-x)>len/2){
				if(bx>x) player._paddle().moveRight();
				else player._paddle().moveLeft();
			}
			else {
				player._paddle().stop();
			}
			return;
		}
			
		Main.message(false,"AI! at "+System.currentTimeMillis());
	}
}
