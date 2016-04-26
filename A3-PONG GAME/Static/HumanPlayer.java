import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class HumanPlayer extends KeyAdapter {
	Paddle paddle;
	public HumanPlayer(Paddle _paddle) {
		paddle = _paddle;
		System.out.println("Adding KeyAdapter to Paddle "+paddle._pos().toString());
	}
	
	public void keyPressed(KeyEvent e) {
		switch(e.getKeyCode()){
			case KeyEvent.VK_LEFT :
				paddle.moveLeft();
				break;
			case KeyEvent.VK_RIGHT : 
				paddle.moveRight();
				break;
		}
	}
	
	public void keyReleased(KeyEvent e) {
		switch(e.getKeyCode()){
			case KeyEvent.VK_LEFT : 
			case KeyEvent.VK_RIGHT : 
				paddle.stop();
				break;
		}
	}
}
