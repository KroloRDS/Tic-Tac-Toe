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
		
	public void setResolution(int resolution)
	{
		this.resolution = resolution;
	}
		
	public int getResolutionX()
	{
		return resolutionX[resolution];
	}
		
	public int getResolutionY()
	{
		return resolutionY[resolution];
	}
		
	public int getFps()
	{
		return fps;
	}
	
	public int getScale()
	{
		return resolution;
	}
	
	public boolean isSeOn()
	{
		return se;
	}
	
	public void setSe(boolean set)
	{
		se = set;
	}
	
	public boolean getMode()
	{
		return twoPlayers;
	}
	
	public void setMode(boolean mode)
	{
		twoPlayers = mode;
	}
}
