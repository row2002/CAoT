package Visualization;

import CAoT.*;
import javax.media.j3d.*;
import javax.vecmath.Color3f;

/**
 *
 * @author Алексей Евсеев
 */
public class CellVisualization {
    private TransformGroup cellTG;
    private Shape3D shape;
    private Appearance cellApp;
    private ColoringAttributes coloringAttributes;
    private PolygonAttributes pa;

    private final static Color3f GRAY = new Color3f(0.6f, 0.6f, 0.6f);

    public CellVisualization(Cell cell, Triangle triangle, Color3f color) {
        cellApp = new Appearance();
        coloringAttributes = new ColoringAttributes();
        coloringAttributes.setColor(color);
        coloringAttributes.setCapability(ColoringAttributes.ALLOW_COLOR_WRITE);
        cellApp.setColoringAttributes(coloringAttributes);

        pa = new PolygonAttributes();
        pa.setCullFace(PolygonAttributes.CULL_NONE);
        cellApp.setPolygonAttributes(pa);

        // Lines
        Appearance lineApp = new Appearance();
        ColoringAttributes lineColor = new ColoringAttributes();
        lineColor.setColor(GRAY);
        lineApp.setColoringAttributes(lineColor);

        PolygonAttributes linePolAttr = new PolygonAttributes();
        linePolAttr.setPolygonMode(PolygonAttributes.POLYGON_LINE);
        lineApp.setPolygonAttributes(linePolAttr);

        TransparencyAttributes lineTrAttr = new TransparencyAttributes();
        lineTrAttr.setTransparency(0.8f);
        lineTrAttr.setTransparencyMode(TransparencyAttributes.BLENDED);
        lineTrAttr.setCapability(TransparencyAttributes.ALLOW_VALUE_WRITE);
        lineApp.setTransparencyAttributes(lineTrAttr);


        Shape3D shapeLines = new Shape3D(triangle, lineApp);
        shape = new Shape3D(triangle, cellApp);
        cellTG = new TransformGroup();
        cellTG.addChild(shape);
        cellTG.addChild(shapeLines);
    }

    public TransformGroup getTG() {
        return cellTG;
    }

    void visualChange(Color3f color) {
        coloringAttributes.setColor(color);
    }

}
