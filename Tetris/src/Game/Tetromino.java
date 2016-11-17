package Game;
//Tetromino is the name of the pieces of tetris
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Tetromino
{
	//Map of the 7 pre-constructed piece. The points are the 4 points that make up a tetromino and their relation to the piece's origin
	public static Map<Shape, Tetromino> tetrominoes = new HashMap<>();

	static
	{
		tetrominoes.put(Shape.I, new Tetromino(Shape.I, new Point(-2, +0), new Point(-1, +0), new Point(+0, +0), new Point(+1, +0)));
		tetrominoes.put(Shape.J, new Tetromino(Shape.J, new Point(+1, +1), new Point(+1, +0), new Point(+0, +0), new Point(-1, +0)));
		tetrominoes.put(Shape.L, new Tetromino(Shape.L, new Point(-1, +1), new Point(-1, +0), new Point(+0, +0), new Point(+1, +0)));
		tetrominoes.put(Shape.O, new Tetromino(Shape.O, new Point(+0, +0), new Point(+1, +0), new Point(+0, +1), new Point(+1, +1)));
		tetrominoes.put(Shape.S, new Tetromino(Shape.S, new Point(+1, +0), new Point(+0, +0), new Point(+0, +1), new Point(-1, +1)));
		tetrominoes.put(Shape.T, new Tetromino(Shape.T, new Point(-1, +0), new Point(+0, +0), new Point(+1, +0), new Point(+0, +1)));
		tetrominoes.put(Shape.Z, new Tetromino(Shape.Z, new Point(+0, +0), new Point(-1, +0), new Point(+1, +1), new Point(+0, +1)));
	}

	//a lazily-filled cache of the clockwise-rotation of tetrominoes.
	private static Map<Tetromino, Tetromino> rotationCache = new HashMap<>();

	private static Comparator<Point> xComparator = (p1, p2) -> p1.x - p2.x;

	private static Comparator<Point> yComparator = (p1, p2) -> p1.y - p2.y;

	private Shape shape;
	private List<Point> points;

	private Tetromino(Shape shape, Point... points)
	{
		this.shape = shape;
		this.points = Arrays.asList(points);
	}

	public Shape getShape()
	{
		return shape;
	}

	public int getSize()
	{
		return points.size();
	}
	public int getX(int i)
	{
		return points.get(i).x;
	}
	public int getY(int i)
	{
		return points.get(i).y;
	}

	public int getMinX()
	{
		return Collections.min(points, xComparator).x;
	}

	public int getMaxX()
	{
		return Collections.max(points, xComparator).x;
	}

	public int getMinY()
	{
		return Collections.min(points, yComparator).y;
	}

	public int getMaxY()
	{
		return Collections.max(points, yComparator).y;
	}

	public int getWidth()
	{
		return Math.abs(getMinX()) + Math.abs(getMaxX()) + 1;
	}

	public int getHeight()
	{
		return Math.abs(getMinY()) + Math.abs(getMaxY()) + 1;
	}
	//Rotation simply removes the current tertromino and replaces it with a new, rotated one
	public static Tetromino rotateClockwise(Tetromino original)
	{
		if (original.shape == Shape.O)
		{
			return original;
		}

		if (!rotationCache.containsKey(original))
		{
			Point[] points = new Point[original.getSize()];

			int i = 0;
			for (Point p : original.points)
			{
				points[i++] = new Point(-p.y, p.x);
			}

			rotationCache.put(original, new Tetromino(original.shape, points));
		}

		return rotationCache.get(original);
	}

	public static Tetromino rotateCounterClockwise(Tetromino original)
	{
		return rotateClockwise(rotateClockwise(rotateClockwise(original)));
	}

	public int hashCode()
	{
		int result = shape.hashCode();

		for (int i = 0; i < this.getSize(); i++)
		{
			result = 31 * result + this.getX(i);
			result = 31 * result + this.getY(i);
		}

		return result;
	}

	public boolean equals(Object obj)
	{
		if (this == obj)
		{
			return true;
		}

		if (obj == null || this.getClass() != obj.getClass())
		{
			return false;
		}

		Tetromino other = (Tetromino) obj;

		if (this.shape != other.shape || this.getSize() != other.getSize())
		{
			return false;
		}

		for (int i = 0; i < getSize(); i++)
		{
			if (this.getX(i) != other.getX(i) || this.getY(i) != other.getY(i))
			{
				return false;
			}
		}
		return true;
	}

	private static class Point
	{
		public int x;
		public int y;

		public Point(int x, int y)
		{
			this.x = x;
			this.y = y;
		}
	}
}
