package TicTacToe;

public class Key
{
	//Every key used in the game: confirmation, cancellation and four directions
	public static Key up = new Key();
	public static Key down = new Key();
	public static Key left = new Key();
	public static Key right = new Key();
	public static Key ok = new Key();
	public static Key esc = new Key();

	//Key state, either pressed or not pressed
	public boolean isDown;
}