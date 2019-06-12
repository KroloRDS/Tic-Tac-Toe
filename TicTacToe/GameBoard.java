package TicTacToe;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

import TicTacToe.Tile.Tick;

@SuppressWarnings("serial")
public class GameBoard extends GameState
{
	private static int width = 3;
	
	private int posX = 0;
	private int posY = 0;
	private int inGamePosY = 0;
	
	private int AIsTurn = 0;
	private final int AIsMoveTime = 40; //Number of frames before AI makes a move
	
	private String turnText;
	private String pauseText = "PAUSED";
	private String[] pauseMenu = {"RESUME", "RESTART", "QUIT"};
	
	private Tile[][] Tiles = new Tile[width][width];
	private Row[] Rows = new Row[width * 2 + 2]; //Every row and column and two diagonal lines
	
	//If any key is currently held don't register any new inputs
	private boolean blockInput = true;
	private boolean blockEscInput = true;
	private boolean blockXInput = true;
	private boolean blockYInput = true;
	
	private boolean turn = true;
	private boolean gameEnded = false;
	
	private boolean paused = false;
	
	//Number of frames before player is prompted to restart
	private int newGameTimeoutMax = 100;
	private int newGameTimeout = newGameTimeoutMax;
	
	public GameBoard(Parameters p, App myApp)
	{	
		super(p, myApp);
		
		//Initialize tiles
		for (int i = 0; i < width; i++)
		{
			for (int j = 0; j < width; j++)
			{
				Tiles[i][j] = new Tile();
			}
		}
		
		//Initialize rows
		for (int i = 0; i < width * 2 + 2; i++)
		{
			Rows[i] = new Row(width);
		}
		
		//Check whether it's two player game or player against AI
		if (p.getMode())
		{
			turnText = "Player 1's turn:";
		}
		else
		{
			turnText = "Player's turn:";
		}
	}
	
	/**
	 * Updates game logic
	 */
	protected void update()
	{	
		//Function that detects pause/unpause input
		pause();
		
		if (paused)
		{
			verticalMovement();
			pauseChoice();
		}
		else
		{
			if (!gameEnded)
			{
				if (AIsTurn == 1)
				{
					AITurn();
					turnText = "Player's turn:";
				}
				if (AIsTurn > 0)
				{
					AIsTurn--;
				}
				else
				{
					playersTurn();
				}
				checkWin();
			}
			else
			{
				if (newGameTimeout > 0)
				{
					newGameTimeout--;
				}
				else
				{
					turnText = "Press ESC to restart";
				}
			}
		}
	}
	
	private void pause()
	{		
		if (!Key.esc.isDown)
		{
			blockEscInput = false;
		}
		if (!blockEscInput && Key.esc.isDown)
		{
			blockEscInput = true;
			
			if (!paused)
			{
				inGamePosY = posY;
				posY = 0;
				
				playSe("pause.wav");
				
				paused = true;
			}
			else
			{
				posY = inGamePosY;
				
				playSe("ok.wav");
				
				paused = false;
			}
		}
	}
	
	private void pauseChoice()
	{
		if (!Key.ok.isDown)
		{
			blockInput = false;
		}
		
		if (Key.ok.isDown && !blockInput)
		{
			//Resume
			if(posY == 0)
			{
				playSe("ok.wav");
				blockInput = true;
				paused = false;
			}
			
			//Restart
			if(posY == 1)
			{
				playSe("ok.wav");
				
				myApp.startGame();
				
				enabled = false;
				setVisible(false);
			}
			
			//Quit
			if(posY == 2)
			{
				playSe("cancel.wav");
				myApp.showMenu();
				enabled = false;
				setVisible(false);
			}
		}
	}
	
	private void playersTurn()
	{	
		//Functions that update current cursor position
		horizontalMovement();
		verticalMovement();
		
		if (!Key.ok.isDown)
		{
			blockInput = false;
		}
		
		if (Key.ok.isDown && !blockInput)
		{
			choice();
			blockInput = true;
			
			//If it's player against AI
			if (!p.getMode())
			{
				turnText = "AI's turn...";
				AIsTurn = AIsMoveTime;
			}
		}
	}
	
	private void choice()
	{
		if (!Tiles[posY][posX].isOccupied())
		{
			playSe("ok.wav");
			
			if (p.getMode())
			{
				//Decide which player is currently making a move and update board with corresponding symbol
				if (turn)
				{
					incRows(posX, posY, Tick.X);
					turnText = "Player 2's turn:";
				}
				else
				{
					incRows(posX, posY, Tick.O);
					turnText = "Player 1's turn:";
				}
				
				turn = !turn;
			}
			else
			{
				incRows(posX, posY, Tick.X);
			}
		}
		else
		{
			playSe("invalid.wav");
		}
	}

