package Command;
//Allows all the movement commands (left, right, hard/soft drop) to exist
import Game.GameContext;
import Game.Tetromino;

abstract public class MovementCommand implements Command
{
	private GameContext context;

	public MovementCommand(GameContext context)
	{
		this.context = context;
	}

	public boolean tryMove(Tetromino piece, int xPos, int yPos)
	{
		if (context.getBoard().canMove(piece, xPos, yPos))
		{
			context.setX(xPos);
			context.setY(yPos);
			context.setCurrent(piece);

			return true;
		}
		return false;
	}
}
