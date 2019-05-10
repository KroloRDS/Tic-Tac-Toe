package TicTacToe;

import java.awt.Color;
import java.awt.Graphics;

public class Title extends GameState
{
	
	private static final long serialVersionUID = 1L;
	
	private final String titleText = "TIC TAC TOE";
	private final String pressStartText = "Press Start";
	
	//Number of frames before text becomes visible
	private final int flashingTextTimerMax = 30;
	private int flashingTextTimer = flashingTextTimerMax;
	
	//Number of frames before input becomes unlocked
	private int introTimer = 150;
	
	private double textPos = 100 + 20 * p.getScale();
	
	private boolean showingText = false;
	private boolean blockInput = true;
			
	public Title(Parameters p, App myApp)
	{
		super(p, myApp);
	}
	
	protected void update()
	{
		if (introTimer != 0)
		{
			introTimer--;
			
			if (textPos > 0)
			{
				textPos -= 1 + 0.2 * p.getScale();
			}
		}
		else
		{
			blockInput = false;
			
			if (flashingTextTimer == 0)
			{
				flashingTextTimer = flashingTextTimerMax;
				showingText = !showingText;
			}
			else
			{
				flashingTextTimer--;
			}
		}
		
		if (Key.ok.isDown && !blockInput)
		{
			playSe("ok.wav");
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
		
		//Draw title text
		g.setFont(bigFont);
		g.drawString(titleText, (p.getResolutionX() - bigFontMetrics.stringWidth(titleText)) / 2, p.getResolutionY() / 4 + (int)textPos);
		
		//Draw title "press start" text
		if(showingText)
		{
			g.setFont(smallFont);
			g.drawString(pressStartText, (p.getResolutionX() - smallFontMetrics.stringWidth(pressStartText)) / 2, p.getResolutionY() / 4 * 3);
		}
	}
}