	private void incRows(int x, int y, Tick symbol)
	{
		//Update tile
		Tiles[y][x].setTick(symbol);
		
		//Update rows
		Rows[y].inc(symbol);
		
		//Update columns
		Rows[width + x].inc(symbol);
		
		//Update diagonal lines
		if (x == y)
		{
			Rows[width * 2].inc(symbol);
		}
		if (x == width - y - 1)
		{
			Rows[width * 2 + 1].inc(symbol);
		}
	}

	private void verticalMovement()
	{
		if (!Key.down.isDown && !Key.up.isDown)
		{
			blockYInput = false;
		}
		
		if (Key.down.isDown && !blockYInput)
		{
			posY++;
			blockYInput = true;
			playSe("select.wav");
		}
		
		if (Key.up.isDown && !blockYInput)
		{
			posY--;
			blockYInput = true;
			playSe("select.wav");
		}
		
		//Top-down wrap
		if (posY >= width)
		{
			posY = 0;
		}
		
		if (posY < 0)
		{
			posY = width - 1;
		}
	}

	private void horizontalMovement()
	{
		if (!Key.left.isDown && !Key.right.isDown)
		{
			blockXInput = false;
		}
		
		if (Key.right.isDown && !blockXInput)
		{
			posX++;
			blockXInput = true;
			playSe("select.wav");
		}
		
		if (Key.left.isDown && !blockXInput)
		{
			posX--;
			blockXInput = true;
			playSe("select.wav");
		}
		
		//Left-right wrap
		if (posX >= width)
		{
			posX = 0;
		}
		
		if (posX < 0)
		{
			posX = width - 1;
		}
	}
	
	private void checkWin()
	{
		for (Row row : Rows)
		{
			if (row.count(Tick.O) == width)
			{
				gameEnded = true;
				
				if (p.getMode())
				{
					turnText = "Player 2 has won";
				}
				else
				{
					turnText = "AI has won";
				}
			}
			if (row.count(Tick.X) == width)
			{
				gameEnded = true;
				
				if (p.getMode())
				{
					turnText = "Player 1 has won";
				}
				else
				{
					turnText = "Player has won";
				}
			}
		}
		
		if (!gameEnded)
		{
			checkTie();
		}
	}
	
	private void checkTie()
	{
		gameEnded = true;
		
		//Check whether every tile on the board is occupied, if so, game has ended
		for (int i = 0; i < width; i++)
		{
			for (int j = 0; j < width; j++)
			{
				if (!Tiles[i][j].isOccupied())
				{
					gameEnded = false;
				}
			}
		}
		
		if (gameEnded)
		{
			turnText = "Tie";
		}
	}
	
	//AI's LOGIC
	
	//If either player or AI is one move from winning
	private void block()
	{
		//Prioritize own winning move
		for (int i = 0; turn && i < width * 2 + 2; i++)
		{
			if (Rows[i].count(Tick.O) == width - 1 && Rows[i].count(Tick.None) == 1)
			{
				findFreeSpot(i);
			}
		}
		
		//If there is none block player from winning
		for (int i = 0; turn && i < width * 2 + 2; i++)
		{
			if (Rows[i].count(Tick.X) == width - 1 && Rows[i].count(Tick.None) == 1)
			{
				findFreeSpot(i);
			}
		}
	}
	
	//Place tick the line that already has one own symbol
	private void continueLine()
	{
		for (int i = 0; turn &&  i < width * 2 + 2; i++)
		{
			if (Rows[i].count(Tick.O) > 0 && Rows[i].count(Tick.X) == 0)
			{
				findFreeSpot(i);
			}
		}
	}
	
	private static int randomInt()
	{
		Random r = new Random();
		return r.nextInt(width);
	}
	
	private void randomMove()
	{
		int x, y;
		
		//Make random moves until selected spot will be vacant
		while (turn)
		{
			x = randomInt();
			y = randomInt();
			
			if (!Tiles[x][y].isOccupied())
			{
				incRows(y, x, Tick.O);
				turn = false;
			}
		}
	}
	
