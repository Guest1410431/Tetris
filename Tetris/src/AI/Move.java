package AI;
//The Move is how much the piece needs to rotate or move 
public class Move
{
	private double score;
	private int rDelta = 0;
	private int mDelta = 0;

	public Move(double score, int rotationDelta, int movementDelta)
	{
		this.score = score;
		this.rDelta = rotationDelta;
		this.mDelta = movementDelta;
	}

	public double getScore()
	{
		return score;
	}

	public int getRotationDelta()
	{
		return rDelta;
	}

	public int getMovementDelta()
	{
		return mDelta;
	}
}
