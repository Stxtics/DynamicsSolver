package project;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.*;

import java.awt.Cursor;
import java.awt.Shape;

/**
 * The controller class controls most things that are happening on screen.
 * Moving, resizing, selecting, deselecting shapes all happens here.
 * And some handling of data for entities and ropes.
 */
@SuppressWarnings("serial")
public class Controller implements ActionListener, MouseMotionListener, KeyListener, MouseListener {
    /**
     * Declaration of variables. Multiple array lists to store all the
     * ropes, entities, shapes, and selected shapes. finals to hold the color
     * of selected shapes and the selected stroke. Variables to hold the shape that is being resized,
     * the direction it is being resized, and its position. Also variables to hold the mouses x and y position,
     * and two graphics components.
     */
    private ShapeManager shapeManager;
    private Content content;
    private Gui gui;

    /**
     * Class constructor with gui parameter which is the gui made in the Gui class.
     * Initialises the gui variable to be the Gui, adds action listeners to all the buttons,
     * and adds a key listener to the JPanel that contains the shapes. Also adds a mouse
     * listener and mouse motion listener to the Gui.
     */
    Controller(Gui gui) {
        this.gui = gui;
        gui.addRope.addActionListener(this);
        gui.addObject.addActionListener(this);
        gui.addPulley.addActionListener(this);
        gui.clearGui.addActionListener(this);
        gui.solve.addActionListener(this);
        gui.content.addKeyListener(this);
        gui.content.addMouseListener(this);
        gui.content.addMouseMotionListener(this);
        shapeManager = new ShapeManager(gui);
        content = gui.content;
    }

    /**
     * This procedure is called when a button is clicked. It works because this class
     * is the action listener for all the buttons. It checks the source of the button pressed to work out
     * which button it was then executes the correct procedure.
     */
    public void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getSource() == gui.addRope) {
            shapeManager.addRope();
        } else if (actionEvent.getSource() == gui.addObject) {
            shapeManager.addObject();
        } else if (actionEvent.getSource() == gui.addPulley) {
            shapeManager.addPulley();
        } else if (actionEvent.getSource() == gui.clearGui) {
            shapeManager.clearGui();
        } else if (actionEvent.getSource() == gui.solve) {
            shapeManager.solve();
        }
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
            shapeManager.deleteSelectedShapes();
        }
        if (e.getKeyCode() == KeyEvent.VK_R) {
            shapeManager.addRope();
        }
        if (e.getKeyCode() == KeyEvent.VK_P) {
            shapeManager.addPulley();
        }
        if (e.getKeyCode() == KeyEvent.VK_O) {
            shapeManager.addObject();
        }
        if (e.getKeyCode() == KeyEvent.VK_C) {
            shapeManager.clearGui();
        }
        if (e.getKeyCode() == KeyEvent.VK_S) {
            shapeManager.solve();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub

    }

    /**
     * This procedure is called when the mouse is dragged. If left click is pressed a shape is resized,
     * and if right click is pressed all selected shapes are moved.
     */
    @Override
    public void mouseDragged(MouseEvent e) {
        int prevX = shapeManager.mouseX;
        int prevY = shapeManager.mouseY;
        shapeManager.mouseX = e.getPoint().x;
        shapeManager.mouseY = e.getPoint().y;
        if (SwingUtilities.isRightMouseButton(e)) {
            shapeManager.moveShapes(prevX, prevY);
        } else if (SwingUtilities.isLeftMouseButton(e)) {
            shapeManager.resizeShape(e);
        }
        content.repaint();
    }

    /**
     * Updates the variables mouseX and MouseY to be the current position of
     * the mouse when it is moved if neither left or right click is pressed.
     */
    @Override
    public void mouseMoved(MouseEvent e) {
        if (!SwingUtilities.isRightMouseButton(e) && !SwingUtilities.isLeftMouseButton(e)) {
            shapeManager.mouseX = e.getPoint().x;
            shapeManager.mouseY = e.getPoint().y;
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
     * Called when the mouse is pressed. If left click is pressed it goes through all the shapes
     * checks if it is a line2d or rectangle2d then sees is the mouse is close to it.
     * If it is it checks if control is pressed. If control is pressed it brings up
     * an input field to enter data for the rope or entity. if it is not pressed
     * it selects the shape if it is not selected and deselects if it is selected.
     */
    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            Shape addedShape = shapeManager.selectShape(e);
            if (addedShape == null) { //if no new shape was selected then user might be trying to deselect a shape.
                shapeManager.deselectShape(e);
            }
        }
    }

    /**
     * Procedure is called when a mouse button is released. It tells the program the
     * user is no longer resizing a shape to it sets resizedShape to null, resets
     * the direction and puts the index to outside of the array and it sets the cursor
     * to the default one.
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        shapeManager.resizedShape = null;
        shapeManager.direction = 0;
        shapeManager.index = -1;
        content.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }
}