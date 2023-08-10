package util;

import java.util.Comparator;

import node.NodeModel;

/**
 * This class is used to compare two NodeModel objects based on their distance
 * to the goal
 */
public class NodeDistanceToGoalComparator implements Comparator<NodeModel> {

    // Comparing the distance to goal of two nodes.
    @Override
    public int compare(NodeModel nM0, NodeModel nM1) {
        if (nM0.getDistToGoal() < nM1.getDistToGoal()) {
            return -1;
        } else if (nM0.getDistToGoal() > nM1.getDistToGoal()) {
            return 1;
        }
        return 0;
    }
}
