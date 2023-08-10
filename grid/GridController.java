package grid;

/**
 * The GridController class is responsible for creating a GridView object and
 * returning it
 */
public class GridController {
    protected GridModel gridModel;
    protected GridView gridView;

    public GridController(GridModel gridModel) {
        this.gridModel = gridModel;
        this.gridView = new GridView(this.gridModel);
    }

    /**
     * This function creates a new GridView object and returns it.
     * 
     * @return A GridView object.
     */
    public GridView getGridView() {
        return this.gridView;
    }

}
