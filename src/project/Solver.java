package project;

import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

public class Solver {
    Content content;

    public Solver(Content c) {
        this.content = c;
    }

    void LinkRopesToEntities(ArrayList<Rope> ropes, ArrayList<Entity> entities, java.util.List<Integer> positions) {
        ArrayList<Shape> lines = new ArrayList<>();
        ArrayList<Shape> rectangles = new ArrayList<>();
        for (Shape shape : content.shapes) {
            if (shape.getClass() == Line2D.Double.class) {
                lines.add(shape);
            } else if (shape.getClass() == Rectangle2D.Double.class) {
                rectangles.add(shape);
            }
        }

        for (Shape rope : lines) {
            for (Shape entity : rectangles) {
                if (rope.getBounds2D().getMinX() - 5 < entity.getBounds2D().getMaxY() && rope.getBounds2D().getY() > entity.getBounds2D().getMinX() && rope.getBounds2D().getY() < entity.getBounds2D().getMaxX()) {
                    //ropes.get(lines.indexOf(rope)).setObject1();
                }
            }
        }
    }
}
