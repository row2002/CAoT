package CAoT;

/**
 *
 * @author Алексей Евсеев
 */
public class Cell{
    private int index;
    private int state = 0;
    private float averageValue = 0.f;

    public Cell() {
        index = -1;
    }

    public Cell(int index) {
        this.index = index;
    }

    public Cell (int index, int state, float averageValue) {
        this.index = index;
        this.state = state;
        this.averageValue = averageValue;
    }

    public Cell (Cell cell) {
        this.index = cell.getIndex();
        this.state = cell.getState();
        this.averageValue = cell.getAverageValue();
    }

    public int getIndex() {
        return index;
    }

    public float getAverageValue() {
        return averageValue;
    }

    public int getState() {
        return state;
    }

    public void setAverageValue( float value ) {
        averageValue = value;
    }

    public void setState( int  state ) {
        this.state = state;
    }

    @Override
    public String toString () {
        StringBuilder sb = new StringBuilder("Cell ");
        sb.append(getIndex());
        sb.append("\n  State = ").append(this.getState());
        sb.append("\n  AverageValue = ").append(this.getAverageValue());
        sb.append("\n \n");
        
        return sb.toString();
    }
}
