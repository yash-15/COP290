package pong_video;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.Timer;
public class Pong implements ActionListener,KeyListener {
	
	public static Pong pong;
	public int width=700, height=700;
	public Renderer  renderer;
	public Paddle player1;
	public Paddle player2;
	public Paddle player3;
	public Paddle player4;
	
	public boolean but=false;
	public boolean w,s,a,d,up,down,right,left;
	public Pong(){
		Timer timer=new Timer(20, this);
		JFrame jframe=new JFrame("Pong");
		renderer = new Renderer();
		jframe.setSize(width, height);
		jframe.setVisible(true);
		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jframe.getContentPane().add(renderer);
		
		jframe.addKeyListener(this);
		start();
		timer.start();
	}
	
	public void start()
	{
		player1= new Paddle(this,1);
		player2= new Paddle(this,2);
		player3= new Paddle(this,3);
		player4= new Paddle(this,4);
		
	}
	public void update(){
	if(w)
	{
		player1.moveup();
	}
	if(s)
	{
		player1.movedown();
	}
	if(up)
	{
		player2.moveup();
	}
	if(down)
	{
		player2.movedown();
	}
	if(a)
	{
		player2.moveleft();
	}
	if(d)
	{
		player2.moveright();
	}
	if(right)
	{
		player2.moveright();
	}
	if(left)
	{
		player2.moveleft();
	}

	}
	public void render(Graphics2D g) {
		g.setColor(Color.black);
		g.fillRect(0, 0, width, height);
		
		g.setColor(Color.WHITE);
		g.setStroke(new BasicStroke(10));
		g.drawLine(width/2, 0, width/2, height);
		g.drawLine(0, height/2, width, height/2);
		g.drawRoundRect(height/2-40,width/2-40	, 80, 80, 10, 10);
	player1.render(g);
	player2.render(g);
	player3.render_h(g);
	player4.render_h(g);
		
	}
@Override
public void actionPerformed(ActionEvent e) {
	// TODO Auto-generated method stub
	update();
	renderer.repaint();
	
}
	public static void main(String[] args)
	{
		pong = new Pong();
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int id=e.getKeyCode();
		if(id==KeyEvent.VK_W)
		{
			w=true;
		}
		if(id==KeyEvent.VK_S)
		{
			s=true;
		}
		if(id==KeyEvent.VK_A)
		{
			a=true;
		}
		if(id==KeyEvent.VK_D)
		{
			d=true;
		}
		if(id==KeyEvent.VK_UP)
		{
			up=true;
		}
		if(id==KeyEvent.VK_DOWN)
		{
			down=true;
		}
		if(id==KeyEvent.VK_RIGHT)
		{
			right=true;
		}
		if(id==KeyEvent.VK_LEFT)
		{
			left=true;
		}
		
		
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		int id=e.getKeyCode();
		if(id==KeyEvent.VK_W)
	{
		w=false;
	}
	if(id==KeyEvent.VK_S)
	{
		s=false;
	}
	if(id==KeyEvent.VK_A)
	{
		a=false;
	}
	if(id==KeyEvent.VK_D)
	{
		d=false;
	}
	if(id==KeyEvent.VK_UP)
	{
		up=false;
	}
	if(id==KeyEvent.VK_DOWN)
	{
		down=false;
	}
	if(id==KeyEvent.VK_RIGHT)
	{
		right=false;
	}
	if(id==KeyEvent.VK_LEFT)
	{
		left=false;
	}
	
	}
	
}	
