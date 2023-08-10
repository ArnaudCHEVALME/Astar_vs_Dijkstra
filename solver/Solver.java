package solver;

import java.util.ArrayList;
import java.util.List;

import grid.GridModel;
import node.NodeModel;
import util.NodeTag;

/**
 * This class is the parent class for all the solvers. It contains the path, the
 * grid model, and the
 * pause time. It also contains the increment and decrement pause methods.
 */
public abstract class Solver extends Thread {
    protected static int pauseTime = 0;
    protected static final int STEP_TIME = 10;
    protected List<NodeModel> path;
    protected GridModel gM;

    protected Solver(GridModel gM) {
        path = new ArrayList<>();
        this.gM = gM;
    }

    /**
     * This function is called when the user presses the "Pause" button. It
     * increments the pause time
     * by 100 milliseconds
     */
    public static void incrementPause() {
        pauseTime += STEP_TIME;
    }

    /**
     * Decrements the pause time by 200 milliseconds.
     */
    public static void decrementPause() {
        int newPauseTime = pauseTime - STEP_TIME;
        if (newPauseTime < 0) {
            pauseTime = 0;
        } else {
            pauseTime = newPauseTime;
        }
    }

    /**
     * A function that is called when the thread is started.
     */
    @Override
    public void run() {
        try {
            solve();
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

    /**
     * The function retraces the path from the end node to the start node by adding
     * the nodes to the path arraylist in reverse order and
     * set the overlay color of the current node to the color corresponding to PATH
     */
    public void retracePath() {
        path = new ArrayList<>();
        NodeModel startNode = gM.getStartNode();
        NodeModel curNode = gM.getEndNode();
        while (curNode != startNode) {
            path.add(0, curNode);
            curNode.setOverLayColor(NodeTag.PATH);
            curNode = curNode.getParent();
        }
    }

    abstract void solve();

}
