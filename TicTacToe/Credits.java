package TicTacToe;

import java.awt.Color;
import java.awt.Graphics;

public class Credits extends GameState
{
	private static final long serialVersionUID = 1L;
	
	private String creditsText = "Game by Mateusz Król";
	private String goBackText = "Press ESC to go back";
	
	//Number of frames before text will become visible
	private final int flashingTextTimerMax = 30;
	private int flashingTextTimer = flashingTextTimerMax;
	
	private boolean showingText = false;

	public Credits(Parameters p, App myApp)
	{
		super(p, myApp);
	}
	
	protected void update()
	{
		if (flashingTextTimer == 0)
		{
			flashingTextTimer = flashingTextTimerMax;
			showingText = !showingText;
		}
		else
		{
			flashingTextTimer--;
		}
		
		if (Key.esc.isDown)
		{
			playSe("cancel.wav");
			myApp.showMenu();
			enabled = false;
			setVisible(false);
		}
	}
	
	protected void drawFrame(Graphics g)
	{
		//Set background color to black
		setBackground(Color.black);
		
		//Set text color to white
		g.setColor(Color.white);
		
		//Draw credits
		g.setFont(smallFont);
		g.drawString(creditsText, (p.getResolutionX() - smallFontMetrics.stringWidth(creditsText)) / 2, p.getResolutionY() / 4);
		
		//Draw title "go back" text
		if(showingText)
		{
			g.setFont(smallFont);
			g.drawString(goBackText, (p.getResolutionX() - smallFontMetrics.stringWidth(goBackText)) / 2, p.getResolutionY() / 4 * 3);
		}
	}
}
