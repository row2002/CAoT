package CAoT;

import Utils.DiscreteDistribution;
import java.lang.IllegalArgumentException;
import java.util.*;
import java.util.ArrayList;

/**
 *
 * @author Алексей Евсеев
 */
public class Triangulation {
    private List<Vertex> vertexes;
    private List<Triangle> triangles;
    private DiscreteDistribution triangleChooser;

    public Triangulation (List<Vertex> vertexes, List<Triangle> triangles) {
        this.vertexes = vertexes;
        this.triangles = triangles;
        triangleChooser = new DiscreteDistribution();
    }

    public int getVertexCount() {
        return vertexes.size();
    }

    public int getTrianglesCount() {
        return triangles.size();
    }

    public Triangle getTriangle (int index) {
        return triangles.get(index);
    }
    
    public Vertex getVertex (int index) {
        return vertexes.get(index);
    }

    public void fillTriangleChooser() {
        for (Triangle t: triangles)
            triangleChooser.addProbability(1. / t.getArea());
        triangleChooser.init();
    }

    public int getNextRandomTriangle() {
        return triangleChooser.getIndexByBinSearch();
    }

    public List<Triangle> getBorder() {
        ArrayList<Triangle> result = new ArrayList<Triangle>();
        for (Triangle t: triangles)
            if (t.getNeighborsCount() < 3) result.add(t);
        return result;
    }
    
    private boolean checkTriangleWithVertex(int vertexIndex, Triangle tr) {
        int v1, v2, v3;
        v1 = tr.getVertexIndex(0);
        v2 = tr.getVertexIndex(1);
        v3 = tr.getVertexIndex(2);
        
        if (v1 != vertexIndex && v2 != vertexIndex && v3 != vertexIndex) {
            return false;
        }
        else
        return true;
    }
    
    private Triangle findNextTriangleInBlock(int vertexIndex, Triangle tr1, Triangle tr2) {
        Triangle neighbor;
        for (int i=0; i<3; i++) {
            neighbor = tr2.getNeighbor(i);
            if (neighbor != tr1 && checkTriangleWithVertex(vertexIndex, neighbor)) return neighbor;
        }
        return null;
    }

    public List<Triangle> getBlock(int vertexIndex, Triangle tr) {
        if (!checkTriangleWithVertex(vertexIndex, tr)) {
            throw new IllegalArgumentException("Triangulation:getBlock() traingle tr doesn't contain vertex vertexIndex");
        }
        else
        {
            ArrayList<Triangle> result = new ArrayList<Triangle>();
            result.add(tr);
            Triangle neighbor = null, nextNeighbor = null, startTriangle = tr;
            for (int i=0; i<3; i++) {
                neighbor = tr.getNeighbor(i);
                if (checkTriangleWithVertex(vertexIndex, neighbor)) break;
            }
            result.add(neighbor);

            while (nextNeighbor != startTriangle) {
                nextNeighbor = findNextTriangleInBlock(vertexIndex, tr, neighbor);
                result.add(nextNeighbor);
                tr = neighbor;
                neighbor = nextNeighbor;
            }
            
            return result;
        }
    }

    public List<Triangle> getBlock(int vertexIndex) {
        int v1, v2, v3;
        for (Triangle t: triangles) {
            v1 = t.getVertexIndex(0);
            v2 = t.getVertexIndex(1);
            v3 = t.getVertexIndex(2);
            if (v1 == vertexIndex || v2 == vertexIndex || v3 == vertexIndex) {
                return getBlock(vertexIndex, t);
            }
        }
        return null;
    }

