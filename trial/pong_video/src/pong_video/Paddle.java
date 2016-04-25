package pong_video;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class Paddle {
	
	public int paddleNumber;
	
	public int x, y, width=25, height=150,width_h=150, height_h=25;
	public int score;
	public Paddle(Pong pong,int paddleNumber){
		this.paddleNumber=paddleNumber;
		if(paddleNumber==1)
		{
			this.x=0;
			this.y=pong.height/2-this.height/2;
		}
		if(paddleNumber==2)
		{
			this.x=pong.width-width;
			this.y=pong.height/2-this.height/2;
		}
		
		if(paddleNumber==3)
		{
			this.x=pong.width/2-this.width_h/2;
			this.y=height_h;
		}
		if(paddleNumber==4)
		{
			this.x=pong.width/2-this.width_h/2;
			this.y=pong.height-height_h;
		}
	
	
	}
	public void render(Graphics2D g) {
		g.setColor(Color.WHITE);
		g.fillRect(x, y, width, height);
		
	}
	public void render_h(Graphics2D g) {
		g.setColor(Color.WHITE);
		g.fillRect(x, y, width_h, height_h);
		
	}
	public void moveup() {
		int speed=15;
		if((y-speed)>0){
			y-=speed;
		}
		else
		{
		 y=0;	
		}	
	}
	public void movedown() {
		int speed=15;
		if((y+height-speed)<Pong.pong.height){
			y+=speed;
		}
		else{
			y=Pong.pong.height-height-speed;
		}
		
	}
	
	public void moveleft() {
		int speed=15;
		if((x-speed)>0){
			x-=speed;
		}
		else
		{
		 x=0;	
		}	
	}
	public void moveright() {
		int speed=15;
		if((x+width_h-speed)<Pong.pong.width){
			x+=speed;
		}
		else{
			x=Pong.pong.width-width_h-speed;
		}
		
	}
}
