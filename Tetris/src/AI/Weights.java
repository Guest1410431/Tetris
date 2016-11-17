package AI;
//A weight is how the network determines the importance of a certain gene
public class Weights
{
	private double[] weights;

	public Weights(double[] weights)
	{
		this.weights = weights;
	}

	public double[] getWeights()
	{
		return weights;
	}

	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		
		for (int i = 0; i < weights.length; i++)
		{
			if (i != 0)
			{
				sb.append(" ");
			}
			sb.append(weights[i]);
		}
		return sb.toString();
	}
}
