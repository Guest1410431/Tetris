package Command;

import Game.GameContext;
import Game.Shape;

public class AddJunkCommand implements Command
{
	private GameContext context;
	private Shape[] overflow;
	private Command subcommand;

	public AddJunkCommand(GameContext context)
	{
		this.context = context;
	}

	public void execute()
	{
		overflow = context.getBoard().getRow(context.getBoard().getHeight() - 1);

		Shape[] line = new Shape[context.getBoard().getWidth()];

		for (int i = 0; i < context.getBoard().getWidth(); i++)
		{
			line[i] = Shape.Junk;
		}
		int holes = (int) (Math.random() * (context.getBoard().getWidth() - 1) + 1);

		while (holes > 0)
		{
			int index = (int) (Math.random() * context.getBoard().getWidth());

			if (line[index] != Shape.NoShape)
			{
				line[index] = Shape.NoShape;
				holes--;
			}
		}
		if (!context.getBoard().canMove(context.getCurrent(), context.getX(), context.getY() - 1))
		{
			subcommand = new HardDropCommand(context);
			subcommand.execute();
		}

		context.getBoard().addRow(0, line);
	}

	public void unexecute()
	{
		context.getBoard().removeRow(0);

		if (subcommand != null)
		{
			subcommand.unexecute();
		}
		context.getBoard().addRow(context.getBoard().getHeight() - 1, overflow);
	}
}
