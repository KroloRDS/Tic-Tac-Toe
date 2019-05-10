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
	
	public void reset()
	{
		x = 0;
		o = 0;
		none = rows;
	}
	
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
