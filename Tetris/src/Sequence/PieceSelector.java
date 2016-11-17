package Sequence;
//Interface implemented by the three different game modes, simply needs to know the next piece to be chosen
import Game.Tetromino;
 
public interface PieceSelector
{
	abstract Tetromino getNextPiece();
}
