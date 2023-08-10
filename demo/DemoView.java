package demo;

import javax.swing.JPanel;

import grid.GridController;
import grid.GridModel;
import grid.GridView;

/**
 * The DemoView class is a JPanel that contains two GridViews, each of which is
 * linked to both
 * GridModels
 */
public class DemoView extends JPanel {

    public DemoView(GridModel gM0, GridModel gM1) {
        super();
        initialize(gM0, gM1);
        setFocusable(true);
    }

    /**
     * The function initializes the two grids and links the views to the models
     * 
     * @param gM0 GridModel
     * @param gM1 GridModel
     */
    private void initialize(GridModel gM0, GridModel gM1) {
        GridController leftGridController = new GridController(gM0);
        GridController rightGridController = new GridController(gM1);

        GridView gV0 = leftGridController.getGridView();
        GridView gV1 = rightGridController.getGridView();
        gV1.toggleFLabels();

        gV0.linkNodeViewToModels(gM0);
        gV0.linkNodeViewToModels(gM1);
        gV1.linkNodeViewToModels(gM0);
        gV1.linkNodeViewToModels(gM1);

        add(gV0);
        add(gV1);
    }
}
