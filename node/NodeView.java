package node;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

/**
 * It's a class that extends JPanel and implements NodeDataListener and
 * NodeTagListener
 */
public class NodeView extends JPanel {

    private static final Border GREY_BORDER = BorderFactory.createLineBorder(Color.DARK_GRAY, 1);
    private JLabel gCostJLabel;
    private JLabel hCostJLabel;
    private JLabel fCostJLabel;

    // It's a constructor that takes a NodeModel object as a parameter and calls the
    // initialize function
    // with the NodeModel object as a parameter.
    public NodeView() {
        initialize();
    }

    /**
     * This function sets the font size of the labels on the node to be 1/4 of the
     * size of the node
     * 
     * @param nodeViewSize The size of the node.
     */
    public void setLabelSize(int nodeViewSize) {
        int fontSize = nodeViewSize / 4;
        Font f = new Font("Verdana", Font.PLAIN, fontSize);
        fCostJLabel.setFont(f);
        gCostJLabel.setFont(f);
        hCostJLabel.setFont(f);
    }

    /**
     * This function initializes the JLabel objects and sets the layout of the
     * JPanel to BorderLayout
     */
    private void initialize() {
        fCostJLabel = new JLabel();
        gCostJLabel = new JLabel();
        hCostJLabel = new JLabel();

        fCostJLabel.setFocusable(false);
        gCostJLabel.setFocusable(false);
        hCostJLabel.setFocusable(false);

        this.setLayout(new BorderLayout());
        add(gCostJLabel, BorderLayout.NORTH);
        add(hCostJLabel, BorderLayout.CENTER);
        add(fCostJLabel, BorderLayout.SOUTH);

        setBackground(Color.WHITE);
        setBorder(GREY_BORDER);
        setFocusable(false);
    }

    public void setGCostLabel(String value) {
        gCostJLabel.setText(value);
    }

    public void setHCostLabel(String value) {
        hCostJLabel.setText(value);
    }

    public void setFCostLabel(String value) {
        fCostJLabel.setText(value);
    }

    /**
     * If the hCostJLabel is visible, make it invisible. If it's invisible, make it
     * visible
     */
    public void toggleHCostLabel() {
        hCostJLabel.setVisible(hCostJLabel.isVisible());

    }

    /**
     * If the fCostJLabel is visible, make it invisible. If it's invisible, make it
     * visible
     */
    public void toggleFCostLabel() {
        fCostJLabel.setVisible(!fCostJLabel.isVisible());
    }

    /**
     * If the gCostJLabel is visible, make it invisible. If it's invisible, make it
     * visible
     */
    public void toggleGCostLabel() {
        gCostJLabel.setVisible(!gCostJLabel.isVisible());
    }

    /**
     * This function toggles the visibility of the fCost, gCost, and hCost labels.
     */
    public void toggleAllLabels() {
        toggleFCostLabel();
        toggleGCostLabel();
        toggleHCostLabel();
    }
}
