package Sequence;
//randomly decides s or z when that game mode is selected
import Game.Shape;
import Game.Tetromino;

public class SZPieceSelector implements PieceSelector
{
	private double decide;

	public Tetromino getNextPiece()
	{
		decide = Math.random();
		return Tetromino.tetrominoes.get(decide > .5 ? Shape.S : Shape.Z);
	}
}
