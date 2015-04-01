package Map;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener{			// this class takes in and interprets input from the user keyboard

	private Screen screen;
	private Screen.KeyTyped keyTyped;
	
	public KeyHandler(Screen screen)
	{
		this.screen = screen;
		
		this.keyTyped = this.screen.new KeyTyped();
		
		//this.keyTyped.keyESC();			// used to call keyESC method
		
		//Screen.KeyTyped.key				//Static call to key method
	}
	
	public void keyPressed(KeyEvent e) {			// pressed every time a key is pressed it produces the event e
		//System.out.println(e.getKeyCode());		// key in ASCII is printed to the console
		int keyCode = e.getKeyCode();
		
		if(keyCode == 27)				// 27 is for the escape key
		{
			this.keyTyped.keyESC();
		}
		/*if(keyCode == 32)				// 27 is for the space key
		{
			this.keyTyped.keySPACE();
		}*/
		if(keyCode == 77)				// 27 is for the space bar
		{
			this.keyTyped.keyM();
		}
		if (keyCode == 70)				// 70 is for the f key
		{
			this.keyTyped.keyF();
		}
		if (keyCode == 76)				// 76 is for the L key
		{
			this.keyTyped.keyL();
		}
		if (keyCode == 49)				// 49 is 1
		{
			this.keyTyped.key1();
		}
		if (keyCode == 50)				// 50 is 2
		{
			this.keyTyped.key2();
		}
		if (keyCode == 51)				// 51 is 3
		{
			this.keyTyped.key3();
		}
		if (keyCode == 52)				// 52 is 4
		{
			this.keyTyped.key4();
		}
		if (keyCode == 53)				// 53 is 5
		{
			this.keyTyped.key5();
		}
		if (keyCode == 54)				// 54 is 6
		{
			this.keyTyped.key6();
		}
		if (keyCode == 55)				// 55 is 7
		{
			this.keyTyped.key7();
		}
		if (keyCode == 56)				// 56 is 8
		{
			this.keyTyped.key8();
		}
		if (keyCode == 57)				// 57 is 9
		{
			this.keyTyped.key9();
		}

	}

	public void keyReleased(KeyEvent e) {
		
	}

	public void keyTyped(KeyEvent e) {
		
	}

}
