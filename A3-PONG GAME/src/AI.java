


public class AI {
	Player player;
	Paddle paddle;
	GameData data;
	int Cos[] = {1,0,-1,0}, Sin[] = {0,1,0,-1},count;
	int x0,y0,x,bx,len,wdt,rad;
	
	
	public AI(GameData _data,Player _player) {
		player = _player;
		data = _data;
		paddle = _player._paddle();
	}
	public void playMove(){
		
		x=(int)paddle._x();
		count = paddle._positionID();
		
		for(Ball ball : data._balls()){
			
			x0=(int) ball._x();
			y0=(int) ball._y();

			bx=(int)(x0*Cos[count]+y0*Sin[count]);//-p._len()/2)
			
	//		if(paddle._pos()==Position.RIGHT)
		//		Main.statusbar.setText(count+" "+(int)ball._x()+" "+(int)ball._y()+" :: "+bx+" Yo");
				
			if(bx>x)
				player._paddle().moveRight();
			else
				player._paddle().moveLeft();
			return;
		}
			
		Main.message(false,"AI! at "+System.currentTimeMillis());
	}
}
