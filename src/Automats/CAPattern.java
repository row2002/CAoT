package Automats;

import CAoT.*;
import Enums.ModeOperation;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Алексей Евсеев
 */
public class CAPattern extends Automaton{
    private CellularArray data; // copy of Cellular Array
    private double weights[];
    private int levelsCount;

    public CAPattern(CellularArray ca, CellularArray copy, double ... weights) {
        super(ca, new Color(255, 255, 248), new Color(28, 32, 31));
        this.data = copy;
        this.weights = new double[10];
        this.levelsCount = Math.min(9, weights.length-1);
        System.arraycopy(weights, 0, this.weights, 0, levelsCount+1);
    }

    public void setWeight(int index, double value) {
        if (index >=0 && index < 10) {
            weights[index] = value;
            if (index > levelsCount) levelsCount = index;
        }
        else
        throw new IndexOutOfBoundsException("CAPattern.setWeight wrong weight index (should be in [0,9])");
    }

    public int getLevelsCount() {
        return levelsCount;
    }

    public void setLevelsCount(int levelsCount) {
        this.levelsCount = levelsCount;
    }

    public double getWeight(int index) {
        if (index >=0 && index <= levelsCount) return weights[index];
        else
        throw new IndexOutOfBoundsException("CAPattern.getWeight wrong weight index "+index);
    }

    @Override
    public String getName() {
        return "Forming patterns";
    }

    @Override
    public ModeOperation getModeOperation() {
        return ModeOperation.synchronous;
    }

    @Override
    public void initialState() {
        this.ca.generateRandomInitialState(3);
    }

    @Override
    public void rule(Cell cell) {
        double R = 0.;
        int cellInd = cell.getIndex();

        if (cell.getState() > 0) R += weights[0];
        for (int i=1; i<=levelsCount; i++) {
            List<Cell> level = this.ca.getNearestNeighbors(cellInd, i);
            for (int j=0; j<level.size(); j++)
                if (level.get(j).getState() > 0) R += weights[i];
        }

        if ( R > 0 ) this.data.setState(cellInd, 1);///////////////////////////////////////////////////
        else
        this.data.setState(cellInd, 0);
    }

    @Override
    public boolean haveProperties() { return true; }

    @Override
    public boolean showProperties() {
        return CAPatternProperties.showDialog(this); // clicked Ok or  Cancel
    }
    
}