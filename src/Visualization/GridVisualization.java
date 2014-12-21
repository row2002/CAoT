package Visualization;

import CAoT.*;
import Enums.PresentedType;
import java.awt.Color;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Color3f;

/**
 *
 * @author Алексей Евсеев
 */
public class GridVisualization {
    private TransformGroup baseTG;
    private CellularArray cellularArray;
    private Colors colorSettings;
    private PresentedType presentedType;
    private CellVisualization[] cells;

    public GridVisualization(CellularArray cellularArray, Colors colorSettings) {
        this.cellularArray = cellularArray;
        this.colorSettings = colorSettings;
        presentedType = PresentedType.booleanValues;
        Triangulation triangulation = cellularArray.getTriangulation();

        baseTG = new TransformGroup();
//        baseTG.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
        baseTG.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);

        cells = new CellVisualization[triangulation.getTrianglesCount()];
        Color3f color = new Color3f();
        Color cellColor;
        for (int i=0; i<triangulation.getTrianglesCount(); i++) {
            cellColor = colorSettings.getColor(cellularArray.getCell(i).getState());
            awtColorToColor3f(cellColor, color);
            cells[i] = new CellVisualization(cellularArray.getCell(i), triangulation.getTriangle(i), color);
            baseTG.addChild(cells[i].getTG());
        }
    }

    public TransformGroup getBaseTG() {
        return baseTG;
    }

    public void update() {
        Triangulation triangulation = cellularArray.getTriangulation();
        Color3f color = new Color3f();
        Cell cell = new Cell();
        for (int i=0; i<triangulation.getTrianglesCount(); i++) {
            cell = cellularArray.getCell(i);
            switch (presentedType) {
                case booleanValues:
                    Color cellColor = colorSettings.getColor(cell.getState());
                    awtColorToColor3f(cellColor, color);
                    break;
                case averagedValues:
                    float value = 1.f - cell.getAverageValue();
                    color.x = value;
                    color.y = value;
                    color.z = value;
                    break;
            }
            cells[i].visualChange(color);
        }
    }

    private void awtColorToColor3f(Color awtColor, Color3f color){
        color.x = awtColor.getRed() / 255f;
        color.y = awtColor.getGreen() / 255f;
        color.z = awtColor.getBlue() / 255f;
    }
    public void setPresentedType(PresentedType presentedType) {
        this.presentedType = presentedType;
    }
    public void setColorSettings(Colors colorSettings) {
        this.colorSettings = colorSettings;
    }
}
