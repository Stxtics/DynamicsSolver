package project;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Line2D;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.*;

import java.awt.Cursor;
import java.awt.Shape;

/**
 * The controller class controls most things that are happening on screen.
 * Moving, resizing, selecting, unselecting shapes all happens here.
 * And some handling of data for entities and ropes.
 */
@SuppressWarnings("serial")
public class ControllerTest implements ActionListener, MouseMotionListener, KeyListener, MouseListener {
    /**
     * Declaration of variables. Multiple array lists to store all the
     * ropes, entities, shapes, and selected shapes. finals to hold the color
     * of selected shapes and the selected stroke. Variables to hold the shape that is being resized,
     * the direction it is being resized, and its position. Also variables to hold the mouses x and y postion,
     * and two graphics components.
     */
    Content content;
    Gui gui;
    ArrayList<Rope> ropes = new ArrayList<>();
    ArrayList<Entity> entities = new ArrayList<>();
    private java.util.List<Integer> positions = new ArrayList<>();

    private int mouseX;
    private int mouseY;
    private Shape resizedShape = null;
    private int direction = 0;
    private int index = -1;

    /**
     * Class constructor with gui parameter which is the gui made in the Gui class.
     * Initialises the gui variable to be the Gui, adds action listeners to all the buttons,
     * and adds a key listener to the JPanel that contains the shapes. Also adds a mouse
     * listener and mouse motion listener to the Gui.
     */
    public ControllerTest(Gui gui) {
        this.gui = gui;
        gui.addRope.addActionListener(this);
        gui.addObject.addActionListener(this);
        gui.addPulley.addActionListener(this);
        gui.clearGui.addActionListener(this);
        gui.solve.addActionListener(this);
        gui.jpContent.addKeyListener(this);
        gui.jpContent.addMouseListener(this);
        gui.jpContent.addMouseMotionListener(this);
        content = gui.content;
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
        if (newShape.contains(e.getPoint())) {
            return true;
        }
        return false;
    }

