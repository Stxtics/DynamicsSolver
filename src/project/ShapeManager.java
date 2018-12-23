package project;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class ShapeManager {
    int mouseX, mouseY, direction = 0, index = -1, precision = 3;
    Double gravity = 9.8, coFriction, time, distance, acceleration, iVelocity, fVelocity;
    boolean hasFriction = true;
    Shape resizedShape = null;
    Content content;
    Gui gui;
    ArrayList<Rope> ropes = new ArrayList<>();
    ArrayList<Entity> entities = new ArrayList<>();
    ArrayList<Pulley> pulleys = new ArrayList<>();

    ShapeManager(Gui gui) {
        this.gui = gui;
        content = gui.content;
    }

    /**
     * The procedure is called when the add rope button is pressed. It creates a new
     * rope and adds it to ropes, then creates a Line2D and adds it to shapes and selected shapes.
     * Then it repaints the graphics component and grabs focus of the jpContent JPanel.
     */
    void addRope() {
        Shape shape = new Line2D.Double(140, 140, 640, 140);
        content.shapes.add(shape);
        content.selectedShapes.add(shape);
        ropes.add(new Rope(content.shapes.indexOf(shape)));
        content.repaint();
        content.grabFocus();
    }

    /**
     * This procedure is called when the add object button is pressed. It does the same
     * as the addRope procedure but instead of creating a Line2D it makes a Rectangle2D.
     */
    void addObject() {
        Shape shape = new Rectangle2D.Double(140, 140, 200, 200);
        content.shapes.add(shape);
        content.selectedShapes.add(shape);
        entities.add(new Entity(content.shapes.indexOf(shape)));
        content.repaint();
        content.grabFocus();
    }

    /**
     * This procedure is called when the add pulley button is pressed. It makes
     * a new rope and adds it to the list of ropes, then it also draws a circle with
     * two lines coming off of it.
     */
    void addPulley() {
        Ellipse2D circle = new Ellipse2D.Double(140, 140, 200, 200);
        content.shapes.add(circle);
        content.selectedShapes.add(circle);
        Line2D tRope = new Line2D.Double(240, 140, 640, 140);
        content.shapes.add(tRope);
        content.selectedShapes.add(tRope);
        ropes.add(new Rope(content.shapes.indexOf(circle)));
        Line2D bRope = new Line2D.Double(240, 340, 640, 340);
        content.shapes.add(bRope);
        content.selectedShapes.add(bRope);
        pulleys.add(new Pulley(circle, bRope, tRope));
        content.repaint();
        content.grabFocus();
    }

    /**
     * This procedure is called when the clear button is pressed. It clears all array lists
     * and calls the repaint method to update the graphics component.
     */
    void clearGui() {
        ropes.clear();
        entities.clear();
        pulleys.clear();
        content.shapes.clear();
        content.selectedShapes.clear();
        gravity = 9.8;
        coFriction = 0.0;
        content.repaint();
        content.grabFocus();
    }

    /**
     * This function is used to tells if the mouse is near the right side of a shape.
     * It does this in a similar way to the isCloseTo function by drawing a shape around
     * the right side of the shape that is in the parameter then it checks if the mouse
     * is inside that shape.
     */
    private boolean right(Shape shape, MouseEvent e) {
        double x = shape.getBounds().getMaxX() - 10, y = shape.getBounds().getMinY() - 10;
        Shape newShape = new Rectangle2D.Double(x, y, 20, shape.getBounds().getHeight() + 20);
        return newShape.contains(e.getPoint());
    }

    /**
     * This function checks if the mouse is near to the bottom of a shape. It does this
     * in a similar way to the right function.
     */
    private boolean bottom(Shape shape, MouseEvent e) {
        double x = shape.getBounds().getMinX() - 10, y = shape.getBounds().getMaxY() - 10;
        Shape newShape = new Rectangle2D.Double(x, y, shape.getBounds().getWidth() + 20, 20);
        return newShape.contains(e.getPoint());
    }

    /**
     * This function checks if the mouse is near to the left of a shape. It does this
     * in a similar way to the right function.
     */
    private boolean left(Shape shape, MouseEvent e) {
        double x = shape.getBounds().getMinX() - 10, y = shape.getBounds().getMinY() - 10;
        Shape newShape = new Rectangle2D.Double(x, y, 20, shape.getBounds().getHeight() + 20);
        return newShape.contains(e.getPoint());
    }

    /**
     * This function checks if the mouse is near to the top of a shape. It does this
     * in a similar way to the right function.
     */
    private boolean top(Shape shape, MouseEvent e) {
        double x = shape.getBounds().getMinX() - 10, y = shape.getBounds().getMinY() - 10;
        Shape newShape = new Rectangle2D.Double(x, y, shape.getBounds().getWidth() + 20, 20);
        return newShape.contains(e.getPoint());
    }

    /**
     * This procedure is called to resized an entity(Rectangle2D). It checks which side
     * of the shape the mouse is at then resizes it based on where the mouse is.
     */
    private void resizeRectangle(Shape shape, MouseEvent e, int i) {
        Shape rectangle = null;
        if ((bottom(shape, e) && right(shape, e) && direction == 0) || direction == 4) {
            direction = 4;
            rectangle = new Rectangle2D.Double(shape.getBounds().x, shape.getBounds().y,
                    mouseX - shape.getBounds().getMinX(),
                    mouseY - shape.getBounds().getMinY());
            content.setCursor(new Cursor(Cursor.SE_RESIZE_CURSOR));
        } else if ((bottom(shape, e) && left(shape, e) && direction == 0) || direction == 6) {
            direction = 6;
            rectangle = new Rectangle2D.Double(mouseX, shape.getBounds().y,
                    shape.getBounds().getMaxX() - mouseX, mouseY - shape.getBounds().getMinY());
            content.setCursor(new Cursor(Cursor.SW_RESIZE_CURSOR));
        } else if ((top(shape, e) && right(shape, e) && direction == 0) || direction == 2) {
            direction = 2;
            rectangle = new Rectangle2D.Double(shape.getBounds().x, mouseY,
                    mouseX - shape.getBounds().getMinX(), shape.getBounds().getMaxY() - mouseY);
            content.setCursor(new Cursor(Cursor.NE_RESIZE_CURSOR));
        } else if ((top(shape, e) && left(shape, e) && direction == 0) || direction == 8) {
            direction = 8;
            rectangle = new Rectangle2D.Double(mouseX, mouseY, shape.getBounds().getMaxX() - mouseX,
                    shape.getBounds().getMaxY() - mouseY);
            content.setCursor(new Cursor(Cursor.NW_RESIZE_CURSOR));
        } else if ((right(shape, e) && direction == 0) || direction == 3) {
            direction = 3;
            rectangle = new Rectangle2D.Double(shape.getBounds().x, shape.getBounds().y,
                    mouseX - shape.getBounds().getMinX(), shape.getBounds().getHeight());
            content.setCursor(new Cursor(Cursor.E_RESIZE_CURSOR));
        } else if ((left(shape, e) && direction == 0) || direction == 7) {
            direction = 7;
            rectangle = new Rectangle2D.Double(mouseX, shape.getBounds().getBounds().y,
                    shape.getBounds().getMaxX() - mouseX, shape.getBounds().getHeight());
            content.setCursor(new Cursor(Cursor.W_RESIZE_CURSOR));
        } else if ((bottom(shape, e) && direction == 0) || direction == 5) {
            direction = 5;
            rectangle = new Rectangle2D.Double(shape.getBounds().x, shape.getBounds().y,
                    shape.getBounds().getWidth(), mouseY - shape.getBounds().getMinY());
            content.setCursor(new Cursor(Cursor.S_RESIZE_CURSOR));
        } else if ((top(shape, e) && direction == 0) || direction == 1) {
            direction = 1;
            rectangle = new Rectangle2D.Double(shape.getBounds().x, mouseY, shape.getBounds().getWidth(),
                    shape.getBounds().getMaxY() - mouseY);
            content.setCursor(new Cursor(Cursor.N_RESIZE_CURSOR));
        }
        try {
            // Check to make sure the rectangle does not get too small.
            if (rectangle != null && (rectangle.getBounds().getWidth() < 20 || rectangle.getBounds().getHeight() < 20 || rectangle.getBounds().getMinX() < 0 || rectangle.getBounds().getMaxX() > content.getWidth()
                    || rectangle.getBounds().getMinY() < 0 || rectangle.getBounds().getMaxY() > content.getHeight())) {
                resizedShape = shape;
            } else if (rectangle != null && direction != 0) {
                index = i;
                resizedShape = rectangle;
                content.shapes.set(i, rectangle);
                int pos = content.selectedShapes.indexOf(shape);
                if (pos != -1) {
                    content.selectedShapes.set(pos, rectangle);
                } else {
                    content.selectedShapes.add(rectangle);
                }
            }
        } catch (NullPointerException e1) {
            // ignore
        }
    }

    /**
     * This procedure resizes a rope (Line2D). It does this in a similar way to resizeRectangle
     * by checking which side the mouse is on (right or left) and resizing it from there.
     */
    private void resizeLine(Shape shape, MouseEvent e, int i) {
        Shape line = null;
        if ((right(shape, e) && direction == 0) || direction == 3) {
            direction = 3;
            line = new Line2D.Double(shape.getBounds().getMinX(), shape.getBounds().y, mouseX, shape.getBounds().y);
            content.setCursor(new Cursor(Cursor.E_RESIZE_CURSOR));
        } else if ((left(shape, e) && direction == 0) || direction == 7) {
            direction = 7;
            line = new Line2D.Double(mouseX, shape.getBounds().y, shape.getBounds().getMaxX(),
                    shape.getBounds().y);
            content.setCursor(new Cursor(Cursor.W_RESIZE_CURSOR));
        }
        try {
            //Check to make sure it doesn't get too small.
            if (line != null && (line.getBounds().getWidth() < 20 || (direction == 3 && shape.getBounds().getMinX() > mouseX) || (direction == 7 && shape.getBounds().getMaxX() < mouseX) || line.getBounds().getMinX() < 0 || line.getBounds().getMaxX() > content.getWidth()
                    || line.getBounds().getY() < 0 || line.getBounds().getY() > content.getHeight())) {
                resizedShape = shape;
            } else if (line != null && direction != 0) {
                index = i;
                resizedShape = line;
                content.shapes.set(i, line);
                int pos = content.selectedShapes.indexOf(shape);
                if (pos != -1) {
                    content.selectedShapes.set(pos, line);
                } else {
                    content.selectedShapes.add(line);
                }
                if (pulleys.stream().filter(x -> x.tRope == shape || x.bRope == shape).findFirst().orElse(null) != null) {
                    Pulley pulley = pulleys.stream().filter(x -> x.tRope == shape || x.bRope == shape).findFirst().orElse(null);
                    assert pulley != null;
                    if (pulley.tRope == shape) {
                        pulley.tRope = (Line2D) line;
                        if (!content.selectedShapes.contains(pulley.bRope)) {
                            content.selectedShapes.add(pulley.bRope);
                        }
                    } else if (pulley.bRope == shape) {
                        pulley.bRope = (Line2D) line;
                        if (!content.selectedShapes.contains(pulley.tRope)) {
                            content.selectedShapes.add(pulley.tRope);
                        }
                    }
                    if (!content.selectedShapes.contains(pulley.circle)) {
                        content.selectedShapes.add(pulley.circle);
                    }
                }
            }
        } catch (NullPointerException e1) {
            // ignore
        }
    }

    private void resizeCircle(Shape shape, MouseEvent e, int i) {
        Shape circle = null;
        if ((bottom(shape, e) && right(shape, e) && direction == 0) || direction == 4) {
            direction = 4;
            circle = new Ellipse2D.Double(shape.getBounds().x, shape.getBounds().y,
                    mouseX - shape.getBounds().getMinX(),
                    mouseY - shape.getBounds().getMinY());
            content.setCursor(new Cursor(Cursor.SE_RESIZE_CURSOR));
        } else if ((bottom(shape, e) && left(shape, e) && direction == 0) || direction == 6) {
            direction = 6;
            circle = new Ellipse2D.Double(mouseX, shape.getBounds().y,
                    shape.getBounds().getMaxX() - mouseX, mouseY - shape.getBounds().getMinY());
            content.setCursor(new Cursor(Cursor.SW_RESIZE_CURSOR));
        } else if ((top(shape, e) && right(shape, e) && direction == 0) || direction == 2) {
            direction = 2;
            circle = new Ellipse2D.Double(shape.getBounds().x, mouseY,
                    mouseX - shape.getBounds().getMinX(), shape.getBounds().getMaxY() - mouseY);
            content.setCursor(new Cursor(Cursor.NE_RESIZE_CURSOR));
        } else if ((top(shape, e) && left(shape, e) && direction == 0) || direction == 8) {
            direction = 8;
            circle = new Ellipse2D.Double(mouseX, mouseY, shape.getBounds().getMaxX() - mouseX,
                    shape.getBounds().getMaxY() - mouseY);
            content.setCursor(new Cursor(Cursor.NW_RESIZE_CURSOR));
        } else if ((right(shape, e) && direction == 0) || direction == 3) {
            direction = 3;
            circle = new Ellipse2D.Double(shape.getBounds().x, shape.getBounds().y,
                    mouseX - shape.getBounds().getMinX(), shape.getBounds().getHeight());
            content.setCursor(new Cursor(Cursor.E_RESIZE_CURSOR));
        } else if ((left(shape, e) && direction == 0) || direction == 7) {
            direction = 7;
            circle = new Ellipse2D.Double(mouseX, shape.getBounds().getBounds().y,
                    shape.getBounds().getMaxX() - mouseX, shape.getBounds().getHeight());
            content.setCursor(new Cursor(Cursor.W_RESIZE_CURSOR));
        } else if ((bottom(shape, e) && direction == 0) || direction == 5) {
            direction = 5;
            circle = new Ellipse2D.Double(shape.getBounds().x, shape.getBounds().y,
                    shape.getBounds().getWidth(), mouseY - shape.getBounds().getMinY());
            content.setCursor(new Cursor(Cursor.S_RESIZE_CURSOR));
        } else if ((top(shape, e) && direction == 0) || direction == 1) {
            direction = 1;
            circle = new Ellipse2D.Double(shape.getBounds().x, mouseY, shape.getBounds().getWidth(),
                    shape.getBounds().getMaxY() - mouseY);
            content.setCursor(new Cursor(Cursor.N_RESIZE_CURSOR));
        }
        try {
            // Check to make sure the rectangle does not get too small.
            if (circle != null && (circle.getBounds().getWidth() < 20 || circle.getBounds().getHeight() < 20 || circle.getBounds().getMinX() < 0 || circle.getBounds().getMaxX() > content.getWidth()
                    || circle.getBounds().getMinY() < 0 || circle.getBounds().getMaxY() > content.getHeight())) {
                resizedShape = shape;
            } else if (circle != null && direction != 0) {
                index = i;
                resizedShape = circle;
                Pulley pulley = pulleys.stream().filter(x -> x.circle == shape).findFirst().orElse(null);
                if (pulley != null) {
                    Shape tRope = content.shapes.stream().filter(x -> x == pulley.tRope).findFirst().orElse(null);
                    Shape bRope = content.shapes.stream().filter(x -> x == pulley.bRope).findFirst().orElse(null);
                    if (tRope != null && bRope != null) {
                        int bRopeIndex = content.shapes.indexOf(bRope), tRopeIndex = content.shapes.indexOf(tRope);
                        tRope = new Line2D.Double(Math.floor(circle.getBounds().getMaxX() - (circle.getBounds().getWidth() / 2)), circle.getBounds().getMinY(), Math.floor((circle.getBounds().getMaxX() - (circle.getBounds().getWidth() / 2)) + tRope.getBounds().getWidth()), circle.getBounds().getMinY());
                        bRope = new Line2D.Double(Math.floor(circle.getBounds().getMaxX() - (circle.getBounds().getWidth() / 2)), circle.getBounds().getMaxY(), Math.floor((circle.getBounds().getMaxX() - (circle.getBounds().getWidth() / 2)) + bRope.getBounds().getWidth()), circle.getBounds().getMaxY());
                        content.shapes.set(i, circle);
                        int pos = content.selectedShapes.indexOf(shape);
                        if (pos != -1) {
                            content.selectedShapes.set(pos, circle);
                        } else {
                            content.selectedShapes.add(circle);
                        }
                        content.shapes.set(tRopeIndex, tRope);
                        pos = content.selectedShapes.indexOf(pulley.tRope);
                        if (pos != -1) {
                            content.selectedShapes.set(pos, tRope);
                        } else {
                            content.selectedShapes.add(tRope);
                        }
                        content.shapes.set(bRopeIndex, bRope);
                        pos = content.selectedShapes.indexOf(pulley.bRope);
                        if (pos != -1) {
                            content.selectedShapes.set(pos, bRope);
                        } else {
                            content.selectedShapes.add(bRope);
                        }
                        pulleys.set(pulleys.indexOf(pulley), new Pulley((Ellipse2D) circle, (Line2D) bRope, (Line2D) tRope));
                    }
                }
            }
        } catch (NullPointerException e1) {
            // ignore
        }
    }

    void moveShapes(int prevX, int prevY) {
        for (int i = 0; i < content.selectedShapes.size(); i++) {
            Shape shape = content.selectedShapes.get(i);
            if (shape.getClass() == Line2D.Double.class) { // line2d
                if (!(shape.getBounds().x + mouseX - prevX < 0 || shape.getBounds().getMaxX() + mouseX - prevX > content.getWidth() // don't let line go off screen
                        || shape.getBounds().y + mouseY - prevY < 0 || shape.getBounds().getMaxY() + mouseY - prevY > content.getHeight())) {
                    if (pulleys.stream().filter(x -> x.tRope == shape || x.bRope == shape).findFirst().orElse(null) == null) {
                        moveLine(shape, prevX, prevY, i);
                    }
                }
            } else if (shape.getClass() == Rectangle2D.Double.class) { // if shape is a rectangle2d
                if (!(shape.getBounds().x + mouseX - prevX < 0 || shape.getBounds().getMaxX() + mouseX - prevX > content.getWidth() // don't let rectangle go off screen
                        || shape.getBounds().y + mouseY - prevY < 0 || shape.getBounds().getMaxY() + mouseY - prevY > content.getHeight())) {
                    moveRectangle(shape, prevX, prevY, i);
                }
            } else if (shape.getClass() == Ellipse2D.Double.class) {
                if (!(shape.getBounds().x + mouseX - prevX < 0 || shape.getBounds().getMaxX() + mouseX - prevX > content.getWidth() // don't let circle go off screen
                        || shape.getBounds().y + mouseY - prevY < 0 || shape.getBounds().getMaxY() + mouseY - prevY > content.getHeight())) {
                    moveCircle(shape, prevX, prevY, i);
                }
            }
        }
    }

    private void moveLine(Shape shape, int prevX, int prevY, int i) {
        Shape line = new Line2D.Double(shape.getBounds().x + mouseX - prevX,
                shape.getBounds().y + mouseY - prevY, shape.getBounds().x + mouseX - prevX + shape.getBounds().getWidth(),
                shape.getBounds().y + mouseY - prevY);
        content.selectedShapes.set(i, line);
        int pos = content.shapes.indexOf(shape);
        content.shapes.set(pos, line);
    }

    private void moveRectangle(Shape shape, int prevX, int prevY, int i) {
        Shape rectangle = new Rectangle2D.Double(shape.getBounds().x + mouseX - prevX,
                shape.getBounds().y + mouseY - prevY, shape.getBounds().getWidth(),
                shape.getBounds().getHeight());
        content.selectedShapes.set(i, rectangle);
        int pos = content.shapes.indexOf(shape);
        content.shapes.set(pos, rectangle);
    }

    private void moveCircle(Shape shape, int prevX, int prevY, int i) {
        Shape circle = new Ellipse2D.Double(shape.getBounds().x + mouseX - prevX,
                shape.getBounds().y + mouseY - prevY, shape.getBounds().getWidth(),
                shape.getBounds().getHeight());
        Pulley pulley = pulleys.stream().filter(x -> x.circle == shape).findFirst().orElse(null);
        if (pulley != null) {
            Shape tRope = content.selectedShapes.stream().filter(x -> x == pulley.tRope).findFirst().orElse(null);
            Shape bRope = content.selectedShapes.stream().filter(x -> x == pulley.bRope).findFirst().orElse(null);
            if (tRope != null && bRope != null) {
                int bRopeIndex = content.selectedShapes.indexOf(bRope), tRopeIndex = content.selectedShapes.indexOf(tRope);
                tRope = new Line2D.Double(Math.floor(circle.getBounds().getMaxX() - (circle.getBounds().getWidth() / 2)), circle.getBounds().getMinY(), Math.floor((circle.getBounds().getMaxX() - (circle.getBounds().getWidth() / 2)) + tRope.getBounds().getWidth()), circle.getBounds().getMinY());
                bRope = new Line2D.Double(Math.floor(circle.getBounds().getMaxX() - (circle.getBounds().getWidth() / 2)), circle.getBounds().getMaxY(), Math.floor((circle.getBounds().getMaxX() - (circle.getBounds().getWidth() / 2)) + bRope.getBounds().getWidth()), circle.getBounds().getMaxY());
                content.selectedShapes.set(i, circle);
                int pos = content.shapes.indexOf(shape);
                content.shapes.set(pos, circle);
                content.selectedShapes.set(tRopeIndex, tRope);
                pos = content.shapes.indexOf(pulley.tRope);
                if (pos != -1) {
                    content.shapes.set(pos, tRope);
                }
                content.selectedShapes.set(bRopeIndex, bRope);
                pos = content.shapes.indexOf(pulley.bRope);
                if (pos != -1) {
                    content.shapes.set(pos, bRope);
                }
                pulleys.set(pulleys.indexOf(pulley), new Pulley((Ellipse2D) circle, (Line2D) bRope, (Line2D) tRope));
            }
        }
    }

    /**
     * This function checks where a mouse click is close to a shape.
     * It does this by drawing another shape that is bigger than the shape in the parameter
     * then it checks if the mouse events location is inside that shape.
     */
    private boolean isCloseTo(Shape shape, MouseEvent e) {
        double minX = shape.getBounds().getMinX() - 10;
        double minY = shape.getBounds().getMinY() - 10;
        Shape newShape = new Rectangle2D.Double(minX, minY, shape.getBounds().getWidth() + 20,
                shape.getBounds().getHeight() + 20);
        return newShape.contains(e.getPoint());
    }

    void resizeShape(MouseEvent e) {
        if (resizedShape != null && direction > 0 && index > -1) {
            if (resizedShape.getClass() == Line2D.Double.class) { // if shape is line2d
                resizeLine(resizedShape, e, index);
            } else if (resizedShape.getClass() == Rectangle2D.Double.class) { // if shape is a rectangle2d
                resizeRectangle(resizedShape, e, index);
            } else if (resizedShape.getClass() == Ellipse2D.Double.class) {
                resizeCircle(resizedShape, e, index);
            }
        } else {
            for (int i = 0; i < content.shapes.size(); i++) {
                if (content.shapes.get(i).getClass() == Line2D.Double.class) { // if shape is line2d
                    if (isCloseTo(content.shapes.get(i), e)) {
                        resizeLine(content.shapes.get(i), e, i);
                    }
                } else if (content.shapes.get(i).getClass() == Rectangle2D.Double.class) { // if shape is a rectangle2d
                    if (isCloseTo(content.shapes.get(i), e)) {
                        resizeRectangle(content.shapes.get(i), e, i);
                    }
                } else if (content.shapes.get(i).getClass() == Ellipse2D.Double.class) {
                    if (isCloseTo(content.shapes.get(i), e)) {
                        resizeCircle(content.shapes.get(i), e, i);
                    }
                }
            }
        }
    }

    void rotateShape(MouseEvent e, int prevX, int prevY) {
        for (int i = 0; i < content.shapes.size(); i++) {
            Shape shape = content.shapes.get(i);
            if (isCloseTo(shape, e)) {
                if (shape.getClass() == Line2D.Double.class) {
                    Shape line = new Line2D.Double(e.getPoint().x, e.getPoint().y, shape.getBounds2D().getMaxX(), shape.getBounds2D().getY());
                    content.shapes.set(i, line);
                    int pos = content.selectedShapes.indexOf(shape);
                    if (pos != -1) {
                        content.selectedShapes.set(pos, line);
                    } else {
                        content.selectedShapes.add(line);
                    }
                } else if (shape.getClass() == Ellipse2D.Double.class) {

                }
            }
        }
    }

    /**
     * Allows the user to enter details for an rope when they ctrl + click on it.
     * The parameter i is the position of the rope in the shapes array. A user can only
     * enter tension for an array so it gets the first value in the data array then checks
     * if it is ok.
     */
    private void enterRopeDetails(int i) {
        String data = "Tension";
        Rope rope = ropes.stream().filter(x -> x.getShapeIndex() == i).findFirst().orElse(null);
        assert rope != null;
        Double value = rope.getTension();
        try {
            List<String> arr = new InputField("Rope Details - Leave blank if unknown", data, value).data;
            if (arr.size() > 0) {
                String tensionS = arr.get(0);
                if (tensionS.length() > 0) {
                    double tension = Double.parseDouble(tensionS);
                    if (tension > 0) {
                        rope.setTension(tension);
                    } else {
                        gui.showMessageBox("Tension must be greater than 0.", "Error");
                    }
                } else {
                    rope.setTension(0);
                }
            }
        } catch (NumberFormatException error) {
            gui.showMessageBox("Tension must be a double.", "Error");
        }
    }

    /**
     * Allows the user to enter details for an entity when they ctrl + click on it.
     * The parameter i is the position of the entity in the shapes array. The for
     * loop goes through all the values entered, checks if they are ok and if they
     * are it sets those values on the entity.
     */
    private void enterObjectDetails(int i) {
        ArrayList<String> data = new ArrayList<>(Arrays.asList("Mass", "Weight", "Resultant force", "Left force", "Right force"));
        Entity entity = entities.stream().filter(x -> x.getShapeIndex() == i).findFirst().orElse(null);
        assert entity != null;
        ArrayList<Double> values = new ArrayList<>(Arrays.asList(entity.getMass(), entity.getWeight(), entity.getResultantForce(), entity.getLeftForce(), entity.getRightForce()));
        data = new InputField("Object Details - Leave blank if unknown", data, values).data;
        for (int j = 0; j < data.size(); j++) {
            if (data.get(j).length() > 0 && j == 0) {
                try {
                    double mass = Double.parseDouble(data.get(j));
                    if (mass > 0) {
                        entity.setMass(mass);
                    } else {
                        gui.showMessageBox("Mass must be greater than 0.", "Error");
                    }
                } catch (NumberFormatException error) {
                    gui.showMessageBox("Mass must be a double.", "Error");
                }
            }  else if (j == 0) {
                entity.setMass(0);
            } else if (data.get(j).length() > 0 && j == 1) {
                try {
                    double weight = Double.parseDouble(data.get(j));
                    entity.setWeight(weight);
                } catch (NumberFormatException error) {
                    gui.showMessageBox("Weight must be a double.", "Error");
                }
            } else if (j == 1) {
                entity.setWeight(0);
            } else if (data.get(j).length() > 0 && j == 2) {
                try {
                    double resultantForce = Double.parseDouble(data.get(j));
                    entity.setResultantForce(resultantForce);
                } catch (NumberFormatException error) {
                    gui.showMessageBox("Resultant force must be a double.", "Error");
                }
            } else if (j == 2) {
                entity.setResultantForce(0);
            } else if (data.get(j).length() > 0 && j == 3) {
                try {
                    double leftForce = Double.parseDouble(data.get(j));
                    entity.setLeftForce(leftForce);
                } catch (NumberFormatException error) {
                    gui.showMessageBox("Left force must be a double.", "Error");
                }
            } else if (j == 3) {
                entity.setLeftForce(0);
            } else if (data.get(j).length() > 0 && j == 4) {
                try {
                    double rightForce = Double.parseDouble(data.get(j));
                    entity.setRightForce(rightForce);
                } catch (NumberFormatException error) {
                    gui.showMessageBox("Right force must be a double.", "Error");
                }
            } else if (j == 4) {
                entity.setRightForce(0);
            }
        }
    }

    Shape selectShape(MouseEvent e) {
        for (int i = content.shapes.size() - 1; i >= 0; i--) {
            Shape shape = content.shapes.get(i);
            if (shape.getClass() == Line2D.Double.class) {// if shape is line2d
                if (isCloseTo(shape, e)) {
                    if (!content.selectedShapes.contains(shape)) {
                        if (pulleys.stream().filter(x -> x.bRope == shape || x.tRope == shape).findFirst().orElse(null) == null) {
                            if (e.isControlDown()) {
                                enterRopeDetails(i);
                                return shape;
                            } else {
                                content.selectedShapes.add(shape);
                                content.repaint();
                                return shape;
                            }
                        } else {
                            if (e.isControlDown()) {
                                Pulley pulley = pulleys.stream().filter(x -> x.bRope == shape || x.tRope == shape).findFirst().orElse(null);
                                assert pulley != null;
                                enterRopeDetails(content.shapes.indexOf(pulley.circle));
                                return shape;
                            } else {
                                Pulley pulley = pulleys.stream().filter(x -> x.bRope == shape || x.tRope == shape).findFirst().orElse(null);
                                assert pulley != null;
                                content.selectedShapes.add(pulley.circle);
                                content.selectedShapes.add(pulley.tRope);
                                content.selectedShapes.add(pulley.bRope);
                                content.repaint();
                                return shape;
                            }
                        }
                    }
                }
            } else if (shape.getClass() == Rectangle2D.Double.class) {// if shape is rectangle2d
                if (shape.contains(e.getPoint())) {
                    if (!content.selectedShapes.contains(shape)) {
                        if (e.isControlDown()) {
                            enterObjectDetails(i);
                            return shape;
                        } else {
                            content.selectedShapes.add(shape);
                            content.repaint();
                            return shape;
                        }
                    }
                }
            } else if (shape.getClass() == Ellipse2D.Double.class) {
                if (shape.contains(e.getPoint())) {
                    if (!content.selectedShapes.contains(shape)) {
                        if (e.isControlDown()) {
                            enterRopeDetails(i);
                            return shape;
                        } else {
                            Pulley pulley = pulleys.stream().filter(x -> x.circle == shape).findFirst().orElse(null);
                            assert pulley != null;
                            content.selectedShapes.add(pulley.circle);
                            content.selectedShapes.add(pulley.tRope);
                            content.selectedShapes.add(pulley.bRope);
                            content.repaint();
                            return shape;
                        }
                    }
                }
            }
        }
        return null;
    }

    void deselectShape(MouseEvent e) {
        for (int i = 0; i < content.selectedShapes.size(); i++) {
            for (int j = content.shapes.size() - 1; j >= 0; j--) {
                Shape shape = content.shapes.get(j);
                if (shape.getClass() == Line2D.Double.class) {// if shape is line2d
                    if (isCloseTo(shape, e)) {
                        if (pulleys.stream().filter(x -> x.bRope == shape || x.tRope == shape).findFirst().orElse(null) == null) {
                            if (e.isControlDown()) {
                                enterRopeDetails(j);
                                i = content.selectedShapes.size();
                            } else {
                                if (content.selectedShapes.get(i) == shape) {
                                    content.selectedShapes.remove(shape);
                                    i = content.selectedShapes.size();
                                }
                                content.repaint();
                            }
                        } else {
                            if (e.isControlDown()) {
                                Pulley pulley = pulleys.stream().filter(x -> x.bRope == shape || x.tRope == shape).findFirst().orElse(null);
                                assert pulley != null;
                                enterRopeDetails(content.shapes.indexOf(pulley.circle));
                                i = content.selectedShapes.size();
                            } else {
                                if (content.selectedShapes.get(i) == shape) {
                                    Pulley pulley = pulleys.stream().filter(x -> x.bRope == shape || x.tRope == shape).findFirst().orElse(null);
                                    assert pulley != null;
                                    content.selectedShapes.remove(pulley.circle);
                                    content.selectedShapes.remove(pulley.tRope);
                                    content.selectedShapes.remove(pulley.bRope);
                                    i = content.selectedShapes.size();
                                }
                                content.repaint();
                            }
                        }
                        break;
                    }
                } else if (shape.getClass() == Rectangle2D.Double.class) { // if shape is rectangle2d
                    if (shape.contains(e.getPoint())) {
                        if (e.isControlDown()) {
                            enterObjectDetails(j);
                            i = content.selectedShapes.size();
                        } else {
                            if (content.selectedShapes.get(i) == shape) {
                                content.selectedShapes.remove(shape);
                                i = content.selectedShapes.size();
                            }
                            content.repaint();
                        }
                        break;
                    }
                } else if (shape.getClass() == Ellipse2D.Double.class) {
                    if (shape.contains(e.getPoint())) {
                        if (e.isControlDown()) {
                            enterRopeDetails(j);
                            i = content.selectedShapes.size();
                        } else {
                            if (content.selectedShapes.get(i) == shape) {
                                Pulley pulley = pulleys.stream().filter(x -> x.circle == shape).findFirst().orElse(null);
                                assert pulley != null;
                                content.selectedShapes.remove(pulley.circle);
                                content.selectedShapes.remove(pulley.tRope);
                                content.selectedShapes.remove(pulley.bRope);
                                i = content.selectedShapes.size();
                            }
                            content.repaint();
                        }
                        break;
                    }
                }
            }
        }
    }

    void deleteSelectedShapes() {
        int size = content.selectedShapes.size();
        for (int i = 0; i < size; i++) {
            Shape shape = content.selectedShapes.remove(0);
            content.shapes.remove(shape);
        }
        content.repaint();
    }

    /**
     * This procedure is called when the solve button is pressed. It will create a new instance
     * of the Solve class and work on solving the problem.
     */
    void solve() {
        ArrayList<String> arr = new SelectionField("Does this system have friction?").selections;
        if (!arr.contains("False")) {
            hasFriction = (!arr.contains("False") && arr.size() > 0);
            if (showSettingsPanel()) {
                final Solver solver = new Solver(this);
                solver.LinkRopesToEntities(ropes, entities, pulleys);
                if (solver.allRopesConnected(ropes)) {
                    ArrayList<String> selections = new ArrayList<>();
                    if (time == null)
                    {
                        selections.add("Time");
                    }
                    if (distance == null)
                    {
                        selections.add("Distance");
                    }
                    if (acceleration == null)
                    {
                        selections.add("Acceleration");
                    }
                    if (iVelocity == null)
                    {
                        selections.add("Initial Velocity");
                    }
                    if (fVelocity == null)
                    {
                        selections.add("Final Velocity");
                    }
                    if (coFriction == null && hasFriction)
                    {
                        selections.add("μ");
                    }
                    selections = new SelectionField("What do you want to find?", selections).selections;
                    List<String> accuracy = new InputField("Object Details - Leave blank if unknown", "Decimal Places", (double)precision).data;
                    if (accuracy.size() > 0) {
                        if (accuracy.get(0).length() > 0) {
                            try {
                                if (Integer.parseInt(accuracy.get(0)) >= 0) {
                                    precision = Integer.parseInt(accuracy.get(0));
                                } else {
                                    gui.showMessageBox("Decimal places must not be negative.", "Error");
                                    return;
                                }
                            } catch (NumberFormatException error) {
                                gui.showMessageBox("Decimal places must be a whole number.", "Error");
                                return;
                            }
                        }
                        if (selections.size() > 0) {
                            solver.calculateSelections(selections);
                        }
                    }
                } else {
                    gui.showMessageBox("Not all Ropes have been connected to an Object.", "Error");
                }
            }
        }
    }

    private boolean showSettingsPanel() {
        ArrayList<String> data = new ArrayList<>(Arrays.asList("Gravity", "Time (s)", "Distance (m)", "Acceleration (m/s/s)", "Initial Velocity (m/s)", "Final Velocity (m/s)"));
        ArrayList<Double> values = new ArrayList<>(Arrays.asList(gravity, time, distance, acceleration, iVelocity, fVelocity));
        if (hasFriction) {
            data.add("μ");
            values.add(coFriction);
        }
        data = new InputField("Settings", data, values).data;
        boolean success = false;
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).length() > 0 && i == 0) {
                try {
                    gravity = Double.parseDouble(data.get(i));
                    success = true;
                } catch (NumberFormatException error) {
                    gui.showMessageBox("Gravity must be a double.", "Error");
                    return false;
                }
            }  else if (data.get(i).length() > 0 && i == 1) {
                try {
                    if (Double.parseDouble(data.get(i)) > 0) {
                        time = Double.parseDouble(data.get(i));
                        success = true;
                    } else {
                        gui.showMessageBox("Time must be greater than 0.", "Error");
                    }
                } catch (NumberFormatException error) {
                    gui.showMessageBox("Time must be a double.", "Error");
                    return false;
                }
            } else if (data.get(i).length() > 0 && i == 2) {
                try {
                    distance = Double.parseDouble(data.get(i));
                    success = true;
                } catch (NumberFormatException error) {
                    gui.showMessageBox("Distance must be a double.", "Error");
                    return false;
                }
            } else if (data.get(i).length() > 0 && i == 3) {
                try {
                    acceleration = Double.parseDouble(data.get(i));
                    success = true;
                } catch (NumberFormatException error) {
                    gui.showMessageBox("Acceleration must be a double.", "Error");
                    return false;
                }
            } else if (data.get(i).length() > 0 && i == 4) {
                try {
                    iVelocity = Double.parseDouble(data.get(i));
                    success = true;
                } catch (NumberFormatException error) {
                    gui.showMessageBox("Initial velocity must be a double.", "Error");
                    return false;
                }
            } else if (data.get(i).length() > 0 && i == 5) {
                try {
                    fVelocity = Double.parseDouble(data.get(i));
                    success = true;
                } catch (NumberFormatException error) {
                    gui.showMessageBox("Final velocity must be a double.", "Error");
                    return false;
                }
            } else if (data.get(i).length() > 0 && i == 6) {
                try {
                    coFriction = Double.parseDouble(data.get(i));
                    success = true;
                } catch (NumberFormatException error) {
                    gui.showMessageBox("μ must be a double.", "Error");
                    return false;
                }
            }
        }
        return success;
    }
}