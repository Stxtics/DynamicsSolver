package project;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Content extends JPanel {
    Graphics2D g2 = null;
    Graphics2D newG2 = null;
    private static Color SELECTED_COLOR = null;
    private static Stroke SELECTED_STROKE = null;
    public java.util.List<Shape> shapes = new ArrayList<>();
    public java.util.List<Shape> selectedShapes = new ArrayList<>();

    public Content() {
        SELECTED_COLOR = Color.red;
        SELECTED_STROKE = new BasicStroke(4f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0,
                new float[]{9}, 0);
    }
    /**
     * This procedure is called when the graphics component needs to be updated to
     * show the correct shapes on screen. First each shape is drawn then the selected
     * shapes are drawn on top on a different graphics component and that that graphics
     * component is disposed.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        for (Shape shape : shapes) {
            g2.setColor(Color.black);
            g2.draw(shape);
        }
        if (selectedShapes.size() > 0) {
            for (int i = 0; i < selectedShapes.size(); i++) {
                newG2 = (Graphics2D) g2.create();
                newG2.setColor(SELECTED_COLOR);
                newG2.setStroke(SELECTED_STROKE);
                newG2.draw(selectedShapes.get(i));
                newG2.dispose();
            }
        }
    }
}