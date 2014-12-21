package Utils;

import java.util.*;

/**
 *
 * @author Алексей Евсеев
 */
public class DiscreteDistribution {
    private ArrayList<Double> probabilities;
    private ArrayList<Double> intervals;
    private double sum;
    private int probabilitiesCount;

    public DiscreteDistribution() {
        probabilities = new ArrayList<Double>();
        intervals = new ArrayList<Double>();
        sum = 0.;
        probabilitiesCount = 0;
    }

    public void addProbability(double value) {
        probabilities.add(value);
        sum += value;
    }

    public void init() {
        probabilitiesCount = probabilities.size() + 1;
        intervals.ensureCapacity(probabilitiesCount);

        intervals.add(0, 0.);
        for (int i=0; i<probabilities.size(); i++)
            intervals.add(i+1, intervals.get(i)+probabilities.get(i)/sum);

        probabilities.clear();
    }

    public int getIndexByBinSearch() {
        int leftBorder = 0, rightBorder = probabilitiesCount, center;
        double randomNumber = Math.random();

        if (randomNumber == 1.0) return probabilitiesCount-1;
        while (true) {
            center = (leftBorder+rightBorder)/2;
            if (randomNumber >= intervals.get(center)) {
                if (randomNumber <= intervals.get(center+1)) return center;
                else leftBorder = center;
            }
            else {
                if (randomNumber >= intervals.get(center-1)) return center-1;
                else rightBorder = center;
            }
        }
    }

}
