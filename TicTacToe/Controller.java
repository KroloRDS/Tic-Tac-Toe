package TicTacToe;

import java.awt.event.KeyEvent;
import java.util.HashMap;

public class Controller
{
	public HashMap<Integer, Key> keyBindings = new HashMap<Integer, Key>();
	
	public Controller()
	{
		bind(KeyEvent.VK_UP, Key.up);
		bind(KeyEvent.VK_LEFT, Key.left);
		bind(KeyEvent.VK_DOWN, Key.down);
		bind(KeyEvent.VK_RIGHT, Key.right);
		
		bind(KeyEvent.VK_ESCAPE, Key.esc);
		bind(KeyEvent.VK_BACK_SPACE, Key.esc);
		bind(KeyEvent.VK_X, Key.esc);
		
		bind(KeyEvent.VK_ENTER, Key.ok);
		bind(KeyEvent.VK_SPACE, Key.ok);
		bind(KeyEvent.VK_Z, Key.ok);
	}
	
	public void bind(Integer keyCode, Key key)
	{
		keyBindings.put(keyCode, key);
	}

	public void keyPressed(KeyEvent e)
	{
		if (keyBindings.get(e.getKeyCode()) != null)
		{
			keyBindings.get(e.getKeyCode()).isDown = true;
		}
	}

	public void keyReleased(KeyEvent e)
	{
		if (keyBindings.get(e.getKeyCode()) != null)
		{
			keyBindings.get(e.getKeyCode()).isDown = false;
		}
	}
	
	public void keyTyped(KeyEvent e)
	{
		//not used
	}
}