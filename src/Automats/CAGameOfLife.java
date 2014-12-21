package Automats;

import CAoT.*;
import Enums.ModeOperation;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Алексей Евсеев
 */
public class CAGameOfLife extends Automaton{
    private CellularArray data; // copy of Cellular Array
    private int weightsLive[];
    private int weightsBorn[];

    public CAGameOfLife(CellularArray ca, CellularArray copy, int weightsLive[], int weightsBorn[]) {
        super(ca);
        this.data = copy;
        this.weightsLive = new int[10];
        this.weightsBorn = new int[10];
        for (int i=0; i<10; i++)
            this.weightsLive[i] = -1;
        for (int i=0; i<10; i++)
            this.weightsBorn[i] = -1;

        System.arraycopy(weightsLive, 0, this.weightsLive, 0, weightsLive.length);
        System.arraycopy(weightsBorn, 0, this.weightsBorn, 0, weightsBorn.length);
    }

    public void setWeightLive(int index, int value) {
        if (index >=0 && index < 10)
            weightsLive[index] = value;
        else
            System.out.println("Error: CAGameOfLife.setWeightLive wrong weight index (should be in [0,9])");
    }

    public void setWeightBorn(int index, int value) {
        if (index >=0 && index < 10)
            weightsBorn[index] = value;
        else
            System.out.println("Error: CAGameOfLife.setWeightBorn wrong weight index (should be in [0,9])");
    }

    public double getWeightLive(int index) {
        if (index >=0 && index <= weightsLive.length) return weightsLive[index];
        else
        {
             System.out.println("Error: CAGameOfLife.getWeightLive wrong weight index "+index);
             return 0.;
        }
    }

    public double getWeightBorn(int index) {
        if (index >=0 && index <= weightsBorn.length) return weightsBorn[index];
        else
        {
             System.out.println("Error: CAGameOfLife.getWeightBorn wrong weight index "+index);
             return 0.;
        }
    }

    @Override
    public String getName() {
        return "Game Of Life";
    }

    @Override
    public ModeOperation getModeOperation() {
        return ModeOperation.synchronous;
    }

    @Override
    public void initialState() {
        this.ca.generateRandomInitialState(50);

//        Planer
//        int half = this.ca.getTriangulation().getTrianglesCount()/2;
//        this.ca.setState(251, 1); this.ca.setState(250, 1);
//        this.ca.setState(251 + half, 1); this.ca.setState(252 + half, 1);
//        this.ca.setState(300, 1); this.ca.setState(299, 1);
    }

    @Override
    public void rule(Cell cell) {
        int R = 0;
        int cellInd = cell.getIndex();

        List<Cell> moor = this.ca.getMoorNeighborhood(cellInd);
        for (Cell c: moor)
            R += c.getState();

        for (int weightLive: weightsLive)
            if ((cell.getState() == 1) && (R == weightLive)) { this.data.setState(cellInd, 1); return; }
        for (int weightBorn: weightsBorn)
            if ((cell.getState() == 0) && (R == weightBorn)) { this.data.setState(cellInd, 1); return; }

        this.data.setState(cellInd, 0);
    }
}
