package TicTacToe;

import java.awt.Color;
import java.awt.Graphics;

public class GameModeSelect extends GameState
{
	
	private static final long serialVersionUID = 1L;
	
	private String menuText[] = {"1 Player", "2 Players"};
	
	private boolean twoPlayers = false;
	private boolean blockInput = true;
	
	public GameModeSelect(Parameters p, App myApp)
	{
		super(p, myApp);
	}
	
	protected void update()
	{
		if (!Key.left.isDown && !Key.right.isDown && !Key.ok.isDown)
		{
			blockInput = false;
		}
		
		if ((Key.left.isDown || Key.right.isDown) && !blockInput)
		{
			twoPlayers = !twoPlayers;
			blockInput = true;
			playSe("select.wav");
		}
		
		if (Key.ok.isDown && !blockInput)
		{
			p.setMode(twoPlayers);
			blockInput = true;
			playSe("select.wav");
			
			myApp.startGame();
			enabled = false;
			setVisible(false);
		}
	}

	protected void drawFrame(Graphics g)
	{
		//Set background color to black
		setBackground(Color.black);
		
		//Draw menu options
		g.setFont(smallFont);
		for (int i = 0; i < 2; i++)
		{
			if (twoPlayers == (i % 2 == 1))
			{
				g.setColor(Color.yellow);
			}
			else
			{
				g.setColor(Color.white);
			}
			g.drawString(menuText[i], (p.getResolutionX() - smallFontMetrics.stringWidth(menuText[i])) / 3 * (i + 1), p.getResolutionY() / 2);
		}
	}
}