	private void findFreeSpot(int id)
	{
		//Find spot in the line with given id number
		
		//Rows
		if (id < width)
		{
			for (int i = 0; turn && i < width; i++)
			{
				if (!Tiles[id][i].isOccupied())
				{
					incRows(i, id, Tick.O);
					turn = false;
				}
			}
		}
		
		//Columns
		else if (id < 2 * width)
		{
			for (int i = 0; turn && i < width; i++)
			{
				if (!Tiles[i][id - width].isOccupied())
				{
					incRows(id - width, i, Tick.O);
					turn = false;
				}
			}
		}
		
		//Diagonal top left to bottom right
		else if (id == 2 * width)
		{
			for (int i = 0; turn && i < width; i++)
			{
				if (!Tiles[i][i].isOccupied())
				{
					incRows(i, i, Tick.O);
					turn = false;
				}
			}
		}
		
		//Diagonal top right to bottom left
		else
		{
			for (int i = 0; turn && i < width; i++)
			{
				if (!Tiles[width - i - 1][i].isOccupied())
				{
					incRows(i, width - i - 1, Tick.O);
					turn = false;
				}
			}
		}
	}
	
	private void AITurn()
	{	
		block();
		
		//If move wasn't made
		if(turn)
		{
			continueLine();
		}
		
		//If move still wasn't made
		if(turn)
		{
			randomMove();
		}
		
		turn = true;
	}

	/**
	 * Updates visuals
	 */
	protected void drawFrame(Graphics g)
	{
		int tileWidth = 100 + 30 * p.getScale();
		int originX = (p.getResolutionX() - tileWidth * width) / 2;
		int originY = (p.getResolutionY() - tileWidth * width) / 2;
		
		g.setFont(smallFont);
		g.drawString(turnText, (p.getResolutionX() - smallFontMetrics.stringWidth(turnText)) / 2, p.getResolutionY() / 6);
		
		drawTicks(g, tileWidth, originX, originY);
		drawLines(g, tileWidth, originX, originY);
		
		if(paused)
		{
			drawPauseMenu(g);
		}
	}

	private void drawTicks(Graphics g, int tileWidth, int originX, int originY)
	{
		if (AIsTurn == 0 && !gameEnded && !paused)
		{
			//Paint selected tile
			if(turn)
			{
				g.setColor(Color.yellow);
			}
			else
			{
				g.setColor(Color.cyan);
			}
			g.fillRect(originX + tileWidth * posX, originY + tileWidth * posY, tileWidth, tileWidth);
		}
		
		//Space between tile border and symbol
		int margin = tileWidth / 10;
		
		//Draw ticks
		g.setColor(Color.black);
		
		for (int i = 0; i < width; i++)
		{
			for (int j = 0; j < width; j++)
			{
				if (Tiles[i][j].getTick() == Tick.O)
				{
					//Draw circle
					g.drawOval(originX + tileWidth * j + margin,
							originY + tileWidth * i + margin,
							tileWidth - 2 * margin,
							tileWidth - 2 * margin);
				}
				
				if (Tiles[i][j].getTick() == Tick.X)
				{
					//Draw X
					g.drawLine(originX + tileWidth * j + margin,
							originY + tileWidth * i + margin,
							originX + tileWidth * (j + 1) - margin,
							originY + tileWidth * (i + 1) - margin);
					g.drawLine(originX + tileWidth * j + margin,
							originY + tileWidth * (i + 1) - margin,
							originX + tileWidth * (j + 1) - margin,
							originY + tileWidth * i + margin);
				}
			}
		}
	}

	private void drawLines(Graphics g, int tileWidth, int originX, int originY)
	{
		//Draw vertical lines
		for (int i = 0; i <= width; i++)
		{
			g.drawLine(originX + i * tileWidth, originY, originX + i * tileWidth, originY + width * tileWidth);
		}
		
		//Draw horizontal lines
		for (int i = 0; i <= width; i++)
		{
			g.drawLine(originX, originY + i * tileWidth, originX + width * tileWidth, originY + i * tileWidth);
		}
	}
	
	private void drawPauseMenu(Graphics g)
	{
		//Dim screen
		g.setColor(new Color(0, 0, 0, 200));
		if (paused)
		{
			g.fillRect(0, 0, p.getResolutionX(), p.getResolutionY());
		} 
		
		//Draw title text
		g.setColor(Color.white);
		g.setFont(bigFont);
		g.drawString(pauseText, (p.getResolutionX() - bigFontMetrics.stringWidth(pauseText)) / 2, p.getResolutionY() / 4);
		
		//Draw menu options
		g.setFont(smallFont);
		for (int i = 0; i < 3; i++)
		{
			if (i == posY)
			{
				g.setColor(Color.yellow);
			}
			else
			{
				g.setColor(Color.white);
			}
			
			g.drawString(pauseMenu[i], (p.getResolutionX() - smallFontMetrics.stringWidth(pauseMenu[i])) / 2, p.getResolutionY() / 10 * (i + 4));
		}
	}
}