    public List<Triangle> getNearestNeighbors(int trInd, int depth) {
        ArrayList<Triangle> oldLevel = new ArrayList<Triangle>();
        ArrayList<Triangle> curLevel = new ArrayList<Triangle>();
        ArrayList<Triangle> nextLevel = new ArrayList<Triangle>();
        Triangle tr;

        oldLevel.add(triangles.get(trInd));
        curLevel.add(triangles.get(trInd));

        for (int curDepth=1; curDepth<=depth; curDepth++)
        {
            for (int j=0; j<curLevel.size(); j++)
            {
                tr = curLevel.get(j);
                for (int i=0; i<tr.getNeighborsCount(); i++)
                    if (!nextLevel.contains(tr.getNeighbor(i)) && !curLevel.contains(tr.getNeighbor(i)) && !oldLevel.contains(tr.getNeighbor(i)))
                      nextLevel.add(tr.getNeighbor(i));
            }
            if (curDepth < depth) {
                oldLevel.clear();
                oldLevel.addAll(curLevel);
                curLevel.clear();
                curLevel.addAll(nextLevel);
                nextLevel.clear();
            }
        }

        return nextLevel;
    }
    
    private boolean compareTrianglesVertexes(Triangle tr1, Triangle tr2) {
        int v1, v2;
        for (int i=0; i<3; i++) {
            v1 = tr1.getVertexIndex(i);
            
            for(int j=0; j<3; j++) {
                v2 = tr2.getVertexIndex(j);
                
                if (v1 == v2) return true;
            }
        }
        
        return false;
    }

    public ArrayList<Triangle> getMoorNeighborhood(int trInd) {
        ArrayList<Triangle> result = new ArrayList<Triangle>(12);
        List<Triangle> nearestNeighbors = getNearestNeighbors(trInd, 1);
        List<Triangle> nearestNeighbors2 = getNearestNeighbors(trInd, 2);
        List<Triangle> nearestNeighbors3 = getNearestNeighbors(trInd, 3);
        result.addAll(nearestNeighbors);
        result.addAll(nearestNeighbors2);

        Triangle center = triangles.get(trInd);
        for (Triangle t: nearestNeighbors3) {
            if (compareTrianglesVertexes(center, t)) result.add(t);
        }

        return result;
    }

    public void calculateNeighbors () {
        NearestTriangles nearestTriangles[] = new NearestTriangles[this.getVertexCount()];
        for(int i=0; i<this.getVertexCount(); i++)
            nearestTriangles[i] = new NearestTriangles();

        int v0, v1, v2;

        for (Triangle t: triangles) {
            v0 = t.getVertexIndex(0);
            v1 = t.getVertexIndex(1);
            v2 = t.getVertexIndex(2);

            nearestTriangles[v0].add(t);
            nearestTriangles[v1].add(t);
            nearestTriangles[v2].add(t);
        }

        for (Triangle t: triangles) {
            v0 = t.getVertexIndex(0);
            v1 = t.getVertexIndex(1);
            v2 = t.getVertexIndex(2);

            //v0 with v1
            for (int i=0; i < nearestTriangles[v0].size(); i++)
            {
                if (nearestTriangles[v1].contains(nearestTriangles[v0].get(i)) && nearestTriangles[v0].get(i).getIndex() != t.getIndex())
                {
                    t.addNeighbor(nearestTriangles[v0].get(i));
                    break;
                }
            }

            //v0 with v2
            for (int i=0; i < nearestTriangles[v0].size(); i++)
            {
                if (nearestTriangles[v2].contains(nearestTriangles[v0].get(i)) && nearestTriangles[v0].get(i).getIndex() != t.getIndex())
                {
                    t.addNeighbor(nearestTriangles[v0].get(i));
                    break;
                }
            }

            //v1 with v2
            for (int i=0; i < nearestTriangles[v1].size(); i++)
            {
                if (nearestTriangles[v2].contains(nearestTriangles[v1].get(i)) && nearestTriangles[v1].get(i).getIndex() != t.getIndex())
                {
                    t.addNeighbor(nearestTriangles[v1].get(i));
                    break;
                }
            }
        }
    }

    @Override
    public String toString () {
        StringBuilder sb = new StringBuilder("Triangulation \n" + "  VertexCount = " + this.getVertexCount() + "\n  TrianglesCount = " + this.getTrianglesCount() + "\n \n");
        for (Triangle t: triangles)
            sb.append(t).append("\n \n");
        return sb.toString();
    }
}
