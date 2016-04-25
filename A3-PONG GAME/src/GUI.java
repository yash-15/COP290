import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;

/**
 * @author YKB
 *
 */
public class GUI extends JPanel{

	boolean debug=true;
	GameData data;
	JLabel statusBar;
	int size;
    public GUI(){}
    public GUI(Game parent, GameData _data){

		System.out.println("Constructing GUI using Data!");
    	data=_data;
    	statusBar = parent._statusBar();

		System.out.println("GUI Constructed!");
	}
	
    private int X(double t){
    	return  1+(int)(t+ size*0.5);
    }
    private int Y(double t){
    	return  1+(int)(-t+ size*0.5);
    }
    
	public void set_gameData( GameData t){	data=t;	}
	
	public void paint(Graphics graphics){
		super.paint(graphics);
		render_boundary(graphics);
	//	render_ball(ball, graphics);
	//	ball.set_x(1+ball._x());
		for(Ball ball  : data._balls())
			render_ball(ball, graphics);
		Player[] players = data._players();
		int numberOfPlayers = data._numberOfPlayers(), localPos=-1;
		if(numberOfPlayers>0)
			localPos = players[data._localUser()]._paddle()._positionID();
		for(int i=0;i<numberOfPlayers;i++)
			render_paddle(localPos, players[i]._paddle(), graphics);
	}
	
	public void render_ball(Ball b, Graphics graphics){
	//	message(System.currentTimeMillis()+ " Render: "+X(b._x())+" , "+Y(b._y()));
	//	statusBar.setText((int)b._x()+" "+(int)b._y()+" :: "+X(b._x()-b._rad())+" "+Y(b._y()-b._rad()));
		graphics.setColor(b._color());
		graphics.fillOval(X(b._x()-b._rad()),Y(b._y()+b._rad()),(int)(2*b._rad()),(int)(2*b._rad()));
//		graphics.fillOval(X(0-20),Y(0+20),40,40);
//		graphics.setColor(Color.green);
//		graphics.fillOval(X(0-10),Y(0+10),20,20);
	//	graphics.setColor(Color.BLACK);
	//	graphics.drawOval((int)b._x(),(int)b._y(),(int)(2*b._rad()),(int)(2*b._rad()));
	}
	
	private void render_paddle(int pos, Paddle p, Graphics graphics){
		
		//message("Adding Paddle at "+System.currentTimeMillis());
		int Cos[] = {1,0,-1,0}, Sin[] = {0,-1,0,1},count;
		int x0,y0,x,y,len,wdt,rad;
		count = (p._positionID()-pos+4)%4;
		
		x0=(int) p._x();
		y0=(int) p._y();
		x=(int)(x0*Cos[count]+y0*Sin[count]);//-p._len()/2);
		y=(int)(-x0*Sin[count]+y0*Cos[count]);//+p._wdt()/2);
		len = (int)(p._len()*Math.abs(Cos[count])+p._wdt()*Math.abs(Sin[count]));
		wdt = (int)(p._len()*Math.abs(Sin[count])+p._wdt()*Math.abs(Cos[count]));
		x-=len/2;
		y+=wdt/2;
		rad = (int)(p._wdt());
		//message(count+" "+x0+" "+y0+" :: "+x+" "+y+" :: "+len+" "+wdt+" :: "+rad);
		//if(count==0)
			//statusBar.setText(p._vx()+ " speed :: "+p._ax()+" accn :: at "+System.currentTimeMillis());
		graphics.setColor(p._color());
		graphics.fillRect(X(x),Y(y),len,wdt);//,rad,rad);
		graphics.setColor(Color.BLACK);
		graphics.drawRect(X(x),Y(y),len,wdt);//,rad,rad);
	}
	
	private void render_boundary(Graphics graphics){
		graphics.setColor(Color.RED);
		for(int dx=-1;dx<2;dx+=2)
			for(int dy=-1;dy<2;dy+=2)
			graphics.fillRect(X(dx*size*19.0/40.0-size/40.0),Y(dy*size*19.0/40.0+size/40.0),size/20,size/20);
		graphics.setColor(Color.BLACK);
		graphics.drawRect(1,1,size,size);
	}
	
	public void set_windowSize(int t){
		size=t;
	}
	
	private void message(String str){
		if(debug)
			System.out.println(str);
	}
}
