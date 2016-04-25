package ping_pong;
import java.awt.*;
import java.awt.event.KeyEvent;
import javax.swing.*;


public class PanelPelota extends JPanel implements Runnable {

private static final long serialVersionUID = 1L;
// Positions on X and Y for the ball, player 1 and player 2
private int ballX = 10, ballY = 100, Player1X=10, Player1Y=100, Player2X=230, Player2Y=100;
Thread thread;
int right=5; // to the right
int left= -5; //to the left
int up=5; // upward
int down= -5; // down
int width, height; // Width and height of the ball
// Scores
int contPlay1=0, contPlay2=0;
boolean player1FlagUp,player1FlagDown, player2FlagUp, player2FlagDown;
boolean game, gameOver;

public PanelPelota(){
game=true;
thread=new Thread(this);
thread.start();
}

// Draw ball and ships
public void paintComponent(Graphics gc){
setOpaque(false);
super.paintComponent(gc);

// Draw ball
gc.setColor(Color.black);
gc.fillOval(ballX, ballY, 8,8);

// Draw ships
gc.fillRect(Player1X, Player1Y, 10, 25);
gc.fillRect(Player2X, Player2Y, 10, 25);

//Draw scores
gc.drawString("Player1: "+contPlay1, 25, 10);
gc.drawString("Player2: "+contPlay2, 150, 10);

if(gameOver)
gc.drawString("Game Over", 100, 125);
}

// Positions on X and Y for the ball
public void repositionball (int nx, int ny)
{
ballX= nx;
ballY= ny;
this.width=this.getWidth();
this.height=this.getHeight();
repaint();
}

// Here we receive from the game container class the key pressed
public void keyPressed(KeyEvent evt)
{
switch(evt.getKeyCode())
{
// Move ship 1
case KeyEvent.VK_W :
player1FlagUp = true;
break;
case KeyEvent.VK_S :
player1FlagDown = true;
break;

// Move ship 2
case KeyEvent.VK_UP:
player2FlagUp=true;
break;
case KeyEvent.VK_DOWN:
player2FlagDown=true;
break;
}
}

// Here we receive from the game container class the key released
public void keyReleased(KeyEvent evt)
{
switch(evt.getKeyCode())
{
// Mover Nave1
case KeyEvent.VK_W :
player1FlagUp = false;
break;
case KeyEvent.VK_S :
player1FlagDown = false;
break;

// Mover nave 2
case KeyEvent.VK_UP:
player2FlagUp=false;
break;
case KeyEvent.VK_DOWN:
player2FlagDown=false;
break;
}
}

// Move player 1
public void moverPlayer1()
{
if (player1FlagUp == true && Player1Y >= 0)
Player1Y += down;
if (player1FlagDown == true && Player1Y <= (this.getHeight()-25))
Player1Y += up;
repositionPlayer1(Player1X, Player1Y);
}

// Move player 2
public void moverPlayer2()
{
if (player2FlagUp == true && Player2Y >= 0)
Player2Y += down;
if (player2FlagDown == true && Player2Y <= (this.getHeight()-25))
Player2Y += up;
repositionPlayer2(Player2X, Player2Y);
}

// Position on Y for the player 1
public void repositionPlayer1(int x, int y){
this.Player1X=x;
this.Player1Y=y;
repaint();
}
// Position on Y for the player 2
public void repositionPlayer2(int x, int y){
this.Player2X=x;
this.Player2Y=y;
repaint();
}

public void run() {
// TODO Auto-generated method stub
boolean LeftRight=false;
boolean UpDown=false;

while(true){

if(game){

// The ball move from left to right
if (LeftRight)
{
// to the right
ballX += right;
if (ballX >= (width - 8))
LeftRight= false;
}
else
{
// to the left
ballX += left;
if ( ballX <= 0)
LeftRight = true;
}


// The ball moves from up to down
if (UpDown)
{
// towards up
ballY += up;
if (ballY >= (height - 8))
UpDown= false;

}
else
{
// towards down
ballY += down;
if ( ballY <= 0)
UpDown = true;
}
repositionball(ballX, ballY);

// Delay
try
{
Thread.sleep(50);
}
catch(InterruptedException ex)
{

}

// Move player 1
moverPlayer1();

// Move player 2
moverPlayer2();

// The score of the player 1 increase
if (ballX >= (width - 8))
contPlay1++;

// The score of the player 2 increase
if ( ballX == 0)
contPlay2++;

// Game over. Here you can change 6 to any value
// When the score reach to the value, the game will end
if(contPlay1==6 || contPlay2==6){
game=false;
gameOver=true;
}

// The ball stroke with the player 1
if(ballX==Player1X+10 && ballY>=Player1Y && ballY<=(Player1Y+25))
LeftRight=true;

// The ball stroke with the player 2
if(ballX==(Player2X-5) && ballY>=Player2Y && ballY<=(Player2Y+25))
LeftRight=false;
}
}
}

}