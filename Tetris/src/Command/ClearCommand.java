package Command;

import java.util.Map.Entry;

import Game.GameContext;
import Game.Shape;

import java.util.SortedMap;
import java.util.TreeMap;

public class ClearCommand implements Command
{
	private GameContext context;
	private long lines;
	private long score;
	private SortedMap<Integer, Shape[]> map = new TreeMap<>();

	public ClearCommand(GameContext context)
	{
		this.context = context;
	}
	public void execute()
	{
		for (int row = context.getBoard().getHeight() - 1; row >= 0; row--)
		{
			if (context.getBoard().isRowFull(row))
			{
				map.put(row, context.getBoard().getRow(row));
				context.getBoard().removeRow(row);
			}
		}
		lines = context.getLines();
		score = context.getScore();

		context.setLines(lines + map.size());
		context.setScore(score + 40 * (long) Math.pow(3, map.size() - 1));
	}

	public void unexecute()
	{
		for (Entry<Integer, Shape[]> e : map.entrySet())
		{
			context.getBoard().addRow(e.getKey(), e.getValue());
		}
		context.setLines(lines);
		context.setScore(score);
	}
}
