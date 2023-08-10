package custom_listener;

public interface NodeDataListener {
    void gCostChanged(int gCost, int newFCost);

    void hCostChanged(int hCost, int newFCost);
}