    /**
     * This procedure is called when a button is clicked. It works because this class
     * is the action listener for all the buttons. It checks the source of the button pressed to work out
     * which button it was then executes the correct procedure.
     */
    public void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getSource() == gui.addRope) {
            addRope();
        } else if (actionEvent.getSource() == gui.addObject) {
            addObject();
        } else if (actionEvent.getSource() == gui.addPulley) {
            addPulley();
        } else if (actionEvent.getSource() == gui.clearGui) {
            clearGui();
        } else if (actionEvent.getSource() == gui.solve) {
            solve();
        }
    }

    /**
     * The procedure is called when the add rope button is pressed. It creates a new
     * rope and adds it to ropes, then creates a Line2D and adds it to shapes and selected shapes.
     * Then it repaints the graphics component and grabs focus of the jpContent JPanel.
     */
    private void addRope() {
        Rope rope = new Rope();
        ropes.add(rope);
        positions.add(ropes.size() - 1);
        Shape shape = new Line2D.Double(140, 140, 640, 140);
        content.shapes.add(shape);
        content.selectedShapes.add(shape);
        content.repaint();
        gui.jpContent.grabFocus();
    }

    /**
     * This procedure is called when the add object button is pressed. It does the same
     * as the addRope procedure but instead of creating a Line2D it makes a Rectangle2D.
     */
    private void addObject() {
        Entity entity = new Entity();
        entities.add(entity);
        positions.add(entities.size() - 1);
        Shape shape = new Rectangle2D.Double(140, 140, 200, 200);
        content.shapes.add(shape);
        content.selectedShapes.add(shape);
        gui.jpContent.repaint();
        gui.jpContent.grabFocus();
    }

    /**
     * This procedure is called when the add pulley button is pressed. It makes
     * a new rope and adds it to the list of ropes, then it also draws a circle with
     * two lines coming off of it.
     */
    private void addPulley() {
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
        gui.jpContent.repaint();
        gui.jpContent.grabFocus();
    }

    /**
     * This procedure is called when the clear button is pressed. It clears all array lists
     * and calls the repaint method to update the graphics component.
     */
    private void clearGui() {
        ropes.clear();
        entities.clear();
        content.shapes.clear();
        content.selectedShapes.clear();
        gui.jpContent.repaint();
        gui.jpContent.grabFocus();
    }

    /**
     * This procedure is called when the solve button is pressed. It will create a new instance
     * of the Solve class and work on solving the problem.
     */
    private void solve() {
        //gui.jpContent.grabFocus();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        // TODO Auto-generated method stub
    }

    /**
     * This procedure is called when a key is released as it is an action listener that was
     * added to the Gui. It checks what key is released and calls the appropriate procedure.
     */
    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_DELETE) {
            int size = content.selectedShapes.size();
            for (int i = 0; i < size; i++) {
                Shape shape = content.selectedShapes.remove(0);
                content.shapes.remove(shape);
            }
            gui.jpContent.repaint();
        }
        if (e.getKeyCode() == KeyEvent.VK_R) {
            addRope();
        }
        if (e.getKeyCode() == KeyEvent.VK_P) {
            addPulley();
        }
        if (e.getKeyCode() == KeyEvent.VK_O) {
            addObject();
        }
        if (e.getKeyCode() == KeyEvent.VK_C) {
            clearGui();
        }
        if (e.getKeyCode() == KeyEvent.VK_S) {
            solve();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub

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
        if (newShape.contains(e.getPoint())) {
            return true;
        }
        return false;
    }

    /**
     * This function checks if the mouse is near to the bottom of a shape. It does this
     * in a similar way to the right function.
     */
    private boolean bottom(Shape shape, MouseEvent e) {
        double x = shape.getBounds().getMinX() - 10, y = shape.getBounds().getMaxY() - 10;
        Shape newShape = new Rectangle2D.Double(x, y, shape.getBounds().getWidth() + 20, 20);
        if (newShape.contains(e.getPoint())) {
            return true;
        }
        return false;
    }

    /**
     * This function checks if the mouse is near to the left of a shape. It does this
     * in a similar way to the right function.
     */
    private boolean left(Shape shape, MouseEvent e) {
        double x = shape.getBounds().getMinX() - 10, y = shape.getBounds().getMinY() - 10;
        Shape newShape = new Rectangle2D.Double(x, y, 20, shape.getBounds().getHeight() + 20);
        if (newShape.contains(e.getPoint())) {
            return true;
        }
        return false;
    }

    /**
     * This function checks if the mouse is near to the top of a shape. It does this
     * in a similar way to the right function.
     */
    private boolean top(Shape shape, MouseEvent e) {
        double x = shape.getBounds().getMinX() - 10, y = shape.getBounds().getMinY() - 10;
        Shape newShape = new Rectangle2D.Double(x, y, shape.getBounds().getWidth() + 20, 20);
        if (newShape.contains(e.getPoint())) {
            return true;
        }
        return false;
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
            gui.setCursor(new Cursor(Cursor.SE_RESIZE_CURSOR));
        } else if ((bottom(shape, e) && left(shape, e) && direction == 0) || direction == 6) {
            direction = 6;
            rectangle = new Rectangle2D.Double(mouseX, shape.getBounds().y,
                    shape.getBounds().getMaxX() - mouseX, mouseY - shape.getBounds().getMinY());
            gui.setCursor(new Cursor(Cursor.SW_RESIZE_CURSOR));
        } else if ((top(shape, e) && right(shape, e) && direction == 0) || direction == 2) {
            direction = 2;
            rectangle = new Rectangle2D.Double(shape.getBounds().x, mouseY,
                    mouseX - shape.getBounds().getMinX(), shape.getBounds().getMaxY() - mouseY);
            gui.setCursor(new Cursor(Cursor.NE_RESIZE_CURSOR));
        } else if ((top(shape, e) && left(shape, e) && direction == 0) || direction == 8) {
            direction = 8;
            rectangle = new Rectangle2D.Double(mouseX, mouseY, shape.getBounds().getMaxX() - mouseX,
                    shape.getBounds().getMaxY() - mouseY);
            gui.setCursor(new Cursor(Cursor.NW_RESIZE_CURSOR));
        } else if ((right(shape, e) && direction == 0) || direction == 3) {
            direction = 3;
            rectangle = new Rectangle2D.Double(shape.getBounds().x, shape.getBounds().y,
                    mouseX - shape.getBounds().getMinX(), shape.getBounds().getHeight());
            gui.setCursor(new Cursor(Cursor.E_RESIZE_CURSOR));
        } else if ((left(shape, e) && direction == 0) || direction == 7) {
            direction = 7;
            rectangle = new Rectangle2D.Double(mouseX, shape.getBounds().getBounds().y,
                    shape.getBounds().getMaxX() - mouseX, shape.getBounds().getHeight());
            gui.setCursor(new Cursor(Cursor.W_RESIZE_CURSOR));
        } else if ((bottom(shape, e) && direction == 0) || direction == 5) {
            direction = 5;
            rectangle = new Rectangle2D.Double(shape.getBounds().x, shape.getBounds().y,
                    shape.getBounds().getWidth(), mouseY - shape.getBounds().getMinY());
            gui.setCursor(new Cursor(Cursor.S_RESIZE_CURSOR));
        } else if ((top(shape, e) && direction == 0) || direction == 1) {
            direction = 1;
            rectangle = new Rectangle2D.Double(shape.getBounds().x, mouseY, shape.getBounds().getWidth(),
                    shape.getBounds().getMaxY() - mouseY);
            gui.setCursor(new Cursor(Cursor.N_RESIZE_CURSOR));
        }
        try {
            // Check to make sure the rectangle does not get too small.
            if (rectangle.getBounds().getWidth() < 20 || rectangle.getBounds().getHeight() < 20) {
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
            gui.setCursor(new Cursor(Cursor.E_RESIZE_CURSOR));
        } else if ((left(shape, e) && direction == 0) || direction == 7) {
            direction = 7;
            line = new Line2D.Double(mouseX, shape.getBounds().y, shape.getBounds().getMaxX(),
                    shape.getBounds().y);
            gui.setCursor(new Cursor(Cursor.W_RESIZE_CURSOR));
        }
        try {
            //Check to make sure it doesn't get too small.
            if (line.getBounds().getWidth() < 20) {
                resizedShape = shape;
            } else if (line != null && direction != 0) {
                index = i;
                resizedShape = line;
                content.shapes.set(i, line);
                int pos = content.selectedShapes.indexOf(shape);
                if (pos != -1) {
                    content.selectedShapes.set(pos, line);
                } else {
                    content. selectedShapes.add(line);
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
            gui.setCursor(new Cursor(Cursor.SE_RESIZE_CURSOR));
        } else if ((bottom(shape, e) && left(shape, e) && direction == 0) || direction == 6) {
            direction = 6;
            circle = new Ellipse2D.Double(mouseX, shape.getBounds().y,
                    shape.getBounds().getMaxX() - mouseX, mouseY - shape.getBounds().getMinY());
            gui.setCursor(new Cursor(Cursor.SW_RESIZE_CURSOR));
        } else if ((top(shape, e) && right(shape, e) && direction == 0) || direction == 2) {
            direction = 2;
            circle = new Ellipse2D.Double(shape.getBounds().x, mouseY,
                    mouseX - shape.getBounds().getMinX(), shape.getBounds().getMaxY() - mouseY);
            gui.setCursor(new Cursor(Cursor.NE_RESIZE_CURSOR));
        } else if ((top(shape, e) && left(shape, e) && direction == 0) || direction == 8) {
            direction = 8;
            circle = new Ellipse2D.Double(mouseX, mouseY, shape.getBounds().getMaxX() - mouseX,
                    shape.getBounds().getMaxY() - mouseY);
            gui.setCursor(new Cursor(Cursor.NW_RESIZE_CURSOR));
        } else if ((right(shape, e) && direction == 0) || direction == 3) {
            direction = 3;
            circle = new Ellipse2D.Double(shape.getBounds().x, shape.getBounds().y,
                    mouseX - shape.getBounds().getMinX(), shape.getBounds().getHeight());
            gui.setCursor(new Cursor(Cursor.E_RESIZE_CURSOR));
        } else if ((left(shape, e) && direction == 0) || direction == 7) {
            direction = 7;
            circle = new Ellipse2D.Double(mouseX, shape.getBounds().getBounds().y,
                    shape.getBounds().getMaxX() - mouseX, shape.getBounds().getHeight());
            gui.setCursor(new Cursor(Cursor.W_RESIZE_CURSOR));
        } else if ((bottom(shape, e) && direction == 0) || direction == 5) {
            direction = 5;
            circle = new Ellipse2D.Double(shape.getBounds().x, shape.getBounds().y,
                    shape.getBounds().getWidth(), mouseY - shape.getBounds().getMinY());
            gui.setCursor(new Cursor(Cursor.S_RESIZE_CURSOR));
        } else if ((top(shape, e) && direction == 0) || direction == 1) {
            direction = 1;
            circle = new Ellipse2D.Double(shape.getBounds().x, mouseY, shape.getBounds().getWidth(),
                    shape.getBounds().getMaxY() - mouseY);
            gui.setCursor(new Cursor(Cursor.N_RESIZE_CURSOR));
        }
        try {
            // Check to make sure the rectangle does not get too small.
            if (circle.getBounds().getWidth() < 20 || circle.getBounds().getHeight() < 20) {
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

    /**
     * This procedure is called when the mouse is dragged. If left click is pressed a shape is resized,
     * and if right click is pressed all selected shapes are moved.
     */
    @Override
    public void mouseDragged(MouseEvent e) {
        int prevX = mouseX;
        int prevY = mouseY;
        mouseX = e.getPoint().x;
        mouseY = e.getPoint().y;
        if (SwingUtilities.isRightMouseButton(e)) {
            for (int i = 0; i < content.selectedShapes.size(); i++) {
                Shape shape = content.selectedShapes.get(i);
                if (shape.getClass() == Line2D.Double.class) { // line2d
                    if (shape.getBounds().x + mouseX - prevX < 0 || shape.getBounds().getMaxX() + mouseX - prevX > gui.jpContent.getWidth()
                            || shape.getBounds().y + mouseY - prevY < 0 || shape.getBounds().getMaxY() + mouseY - prevY > gui.jpContent.getHeight()) {
                        //don't let shape go offscreen
                    } else {
                        Shape line = new Line2D.Double(shape.getBounds().x + mouseX - prevX,
                                shape.getBounds().y + mouseY - prevY, shape.getBounds().x + mouseX - prevX + shape.getBounds().getWidth(),
                                shape.getBounds().y + mouseY - prevY);
                        content.selectedShapes.set(i, line);
                        int pos = content.shapes.indexOf(shape);
                        content. shapes.set(pos, line);
                    }
                } else if (shape.getClass() == Rectangle2D.Double.class) { // if shape is a rectangle2d
                    Shape rectangle = new Rectangle2D.Double(shape.getBounds().x + mouseX - prevX,
                            shape.getBounds().y + mouseY - prevY, shape.getBounds().getWidth(),
                            shape.getBounds().getHeight());
                    content.selectedShapes.set(i, rectangle);
                    int pos = content.shapes.indexOf(shape);
                    content.shapes.set(pos, rectangle);
                } else if (shape.getClass() == Ellipse2D.Double.class) {
                    Shape circle = new Ellipse2D.Double(shape.getBounds().x + mouseX - prevX,
                            shape.getBounds().y + mouseY - prevY, shape.getBounds().getWidth(),
                            shape.getBounds().getHeight());
                    content.selectedShapes.set(i, circle);
                    int pos = content.shapes.indexOf(shape);
                    content.shapes.set(pos, circle);
                }
            }
        } else if (SwingUtilities.isLeftMouseButton(e)) {
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
        gui.jpContent.repaint();
    }

    /**
     * Updates the variables mouseX and MouseY to be the current postion of
     * the mouse when it is moved if neither left or right click is pressed.
     */
    @Override
    public void mouseMoved(MouseEvent e) {
        if (!SwingUtilities.isRightMouseButton(e) && !SwingUtilities.isLeftMouseButton(e)) {
            mouseX = e.getPoint().x;
            mouseY = e.getPoint().y;
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseEntered(MouseEvent arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseExited(MouseEvent arg0) {
        // TODO Auto-generated method stub

    }

    /**
     * Allows the user to enter details for an rope when they ctrl + click on it.
     * The parameter i is the position of the rope in the shapes array. A user can only
     * enter tension for an array so it gets the first value in the data array then checks
     * if it is ok.
     */
    public void enterRopeDetails(int i) {
        java.util.List<String> data = Arrays.asList("Tension");
        java.util.List<Double> values = Arrays.asList(ropes.get(positions.get(i)).getTension());
        try {
            String tensionS = new InputField("Rope Details - Leave blank if unknown", data, values).data.get(0);
            if (tensionS.length() > 0) {
                double tension = Double.parseDouble(tensionS);
                if (tension > 0) {
                    ropes.get(positions.get(i)).setTension(tension);
                } else {
                    gui.showInputError("Tension must be greater than 0.", "Error");
                }
            } else {
                ropes.get(positions.get(i)).setTension(-1);
            }
        } catch (NumberFormatException error) {
            gui.showInputError("Tension must be a double.", "Error");
        }
    }

    /**
     * Allows the user to enter details for an entity when they ctrl + click on it.
     * The parameter i is the position of the entity in the shapes array. The for
     * loop goes through all the values entered, checks if they are ok and if they
     * are it sets those values on the entity.
     */
    public void enterObjectDetails(int i) {
        java.util.List<String> data = Arrays.asList("Weight", "Down force", "Up force", "Left force", "Right force");
        List<Double> values = Arrays.asList(entities.get(positions.get(i)).getMass(), entities.get(positions.get(i)).getDownForce(), entities.get(positions.get(i)).getUpForce(), entities.get(positions.get(i)).getRightForce(), entities.get(positions.get(i)).getLeftForce());
        data = new InputField("Object Details - Leave blank if unknown", data, values).data;
        for (int j = 0; j < data.size(); j++) {
            if (data.get(j).length() > 0 && j == 0) {
                try {
                    double mass = Double.parseDouble(data.get(j));
                    if (mass > 0) {
                        entities.get(positions.get(i)).setMass(mass);
                    } else {
                        gui.showInputError("Weight must be greater than 0.", "Error");
                    }
                } catch (NumberFormatException error) {
                    gui.showInputError("Weight must be a double.", "Error");
                }
            } else if (j == 0) {
                entities.get(positions.get(i)).setMass(-1);
            } else if (data.get(j).length() > 0 && j == 1) {
                try {
                    double downForce = Double.parseDouble(data.get(j));
                    entities.get(positions.get(i)).setDownForce(downForce);
                } catch (NumberFormatException error) {
                    gui.showInputError("Down force must be a double.", "Error");
                }
            } else if (j == 1) {
                entities.get(positions.get(i)).setDownForce(-1);
            } else if (data.get(j).length() > 0 && j == 2) {
                try {
                    double upForce = Double.parseDouble(data.get(j));
                    entities.get(positions.get(i)).setUpForce(upForce);
                } catch (NumberFormatException error) {
                    gui.showInputError("Up force must be a double.", "Error");
                }
            } else if (j == 2) {
                entities.get(positions.get(i)).setUpForce(-1);
            } else if (data.get(j).length() > 0 && j == 3) {
                try {
                    double rightForce = Double.parseDouble(data.get(j));
                    entities.get(positions.get(i)).setRightForce(rightForce);
                } catch (NumberFormatException error) {
                    gui.showInputError("Right force must be a double.", "Error");
                }
            } else if (j == 3) {
                entities.get(positions.get(i)).setRightForce(-1);
            } else if (data.get(j).length() > 0 && j == 4) {
                try {
                    double leftForce = Double.parseDouble(data.get(j));
                    entities.get(positions.get(i)).setLeftForce(leftForce);
                } catch (NumberFormatException error) {
                    gui.showInputError("Left force must be a double.", "Error");
                }
            } else if (j == 4) {
                entities.get(positions.get(i)).setLeftForce(-1);
            }
        }
    }

    /**
     * Called when the mouse is pressed. If left click is pressed it goes through all the shapes
     * checks if it is a line2d or rectangle2d then sees is the mouse is close to it.
     * If it is it checks if control is pressed. If control is pressed it brings up
     * an input field to enter data for the rope or entity. if it is not pressed
     * it selects the shape if it is not selected and unselects if it is selected.
     */
    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            Shape addedShape = null;
            for (int i = content.shapes.size() - 1; i >= 0; i--) {
                if (content.shapes.get(i).getClass() == Line2D.Double.class) {// if shape is line2d
                    if (isCloseTo(content.shapes.get(i), e)) {
                        if (!content.selectedShapes.contains(content.shapes.get(i))) {
                            if (e.isControlDown()) {
                                enterRopeDetails(i);
                            } else {
                                content.selectedShapes.add(content.shapes.get(i));
                                addedShape = content.shapes.get(i);
                                gui.jpContent.repaint();
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
                                gui.jpContent.repaint();
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
                                gui.jpContent.repaint();
                            }
                            break;
                        }
                    }
                }
            }
            if (addedShape == null) { //if no new shape was selected then user might be trying to unselect a shape.
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
                                    gui.jpContent.repaint();
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
                                    gui.jpContent.repaint();
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
                                    gui.jpContent.repaint();
                                }
                                break;
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * Procedure is called when a mouse button is released. It tells the program the
     * user is no longer resizing a shape to it sets resizedShape to null, resets
     * the direction and puts the index to outside of the array and it sets the curson
     * to the default one.
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        resizedShape = null;
        direction = 0;
        index = -1;
        gui.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }
}