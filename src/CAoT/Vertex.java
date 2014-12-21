package CAoT;

import javax.vecmath.Point3d;

/**
 *
 * @author Алексей Евсеев
 */
public class Vertex extends Point3d{
    private int index;

    public Vertex() {
        super();
        index = -1;
    }

    public Vertex(double x, double y, double z, int index) {
        super(x, y, z);
        this.index = index;
    }

    public Vertex(Point3d p, int index) {
        super(p);
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    @Override
    public String toString () {
        return "Point(" + this.getX() + ", " + this.getY() + ", " + this.getZ() + ", " + this.index + ")";
    }
}
