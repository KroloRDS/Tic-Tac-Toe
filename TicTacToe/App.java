package TicTacToe;

import java.awt.EventQueue;

import javax.swing.JFrame;

public class App extends JFrame
{

	private static final long serialVersionUID = 1L;
	
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
	
	public void showMenu()
	{
		setSize(p.getResolutionX(), p.getResolutionY());
		setLocationRelativeTo(null);
		add(new Menu(p, this));
	}
	
	public void showGameModeSelect()
	{
		add(new GameModeSelect(p, this));
	}
	
	public void showOptions()
	{
		add(new Options(p, this));
	}
	
	public void showCredits()
	{
		add(new Credits(p, this));
	}
	
	public void startGame()
	{
		add(new GameBoard(p, this));
	}

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
