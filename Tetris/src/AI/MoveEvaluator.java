package AI;
//How the AI determines the next move to make, it compares all possible moves, and their scores respectively
import Game.Board;
import Game.Tetromino;

public class MoveEvaluator
{
	private ScoringSystem scoring;

	public MoveEvaluator(ScoringSystem scoring)
	{
		this.scoring = scoring;
	}
	//Gets the next move for the AI to make, given it's the first piece
	public Move getNextMove(Board board, Tetromino current, int x1, int y1)
	{
		return getNextMove(board, current, x1, y1, null, 0, 0);
	}
	//Gets the AI's next move
	public Move getNextMove(Board board, Tetromino current, int x1, int y1, Tetromino preview, int x2, int y2)
	{
		double best = Double.NEGATIVE_INFINITY;
		Move move = new Move(best, 0, 0);

		for (int i = 0; i < 4; i++)
		{
			Move m = getBestMoveForRotatedPiece(board, i, current, x1, y1, preview, x2, y2);

			if (m.getScore() > best)
			{
				best = m.getScore();
				move = m;
			}
			current = Tetromino.rotateClockwise(current);
		}
		return move;
	}
	//Returns the best way to rotate the piece
	private Move getBestMoveForRotatedPiece(Board board, int rot, Tetromino current, int x1, int y1, Tetromino preview, int x2, int y2)
	{
		double best = Double.NEGATIVE_INFINITY;
		Move move = new Move(best, 0, 0);

		int min = getMaxTranslationDeltaMagnitude(board, current, x1, y1, -1);
		int max = getMaxTranslationDeltaMagnitude(board, current, x1, y1, +1);

		for (int translation = min; translation <= max; translation++)
		{
			int target = board.dropHeight(current, x1 + translation, y1);
			board.addPiece(current, x1 + translation, target);

			double score = preview == null ? scoring.score(board) : getNextMove(board, preview, x2, y2).getScore();

			if (score > best)
			{
				best = score;
				move = new Move(score, rot, translation);
			}

			board.removePiece(current, x1 + translation, target);
		}
		return move;
	}
	//Returns the best way to move the piece horizontally
	private int getMaxTranslationDeltaMagnitude(Board board, Tetromino current, int xPos, int yPos, int step)
	{
		int delta = 0;
		while (board.canMove(current, xPos + delta + step, yPos))
		{
			delta += step;
		}

		return delta;
	}
}
