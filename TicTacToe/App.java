package TicTacToe;

import java.awt.EventQueue;

import javax.swing.JFrame;


@SuppressWarnings("serial")
public class App extends JFrame
{	
	//Get window parameters
	Parameters p = new Parameters();
	
	public App()
	{
		//Initialization
		
		//Window size
		setSize(p.getResolutionX(), p.getResolutionY());
		setResizable(false);
		
		//Widow title
		setTitle("TIC TAC TOE");
		
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//Show title screen
		add(new Title(p, this));
		
	}
	
	/**
	 * Function that changes game state to show menu
	 */
	public void showMenu()
	{
		setSize(p.getResolutionX(), p.getResolutionY());
		setLocationRelativeTo(null);
		add(new Menu(p, this));
	}
	
	/**
	 * Function that changes game state to show menu screen
	 */
	public void showGameModeSelect()
	{
		add(new GameModeSelect(p, this));
	}
	
	/**
	 * Function that changes game state to show options screen
	 */
	public void showOptions()
	{
		add(new Options(p, this));
	}
	
	/**
	 * Function that changes game state to show credits screen
	 */
	public void showCredits()
	{
		add(new Credits(p, this));
	}
	
	/**
	 * Function that changes game state to show main game screen
	 */
	public void startGame()
	{
		add(new GameBoard(p, this));
	}

	/**
	 * Main function that starts the program
	 */
	public static void main(String[] args)
	{	
		EventQueue.invokeLater(() -> {
			
			//Create application object
			App myApp = new App();
			
			//Show window
			myApp.setVisible(true);
		});
	}

}
