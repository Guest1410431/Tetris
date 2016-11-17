package Sequence;
//A fun mode where only the I blocks are spawned
import Game.Shape;
import Game.Tetromino;
 
public class LinePieceSelector implements PieceSelector
{
	public Tetromino getNextPiece() 
	{
		return Tetromino.tetrominoes.get(Shape.I);
	}
}
