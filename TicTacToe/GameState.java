package TicTacToe;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.net.URL;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public abstract class GameState extends JPanel implements Runnable
{
	private static final long serialVersionUID = 1L;

	private Thread animator;
	
	private Controller controller = new Controller();
	
	protected Font bigFont;
	protected Font smallFont;
	
	protected FontMetrics bigFontMetrics;
	protected FontMetrics smallFontMetrics;
	
	//If the thread is still active
	protected boolean enabled = true;
	
	protected Parameters p;
	protected App myApp;
	
	private String pathToSoundFolder = "/sounds/";
	
	private Clip clipSe;
	
	private KeyListener keyListener = new KeyListener();
	
	public GameState(Parameters p, App myApp)
	{
		this.p = p;
		this.myApp = myApp;
		
		addKeyListener(keyListener);
		setFocusable(true);
		
		bigFont = new Font("Helvetica", Font.BOLD, 80 + 20 * p.getScale());
		smallFont = new Font("Helvetica", Font.BOLD, 20 + 5 * p.getScale());
		
		bigFontMetrics = getFontMetrics(bigFont);
		smallFontMetrics = getFontMetrics(smallFont);
	}
	
	/**
	 * Starts thread
	 */
	@Override
	public void addNotify()
	{
		super.addNotify();

		animator = new Thread(this);
		animator.start();
	}

	/**
	 * This function is called every time a new frame is drawn
	 */
	@Override
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		
		Toolkit.getDefaultToolkit().sync();
		
		drawFrame(g);
	}
	
	/**
	 * This function is called when the thread is started
	 */
	@Override
	public void run()
	{
		long beforeTime, dTime;
		int sleep;

		beforeTime = System.currentTimeMillis();

		while(enabled)
		{
			//Update game logic
			update();
			
			//Redraw frame
			repaint();
			
			//Constant frame rate
			dTime = System.currentTimeMillis() - beforeTime;
			sleep = (int) (1000 / p.getFps() - dTime);
			
			if(sleep < 0)
			{
				sleep = 0;
			}
			
			try
			{
				Thread.sleep(sleep);
			}
			catch (InterruptedException e)
			{			   
				String msg = String.format("Thread interrupted: %s", e.getMessage());
				
				JOptionPane.showMessageDialog(this, msg, "Error", 
					JOptionPane.ERROR_MESSAGE);
			}
			
			beforeTime = System.currentTimeMillis();
		}
	}
	
	abstract protected void update();
	
	abstract protected void drawFrame(Graphics g);
	
	/**
	 * Function that plays sound effects
	 * Plays selected sound clip only once
	 * @param location path to sound file
	 */
	protected void playSe(String location)
	{
		if (p.isSeOn())
		{
			try
			{
				location = pathToSoundFolder + location;

				URL url = getClass().getResource(location);
				AudioInputStream audioInput = AudioSystem.getAudioInputStream(url);
				
				clipSe = AudioSystem.getClip();
				clipSe.open(audioInput);
				clipSe.start();
			}
			catch (Exception e)
			{
				System.out.println("Cannot find file: " + location);
			}
		}
	}
	
	private class KeyListener extends KeyAdapter
	{
		@Override
		public void keyReleased(KeyEvent e)
		{
			controller.keyReleased(e);
		}

		@Override
		public void keyPressed(KeyEvent e)
		{
			controller.keyPressed(e);
		}
	}
}
