package CAoT;

import java.util.ArrayList;
import java.awt.Color;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Алексей Евсеев
 */
public class Colors {
    private List<Color> colors;

    public Colors(Color ... colors) {
        this.colors = new ArrayList<Color> (colors.length);
        this.colors.addAll(Arrays.asList(colors));
    }

    public Color getColor(int index) {
        return colors.get(index);
    }

    public void setColor(int index, Color color) {
        colors.set(index, color);
    }
}
