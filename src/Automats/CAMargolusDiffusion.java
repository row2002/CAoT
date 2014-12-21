package Automats;

import CAoT.*;
import Enums.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.vecmath.Point3d;

/**
 *
 * @author Алексей Евсеев
 */
public class CAMargolusDiffusion extends Automaton{
    private double probability;

    public void setProbability(double probability) {
        this.probability = probability;
    }

    public double getProbability() {
        return probability;
    }
    
    public CAMargolusDiffusion(CellularArray ca, double probability) {
        super(ca);
        this.averageType = AverageType.circle;
        setRadius(20.);
        this.probability = probability;
    }

    public CAMargolusDiffusion(CellularArray ca) {
        this(ca, 0.5);
    }

    @Override
    public String getName() {
        return "Margolus Diffusion";
    }

    @Override
    public ModeOperation getModeOperation() {
        return ModeOperation.other;
    }

    @Override
    public void initialState() {
        int l = 20;
        int horiz = 150/*150*/, y = (horiz/2-(l/2)) - 1, endY = (horiz/2 + (l/2));
        int x = (horiz/2 - l/2) - 1, endX = (horiz/2 + l/2) - 1, cx, cy;

        for (int j=y; j<=endY; j++) {
            for (int i=x; i<=endX; i++) {
                cy = j*horiz*4; cx = i*4;
                for (int k=0; k<4; k++)
                    ca.setState(cy+cx+k, 1, 1.f);
            }
        }
    }

    private Triangle clockwise(int trInd, int vertexInd, Triangle tr1, Triangle tr2) {
        Point3d center = ca.getTriangle(trInd).getVertex(vertexInd);
        Point3d m1 = tr1.getMedianCrossing();
        Point3d m2 = tr2.getMedianCrossing();

        if (m1.getY() >= center.getY() && m2.getY() >= center.getY()) {
          if (m1.getX() > m2.getX()) return tr1;
          else return tr2;
        }
        else if (m1.getY() < center.getY() && m2.getY() < center.getY()) {
          if (m1.getX() > m2.getX()) return tr2;
          else return tr1;
        }
        else if (m1.getX() >= center.getX() && m2.getX() >= center.getX())
        {
            if (m1.getY() > m2.getY()) return tr2;
            else return tr1;
        }
        else if (m1.getX() < center.getX() && m2.getX() < center.getX())
        {
            if (m1.getY() > m2.getY()) return tr1;
            else return tr2;
        }

        System.out.println("CAMargolusDiffusion.Clockwise: Случилось непредвиденное!!! Обратитесь к разработчику!");
        return null;
    }

    private Triangle getNextTriangleInBlock(List<Triangle> block, Triangle tr1, Triangle tr2) {
        // Ищем следующий треугольник в блоке после tr2, предыдущий tr1

        Triangle n0 = tr2.getNeighbor(0);
        Triangle n1 = tr2.getNeighbor(1);
        Triangle n2 = tr2.getNeighbor(2);

        // зануляем того соседа, которого нет в блоке
        if (!block.contains(n0)) n0 = null;
        else
        if (!block.contains(n1)) n1 = null;
        else
        if (!block.contains(n2)) n2 = null;

        // удяляем соседа tr2, который является tr1
        if (n0 == tr1) n0 = null;
        else
        if (n1 == tr1) n1 = null;
        else
        if (n2 == tr1) n2 = null;

        // возвращаем того, который остался не null
        if (n0 != null) return n0;
        else
        if (n1 != null) return n1;
        else
        return n2;
    }

    @Override
    public void rule(Cell cell) {
//        PrintWriter writer = null;
//        try {
//         writer = new PrintWriter(
//                 new OutputStreamWriter(
//                 new FileOutputStream("margolus "+MainFrame.iterationsCounter+".csv"), "windows-1251"));
//
//         String s;
//         double sum = 0.;
//         int num = 0;
//         int y = (150/2)*4*150;
//         for (int x=0; x<150; x++) {
//             num = x*4;
//             sum = ca.getCell(y+num).getAverageValue();
//             sum += ca.getCell(y+num+1).getAverageValue();
//             sum += ca.getCell(y+num+2).getAverageValue();
//             sum += ca.getCell(y+num+3).getAverageValue();
//             sum /= 4.;
//             s = x+";"+sum+"\n";
//             s = s.replace(".", ",");
//             writer.write(s);
//         }
//
//         writer.close();
//        } catch (Exception ex) {}

        List<Triangle> border = ca.getTriangulation().getBorder();
        ArrayList<Integer> borderVertices = new ArrayList<Integer>(3*border.size());
        List<Triangle> block;
        int trianglesCount = ca.getTriangulation().getTrianglesCount();
        int randomVertex, randomTriangle, randomVertexIndex;
        Triangle curTr, neighbor1, neighbor2, neighbor, tmp;
        Random rnd = new Random();

        for (Triangle t: border) {
            borderVertices.add(t.getVertexIndex(0));
            borderVertices.add(t.getVertexIndex(1));
            borderVertices.add(t.getVertexIndex(2));
        }

        for (int count=0; count<trianglesCount; count++) {
            randomTriangle = (int) (Math.random()*trianglesCount);
            randomVertexIndex = (int) (Math.random()*3);
            randomVertex = ca.getTriangle(randomTriangle).getVertexIndex(randomVertexIndex);

            if (!borderVertices.contains(randomVertex)) {
                block = ca.getTriangulation().getBlock(randomVertex, ca.getTriangle(randomTriangle));
                curTr = block.get(0);
                neighbor1 = curTr.getNeighbor(0);
                neighbor2 = curTr.getNeighbor(1);

                if (!block.contains(neighbor1)) neighbor1 = curTr.getNeighbor(2);
                else
                if (!block.contains(neighbor2)) neighbor2 = curTr.getNeighbor(2);

                //В neighbor будет храниться номер соседа, находящегося по ходу часовой стрелки
                neighbor = clockwise(randomTriangle, randomVertexIndex, neighbor1, curTr);
                if (neighbor == curTr) neighbor = neighbor2;

                // Поворот против часовой стрелки (probability - вероятность поворота по часовой)
                if (probability < rnd.nextDouble()) {
                    if (neighbor == neighbor1) neighbor = neighbor2;
                    else
                    neighbor = neighbor1;
                }

                int state0 = ca.getState(curTr);    // curTr
                int state1 = ca.getState(neighbor); // neighbor
                for (int i=0; i<block.size(); i++) {
                    ca.setState(neighbor, state0);
                    state0 = state1;

                    tmp = neighbor;
                    neighbor = getNextTriangleInBlock(block, curTr, neighbor);
                    curTr = tmp;

                    state1 = ca.getState(neighbor);
                }
            }
        }
    }

    @Override
    public boolean haveProperties() {
        return true;
    }

    @Override
    public boolean canBeAveraged() {
        return true;
    }

    @Override
    public boolean showProperties() {
        return CAMargolusDiffusionProperties.showDialog(this); // clicked Ok or  Cancel
    }
}