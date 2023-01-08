import javax.swing.*;

public class Greedy {
	// Input values
	private int noItems;
	private double[] values;
	private double[] weights;
	private double knapsackSize;

	// Output values
	private double maxValue;
	private double maxWeight;
	public long timeSpent;
	public boolean isDivided;
	public JTextArea textarea;

	public Greedy(int noItems, double[] values, double[] weights, double knapsackSize, boolean isDivided,
			JTextArea textarea) {
		this.noItems = noItems;
		this.values = values;
		this.weights = weights;
		this.knapsackSize = knapsackSize;
		this.isDivided = isDivided;
		this.textarea = textarea;
		this.textarea.setText("");
		long start = System.nanoTime();
		getSolution();
		long end = System.nanoTime();
		timeSpent = end - start;
	}

	// get the solution
	public void getSolution() {
		double[] ratio = new double[noItems];
		int[] index = new int[noItems];
		// measure the ration of the elements
		for (int i = 0; i < ratio.length; i++) {
			ratio[i] = (double) values[i] / weights[i];
			index[i] = i;
		}

		// sort the ratio array on descending order
		for (int i = 0; i < ratio.length - 1; i++) {
         // check if swapping occurs
         boolean swapped = false;
			for (int j = 0; j < ratio.length - 1 - i; j++) {
				if (ratio[j] < ratio[j + 1]) {
					double temp = ratio[j];
					ratio[j] = ratio[j + 1];
					ratio[j + 1] = temp;

					int temp1 = index[j];
					index[j] = index[j + 1];
					index[j + 1] = temp1;
               
               // swapping occurs if elements are not in the intended order
               swapped = true;
				}
			}
         if (!swapped) {
            break;
         }
		}

		// calculate the solution with the ration array
		textarea.append(" " + "Knapsack Size: " + knapsackSize + "\n");
		for (int i = 0; i < index.length; i++) {
			if (maxWeight + weights[index[i]] <= knapsackSize) {
				textarea.append("Total weight: " + maxWeight + "  ");
				textarea.append("Current Item weight: " + weights[index[i]] + "\n");

				maxWeight += weights[index[i]];
				maxValue += values[index[i]];

			} else if (isDivided && maxWeight + weights[index[i]] > knapsackSize && maxWeight < knapsackSize) {
				textarea.append("Total weight: " + maxWeight + "  ");
				textarea.append("Current Item weight: " + weights[index[i]] + "\n");

				maxValue += values[index[i]] / (weights[index[i]] / (knapsackSize - maxWeight));
				maxWeight += knapsackSize - maxWeight;

			}
		}

		textarea.append("Total weight: " + maxWeight + "\n");
		textarea.append("Max Value: " + maxValue + "\n");

	}
}