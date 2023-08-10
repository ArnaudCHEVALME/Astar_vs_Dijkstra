package solver;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

import grid.GridModel;
import node.NodeModel;
import util.NodeDistanceToGoalComparator;
import util.NodeTag;

/**
 * This class is a subclass of the Solver class and implements the solve()
 * method
 */
public class SolverDijkstra extends Solver {

    private PriorityQueue<NodeModel> queue;
    private List<NodeModel> visited;

    public SolverDijkstra(GridModel gM) {
        super(gM);
        queue = new PriorityQueue<>(gM.getCols() * gM.getRows(), new NodeDistanceToGoalComparator());
        visited = new ArrayList<>();
    }

    // Creating a method called solve() that will be used to solve the problem.
    @Override
    public void solve() {
        gM.resetGridData();
        gM.resetTags();

        NodeModel startNode = gM.getStartNode();
        NodeModel endNode = gM.getEndNode();
        queue.clear();
        visited.clear();

        for (int row = 0; row < gM.getRows(); row++) {
            for (int col = 0; col < gM.getCols(); col++) {
                gM.getNodeModel(row, col).setDistToGoal(Integer.MAX_VALUE);
            }
        }
        startNode.setDistToGoal(0);
        queue.add(startNode);

        while (queue.peek() != endNode) {

            NodeModel curNode = queue.remove();

            visited.add(curNode);
            curNode.setOverLayColor(NodeTag.EXPLORED);
            processNeighbours(curNode);

            try {
                Thread.sleep(Solver.pauseTime);
            } catch (InterruptedException e) {
                interrupt();
                e.printStackTrace();
                return;
            }
        }
        retracePath();
    }

    /**
     * For each node that is linked to the current node, if the node is not visited,
     * not in the queue,
     * and is walkable, then set the distance to the goal to the current node's
     * distance to the goal
     * plus the distance between the current node and the linked node
     * 
     * @param nM The current node being processed
     */
    public void processNeighbours(NodeModel nM) {
        int edgeDistance = -1;
        int newDistance = -1;

        Map<NodeModel, Integer> verticesDistances = nM.getLinkedNodesAndDistance();
        for (NodeModel linkedNode : nM.getLinkedNodes()) {
            if (!visited.contains(linkedNode) && !queue.contains(linkedNode) && linkedNode.isWalkable()) {
                edgeDistance = verticesDistances.get(linkedNode);
                newDistance = nM.getDistToGoal() + edgeDistance;
                if (newDistance < linkedNode.getDistToGoal()) {
                    linkedNode.setDistToGoal(newDistance);
                    linkedNode.setgCost(newDistance);
                    linkedNode.setParent(nM);
                }
                queue.add(linkedNode);
                linkedNode.setOverLayColor(NodeTag.QUEUE);
            }
        }
    }

}