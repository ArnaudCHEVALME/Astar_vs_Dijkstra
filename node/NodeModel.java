package node;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import util.NodeTag;
import util.Position;

/**
 * It's a node that can be linked to other nodes, and it can have a tag
 */
public class NodeModel extends NodeDataEventSender {

    private Map<NodeModel, Integer> linked;
    private NodeModel parent;
    private NodeTag tag;
    private Position position;
    private int distToGoal;
    private int gCost;
    private int hCost;

    public NodeModel(Position position) {

        linked = new HashMap<>();
        this.position = position;
        tag = NodeTag.EMPTY;
        distToGoal = Integer.MAX_VALUE;
    }

    /**
     * Returns true if the node is linked to another node.
     * 
     * @param n The node to check if it's linked.
     * @return A boolean value.
     */
    public boolean isLinked(NodeModel n) {
        return linked.containsKey(n);
    }

    /**
     * This function links the current node to the target node, with a distance of
     * distance, and if
     * bidirectional is true, it also links the target node to the current node.
     * 
     * @param target        The node to link to.
     * @param distance      The distance between the two nodes.
     * @param bidirectional If true, the target node will also link back to this
     *                      node.
     */
    public void link(NodeModel target, int distance, boolean bidirectional) {
        linked.put(target, distance);
        if (bidirectional) {
            target.linked.put(this, distance);
        }
    }

    /**
     * Remove the given node from the list of nodes that this node is linked to.
     * 
     * @param n             The node to unlink from.
     * @param bidirectional If true, the link will be removed from both nodes. If
     *                      false, only the link
     *                      from this node will be removed.
     */
    public void unlink(NodeModel n, boolean bidirectional) {
        linked.remove(n);
        if (bidirectional) {
            n.linked.remove(this);
        }
    }

    /**
     * > Returns a map of all the nodes that are linked to this node and the
     * distance between them
     * 
     * @return A map of the linked nodes and the distance to them.
     */
    public Map<NodeModel, Integer> getLinkedNodesAndDistance() {
        return linked;
    }

    /**
     * Returns a set of all the nodes that are linked to this node.
     * 
     * @return A set of all the nodes that are linked to the current node.
     */
    public Set<NodeModel> getLinkedNodes() {
        return linked.keySet();
    }

    /**
     * The fCost is the sum of the gCost and hCost
     * 
     * @return The fCost is being returned.
     */
    public int getFCost() {
        return gCost + hCost;
    }

    /**
     * Returns the parent of this node, or null if this node has no parent.
     * 
     * @return The parent node of the current node.
     */
    public NodeModel getParent() {
        return parent;
    }

    /**
     * This function sets the parent of the current node to the node passed in as a
     * parameter.
     * 
     * @param parent The parent node of the current node.
     */
    public void setParent(NodeModel parent) {
        this.parent = parent;
    }

    /**
     * This function returns the gCost of the node
     * 
     * @return The gCost of the node.
     */
    public int getGCost() {
        return gCost;
    }

    /**
     * This function returns the hCost of the node
     * 
     * @return The hCost of the node.
     */
    public int getHCost() {
        return hCost;
    }

    /**
     * @return the distToGoal
     */
    public int getDistToGoal() {
        return distToGoal;
    }

    /**
     * @param distToGoal the distToGoal to set
     */
    public void setDistToGoal(int distToGoal) {
        this.distToGoal = distToGoal;
    }

    /**
     * This function sets the gCost of the node and then triggers the gCostChanged
     * event.
     * 
     * @param gCost The cost of moving from the starting node to this node.
     */
    public void setgCost(int gCost) {
        this.gCost = gCost;
        triggerGCostChanged(gCost, getFCost());
    }

    /**
     * This function sets the hCost variable to the value passed in, and then calls
     * the
     * triggerHCostChanged() function.
     * 
     * @param hCost The cost of moving from the current node to the next node.
     */
    public void sethCost(int hCost) {
        this.hCost = hCost;
        triggerHCostChanged(hCost, getFCost());
    }

    /**
     * Returns true if the node is a start node.
     * 
     * @return A boolean value.
     */
    public boolean isStart() {
        return tag == NodeTag.START;
    }

    /**
     * Returns true if the node is an end node.
     * 
     * @return The tag of the node.
     */
    public boolean isEnd() {
        return tag == NodeTag.END;
    }

    /**
     * @return the walkable
     */
    /**
     * If the node is not a wall, then it is walkable.
     * 
     * @return The boolean value of the isWalkable() method.
     */
    public boolean isWalkable() {
        return tag != NodeTag.WALL;
    }

    /**
     * This function sets the tag of the node and triggers the tag changed event.
     * 
     * @param tag The tag to set.
     */
    public void setNodeTag(NodeTag tag) {
        this.tag = tag;
        triggerTagChanged(this);
    }

    /**
     * @return the tag
     */
    public NodeTag getTag() {
        return tag;
    }

    /**
     * This function returns the position of the player.
     * 
     * @return The position of the player.
     */
    public Position getPosition() {
        return position;
    }

    /**
     * Reset the node to its default state.
     */
    public void resetNode() {
        linked.clear();
        tag = NodeTag.EMPTY;
        gCost = 0;
        hCost = 0;
        triggerGCostChanged(gCost, getFCost());
        triggerHCostChanged(hCost, getFCost());
        triggerTagChanged(this);
    }

    /**
     * This function resets the gCost and hCost of a node to 0
     */
    public void resetData() {
        setgCost(0);
        sethCost(0);
    }

    /**
     * If the node is not a start, end, wall, or portal, then set the node tag to
     * empty
     */
    public void resetTag() {
        if (tag != NodeTag.START && tag != NodeTag.END && tag != NodeTag.WALL && tag != NodeTag.PORTAL) {
            setNodeTag(NodeTag.EMPTY);
        }
    }

    /**
     * If the node is not a start, end, wall, or portal, then set the node's tag to
     * the new tag
     * 
     * @param newTag The new tag to set the node to.
     */
    public void setOverLayColor(NodeTag newTag) {
        if (tag != NodeTag.START && tag != NodeTag.END && tag != NodeTag.WALL && tag != NodeTag.PORTAL) {
            setNodeTag(newTag);
        }
    }

    public boolean isFree() {
        return tag != NodeTag.START && tag != NodeTag.END && tag != NodeTag.WALL;
    }
}