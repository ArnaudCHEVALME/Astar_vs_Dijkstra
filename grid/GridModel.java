package grid;

import java.util.ArrayList;
import java.util.List;

import custom_exceptions.NodePositionOutOfModelBounds;
import custom_listener.NodeTagListener;
import node.NodeModel;
import util.NodeTag;
import util.Position;

/**
 * It's a 2D array of NodeModels, with some methods to help with the A*
 * algorithm
 */
public class GridModel implements NodeTagListener {

    protected NodeModel[][] nodeModels;
    protected NodeModel startNode;
    protected NodeModel endNode;

    public GridModel(int rows, int cols) {
        nodeModels = new NodeModel[rows][cols];
        initGrid();
        setupNeighbours();
    }

    /**
     * For each row and column, create a new NodeModel, add a tag listener to it,
     * and place it in the
     * grid.
     */
    public void initGrid() {
        for (int row = 0; row < getRows(); row++) {
            for (int col = 0; col < getCols(); col++) {
                NodeModel newNodeModel = new NodeModel(new Position(row, col));
                newNodeModel.addTagListenner(this);
                placeNode(newNodeModel, row, col);
            }
        }
    }

    /**
     * It resets the grid by resetting each node in the grid
     */
    public void resetGrid() {
        for (NodeModel[] NodeLine : nodeModels) {
            for (NodeModel nodeModel : NodeLine) {
                nodeModel.resetNode();
            }
        }
        setupNeighbours();
    }

    /**
     * It resets all the nodes in the grid except the start and end nodes
     */
    public void resetGridData() {
        for (NodeModel[] NodeLine : nodeModels) {
            for (NodeModel nodeModel : NodeLine) {
                if (nodeModel == startNode || nodeModel == endNode) {
                    continue;
                }
                nodeModel.resetData();
            }
        }
    }

    /**
     * For each node, link it to its neighbours
     */
    public void setupNeighbours() {
        for (int row = 0; row < nodeModels.length; row++) {
            for (int col = 0; col < nodeModels[0].length; col++) {
                for (NodeModel neighbour : getNeighbours(row, col)) {
                    int distance = getDistance(nodeModels[row][col], neighbour);
                    nodeModels[row][col].link(neighbour, distance, false);
                }
            }
        }
    }

    /**
     * The distance between two nodes is the number of rows between them plus the
     * number of columns
     * between them, multiplied by 10, plus the number of rows minus the number of
     * columns, multiplied
     * by 14.
     * 
     * 
     * @param nodeA The first node to calculate the distance between.
     * @param nodeB The node to which we want to find the distance.
     * @return The distance between two nodes.
     */
    public int getDistance(NodeModel nodeA, NodeModel nodeB) {
        Position posA = nodeA.getPosition();
        Position posB = nodeB.getPosition();

        int distRows = Math.abs(posA.getRow() - posB.getRow());
        int distCols = Math.abs(posA.getCol() - posB.getCol());
        if (distCols < distRows) {
            return distCols * 14 + (distRows - distCols) * 10;
        }
        return distRows * 14 + (distCols - distRows) * 10;
    }

    /**
     * If the row of node A is the same as the row of node B, or the column of node
     * A is the same as
     * the column of node B, then the nodes are aligned.
     * 
     * @param nodeA The first node to check
     * @param nodeB The node to check if it's aligned with nodeA.
     * @return A boolean value.
     */
    public boolean areAligned(NodeModel nodeA, NodeModel nodeB) {
        Position posA, posB;
        int rowA, colA, rowB, colB;

        posA = nodeA.getPosition();
        posB = nodeB.getPosition();

        rowA = posA.getRow();
        colA = posA.getCol();
        rowB = posB.getRow();
        colB = posB.getRow();

        return rowA == rowB || colA == colB;
    }

    /**
     * > It returns a list of all the nodes that are adjacent to the node at the
     * given row and column
     * 
     * @param nodeRow The row of the node we're checking.
     * @param nodeCol The column of the node we're checking.
     * @return A list of all the neighbours of the node at the given row and column.
     */
    public List<NodeModel> getNeighbours(int nodeRow, int nodeCol) {
        List<NodeModel> neighbours = new ArrayList<>();
        for (int row = -1; row < 2; row++) {
            for (int col = -1; col < 2; col++) {
                if (row == 0 && col == 0) {
                    continue;
                }
                int checkRow = nodeRow + row;
                int checkCol = nodeCol + col;
                if (checkRow >= 0 && checkRow < getRows() && checkCol >= 0 && checkCol < getCols()) {
                    neighbours.add(nodeModels[checkRow][checkCol]);
                }
            }
        }
        return neighbours;
    }

