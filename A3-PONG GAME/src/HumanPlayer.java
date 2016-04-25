import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;


public class HumanPlayer extends KeyAdapter {
	GameData data;
	boolean debug;
	public HumanPlayer(Game game) {
		data = game._data();
	}
	
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		Main.message(debug,"KEY : "+key+" at "+System.currentTimeMillis());
		switch(key){
			case KeyEvent.VK_LEFT :
				data._player(data._localUser())._paddle().moveLeft();
				break;
			case KeyEvent.VK_RIGHT : 
				data._player(data._localUser())._paddle().moveRight();
				break;
		}
	}
	
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		Main.message(debug,"REL : "+key+" at "+System.currentTimeMillis());
		switch(key){
			case KeyEvent.VK_LEFT : 
			case KeyEvent.VK_RIGHT : 
				data._player(data._localUser())._paddle().stop();
				break;
		}
	}
	
	
	
}
