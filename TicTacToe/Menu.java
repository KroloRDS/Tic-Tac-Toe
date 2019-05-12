package TicTacToe;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JOptionPane;

public class Menu extends GameState
{
	
	private static final long serialVersionUID = 1L;
	
	private String titleText = "TIC TAC TOE";
	private String[] menuText = {"START GAME", "OPTIONS", "CREDITS", "QUIT"};
	
	int option = 1;
	int optionsCount = menuText.length;
	
	private boolean blockInput = true;
	
	public Menu(Parameters p, App myApp)
	{
		super(p, myApp);
	}
	
	protected void update()
	{
		if (!Key.down.isDown && !Key.up.isDown && !Key.ok.isDown)
		{
			blockInput = false;
		}
		
		if (Key.down.isDown && !blockInput)
		{
			option++;
			blockInput = true;
			playSe("select.wav");
		}
		
		if (Key.up.isDown && !blockInput)
		{
			option--;
			blockInput = true;
			playSe("select.wav");
		}
		
		//Top-down wrap
		if (option > menuText.length)
		{
			option = 1;
		}
		
		if (option <= 0)
		{
			option = menuText.length;
		}
		
		if (Key.ok.isDown && !blockInput)
		{
			if (option != 4)
			{
				playSe("ok.wav");
			}
			else
			{
				playSe("cancel.wav");
			}
			
			choice();
		}
	}
	
	private void choice()
	{
		//START
		if (option == 1)
		{
			myApp.showGameModeSelect();
			enabled = false;
			setVisible(false);
		}
		
		//OPTIONS
		if (option == 2)
		{
			myApp.showOptions();
			enabled = false;
			setVisible(false);
		}
		
		//CREDITS
		if (option == 3)
		{
			myApp.showCredits();
			enabled = false;
			setVisible(false);
		}
		
		//QUIT
		if (option == 4)
		{
			try
			{
				Thread.sleep(400);
			}
			catch (InterruptedException e)
			{			   
				String msg = String.format("Thread interrupted: %s", e.getMessage());
				
				JOptionPane.showMessageDialog(this, msg, "Error", 
					JOptionPane.ERROR_MESSAGE);
			}
			
			System.exit(0);
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
		g.drawString(titleText, (p.getResolutionX() - bigFontMetrics.stringWidth(titleText)) / 2, p.getResolutionY() / 4);
		
		//Draw menu options
		g.setFont(smallFont);
		for (int i = 1; i <= 4; i++)
		{
			if (i == option)
			{
				g.setColor(Color.yellow);
			}
			else
			{
				g.setColor(Color.white);
			}
			
			g.drawString(menuText[i - 1], (p.getResolutionX() - smallFontMetrics.stringWidth(menuText[i - 1])) / 2, p.getResolutionY() / 10 * (i + 4));
		}
	}

}
