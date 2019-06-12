package TicTacToe;

public class Tile
{
	public enum Tick {None, X, O};
	private Tick tick;
	
	public Tile()
	{
		tick = Tick.None;
	}
	
	/**
	 * Changes the symbol of this tile
	 * @param tick X, O, or blank
	 */
	public void setTick(Tick tick)
	{
		this.tick = tick;
	}
	
	/**
	 * @return symbol of this tile: X, O, or blank
	 */
	public Tick getTick()
	{
		return tick;
	}
	
	/**
	 * @return false if this tile is vacant (no X and no O)
	 */
	public boolean isOccupied()
	{
		if (tick == Tick.None)
		{
			return false;
		}
		return true;
	}
}
