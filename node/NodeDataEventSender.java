package node;

import java.util.ArrayList;
import java.util.List;

import custom_listener.NodeDataListener;
import custom_listener.NodeTagListener;

public abstract class NodeDataEventSender {
    private List<NodeDataListener> dataListeners = new ArrayList<>(1);
    private List<NodeTagListener> tagListeners = new ArrayList<>(2);

    /**
     * Add a listener to the list of listeners.
     * 
     * @param toAdd The listener to add.
     */
    public void addDataListener(NodeDataListener toAdd) {
        dataListeners.add(toAdd);
    }

    /**
     * This function adds a NodeTagListener to the list of NodeTagListeners.
     * 
     * @param toAdd The NodeTagListener to add to the list of listeners.
     */
    public void addTagListenner(NodeTagListener toAdd) {
        tagListeners.add(toAdd);
    }

    /**
     * When the hCost changes, tell all the listeners that the hCost has changed.
     */
    public void triggerHCostChanged(int hCost, int fCost) {
        for (NodeDataListener dataListener : dataListeners) {
            dataListener.hCostChanged(hCost, fCost);
        }
    }

    /**
     * When the gCost changes, tell all the listeners that the gCost has changed.
     */
    public void triggerGCostChanged(int gCost, int fCost) {
        for (NodeDataListener dataListener : dataListeners) {
            dataListener.gCostChanged(gCost, fCost);
        }
    }

    /**
     * When a node's tag changes, notify all the listeners that the node's tag has
     * changed.
     */
    public void triggerTagChanged(NodeModel nodeModel) {
        for (NodeTagListener tagListener : tagListeners) {
            tagListener.nodeTagChanged(nodeModel);
        }
    }
}
