package Command;
//Hard drop drops the piece straight down to its lowest point, instantly
import Game.GameContext;

public class HardDropCommand extends MovementCommand
{
	private GameContext context;
	private int y;
	private long score;
	private boolean success = false;
	private Command subcommand1;
	private Command subcommand2;

	public HardDropCommand(GameContext context)
	{
		super(context);
		this.context = context;
	}

	public void execute()
	{
		y = context.getY();

		success = tryMove(context.getCurrent(), context.getX(), context.getBoard().dropHeight(context.getCurrent(), context.getX(), y));

		if (success)
		{
			int pieceReward = ((context.getBoard().getHeight() + (3 * context.getLevel())) - (context.getBoard().getHeight() - y));

			score = context.getScore();
			context.setScore(score + pieceReward);
			context.setDrops(context.getDrops() + 1);

			context.getBoard().addPiece(context.getCurrent(), context.getX(), context.getY());

			subcommand1 = new ClearCommand(context);
			subcommand1.execute();

			subcommand2 = new NewTetrominoCommand(context);
			subcommand2.execute();
		}
	}

	public void unexecute()
	{
		if (success)
		{
			subcommand2.unexecute();
			subcommand1.unexecute();

			context.getBoard().removePiece(context.getCurrent(), context.getX(), context.getY());

			tryMove(context.getCurrent(), context.getX(), y);

			context.setScore(score);
			context.setDrops(context.getDrops() - 1);
		}
	}
}
