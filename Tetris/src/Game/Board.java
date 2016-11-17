package Game;
//the board is the board, It stores all information about the current state of the game
public class Board
{
	private int width;
	private int height;
	private Shape[] board;

	public Board(int width, int height)
	{
		this.width = width;
		this.height = height;

		board = new Shape[width * height];
		clear();
	}
	//Clears the board
	public void clear()
	{
		for(int i=0; i<board.length; i++)
		{
			board[i] = Shape.NoShape;
		}
	}
	//Clone the state of the board. If `fill` is non-null and has the same dimensions, the state will be copied
	//into that board instance. Otherwise, a new instance will be created
	public Board tryClone(Board fill)
	{
		if (fill == null || fill.width != width || fill.height != height)
		{
			fill = new Board(width, height);
		}
		System.arraycopy(board, 0, fill.board, 0, board.length);

		return fill;
	}

	public int getWidth()
	{
		return width;
	}

	public int getHeight()
	{
		return height;
	}
	//Returns the block type at the given position
	public Shape getShapeAt(int row, int col)
	{
		return board[(row * width) + col];
	}
	//True if the given piece can move, false if not
	public boolean canMove(Tetromino piece, int xPos, int yPos)
	{
		for (int i = 0; i < piece.getSize(); i++)
		{
			int x = xPos + piece.getX(i);
			int y = yPos - piece.getY(i);

			if (x < 0 || x >= width || y < 0)
			{
				return false;
			}

			if (y < height && getShapeAt(y, x) != Shape.NoShape)
			{
				return false;
			}
		}
		return true;
	}
	//Adds a piece to the board
	public void addPiece(Tetromino piece, int xPos, int yPos)
	{
		fillTetromino(piece, xPos, yPos, piece.getShape());
	}
	//removes a piece from the board
	public void removePiece(Tetromino piece, int xPos, int yPos)
	{
		fillTetromino(piece, xPos, yPos, Shape.NoShape);
	}
	//True if the row given if full, false if not
	public boolean isRowFull(int row)
	{
		for (int col = 0; col < width; col++)
		{
			if (getShapeAt(row, col) == Shape.NoShape)
			{
				return false;
			}
		}
		return true;
	}
	//Return an array of shapes that makeup a given row
	public Shape[] getRow(int row)
	{
		Shape[] shapes = new Shape[width];

		for (int col = 0; col < width; col++)
		{
			shapes[col] = getShapeAt(row, col);
		}
		return shapes;
	}
	//Inserts a given row into the board. All rows that lie above this index will be pushed up by one. 
	//The blocks in the highest row will be pushed off of the board.
	public void addRow(int row, Shape[] shapes)
	{
		if (shapes.length != width)
		{
			throw new IllegalArgumentException("Cannot add row to board with non-matching dimensions.");
		}
		for (int i = height - 1; i > row; i--)
		{
			for (int col = 0; col < width; col++)
			{
				setShapeAt(i, col, getShapeAt(i - 1, col));
			}
		}
		for (int col = 0; col < width; col++)
		{
			setShapeAt(row, col, shapes[col]);
		}
	}
	//Removes a row from the board, updates the other rows
	public void removeRow(int row)
	{
		for (int i = row; i < height - 1; i++)
		{
			for (int col = 0; col < width; col++)
			{
				setShapeAt(i, col, getShapeAt(i + 1, col));
			}
		}
	}

	public int getSpawnX(Tetromino piece)
	{
		return (width - piece.getWidth()) / 2 + Math.abs(piece.getMinX());
	}
	public int getSpawnY(Tetromino piece)
	{
		return height - 1 - piece.getMinY();
	}
	//Determines the height the piece will drop given a hard drop command
	public int dropHeight(Tetromino piece, int xPos)
	{
		return dropHeight(piece, xPos, height);
	}
	
	public int dropHeight(Tetromino piece, int xPos, int yPos)
	{
		int diff = 0;
		
		while (canMove(piece, xPos, yPos - diff))
		{
			diff++;
		}
		return yPos - diff + 1;
	}	
	//True if the piece can fall more, false else
	public boolean isFalling(Tetromino piece, int xPos, int yPos)
	{
		return canMove(piece, xPos, yPos - 1);
	}
	//Updates the block at the current location
	private void setShapeAt(int row, int col, Shape type)
	{
		board[(row * width) + col] = type;
	}
	//Adds the rest of the tetromino at the given position
	private void fillTetromino(Tetromino piece, int xPos, int yPos, Shape shape)
	{
		for (int i = 0; i < piece.getSize(); i++)
		{
			int col = xPos + piece.getX(i);
			int row = yPos - piece.getY(i);

			if (col >= 0 && col < width && row >= 0 && row < height)
			{
				setShapeAt(row, col, shape);
			}
		}
	}
}
