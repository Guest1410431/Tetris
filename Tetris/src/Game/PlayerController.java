package Game;
//A controller for a person, no AI
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import Command.HardDropCommand;
import Command.MoveLeftCommand;
import Command.MoveRightCommand;
import Command.RotateClockwiseCommand;
import Command.RotateCounterClockwiseCommand;
import Command.SoftDropCommand;

public class PlayerController implements KeyListener
{
	private GameContext context;
	private long lastGravity = System.currentTimeMillis();
	private Map<Integer, Boolean> keys = new HashMap<>();

	public PlayerController(GameContext context)
	{
		this.context = context;
	}

	public void update()
	{
		if (checkGravityTimeout())
		{
			context.store(new SoftDropCommand(context));
		}

		for (int keyCode : getKeys())
		{
			switch (keyCode)
			{
			case KeyEvent.VK_LEFT:
				context.store(new MoveLeftCommand(context));
				break;

			case KeyEvent.VK_RIGHT:
				context.store(new MoveRightCommand(context));
				break;

			case KeyEvent.VK_Z:
			case KeyEvent.VK_UP:
				context.store(new RotateClockwiseCommand(context));
				break;

			case KeyEvent.VK_X:
				context.store(new RotateCounterClockwiseCommand(context));
				break;

			case KeyEvent.VK_DOWN:
				context.store(new SoftDropCommand(context));
				break;

			case KeyEvent.VK_SPACE:
				context.store(new HardDropCommand(context));
				break;
			}
		}
	}

	private boolean checkGravityTimeout()
	{
		long time = System.currentTimeMillis();
		long wait = (long) (((11 - context.getLevel()) * 0.05) * 1000);

		if (time - wait >= lastGravity)
		{
			lastGravity = time;
			return true;
		}
		return false;
	}

	
	public void keyPressed(KeyEvent ke)
	{
		toggle(ke.getKeyCode(), true);
	}

	
	public void keyReleased(KeyEvent ke)
	{
		toggle(ke.getKeyCode(), false);
	}

	
	public void keyTyped(KeyEvent ke)
	{
	}

	private void toggle(int keyCode, boolean down)
	{
		keys.put(keyCode, down);
	}

	private List<Integer> getKeys()
	{
		List<Integer> result = new LinkedList<>();

		for (Map.Entry<Integer, Boolean> entry : keys.entrySet())
		{
			if (entry.getValue())
			{
				result.add(entry.getKey());
			}

			keys.put(entry.getKey(), false);
		}
		return result;
	}
}
