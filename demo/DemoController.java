package demo;

import grid.GridModel;
import solver.Solver;
import solver.SolverAStar;
import solver.SolverDijkstra;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

/**
 * This class is the controller for the demo view. It handles the key events and
 * starts the solvers
 */
public class DemoController implements KeyListener {
    private static final Random rd = new Random();
    GridModel gridModel0;
    GridModel gridModel1;
    SolverAStar solverAStar;
    SolverDijkstra solverDijkstra;

    public DemoController(GridModel gM0, GridModel gM1) {
        gridModel0 = gM0;
        gridModel1 = gM1;
    }

    /**
     * This function creates a new view of the grid, and adds a key listener to it
     * 
     * @return A new DemoView object is being returned.
     */
    public DemoView createNewView() {
        DemoView dV = new DemoView(gridModel0, gridModel1);
        dV.addKeyListener(this);
        return dV;
    }

    @Override
    // A method that is called when a key is released.
    public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();
        // Resetting the grid.
        if (keyCode == KeyEvent.VK_N) {
            if (solverAStar != null) {
                solverAStar.interrupt();
            }
            if (solverDijkstra != null) {
                solverDijkstra.interrupt();
            }
            gridModel0.resetGrid();
            gridModel1.resetGrid();

            // Starting the solvers.
        } else if (keyCode == KeyEvent.VK_S) {
            solverAStar = new SolverAStar(gridModel0);
            solverDijkstra = new SolverDijkstra(gridModel1);
            solverAStar.start();
            solverDijkstra.start();

            // Decreasing the pause time between each step of the solver.
        } else if (keyCode == 109) {
            Solver.decrementPause();

            // Increasing the pause time between each step of the solver.
        } else if (keyCode == 107) {
            Solver.incrementPause();

            // Resetting the grid.
        } else if (keyCode == KeyEvent.VK_C) {
            gridModel0.resetTags();
            gridModel1.resetTags();
            gridModel0.resetGridData();
            gridModel1.resetGridData();
        } else if (keyCode == KeyEvent.VK_O) {
            placeRandomWalls();
        }
    }

    public void placeRandomWalls() {
        int toPlace = 10;
        int row, col;
        while (toPlace != 0) {
            row = rd.nextInt(gridModel0.getRows());
            col = rd.nextInt(gridModel0.getCols());
            if (gridModel0.getNodeModel(row, col).isFree() && gridModel1.getNodeModel(row, col).isFree()) {
                gridModel0.placeWall(row, col);
                gridModel1.placeWall(row, col);
                toPlace--;
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // Not use but must be implemented
    }

    @Override
    public void keyPressed(KeyEvent e) {
        // Not use but must be implemented
    }
}