    /**
     * If the row or column is less than zero or greater than or equal to the number
     * of rows or
     * columns, throw an exception.
     * 
     * @param row The row of the node
     * @param col The column of the node
     */
    private void validateNodePosition(int row, int col) throws NodePositionOutOfModelBounds {
        if (row < 0 || row >= getRows() || col < 0 || col >= getCols()) {
            throw new NodePositionOutOfModelBounds("Node position outside of the bounds of the model");
        }
    }

    /**
     * This function places a node in the grid at the specified row and column.
     * 
     * @param nM  The node model to be placed in the grid
     * @param row the row of the node
     * @param col the column of the node
     */
    private void placeNode(NodeModel nM, int row, int col) {
        try {
            validateNodePosition(row, col);
            nodeModels[row][col] = nM;
        } catch (NodePositionOutOfModelBounds e) {
            System.err.println("Node not inserted in grid");
            e.printStackTrace();
        }
    }

    /**
     * Get the node model at the specified row and column.
     * 
     * @param row The row of the node you want to get.
     * @param col The column of the node you want to get.
     * @return A NodeModel object.
     */
    public NodeModel getNodeModel(int row, int col) {
        try {
            validateNodePosition(row, col);
            return nodeModels[row][col];
        } catch (NodePositionOutOfModelBounds e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * This function returns the start node of the graph.
     * 
     * @return The startNode
     */
    public NodeModel getStartNode() {
        return startNode;
    }

    /**
     * This function returns the end node of the edge.
     * 
     * @return The endNode
     */
    public NodeModel getEndNode() {
        return endNode;
    }

    /**
     * If the new start node is the same as the end node, set the end node to null.
     * 
     * @param newStartNode The new start node for the edge.
     */
    public void setStartNode(NodeModel newStartNode) {
        if (startNode != null) {
            startNode.setNodeTag(NodeTag.EMPTY);
        }
        if (newStartNode == endNode) {
            endNode = null;
        }
        startNode = newStartNode;
    }

    /**
     * If the end node is not null, set the end node's tag to empty. Then set the
     * end node to the new
     * end node.
     * 
     * @param newEndNode The node that will be the new end node.
     */
    public void setEndNode(NodeModel newEndNode) {
        if (endNode != null) {
            endNode.setNodeTag(NodeTag.EMPTY);
        }
        endNode = newEndNode;
    }

    /**
     * This function returns the number of rows in the table.
     * 
     * @return The number of rows in the table.
     */
    public int getRows() {
        return nodeModels.length;
    }

    /**
     * Returns the number of columns in the grid.
     * 
     * @return The number of columns in the nodeModels array.
     */
    public int getCols() {
        return nodeModels[0].length;
    }

    /**
     * If the node's tag is START, set the start node to the node that was passed
     * in. If the node's tag
     * is END, set the end node to the node that was passed in. If the node's tag is
     * EMPTY, and the
     * node that was passed in is the start node, set the start node to null. If the
     * node's tag is
     * EMPTY, and the node that was passed in is the end node, set the end node to
     * null
     * 
     * @param sourceNodeModel The node that was changed.
     */
    @Override
    public void nodeTagChanged(NodeModel sourceNodeModel) {
        switch (sourceNodeModel.getTag()) {
            case START:
                setStartNode(sourceNodeModel);
                break;
            case END:
                setEndNode(sourceNodeModel);
                break;

            case EMPTY:
                if (sourceNodeModel == startNode) {
                    startNode = null;
                } else if (sourceNodeModel == endNode) {
                    endNode = null;
                }
                break;

            default:
                break;
        }
    }

    /**
     * Reset the tag of each node in the nodeModels array.
     */
    public void resetTags() {
        for (NodeModel[] nodeLine : nodeModels) {
            for (NodeModel nodeModel : nodeLine) {
                nodeModel.resetTag();
            }
        }
    }

    public void placeWall(int row, int col) {
        getNodeModel(row, col).setNodeTag(NodeTag.WALL);
    }
}
