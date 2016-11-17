package Sequence;
//Keeps track of the pieces that have been played, as well as the order that they have been played
import java.util.ArrayList;
import java.util.List;

import Game.Tetromino;

public class PieceSequence
{
	private int current = -1;
	private int preview = +0;
	private List<Tetromino> pieces = new ArrayList<>();

	private PieceSelector selector;

	public PieceSequence(PieceSelector selector)
	{
		this.selector = selector;
	}

	public void clear()
	{
		current = -1;
		preview = +0;

		pieces.clear();
	}

	public void advance()
	{
		current++;
		preview++;

		while (pieces.size() <= preview)
		{
			pieces.add(selector.getNextPiece());
		}
	}

	public void rewind()
	{
		current--;
		preview--;
	}

	public Tetromino peekCurrent()
	{
		return pieces.get(current);
	}

	public Tetromino peekPreview()
	{
		return pieces.get(preview);
	}
}
