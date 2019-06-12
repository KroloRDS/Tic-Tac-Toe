package TicTacToe;
import TicTacToe.Tile.Tick;

public class Row
{
	private int x;
	private int o;
	private int none;
	private int rows;
	
	public Row(int rows)
	{
		this.rows = rows;
		none = rows;
		x = 0;
		o = 0;
	}
	
	/**
	 * Resets the number of Xs and Os in this row
	 */
	public void reset()
	{
		x = 0;
		o = 0;
		none = rows;
	}
	
	/**
	 * Increments number of given symbol in this row
	 * @param symbol X or O
	 */
	public void inc(Tick symbol)
	{
		if (symbol == Tick.O)
		{
			o++;
		}
		if (symbol == Tick.X)
		{
			x++;
		}
		none--;
	}
	
	/**
	 * @param symbol X, O or Blank
	 * @return number of given symbol in this row
	 */
	public int count(Tick symbol)
	{
		if (symbol == Tick.O)
		{
			return o;
		}
		if (symbol == Tick.X)
		{
			return x;
		}
		return none;
	}
}
