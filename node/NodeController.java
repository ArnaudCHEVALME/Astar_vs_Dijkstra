package node;

import java.awt.event.MouseEvent;
import java.awt.Color;
import javax.swing.event.MouseInputListener;

import custom_listener.NodeDataListener;
import custom_listener.NodeTagListener;
import util.NodeTag;

/**
 * It's a controller class for the NodeModel and NodeView classes
 */
public class NodeController implements MouseInputListener, NodeDataListener, NodeTagListener {
    private NodeModel nodeModel;
    private NodeView nodeView;

    // A constructor for the NodeController class. It takes in a NodeModel and a
    // NodeView and sets the
    // nodeModel and nodeView fields to the parameters. It also adds a MouseListener
    // to the NodeView.
    public NodeController(NodeModel nodeModel) {
        this.nodeModel = nodeModel;
        this.nodeView = new NodeView();
        this.nodeModel.addTagListenner(this);
        this.nodeModel.addDataListener(this);
    }

    /**
     * The function creates a new NodeView object, which is a subclass of JPanel,
     * and returns it
     * 
     * @return A new NodeView object.
     */
    public NodeView getNodeView() {
        return nodeView;
    }

    /**
     * This function returns the node model
     * 
     * @return The nodeModel object.
     */
    public NodeModel getNodeModel() {
        return nodeModel;
    }

    // It's a function that takes two integers as parameters and sets the text of
    // the gCostJLabel object to the value of the first parameter. If the first
    // parameter is 0, it sets the text of the gCostJLabel object to an empty
    // string.
    // It also sets the text of the fCostJLabel object to the value of the second
    // parameter. If the second parameter is 0, it sets the text of the fCostJLabel
    // object to an empty string.
    @Override
    public void gCostChanged(int gCost, int newFCost) {
        if (gCost == 0)
            nodeView.setGCostLabel("");
        else
            nodeView.setGCostLabel(String.valueOf(gCost));
        if (newFCost == 0)
            nodeView.setFCostLabel("");
        else
            nodeView.setFCostLabel(String.valueOf(newFCost));
    }

    // It's a function that takes two integers as parameters and sets the text of
    // the hCostJLabel
    // object to the value of the first parameter. If the first parameter is 0, it
    // sets the text of the
    // hCostJLabel object to an empty string. It also sets the text of the
    // fCostJLabel object to the
    // value of the second parameter. If the second parameter is 0, it sets the text
    // of the fCostJLabel
    // object to an empty string.
    @Override
    public void hCostChanged(int hCost, int newFCost) {
        if (hCost == 0)
            nodeView.setHCostLabel("");
        else
            nodeView.setHCostLabel(String.valueOf(hCost));
        if (newFCost == 0)
            nodeView.setFCostLabel("");
        else
            nodeView.setFCostLabel(String.valueOf(newFCost));
    }

    /**
     * When the nodeTagChanged function is called, the background color of the node
     * is changed to the
     * color that corresponds to the node's tag.
     * 
     * @param sourceNodeModel The node that was changed.
     */
    @Override
    public void nodeTagChanged(NodeModel sourceNodeModel) {
        switch (sourceNodeModel.getTag()) {
            case EMPTY:
                nodeView.setBackground(Color.WHITE);
                break;
            case START:
                nodeView.setBackground(new Color(52, 101, 164));
                break;
            case END:
                nodeView.setBackground(Color.RED);
                break;
            case WALL:
                nodeView.setBackground(Color.BLACK);
                break;
            case PORTAL:
                nodeView.setBackground(Color.PINK);
                break;
            case PATH:
                nodeView.setBackground(Color.YELLOW);
                break;
            case QUEUE:
                nodeView.setBackground(new Color(119, 188, 101));
                break;

            case EXPLORED:
                nodeView.setBackground(new Color(200, 200, 200));
                break;
            default:
                break;
        }
    }

    @Override
    // A method that is called when the mouse enters the node.
    public void mouseEntered(MouseEvent e) {
        if (e.isShiftDown()) {
            nodeModel.setNodeTag(NodeTag.WALL);
        } else if (e.isControlDown()) {
            nodeModel.setNodeTag(NodeTag.EMPTY);
        }
    }

    // A method that is called when the mouse is clicked on the node.
    @Override
    public void mouseClicked(MouseEvent e) {
        switch (nodeModel.getTag()) {
            case EMPTY:
                nodeModel.setNodeTag(NodeTag.END);
                break;

            case END:
                nodeModel.setNodeTag(NodeTag.START);
                break;

            case START:
                nodeModel.setNodeTag(NodeTag.EMPTY);
                break;

            default:
                break;
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        // Not used but must be implemented
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // Not used but must be implemented
    }

    @Override
    public void mouseExited(MouseEvent e) {
        // Not used but must be implemented
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        // Not used but must be implemented
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        // Not used but must be implemented
    }
}
