package CAoT;

import Enums.*;
import java.awt.Color;

/**
 *
 * @author Алексей Евсеев
 */
public abstract class Automaton {
    protected CellularArray ca;
    private Colors colorSettings;

    // average
    protected AverageType averageType;
    protected double radius;
    protected int depth;

    public Automaton (CellularArray ca) {
        this.ca = ca;
        // Standard colorSettings
        this.colorSettings = new Colors(Color.WHITE, Color.RED, Color.BLUE, Color.GREEN, Color.MAGENTA, Color.ORANGE);
    }

    public Automaton (CellularArray ca, Colors colorSettings) {
        this.ca = ca;
        this.colorSettings = colorSettings;
    }

    public Automaton (CellularArray ca, Color ... colorSettings) {
        this.ca = ca;
        this.colorSettings = new Colors(colorSettings);
    }

    public Colors getColorSettings() {
        return colorSettings;
    }

    public void seColorSetings(Colors colors) {
        this.colorSettings = colors;
    }

    public void seColorSetings(Color ... colors) {
        this.colorSettings = new Colors(colors);
    }

    public void average() {
        switch (averageType) {
            case circle:
                ca.smoothByCircle(radius);
                break;
            case nearestNeighbors:
                ca.smoothByNearestNeighboors(depth);
                break;
        }
    }

    public AverageType getAverageType() {
        return averageType;
    }

    public int getDepth() {
        return depth;
    }

    public double getRadius() {
        return radius;
    }

    public void setAverageType(AverageType averageType) {
        this.averageType = averageType;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public boolean canBeAveraged() { return false; }
    public boolean haveProperties() { return false; }
    public boolean showProperties() { return false; } // boolean value means clicked Ok or Cancel
    public String getName() { return "unnamed"; }
    public abstract ModeOperation getModeOperation();
    public abstract void initialState();
    public abstract void rule(Cell cell);
}