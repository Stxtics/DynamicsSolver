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
    int mouseX, mouseY, direction = 0, index = -1;
    Double gravity = 9.8, coFriction, time, distance, acceleration, iVelocity, fVelocity;
    boolean hasFriction = true;
    Shape resizedShape = null;
    Content content;
    Gui gui;
    ArrayList<Rope> ropes = new ArrayList<>();
    ArrayList<Entity> entities = new ArrayList<>();
    List<Integer> positions = new ArrayList<>();

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
        Rope rope = new Rope();
        ropes.add(rope);
        positions.add(ropes.size() - 1);
        Shape shape = new Line2D.Double(140, 140, 640, 140);
        content.shapes.add(shape);
        content.selectedShapes.add(shape);
        content.repaint();
        content.grabFocus();
    }

    /**
     * This procedure is called when the add object button is pressed. It does the same
     * as the addRope procedure but instead of creating a Line2D it makes a Rectangle2D.
     */
    void addObject() {
        Entity entity = new Entity();
        entities.add(entity);
        positions.add(entities.size() - 1);
        Shape shape = new Rectangle2D.Double(140, 140, 200, 200);
        content.shapes.add(shape);
        content.selectedShapes.add(shape);
        content.repaint();
        content.grabFocus();
    }

    /**
     * This procedure is called when the add pulley button is pressed. It makes
     * a new rope and adds it to the list of ropes, then it also draws a circle with
     * two lines coming off of it.
     */
    void addPulley() {
        Rope rope = new Rope();
        ropes.add(rope);
        positions.add(ropes.size() - 1);
        Shape shape = new Ellipse2D.Double(140, 140, 200, 200);
        content.shapes.add(shape);
        content.selectedShapes.add(shape);
        shape = new Line2D.Double(240, 140, 640, 140);
        content.shapes.add(shape);
        content.selectedShapes.add(shape);
        shape = new Line2D.Double(240, 340, 640, 340);
        content.shapes.add(shape);
        content.selectedShapes.add(shape);
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
        content.shapes.clear();
        content.selectedShapes.clear();
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
            if (rectangle != null && (rectangle.getBounds().getWidth() < 20 || rectangle.getBounds().getHeight() < 20)) {
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
            line = new Line2D.Double(shape.getBounds().x, shape.getBounds().y, mouseX, shape.getBounds().y);
            content.setCursor(new Cursor(Cursor.E_RESIZE_CURSOR));
        } else if ((left(shape, e) && direction == 0) || direction == 7) {
            direction = 7;
            line = new Line2D.Double(mouseX, shape.getBounds().y, shape.getBounds().getMaxX(),
                    shape.getBounds().y);
            content.setCursor(new Cursor(Cursor.W_RESIZE_CURSOR));
        }
        try {
            //Check to make sure it doesn't get too small.
            if (line != null && line.getBounds().getWidth() < 20) {
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
            if (circle != null && (circle.getBounds().getWidth() < 20 || circle.getBounds().getHeight() < 20)) {
                resizedShape = shape;
            } else if (circle != null && direction != 0) {
                index = i;
                resizedShape = circle;
                content.shapes.set(i, circle);
                int pos = content.selectedShapes.indexOf(shape);
                if (pos != -1) {
                    content.selectedShapes.set(pos, circle);
                } else {
                    content.selectedShapes.add(circle);
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
                    Shape line = new Line2D.Double(shape.getBounds().x + mouseX - prevX,
                            shape.getBounds().y + mouseY - prevY, shape.getBounds().x + mouseX - prevX + shape.getBounds().getWidth(),
                            shape.getBounds().y + mouseY - prevY);
                    content.selectedShapes.set(i, line);
                    int pos = content.shapes.indexOf(shape);
                    content.shapes.set(pos, line);
                }
            } else if (shape.getClass() == Rectangle2D.Double.class) { // if shape is a rectangle2d
                if (!(shape.getBounds().x + mouseX - prevX < 0 || shape.getBounds().getMaxX() + mouseX - prevX > content.getWidth() // don't let rectangle go off screen
                        || shape.getBounds().y + mouseY - prevY < 0 || shape.getBounds().getMaxY() + mouseY - prevY > content.getHeight())) {
                    Shape rectangle = new Rectangle2D.Double(shape.getBounds().x + mouseX - prevX,
                            shape.getBounds().y + mouseY - prevY, shape.getBounds().getWidth(),
                            shape.getBounds().getHeight());
                    content.selectedShapes.set(i, rectangle);
                    int pos = content.shapes.indexOf(shape);
                    content.shapes.set(pos, rectangle);
                }
            } else if (shape.getClass() == Ellipse2D.Double.class) {
                if (!(shape.getBounds().x + mouseX - prevX < 0 || shape.getBounds().getMaxX() + mouseX - prevX > content.getWidth() // don't let circle go off screen
                        || shape.getBounds().y + mouseY - prevY < 0 || shape.getBounds().getMaxY() + mouseY - prevY > content.getHeight())) {
                    Shape circle = new Ellipse2D.Double(shape.getBounds().x + mouseX - prevX,
                            shape.getBounds().y + mouseY - prevY, shape.getBounds().getWidth(),
                            shape.getBounds().getHeight());
                    content.selectedShapes.set(i, circle);
                    int pos = content.shapes.indexOf(shape);
                    content.shapes.set(pos, circle);
                }
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
                    resizeLine(content.shapes.get(i), e, i);
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
                    int pos = content.shapes.indexOf(shape);
                    content.shapes.set(pos, line);
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
        Double value = ropes.get(positions.get(i)).getTension();
        try {
            List<String> arr = new InputField("Rope Details - Leave blank if unknown", data, value).data;
            if (arr.size() > 0) {
                String tensionS = arr.get(0);
                if (tensionS.length() > 0) {
                    double tension = Double.parseDouble(tensionS);
                    if (tension > 0) {
                        ropes.get(positions.get(i)).setTension(tension);
                    } else {
                        gui.showMessageBox("Tension must be greater than 0.", "Error");
                    }
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
        List<String> data = Arrays.asList("Weight", "Down force", "Up force", "Left force", "Right force");
        List<Double> values = Arrays.asList(entities.get(positions.get(i)).getMass(), entities.get(positions.get(i)).getDownForce(), entities.get(positions.get(i)).getUpForce(), entities.get(positions.get(i)).getLeftForce(), entities.get(positions.get(i)).getRightForce());
        data = new InputField("Object Details - Leave blank if unknown", data, values).data;
        for (int j = 0; j < data.size(); j++) {
            if (data.get(j).length() > 0 && j == 0) {
                try {
                    double mass = Double.parseDouble(data.get(j));
                    if (mass > 0) {
                        entities.get(positions.get(i)).setMass(mass);
                    } else {
                        gui.showMessageBox("Weight must be greater than 0.", "Error");
                    }
                } catch (NumberFormatException error) {
                    gui.showMessageBox("Weight must be a double.", "Error");
                }
            }  else if (data.get(j).length() > 0 && j == 1) {
                try {
                    double downForce = Double.parseDouble(data.get(j));
                    entities.get(positions.get(i)).setDownForce(downForce);
                } catch (NumberFormatException error) {
                    gui.showMessageBox("Down force must be a double.", "Error");
                }
            } else if (data.get(j).length() > 0 && j == 2) {
                try {
                    double upForce = Double.parseDouble(data.get(j));
                    entities.get(positions.get(i)).setUpForce(upForce);
                } catch (NumberFormatException error) {
                    gui.showMessageBox("Up force must be a double.", "Error");
                }
            } else if (data.get(j).length() > 0 && j == 3) {
                try {
                    double leftForce = Double.parseDouble(data.get(j));
                    entities.get(positions.get(i)).setLeftForce(leftForce);
                } catch (NumberFormatException error) {
                    gui.showMessageBox("Left force must be a double.", "Error");
                }
            } else if (data.get(j).length() > 0 && j == 4) {
                try {
                    double rightForce = Double.parseDouble(data.get(j));
                    entities.get(positions.get(i)).setRightForce(rightForce);
                } catch (NumberFormatException error) {
                    gui.showMessageBox("Right force must be a double.", "Error");
                }
            }
        }
    }

    Shape selectShape(MouseEvent e) {
        Shape addedShape;
        for (int i = content.shapes.size() - 1; i >= 0; i--) {
            if (content.shapes.get(i).getClass() == Line2D.Double.class) {// if shape is line2d
                if (isCloseTo(content.shapes.get(i), e)) {
                    if (!content.selectedShapes.contains(content.shapes.get(i))) {
                        if (e.isControlDown()) {
                            enterRopeDetails(i);
                        } else {
                            content.selectedShapes.add(content.shapes.get(i));
                            addedShape = content.shapes.get(i);
                            content.repaint();
                            if (addedShape != null) {
                                return addedShape;
                            }
                        }
                        break;
                    }
                }
            } else if (content.shapes.get(i).getClass() == Rectangle2D.Double.class) {// if shape is rectangle2d
                if (content.shapes.get(i).contains(e.getPoint())) {
                    if (!content.selectedShapes.contains(content.shapes.get(i))) {
                        if (e.isControlDown()) {
                            enterObjectDetails(i);
                        } else {
                            content.selectedShapes.add(content.shapes.get(i));
                            addedShape = content.shapes.get(i);
                            content.repaint();
                            if (addedShape != null) {
                                return addedShape;
                            }
                        }
                        break;
                    }
                }
            } else if (content.shapes.get(i).getClass() == Ellipse2D.Double.class) {
                if (content.shapes.get(i).contains(e.getPoint())) {
                    if (!content.selectedShapes.contains(content.shapes.get(i))) {
                        if (e.isControlDown()) {
                            enterObjectDetails(i);
                        } else {
                            content.selectedShapes.add(content.shapes.get(i));
                            addedShape = content.shapes.get(i);
                            content.repaint();
                            if (addedShape != null) {
                                return addedShape;
                            }
                        }
                        break;
                    }
                }
            }
        }
        return null;
    }

    void deselectShape(MouseEvent e) {
        for (int i = 0; i < content.selectedShapes.size(); i++) {
            for (int j = content.shapes.size() - 1; j >= 0; j--) {
                if (content.shapes.get(j).getClass() == Line2D.Double.class) {// if shape is line2d
                    if (isCloseTo(content.shapes.get(j), e)) {
                        if (e.isControlDown()) {
                            enterRopeDetails(j);
                            i = content.selectedShapes.size();
                        } else {
                            if (content.selectedShapes.get(i) == content.shapes.get(j)) {
                                content.selectedShapes.remove(content.shapes.get(j));
                                i = content.selectedShapes.size();
                            }
                            content.repaint();
                        }
                        break;
                    }
                } else if (content.shapes.get(j).getClass() == Rectangle2D.Double.class) { // if shape is rectangle2d
                    if (content.shapes.get(j).contains(e.getPoint())) {
                        if (e.isControlDown()) {
                            enterObjectDetails(j);
                            i = content.selectedShapes.size();
                        } else {
                            if (content.selectedShapes.get(i) == content.shapes.get(j)) {
                                content.selectedShapes.remove(content.shapes.get(j));
                                i = content.selectedShapes.size();
                            }
                            content.repaint();
                        }
                        break;
                    }
                } else if (content.shapes.get(j).getClass() == Ellipse2D.Double.class) {
                    if (content.shapes.get(j).contains(e.getPoint())) {
                        if (e.isControlDown()) {
                            enterRopeDetails(j);
                            i = content.selectedShapes.size();
                        } else {
                            if (content.selectedShapes.get(i) == content.shapes.get(j)) {
                                content.selectedShapes.remove(content.shapes.get(j));
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
        hasFriction = (new SelectionField("Does this system have friction?").selections.size() > 0);
        if (showSettingsPanel()) {
            final Solver solver = new Solver(this);
            solver.LinkRopesToEntities(ropes, entities);
            if (solver.allRopesConnected(ropes)) {
                ArrayList<String> selections;
                if (hasFriction) {
                    selections = new SelectionField("What do you want to find?", time, distance, acceleration, iVelocity, fVelocity, coFriction).selections;
                } else {
                    selections = new SelectionField("What do you want to find?", time, distance, acceleration, iVelocity, fVelocity, 5.0).selections;
                }
                if (selections.size() > 0) {
                    solver.calculateSelections(selections);
                } else {

                }
            } else {
                gui.showMessageBox("Not all Ropes have been connected to an Object.", "Error");
            }
        }
    }

    boolean showSettingsPanel() {
        List<String> data = Arrays.asList("Gravity", "Time (s)", "Distance (m)", "Acceleration (m/s/s)", "Initial Velocity (m/s)", "Final Velocity (m/s)");
        List<Double> values = Arrays.asList(gravity, time, distance, acceleration, iVelocity, fVelocity);
        if (hasFriction) {
            data = Arrays.asList("Gravity", "Time (s)", "Distance (m)", "Acceleration (m/s/s)", "Initial Velocity (m/s)", "Final Velocity (m/s)", "μ");
            values = Arrays.asList(gravity, time, distance, acceleration, iVelocity, fVelocity, coFriction);
        }
        data = new InputField("Settings", data, values).data;
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).length() > 0 && i == 0) {
                try {
                    gravity = Double.parseDouble(data.get(i));
                } catch (NumberFormatException error) {
                    gui.showMessageBox("Gravity must be a double.", "Error");
                    return false;
                }
            }  else if (data.get(i).length() > 0 && i == 1) {
                try {
                    if (Double.parseDouble(data.get(i)) > 0) {
                        time = Double.parseDouble(data.get(i));
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
                } catch (NumberFormatException error) {
                    gui.showMessageBox("Distance must be a double.", "Error");
                    return false;
                }
            } else if (data.get(i).length() > 0 && i == 3) {
                try {
                    acceleration = Double.parseDouble(data.get(i));
                } catch (NumberFormatException error) {
                    gui.showMessageBox("Acceleration must be a double.", "Error");
                    return false;
                }
            } else if (data.get(i).length() > 0 && i == 4) {
                try {
                    iVelocity = Double.parseDouble(data.get(i));
                } catch (NumberFormatException error) {
                    gui.showMessageBox("Initial velocity must be a double.", "Error");
                    return false;
                }
            } else if (data.get(i).length() > 0 && i == 5) {
                try {
                    fVelocity = Double.parseDouble(data.get(i));
                } catch (NumberFormatException error) {
                    gui.showMessageBox("Final velocity must be a double.", "Error");
                    return false;
                }
            } else if (data.get(i).length() > 0 && i == 6) {
                try {
                    coFriction = Double.parseDouble(data.get(i));
                } catch (NumberFormatException error) {
                    gui.showMessageBox("μ must be a double.", "Error");
                    return false;
                }
            }
        }
        return true;
    }
}
