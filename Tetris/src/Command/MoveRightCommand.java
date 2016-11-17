package Command;
//Moves a piece one unit right
import Game.GameContext;

public class MoveRightCommand extends MovementCommand
{
	private GameContext context;
	private boolean success = false;

	public MoveRightCommand(GameContext context)
	{
		super(context);
		this.context = context;
	}

	public void execute()
	{
		success = tryMove(context.getCurrent(), context.getX() + 1, context.getY());
	}
	
	public void unexecute()
	{
		if (success)
		{
			tryMove(context.getCurrent(), context.getX() - 1, context.getY());
		}
	}
}
