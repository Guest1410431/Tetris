package Sequence;
//Returns the worst scoring piece to test the limits of the AI
import AI.Move;
import AI.MoveEvaluator;
import Game.GameContext;
import Game.Tetromino;

public class WorstPieceSelector implements PieceSelector
{
	private GameContext context;
	private MoveEvaluator evaluator;

	public WorstPieceSelector(GameContext context, MoveEvaluator evaluator)
	{
		this.context = context;
		this.evaluator = evaluator;
	}

	public Tetromino getNextPiece()
	{
		double worst = Double.POSITIVE_INFINITY;
		Tetromino piece = null;

		for (Tetromino tetromino : Tetromino.tetrominoes.values())
		{
			int x = context.getBoard().getSpawnX(tetromino);
			int y = context.getBoard().getSpawnY(tetromino);

			Move m = evaluator.getNextMove(context.getBoard(), tetromino, x, y);

			if (m.getScore() < worst)
			{
				worst = m.getScore();
				piece = tetromino;
			}
		}
		return piece;
	}
}
