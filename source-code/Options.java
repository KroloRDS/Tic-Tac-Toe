package TicTacToe;

import java.awt.Color;
import java.awt.Graphics;

public class Options extends GameState
{
	
	private static final long serialVersionUID = 1L;
	
	private int options[] = {0, 0, 1};
	
	private String[][] cols = {{"Sound effects:", "Resolution:", "DISCARD"}, {"", "", "APPLY"}};
	
	private String[] row1 = {"ON", "OFF"};
	private String[] row2 = {"800x600", "1024x768", "1280x960"};
	
	int selection = 1;
	int optionsCount = cols[0].length;
	
	private boolean blockInput = true;
	private boolean blockInputX = true;
	
	public Options(Parameters p, App myApp)
	{
		super(p, myApp);
		
		if(!p.isSeOn())
		{
			options[0] = 1;
		}
		options[1] = p.getScale();
	}
	
	protected void update()
	{
		changeOptions();
		
		if (!Key.down.isDown && !Key.up.isDown && !Key.ok.isDown)
		{
			blockInput = false;
		}
		
		if (Key.down.isDown && !blockInput)
		{
			selection++;
			blockInput = true;
			playSe("select.wav");
		}
		
		if (Key.up.isDown && !blockInput)
		{
			selection--;
			blockInput = true;
			playSe("select.wav");
		}
		
		//Top-down wrap
		if (selection > optionsCount)
		{
			selection = 1;
		}
		if (selection <= 0)
		{
			selection = optionsCount;
		}
		
		if (Key.ok.isDown && !blockInput)
		{
			if (selection == 3)
			{
				blockInput = true;
				
				//DISCARD
				if (options[2] == 0)
				{
					playSe("cancel.wav");
				}
				
				//APPLY
				else
				{
					playSe("ok.wav");
					apply();
				}
				
				myApp.showMenu();
				enabled = false;
				setVisible(false);
			}
		}
		
		if (Key.esc.isDown && !blockInput)
		{
			blockInput = true;
			playSe("cancel.wav");
			
			myApp.showMenu();
			enabled = false;
			setVisible(false);
		}
	}
	
	private void changeOptions()
	{  	
		if (!Key.left.isDown && !Key.right.isDown)
		{
			blockInputX = false;
		}
		
		if (Key.left.isDown && !blockInputX)
		{
			options[selection- 1]--;
			blockInputX = true;
			playSe("select.wav");
		}
		
		if (Key.right.isDown && !blockInputX)
		{
			options[selection - 1]++;
			blockInputX = true;
			playSe("select.wav");
		}
		
		//Left-right wrap
		if (selection == 2)
		{
			//For resolution
			if(options[1] == 3)
			{
				options[1] = 0;
			}
			
			if(options[1] == -1)
			{
				options[1] = 2;
			}
		}
		else
		{
			//For other options
			if (options[selection - 1] == 2)
			{
				options[selection - 1] = 0;
			}
			
			if (options[selection - 1] == -1)
			{
				options[selection - 1] = 1;
			}
		}
		
		//Update displayed text with currently selected options
		cols[1][0] = row1[options[0]];
		cols[1][1] = row2[options[1]];
	}
	
	private void apply()
	{ 	
		if (options[0] == 0)
		{
			p.setSe(true);
		}
		else
		{
			p.setSe(false);
		}
		
		p.setResolution(options[1]);
	}

	protected void drawFrame(Graphics g)
	{
		//Set background color to black
		setBackground(Color.black);
		
		//Set text color to white
		g.setColor(Color.white);
		
		//Draw menu options
		g.setFont(smallFont);
		
		//For every row
		for (int i = 1; i <= optionsCount; i++)
		{
			//For every column
			for (int j = 0; j < cols.length; j++)
			{
				//Draw column
				if (i == selection && (i != 3 || options[2] == j))
				{
					g.setColor(Color.yellow);
				}
				else
				{
					g.setColor(Color.white);
				}
				g.drawString(cols[j][i - 1], p.getResolutionX() / 10 * (1 + j * 3), p.getResolutionY() / 10 + (p.getScale() + 5) * 15 * i);
			}
		}
	}
}
