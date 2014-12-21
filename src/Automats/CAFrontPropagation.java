package Automats;

import CAoT.*;
import Enums.AverageType;
import Enums.ModeOperation;

/**
 *
 * @author Алексей Евсеев
 */
public class CAFrontPropagation extends Automaton{
    CADiffusion diffusion;

    public CAFrontPropagation (CellularArray ca) {
        super (ca);
        this.averageType = AverageType.none;
        diffusion = new CADiffusion(ca);
    }

    @Override
    public String getName() {
        return "Front Propagation";
    }

    @Override
    public ModeOperation getModeOperation() {
        return ModeOperation.other;
    }

    @Override
    public void rule (Cell cell) {
        int index = 0;
        //Global Diffusion
        for (int i=0; i<ca.getTriangulation().getTrianglesCount(); i++) {
            index = (int) (Math.random()*ca.getTriangulation().getTrianglesCount());
            diffusion.rule(ca.getCell(index));
        }
        ca.smoothByNearestNeighboors(1);
        ca.applyStreamFunction();
        ca.makeGlobalDiscretization();
    }

    @Override
    public void initialState () {
        ca.testInitialState();
    }

    @Override
    public boolean canBeAveraged() {
        return true;
    }
}
