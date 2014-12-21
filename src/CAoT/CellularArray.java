package CAoT;

import Utils.Utils;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Алексей Евсеев
 */
public class CellularArray{
    private List<Cell> cells;
    private static Triangulation triangulation;

    public CellularArray (Triangulation triangulation) {
        CellularArray.triangulation = triangulation;
        cells = new ArrayList<Cell>( CellularArray.triangulation.getTrianglesCount() );
        for (int i=0; i<CellularArray.triangulation.getTrianglesCount(); i++)
            cells.add(new Cell(i));
    }

    public Cell getCell(int index) {
        return cells.get(index);
    }

    public Triangle getTriangle (int index) {
        return triangulation.getTriangle(index);
    }

    public Triangulation getTriangulation() {
        return triangulation;
    }

    public int getState(int index) {
        return cells.get(index).getState();
    }

    public int getState(Triangle triangle) {
        return getState(triangle.getIndex());
    }

    public void setState(int index, int state) {
        cells.get(index).setState(state);
    }

    public void setState(int index, int state, float averageValue) {
        cells.get(index).setState(state);
        cells.get(index).setAverageValue(averageValue);
    }

    public void setState(Triangle trianlge, int state) {
        setState(trianlge.getIndex(), state);
    }

    public void setCell(Cell cell) {
        setState(cell.getIndex(), cell.getState(), cell.getAverageValue());
    }

    public List<Cell> trianglesToCells(List<Triangle> triangles) {
        ArrayList<Cell> result = new ArrayList<Cell> ();
        
        for (Triangle t: triangles)
            result.add(cells.get(t.getIndex()));
        
        return result;
    }
    
    public Cell triangleToCell (Triangle t) {
        return cells.get(t.getIndex());
    }

    public List<Cell> getNearestNeighbors (int trInd, int depth) {
        return trianglesToCells(triangulation.getNearestNeighbors(trInd, depth));
    }

    public List<Cell> getMoorNeighborhood (int trInd) {
        return trianglesToCells(triangulation.getMoorNeighborhood(trInd));
    }

    public void smoothByNearestNeighboors(int depth) {
        int particles = 0;
        ArrayList<Cell> neighborhood = new ArrayList<Cell>(3);

        for (Cell c: cells) {
            particles = 0;
            neighborhood.clear();
            if (c.getState() > 0) particles++;

            for (int curDepth=1; curDepth<=depth; curDepth++)
                neighborhood.addAll(getNearestNeighbors(c.getIndex(), curDepth));

            for (Cell neighbor: neighborhood)
                if (neighbor.getState() > 0) particles++;

            c.setAverageValue((float) particles / (neighborhood.size() + 1));
        }
    }

    public void smoothByCircle(double radius) {
        int numOfTrianglesInCircle = 0;
        int numOfParticles = 0;
        Triangle t1;
        Triangle t2;

        for (Cell c1: cells) {
            numOfTrianglesInCircle = 0;
            numOfParticles = 0;
            t1 = triangulation.getTriangle(c1.getIndex());

            for (Cell c2: cells) {
                t2 = triangulation.getTriangle(c2.getIndex());
                if (radius > t1.getMedianCrossing().distance(t2.getMedianCrossing())) {
                    numOfTrianglesInCircle++;
                    if (c2.getState() > 0) numOfParticles++;
                }
            }

            c1.setAverageValue( (float) numOfParticles / numOfTrianglesInCircle);
        }
    }

    public void makeGlobalDiscretization() {
	double rr = 0;
        for (Cell c: cells) {
            rr = Utils.getRandom();
            if (rr < c.getAverageValue()) c.setState(1);
            else c.setState(0);
        }
    }
    
    public void applyStreamFunction() {
	float u = 0.f;
        for (Cell c: cells) {
            u = c.getAverageValue();
            c.setAverageValue(u + 0.5f*u*(1.f - u));
        }
    }

    public void clear() {
        for (Cell c: cells) {
            c.setState(0);
            c.setAverageValue(0.f);
        }
    }

    public void generateRandomInitialState(int percentage, int state) {
        clear();
        int count = 0;
        int numOfTriangles = triangulation.getTrianglesCount();
        int numberOfParticles = (int) ((percentage / 100.)*numOfTriangles);
        int ind;
        while (count < numberOfParticles)
        {
            while (true)
            {
                ind = (int) (Math.random() * numOfTriangles);
                if (cells.get(ind).getState() == 0) break;
            }
            cells.get(ind).setState(state);
            count++;
        }
    }

    public void generateRandomInitialState(int percentage) {
        generateRandomInitialState(percentage, 1);
    }

    public void testInitialState() {
        int horiz = 50/*40*/, half = CellularArray.triangulation.getTrianglesCount()/2;
        int l = 9, start = horiz/2 - l/2;
        horiz--; start--;

        for (int i1=start; i1<=start+l; i1++)
          for (int i2=start; i2<=start+l; i2++)
            cells.get(i1*horiz + i2).setState(1);

        for (int i1=start; i1<=start+l; i1++)
          for (int i2=start; i2<=start+l; i2++)
            cells.get(i1*horiz + i2 + half).setState(1);
    }

    @Override
    public String toString () {
        StringBuilder sb = new StringBuilder();
        for (Cell c: cells)
            sb.append(c);

        return sb.toString();
    }

    public CellularArray copy()
    {
        CellularArray result = new CellularArray(triangulation);
        for(Cell c: cells)
            result.setCell(c);

        return result;
    }

    public void copy(CellularArray cellularArray) {
        Cell curCell;

        for (Cell c: cells) {
            curCell = cellularArray.getCell(c.getIndex());
            c.setState(curCell.getState());
            c.setAverageValue(curCell.getAverageValue());
        }
    }

    public void inverse() {
        for (Cell c: cells) {
            if (c.getState() == 0) c.setState(1);
            else
            if (c.getState() == 1) c.setState(0);
        }
    }
}
