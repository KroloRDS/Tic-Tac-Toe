package TicTacToe;

public class Parameters
{
	//Window size
	private final int[] resolutionX = {800, 1024, 1280};
	private final int[] resolutionY = {600, 768, 960};
	private int resolution = 0;
	
	//Sound on/off
	private boolean se = true;
	
	private boolean twoPlayers = false;
		
	//Frames per second
	private final int fps = 60;
	
	/**
	 * Resolution setter
	 * @param resolution 0 = 800x600, 1 = 1024x768 etc
	 */
	public void setResolution(int resolution)
	{
		this.resolution = resolution;
	}
		
	/**
	 * @return current screen width in pixels
	 */
	public int getResolutionX()
	{
		return resolutionX[resolution];
	}
	
	/**
	 * @return current screen height in pixels
	 */
	public int getResolutionY()
	{
		return resolutionY[resolution];
	}
	
	/**
	 * @return current screen refresh rate (frames per second)
	 */
	public int getFps()
	{
		return fps;
	}
	
	/**
	 * @return current resolution option, 0 = 800x600, 1 = 1024x768 etc
	 */
	public int getScale()
	{
		return resolution;
	}
	
	/**
	 * @return true if the sound is on
	 */
	public boolean isSeOn()
	{
		return se;
	}
	
	/**
	 * Turns the sounds on or off
	 * @param set true for on, false for off
	 */
	public void setSe(boolean set)
	{
		se = set;
	}
	
	/**
	 * @return true if the game is in the mode
	 * where two human players play against each other
	 */
	public boolean getMode()
	{
		return twoPlayers;
	}
	
	/**
	 * Switches the game between human vs human mode
	 * and human vs ai mode
	 * @param mode true for human v human, false for human v ai
	 */
	public void setMode(boolean mode)
	{
		twoPlayers = mode;
	}
}
