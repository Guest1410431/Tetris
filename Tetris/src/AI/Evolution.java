package AI;
//The brain of the operation
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Scanner;

public class Evolution
{
	private final static String filename = "aiscores.txt";

	private final int populationSize = 16;
	private final double elitePercent = 1 / 4.0;
	private final double mutationRate = 1 / 10.0;

	private int current = 0;
	private int generation = 1;
	
	Long[] scores = new Long[populationSize];
	Weights[] population = new Weights[populationSize];

	private ScoringSystem scoring;
	//Creates a new Evolution
	public Evolution(ScoringSystem scoring)
	{
		this.scoring = scoring;
		//Reads in previous generations from file
		try (Scanner scanner = new Scanner(new BufferedReader(new FileReader(filename))))
		{
			for (int i = 0; i < populationSize; i++)
			{
				double[] weights = new double[8];

				for (int j = 0; j < weights.length; j++)
				{
					weights[j] = scanner.nextDouble();
				}
				population[i] = new Weights(weights);
				scanner.nextLine();
			}
		}
		catch (FileNotFoundException e) //If no data is found/no data given
		{
			System.out.println("Population data not found - generating random population.");

			for (int i = 0; i < populationSize; i++)
			{
				double[] weights = new double[8];

				for (int j = 0; j < weights.length; j++)
				{
					weights[j] = Math.random() * 10 - 5;
				}

				population[i] = new Weights(weights);
			}
		}
	}

	//Apply the next chromosome to the scoring system.
	public void updateScoring()
	{
		scoring.setWeights(population[current]);
	}
	//Records the score of the game given the current weights
	public void submit(long score)
	{
		System.out.printf("Trial %-2d - Run %-2d: score = %d\n", generation, current + 1, score);

		scores[current++] = score;

		if (current == populationSize)
		{
			newGeneration();
		}
	}
	//Creates a new generation based off the current generation
	private void newGeneration()
	{
		int sum = 0;
		
		Integer[] idx = new Integer[populationSize];

		for (int i = 0; i < populationSize; i++)
		{
			idx[i] = i;
		}
		for(int i=0; i<scores.length; i++)
		{
			sum += scores[i];
		}
		Arrays.sort(idx, (i, j) -> Double.compare(scores[j], scores[i]));
		
		System.out.printf("Trial %-2d - max = %d, min = %d med = %d avg = %d\n", generation, scores[idx[0]], scores[idx[populationSize - 1]], scores[idx[populationSize / 2]], sum/populationSize);
		System.out.printf("\n");

		Weights[] newPopulation = new Weights[populationSize];

		for (int i = 0; i < populationSize; i++)
		{
			if (i < populationSize * elitePercent)
			{
				newPopulation[i] = population[idx[i]];
			}
			else
			{
				int w1 = (int) (Math.random() * (populationSize / 2));
				int w2 = (int) (Math.random() * (populationSize / 2));

				double[] child = new double[8];

				for (int j = 0; j < child.length; j++)
				{
					child[j] = population[idx[Math.random() < .5 ? w1 : w2]].getWeights()[j];

					if (Math.random() < mutationRate)
					{
						child[j] = Math.random() * 10 - 5;
					}
				}
				newPopulation[i] = new Weights(child);
			}
		}
		population = newPopulation;

		current = 0;
		generation++;
		//Writes the weights to the file
		try (FileWriter writer = new FileWriter(filename))
		{
			for (Weights weights : population)
			{
				writer.write(weights + "\n");
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public void reset()
	{
		try
		{
			Files.delete(new File(filename).toPath());
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		System.out.println("Network Reset");
	}
}
