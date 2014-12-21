package Automats;

import CAoT.*;
import Enums.ModeOperation;
import Utils.Utils;
import java.awt.Color;

/**
 *
 * @author Алексей Евсеев
 */
public class CADLA extends Automaton{
    public CADLA (CellularArray ca) {
        super(ca, Color.WHITE, Color.BLUE, Color.GREEN);
    }

    @Override
    public String getName() {
        return "Diffusion-Limited Aggregation";
    }

    @Override
    public ModeOperation getModeOperation() {
        return ModeOperation.asynchronous;
    }

    @Override
    public void rule(Cell cell) {
        Triangle t = ca.getTriangulation().getTriangle(cell.getIndex());
        int numberOfNeighbors = t.getNeighborsCount();

	int status = cell.getState();
	if ((numberOfNeighbors > 0) && (status != 2))
	{
            int neighbInd;
            double rr = Utils.getRandom();

            while (true)
            {
                rr = Utils.getRandom();
                if (rr >= 0. && rr < 1./3. && t.getNeighbor(0) != null) { neighbInd = 0; break; }
                else if (rr >= 1./3. && rr <= 2./3. && t.getNeighbor(1) != null) { neighbInd = 1; break; }
                else if (rr > 2./3. && rr <= 1. && t.getNeighbor(2) != null) { neighbInd = 2; break; }
            }

            int neighbor = t.getNeighbor(neighbInd).getIndex();
            int neighborStatus = ca.getState(neighbor);

            if (neighborStatus != 2)
            {
                ca.setState(cell.getIndex(), neighborStatus);
                ca.setState(neighbor, status);
            }
            else
            if (status == 1) ca.setState(cell.getIndex(), 2);
	}
    }

    @Override
    public void initialState() {
        //blue cells
        ca.generateRandomInitialState(15, 1);
        //green cell
        ca.setState(800, 2);
    }

    @Override
    public boolean canBeAveraged() {
        return false;
    }

    @Override
    public boolean haveProperties() {
        return false;
    }
}
