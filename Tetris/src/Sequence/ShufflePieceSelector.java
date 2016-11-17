package Sequence;

//Randomly picks a piece out of a bag to play
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import Game.Tetromino;

public class ShufflePieceSelector implements PieceSelector
{
	private Random random;
	private List<Tetromino> bag = new LinkedList<>();

	public ShufflePieceSelector()
	{
		this(System.nanoTime());
	}

	public ShufflePieceSelector(long seed)
	{
		random = new Random(seed);
	}

	public Tetromino getNextPiece()
	{
		if (bag.size() == 0)
		{
			bag.addAll(Tetromino.tetrominoes.values());
			Collections.shuffle(bag, random);
		}
		return bag.remove(0);
	}
}
