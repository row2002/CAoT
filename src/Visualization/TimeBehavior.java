package Visualization;

/**
 *
 * @author Алексей Евсеев
 */
import CAoT.MainFrame;
import java.util.Enumeration;
import javax.media.j3d.*;

public class TimeBehavior extends Behavior
{
  private WakeupCondition timeOut;
  private int timeDelay;
  private Visualization visualization;

  public TimeBehavior(int td, Visualization visualization)
  {
    timeDelay = td;
    this.visualization = visualization;
    timeOut = new WakeupOnElapsedTime(timeDelay);
  }

  public void initialize()
  { wakeupOn(timeOut); }

  public void processStimulus(Enumeration criteria)
  {
    MainFrame.CAOperation(); // ignore criteria
    MainFrame.average();
    visualization.update();
    wakeupOn(timeOut);
  }

}

