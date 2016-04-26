import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class HumanPlayer extends KeyAdapter {
	GameData data;
	int localUser;
	boolean debug;
	public HumanPlayer(Game game) {
		data = game._data();
		localUser = game._localUser();
	}
	
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		switch(key){
			case KeyEvent.VK_LEFT :
				data._player(localUser)._paddle().moveLeft();
				break;
			case KeyEvent.VK_RIGHT : 
				data._player(localUser)._paddle().moveRight();
				break;
		}
	}
	
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		switch(key){
			case KeyEvent.VK_LEFT : 
			case KeyEvent.VK_RIGHT : 
				data._player(localUser)._paddle().stop();
				break;
		}
	}
	
	public void set_localUser(int t) {
		localUser = t;
	}
	
}
