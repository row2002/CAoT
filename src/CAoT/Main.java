package CAoT;

import java.io.IOException;


/**
 *
 * @author Алексей Евсеев
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        Main m = new Main();
        m.showMainFrame();

        //Triangulation t = ParserInp.load("test.inp");
        //System.out.println(t);
//        t.calculateNeighbors();
//
//        CellularAutomaton ca = new CellularAutomaton (t);
//        ca.testInitialState();
//        System.out.println(ca);
    }

    private void showMainFrame() {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainFrame().setVisible(true);
            }
        });
    }
}
