package Command;
//Makes it easier to implement the commands via this one
import Game.GameContext;
import Game.Tetromino;

public class NewTetrominoCommand implements Command
{
	private GameContext context;
	private Tetromino current;
	private Tetromino preview;
	private int x;
	private int y;

	public NewTetrominoCommand(GameContext context)
	{
		this.context = context;
	}

	public void execute()
	{
		current = context.getCurrent();
		preview = context.getPreview();

		x = context.getX();
		y = context.getY();

		context.getSequence().advance();
		context.setCurrent(context.getSequence().peekCurrent());
		context.setPreview(context.getSequence().peekPreview());

		context.setX(context.getBoard().getSpawnX(context.getCurrent()));
		context.setY(context.getBoard().getSpawnY(context.getCurrent()));
	}

	public void unexecute()
	{
		context.setCurrent(current);
		context.setPreview(preview);

		context.setX(x);
		context.setY(y);

		context.getSequence().rewind();
	}
}
