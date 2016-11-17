package AI;
//The AI of the game
import Game.GameContext;

import java.util.LinkedList;
import java.util.Queue;

import Command.Command;
import Command.HardDropCommand;
import Command.MoveLeftCommand;
import Command.MoveRightCommand;
import Command.RotateClockwiseCommand;
import Command.SoftDropCommand;

public class AI
{
	private GameContext context;

	private long lastUpdate = System.currentTimeMillis();
	private Queue<Command> commands = new LinkedList<>();

	private int delay = 128;
	private boolean enabled = false;
	private boolean training = false;
	private MoveEvaluator evaluator;

	public AI(GameContext context, MoveEvaluator evaluator)
	{
		this.context = context;
		this.evaluator = evaluator;
	}
	//Updates the AI per tick
	public void update()
	{
		long time = System.currentTimeMillis();

		if (time - delay >= lastUpdate)
		{
			lastUpdate = time;

			if (commands.size() == 0)
			{
				int x1 = context.getX();
				int y1 = context.getY();
				int x2 = context.getBoard().getSpawnX(context.getPreview());
				int y2 = context.getBoard().getSpawnY(context.getPreview());

				Move move = evaluator.getNextMove(context.getBoard(), context.getCurrent(), x1, y1, context.getPreview(), x2, y2);

				int rDelta = move.getRotationDelta();
				int mDelta = move.getMovementDelta();

				int currX = context.getBoard().getSpawnX(context.getCurrent()) + mDelta;
				int currY = context.getBoard().getSpawnY(context.getCurrent());

				while (rDelta != 0 || mDelta != 0)
				{
					if (rDelta > 0)
					{
						rDelta--;
						commands.add(new RotateClockwiseCommand(context));
					}
					else if (mDelta < 0)
					{
						mDelta++;
						commands.add(new MoveLeftCommand(context));
					}
					else if (mDelta > 0)
					{
						mDelta--;
						commands.add(new MoveRightCommand(context));
					}
				}
				if (delay > 1)
				{
					while (context.getBoard().isFalling(context.getCurrent(), currX, currY--))
					{
						commands.add(new SoftDropCommand(context));
					}
				}
				commands.add(new HardDropCommand(context));
			}
			animate();
		}
	}
	//Deals with rendering and smoothing
	private void animate()
	{
		if (commands.size() > 0)
		{
			do
			{
				context.store(commands.remove());
			} while (commands.size() > 0 && delay == 1);
		}
	}

	public boolean isEnabled()
	{
		return enabled;
	}

	public void setEnabled(boolean enabled)
	{
		this.enabled = enabled;
	}

	public boolean isTraining()
	{
		return training;
	}

	public void setTraining(boolean training)
	{
		this.training = training;
	}

	public void setDelay(int delay)
	{
		this.delay = delay;
	}
}
