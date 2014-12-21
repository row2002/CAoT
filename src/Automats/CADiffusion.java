package Automats;

import CAoT.*;
import Enums.*;
import Utils.Utils;

/**
 *
 * @author Алексей Евсеев
 */
public class CADiffusion extends Automaton{

    public CADiffusion(CellularArray ca, double radius, int depth) {
        super(ca);
        this.averageType = AverageType.nearestNeighbors;
        this.radius = radius;
        this.depth = depth;
    }

    public CADiffusion(CellularArray ca) {
        this(ca, 20., 1);
    }

    @Override
    public String getName() {
        return "Simple Diffusion";
    }

    @Override
    public ModeOperation getModeOperation() {
        return ModeOperation.asynchronous;
    }

    @Override
    public void initialState() {
        //ca.testInitialState();
        //ca.inverse();////////////////////////////////////////////////////
    }

    @Override
    public void rule(Cell cell) {
        Triangle t = ca.getTriangulation().getTriangle(cell.getIndex());
        int numberOfNeighbors = t.getNeighborsCount();
        if (numberOfNeighbors > 0)
        {
            int status = cell.getState();
            double rr = Utils.getRandom();
            int neighbInd;

            while (true)
            {
                rr = Utils.getRandom();
                if (rr >= 0. && rr < 1./3. && t.getNeighbor(0) != null) { neighbInd = 0; break; }
                else if (rr >= 1./3. && rr <= 2./3. && t.getNeighbor(1) != null) { neighbInd = 1; break; }
                else if (rr > 2./3. && rr <= 1. && t.getNeighbor(2) != null) { neighbInd = 2; break; }
            }

            int neighbor = t.getNeighbor(neighbInd).getIndex();
            ca.setState(cell.getIndex(), ca.getState(neighbor));
            ca.setState(neighbor, status);
        }
    }

    @Override
    public boolean canBeAveraged() {
        return true;
    }

    @Override
    public boolean haveProperties() {
        return true;
    }

    @Override
    public boolean showProperties() {
        boolean result = AverageProperties.showDialog(this); // clicked Ok or  Cancel

        // averaging after changing average parametrs
        switch (averageType) {
            case nearestNeighbors:
                ca.smoothByNearestNeighboors(depth);
                break;
            case circle:
                ca.smoothByCircle(radius);
                break;
        }
        return result;
    }
}
