package solver;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.PriorityQueue;

import grid.GridModel;
import node.NodeModel;
import util.NodeCostComparator;
import util.NodeTag;

/**
 * It's a class that extends the Solver class and implements the A* algorithm
 */
public class SolverAStar extends Solver {

    private PriorityQueue<NodeModel> open;
    private HashSet<NodeModel> closed;

    public SolverAStar(GridModel gM) {
        super(gM);
        open = new PriorityQueue<>(new NodeCostComparator());
        closed = new HashSet<>();
        path = new ArrayList<>();
    }

    /**
     * It takes a start node and an end node, and then it finds the shortest path
     * between them
     */
    @Override
    public void solve() {
        gM.resetGridData();
        gM.resetTags();

        open.clear();
        closed.clear();
        path.clear();

        NodeModel startNode = gM.getStartNode();
        NodeModel endNode = gM.getEndNode();

        if (startNode == null || endNode == null) {
            return;
        }
        open.add(startNode);

        while (!open.isEmpty()) {
            NodeModel curNode = open.poll();
            closed.add(curNode);

            if (curNode == endNode) {
                retracePath();
                return;
            }
            curNode.setOverLayColor(NodeTag.EXPLORED);

            for (NodeModel linkedNode : curNode.getLinkedNodes()) {
                if (!linkedNode.isWalkable() || closed.contains(linkedNode)) {
                    continue;
                }

                int newMovementCostToNeighbour = curNode.getGCost() + gM.getDistance(curNode, linkedNode);
                if (newMovementCostToNeighbour < linkedNode.getGCost() || !open.contains(linkedNode)) {
                    linkedNode.setgCost(newMovementCostToNeighbour);
                    linkedNode.sethCost(gM.getDistance(linkedNode, endNode));
                    linkedNode.setParent(curNode);

                    if (!open.contains(linkedNode)) {
                        open.add(linkedNode);
                        linkedNode.setOverLayColor(NodeTag.QUEUE);
                    }
                }
            }
            try {
                Thread.sleep(Solver.pauseTime);
            } catch (InterruptedException e) {
                interrupt();
                e.printStackTrace();
                return;
            }
        }
    }

}
