package CAoT;

import java.util.*;

/**
 *
 * @author Алексей Евсеев
 */

// Вспомогательный класс, необходимый только лишь потому что Java не позволяет создавать массивы генериков
public class NearestTriangles {
    private List<Triangle> data;

    public NearestTriangles() {
        data = new ArrayList<Triangle>();
    }

    public List<Triangle> getNearestTriangles () {
        return data;
    }

    public void add (Triangle t) {
        data.add(t);
    }

    public int size () {
        return data.size();
    }

    public boolean contains (Triangle t) {
        return data.contains(t);
    }

    public Triangle get (int index) {
        return data.get(index);
    }
}
