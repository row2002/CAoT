package Automats;

import CAoT.*;
import Enums.ModeOperation;
import java.util.ArrayList;
import java.util.List;
import javax.vecmath.Point3d;

/**
 *
 * @author Алексей Евсеев
 */
public class CAPatternByCircles extends Automaton{
    private CellularArray data; // copy of Cellular Array
    private List<Double> radiuses;
    private List<Double> weights;
    private int levelsCount;

    public CAPatternByCircles(CellularArray ca, CellularArray copy) {
        super(ca);
        this.data = copy;
        radiuses = new ArrayList<Double>(); radiuses.add(0.);
        weights = new ArrayList<Double>(); weights.add(0.);
        levelsCount = 0;
    }

    public void setLevel(int index, double radius, double weight) {
        if (index > 0 && index == levelsCount+1) {
            radiuses.add(radius); weights.add(weight);
            levelsCount = index;
        }
        else
        if (index > 0 && index <= levelsCount) {
            radiuses.set(index, radius); weights.set(index, weight);
        }
        else
        throw new IndexOutOfBoundsException("CAPatternByCircle.setLevel wrong index "+index);
    }

    public void setLevelsCount(int levelsCount) {
        this.levelsCount = levelsCount;
    }

    @Override
    public String getName() {
        return "Forming patterns by circle";
    }

    @Override
    public ModeOperation getModeOperation() {
        return ModeOperation.synchronous;
    }

    @Override
    public void initialState() {
        this.ca.generateRandomInitialState(50);
    }

    @Override
    public void rule(Cell cell) {
        double R = 0.;
        int trianglesCount = ca.getTriangulation().getTrianglesCount();
        Point3d center = ca.getTriangulation().getTriangle(cell.getIndex()).getMedianCrossing();
        Point3d search;

        if (levelsCount > 0)
        for (int i=0; i<trianglesCount; i++)
        {
            if (ca.getCell(i).getState() != 0) {
                search = ca.getTriangulation().getTriangle(i).getMedianCrossing();
                for (int j=1; j<=levelsCount; j++)
                    if (center.distance(search) <= radiuses.get(j)) { R += weights.get(j); /*ca.getCell(i).setState(j);*/ break; }
            }
        }

        if (R >= 0.) data.getCell(cell.getIndex()).setState(1);
        else
        data.getCell(cell.getIndex()).setState(0);
    }

    @Override
    public boolean haveProperties() { return false; }

    @Override
    public boolean showProperties() {
        return false;
    }
}
