package TicTacToe;

public class Tile
{
	public enum Tick {None, X, O};
	private Tick tick;
	
	public Tile()
	{
		tick = Tick.None;
	}
	
	public void setTick(Tick tick)
	{
		this.tick = tick;
	}
	
	public Tick getTick()
	{
		return tick;
	}
	
	public boolean isOccupied()
	{
		if (tick == Tick.None)
		{
			return false;
		}
		return true;
	}
}
