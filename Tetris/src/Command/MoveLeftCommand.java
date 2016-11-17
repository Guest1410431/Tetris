package Command;
//Moves a piece one unit left
import Game.GameContext;

public class MoveLeftCommand extends MovementCommand
{
	private GameContext context;
	private boolean success = false;

	public MoveLeftCommand(GameContext context)
	{
		super(context);
		this.context = context;
	}

	public void execute()
	{
		success = tryMove(context.getCurrent(), context.getX() - 1, context.getY());
	}

	public void unexecute()
	{
		if (success)
		{
			tryMove(context.getCurrent(), context.getX() + 1, context.getY());
		}
	}
}
