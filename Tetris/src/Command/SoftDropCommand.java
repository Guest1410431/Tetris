package Command;
//Raidly drops a piece to its lowest point, not instant, but faster than normal
import Game.GameContext;

public class SoftDropCommand extends MovementCommand
{
	private GameContext context;
	private Command subcommand;
	private boolean success = false;

	public SoftDropCommand(GameContext context)
	{
		super(context);
		this.context = context;
	}

	public void execute()
	{
		if (!context.getBoard().isFalling(context.getCurrent(), context.getX(), context.getY()))
		{
			subcommand = new HardDropCommand(context);
			subcommand.execute();
		}
		else
		{
			success = tryMove(context.getCurrent(), context.getX(), context.getY() - 1);
		}
	}

	public void unexecute()
	{
		if (success)
		{
			tryMove(context.getCurrent(), context.getX(), context.getY() + 1);
		}
		if (subcommand != null)
		{
			subcommand.unexecute();
		}
	}
}
