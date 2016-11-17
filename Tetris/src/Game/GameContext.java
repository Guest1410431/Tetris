package Game;
//The game. Runs everything related to the game, unrelated to the AI
import Sequence.PieceSequence;
import Sequence.ShufflePieceSelector;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

import Command.Command;
import Command.NewTetrominoCommand;

public class GameContext
{
	public enum State
	{
		PLAYING, PAUSED, GAMEOVER
	}

	private final int MAX_HISTORY = 5000;

	private State state = State.PAUSED;
	private Board board = new Board(10, 20);
	private PieceSequence sequence = new PieceSequence(new ShufflePieceSelector());

	private long score = 0;
	private long lines = 0;
	private long drops = 0;

	private int xPos;
	private int yPos;
	private Tetromino current;
	private Tetromino preview;

	private boolean autoRestart = true;
	private boolean firstGame = true;
	
	private Queue<Command> queue = new LinkedList<>();

	private Stack<Command> history = new Stack<Command>()
			{
		public boolean add(Command element)
		{
			if (this.size() > MAX_HISTORY - 1)
			{
				this.remove(0);
			}

			return super.add(element);
		}
	};
	private List<NewGameListener> newGameListeners = new ArrayList<>();
	private List<EndGameListener> endGameListeners = new ArrayList<>();

	// General Game Settings
	boolean isAutoRestart()
	{
		return autoRestart;
	}

	void setAutoRestart(boolean autoRestart)
	{
		this.autoRestart = autoRestart;
	}

	public Board getBoard()
	{
		return board;
	}

	public void setBoard(Board board)
	{
		this.board = board;
	}

	public PieceSequence getSequence()
	{
		return sequence;
	}

	public void setSequence(PieceSequence sequence)
	{
		this.sequence = sequence;
	}

	// Current Piece State
	public int getX()
	{
		return xPos;
	}

	public void setX(int xPos)
	{
		this.xPos = xPos;
	}

	public int getY()
	{
		return yPos;
	}

	public void setY(int yPos)
	{
		this.yPos = yPos;
	}

	public Tetromino getCurrent()
	{
		return current;
	}

	public void setCurrent(Tetromino current)
	{
		this.current = current;
	}

	public Tetromino getPreview()
	{
		return preview;
	}

	public void setPreview(Tetromino preview)
	{
		this.preview = preview;
	}
	// Score State
	public State getState()
	{
		return state;
	}

	public void setState(State state)
	{
		this.state = state;
	}

	public void pause(boolean pause)
	{
		if (state == State.GAMEOVER)
		{
			throw new RuntimeException("Tried pausing out of game.");
		}

		state = pause ? State.PAUSED : State.PLAYING;
	}

	public long getScore()
	{
		return score;
	}

	public void setScore(long score)
	{
		this.score = score;
	}

	public int getLevel()
	{
		return (int) Math.min(10, ((lines - 1) / 10) + 1);
	}

	public long getLines()
	{
		return lines;
	}

	public void setLines(long lines)
	{
		this.lines = lines;
	}

	public long getDrops()
	{
		return drops;
	}

	public void setDrops(long drops)
	{
		this.drops = drops;
	}

	// Command Execution
	public void newGame()
	{
		this.score = 0;
		this.lines = 0;
		this.drops = 0;

		if(firstGame)
		{
			state = State.PAUSED;
			firstGame = false;
		}
		else
		{
			state = state.PLAYING;
		}

		board.clear();
		history.clear();
		sequence.clear();

		this.store(new NewTetrominoCommand(this));
		this.execute();

		for (NewGameListener listener : newGameListeners)
		{
			listener.onNewGame();
		}
	}

	public void registerNewGameListener(NewGameListener listener)
	{
		newGameListeners.add(listener);
	}

	public void registerEndGameListener(EndGameListener listener)
	{
		endGameListeners.add(listener);
	}

	public void store(Command command)
	{
		queue.add(command);
	}

	public void execute()
	{
		for (Command command : queue)
		{
			if (state == State.GAMEOVER)
			{
				break;
			}

			command.execute();
			history.add(command);

			if (!getBoard().canMove(getCurrent(), getX(), getY()))
			{
				state = State.GAMEOVER;

				for (EndGameListener listener : endGameListeners)
				{
					listener.onEndGame();
				}
			}
		}

		queue.clear();
	}

	public void undo()
	{
		undo(1);
	}

	public void undo(int turns)
	{
		while (turns-- > 0 && history.size() > 0)
		{
			history.pop().unexecute();
		}
	}
}
