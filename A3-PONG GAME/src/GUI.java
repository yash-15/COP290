import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

@SuppressWarnings("serial")
public class GUI extends JPanel{

	boolean debug=true;
	GameData data;
	JLabel statusBar;
	int size;
	int localUser,localPos;
	int offSet=30;
    public GUI(Game game){

		System.out.println("Constructing GUI using Data!");
    	data=game._data();
    	statusBar = game._statusBar();
    	setFocusable(true);
    	localUser = -1;
		System.out.println("GUI Constructed!");
	}
	
    private int X(double t){
    	return  offSet+(int)(t+ size*0.5);
    }
    private int Y(double t){
    	return  offSet+(int)(-t+ size*0.5);
    }
	
	public void paintComponent(Graphics graphics){
		super.paintComponent(graphics);
		render_boundary(graphics);
	//	render_ball(ball, graphics);
	//	ball.set_x(1+ball._x());
		for(Ball ball  : data._balls())
			render_ball(ball, graphics);
		Player[] players = data._players();
		int numberOfPlayers = data._numberOfPlayers(), localPos=-1;
		for(int i=0;i<numberOfPlayers;i++)
			if(players[i].isAlive) render_paddle(localPos, players[i]._paddle(), graphics);

		render_playerInfo(graphics);
	}
	
	double[] pair={0,0};
	
	public void render_ball(Ball b, Graphics graphics){
	//	System.out.println(System.currentTimeMillis()+ " Render: "+X(b._x())+" , "+Y(b._y()));
	//	statusBar.setText((int)b._x()+" "+(int)b._y()+" :: "+X(b._x()-b._rad())+" "+Y(b._y()-b._rad()));
		graphics.setColor(b._color());
		pair = Physics.Rotate((4-localPos)%4,b._x(),b._y());
		graphics.fillOval(X(pair[0]-b._rad()),Y(pair[1]+b._rad()),(int)(2*b._rad()),(int)(2*b._rad()));
	}
	
	private void render_paddle(int pos, Paddle p, Graphics graphics){
		
		//message("Adding Paddle at "+System.currentTimeMillis());
		int Cos[] = {1,0,-1,0}, Sin[] = {0,-1,0,1},count;
		int x0,y0,x,y,len,wdt,rad;
		count = (p._positionID());//-pos+4)%4;
		
		x0=(int) p._x();
		y0=(int) p._y();
		x=(int)(x0*Cos[count]+y0*Sin[count]);//-p._len()/2);
		y=(int)(-x0*Sin[count]+y0*Cos[count]);//+p._wdt()/2);
		len = (int)(p._len()*Math.abs(Cos[count])+p._wdt()*Math.abs(Sin[count]));
		wdt = (int)(p._len()*Math.abs(Sin[count])+p._wdt()*Math.abs(Cos[count]));
		x-=len/2;
		y+=wdt/2;
		rad = (int)(p._wdt());
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
		graphics.drawRect(offSet,offSet,size,size);
	}
	
	
	//TODO account for position in random orders
	public void render_playerInfo(Graphics graphics) {
		Graphics2D graphics2d = (Graphics2D) graphics;
		
		AffineTransform at = new AffineTransform();
		int dist=(int) (size+offSet)/2;
		//statusBar.setText(String.valueOf(dist));
		double rel_co_or[]={0,dist};
		for(int i=0;i<4;i++){
			rel_co_or=Physics.Rotate((4-i)%4,0.0, -(double)dist);
			graphics2d.setTransform(at);
			rel_co_or=Physics.Rotate((i%2==0)?0:1,X(rel_co_or[0]), Y(rel_co_or[1]) );
			
			//TODO: Replace info with the appropriate string
			String sub_info;
			if(data._player(i)._isAlive()){
				if(data._player(i)._isHuman())
					sub_info="HUMAN";
				else
					sub_info="BOT";
			}
			
			else	sub_info="DEAD";
				
			String info=sub_info+" "+i+" "+data._player(i)._name()+" LIVES: "+data._player(i)._health();
			graphics2d.drawString(info, (int)rel_co_or[0],(int)rel_co_or[1]);
			at.rotate(((i%2==0)?1:-1)*Math.PI/2);
		}
	}
	
	public void set_windowSize(int t)	{	size=t;			}
	public void set_localUser(int t) {
		localUser=t;
		localPos=0;
		System.out.println("UI local user set to : "+localPos);
	}
	
}
