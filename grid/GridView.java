package grid;

import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.event.MouseInputListener;

import node.NodeController;
import node.NodeModel;
import node.NodeView;
import util.Position;

/**
 * It's a JPanel that contains a grid of NodeViews
 */
public class GridView extends JPanel {
    private NodeView[][] nodeViews;

    public GridView(GridModel gM) {
        setLayout(new GridLayout(gM.getRows(), gM.getCols()));
        int nodeSize = 800 / gM.getRows();

        nodeViews = new NodeView[gM.getRows()][gM.getCols()];
        for (int row = 0; row < gM.getRows(); row++) {
            for (int col = 0; col < gM.getCols(); col++) {
                NodeController nC = new NodeController(gM.getNodeModel(row, col));
                NodeView nV = nC.getNodeView();
                nV.setPreferredSize(new Dimension(nodeSize, nodeSize));
                nV.setLabelSize(nodeSize);
                nodeViews[row][col] = nV;
                this.add(nV);
            }
        }
        setFocusable(false);
    }

    /**
     * "For each nodeView in the nodeViews array, add a mouse listener that will
     * call the NodeController when the mouse is clicked."
     * 
     * @param gM1 The GridModel that the NodeViews will be linked to.
     */
    public void linkNodeViewToModels(GridModel gM1) {
        for (int row = 0; row < nodeViews.length; row++) {
            for (int col = 0; col < nodeViews[0].length; col++) {
                NodeModel nM1 = gM1.getNodeModel(row, col);
                nodeViews[row][col].addMouseListener(new NodeController(nM1));
            }
        }
    }

    /**
     * This function adds a mouse listener to all the node views in the grid.
     * 
     * @param toAdd The MouseInputListener to add to the NodeViews.
     */
    public void addMouseListenerToNodeViews(MouseInputListener toAdd) {
        for (NodeView[] nodeLine : nodeViews) {
            for (NodeView nodeView : nodeLine) {
                nodeView.addMouseListener(toAdd);
            }
        }
    }

    public void toggleLabels() {
        for (NodeView[] nodeViewLine : nodeViews) {
            for (NodeView nodeView : nodeViewLine) {
                nodeView.toggleAllLabels();
            }
        }
    }

    public void toggleFLabels() {
        for (NodeView[] nodeViewLine : nodeViews) {
            for (NodeView nodeView : nodeViewLine) {
                nodeView.toggleFCostLabel();
            }
        }
    }

    /**
     * This function returns the node view at the given row and column.
     * 
     * @param row The row of the node you want to get.
     * @param col The column of the node you want to get.
     * @return The nodeView at the given row and column.
     */
    public NodeView getNodeView(int row, int col) {
        return nodeViews[row][col];
    }

    /**
     * This function returns a NodeView object at the given position.
     * 
     * @param pos The position of the node you want to get the view of.
     * @return A NodeView object
     */
    public NodeView getNodeView(Position pos) {
        return getNodeView(pos.getRow(), pos.getCol());
    }
}