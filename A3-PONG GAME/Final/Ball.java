import java.awt.Color;

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