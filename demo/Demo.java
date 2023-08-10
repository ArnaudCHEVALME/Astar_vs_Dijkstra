package demo;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import grid.GridModel;

/**
 * It creates a new window, adds a new view to it, and then displays the window
 */
public class Demo {

    private static final int SIZE = 4;

    public static void main(String[] args) {
        JFrame f = new JFrame("A* VS Djikstra");
        GridModel gM0 = new GridModel(SIZE, SIZE);
        GridModel gM1 = new GridModel(SIZE, SIZE);
        DemoController dC = new DemoController(gM0, gM1);
        DemoView dW = dC.createNewView();
        f.add(dW);
        f.pack();
        f.setVisible(true);
        f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
}
