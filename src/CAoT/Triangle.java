package CAoT;

import javax.media.j3d.TriangleArray;
import javax.vecmath.Point3d;

/**
 *
 * @author Алексей Евсеев
 */
public class Triangle extends TriangleArray{
    private Vertex vertexes[] = new Vertex[3];
    private Point3d medianCrossing = new Point3d();
    private int neighborsCount = 0;
    private Triangle neighbors[] = {null, null, null};
    private int index = -1;

    public Triangle(Vertex v1, Vertex v2, Vertex v3, int index) {
        super(3, TriangleArray.COORDINATES);
        vertexes[0] = v1;
        vertexes[1] = v2;
        vertexes[2] = v3;
        this.index = index;
        calculateMedianCrossing();
        super.setCoordinate(0, v1);
        super.setCoordinate(1, v2);
        super.setCoordinate(2, v3);
    }

    public Triangle (Triangle t) {
        super(3, TriangleArray.COORDINATES);
        for (int i=0; i<3; i++) {
            vertexes[i] = t.getVertex(i);
            super.setCoordinate(i, t.getVertex(i));
        }
        this.index = t.getIndex();
        this.medianCrossing = t.getMedianCrossing();
    }

    private void calculateMedianCrossing() {
        double x = ( vertexes[0].getX() + vertexes[1].getX() + vertexes[2].getX() ) / 3.0;
        double y = ( vertexes[0].getY() + vertexes[1].getY() + vertexes[2].getY() ) / 3.0;
        double z = ( vertexes[0].getZ() + vertexes[1].getZ() + vertexes[2].getZ() ) / 3.0;
        medianCrossing.set(x, y, z);
    }

    public int getIndex () {
        return index;
    }

    public int getNeighborsCount() {
        return neighborsCount;
    }

    public Triangle getNeighbor(int index) {

        return neighbors[index];
    }

    public Point3d getMedianCrossing() {
        return medianCrossing;
    }

    public Vertex getVertex(int index) {
        return vertexes[index];
    }

    public void addNeighbor( Triangle neighbor ) {
        neighbors[neighborsCount++] = neighbor;
    }

    public int getVertexIndex (int index ) {
        return vertexes[index].getIndex();
    }

    public double getArea () {
        double l1, l2, l3, p;
        l1 = vertexes[0].distance(vertexes[1]);
        l2 = vertexes[0].distance(vertexes[2]);
        l3 = vertexes[1].distance(vertexes[2]);
        p = (l1 + l2 + l3) / 2.0;
        return Math.sqrt(p*(p-l1)*(p-l2)*(p-l3));
    }

    @Override
    public String toString () {
        StringBuilder sb = new StringBuilder("Triangle ");
        sb.append(this.index);
        sb.append("\n   Vertex1: ").append(this.getVertex(0));
        sb.append("\n   Vertex2: ").append(this.getVertex(1));
        sb.append("\n   Vertex3: ").append(this.getVertex(2));
        sb.append("\n  Area = ").append(this.getArea());
        sb.append("\n \n  NeighborsCount = ").append(this.getNeighborsCount());

        for (int i=0; i<3; i++)
            if (neighbors[i] != null) sb.append("\n  Neighbor").append(i+1).append(": ").append(neighbors[i].getIndex());
        return sb.toString();
    }
}
