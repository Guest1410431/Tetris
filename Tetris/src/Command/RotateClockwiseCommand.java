package Command;
//rotates a piece clockwise, given it has enough room
import Game.GameContext;
import Game.Tetromino;

public class RotateClockwiseCommand extends MovementCommand
{
	private GameContext context;
	private boolean success = false;

	public RotateClockwiseCommand(GameContext context)
	{
		super(context);
		this.context = context;
	}

	public void execute()
	{
		success = tryMove(Tetromino.rotateClockwise(context.getCurrent()), context.getX(), context.getY());
	}
	
	public void unexecute()
	{
		if (success)
		{
			tryMove(Tetromino.rotateCounterClockwise(context.getCurrent()), context.getX(), context.getY());
		}
	}
}
