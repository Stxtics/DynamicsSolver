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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.*;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.Stroke;

@SuppressWarnings("serial")
public class Controller extends JPanel implements ActionListener, MouseMotionListener, KeyListener, MouseListener {
	Gui gui;
	ArrayList<Rope> ropes = new ArrayList<Rope>();
	ArrayList<Entity> entities = new ArrayList<Entity>();
	private List<Shape> shapes = new ArrayList<>();
	private static final Color SELECTED_COLOR = Color.red;
	private static final Stroke SELECTED_STROKE = new BasicStroke(4f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0,
			new float[] { 9 }, 0);
	private List<Shape> selectedShapes = new ArrayList<>();
	private int mouseX;
	private int mouseY;
	private Shape resizedShape = null;
	private int direction = 0;
	private int index = -1;
	Graphics2D g2 = null;
	Graphics2D newG2 = null;

	public Controller(Gui gui) {
		this.gui = gui;
		this.setBackground(Color.WHITE);
		gui.addRope.addActionListener(this);
		gui.addObject.addActionListener(this);
		gui.addPulley.addActionListener(this);
		gui.clearGui.addActionListener(this);
		gui.solve.addActionListener(this);
		gui.jpContent.addKeyListener(this);
		addMouseListener(this);
		addMouseMotionListener(this);
	}

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

	private void addRope() {
		Rope rope = new Rope();
		ropes.add(rope);
		Shape shape = new Line2D.Double(140, 140, 640, 140);
		shapes.add(shape);
		selectedShapes.add(shape);
		repaint();
		gui.jpContent.grabFocus();
	}

	private void addObject() {
		Entity entity = new Entity();
		entities.add(entity);
		Shape shape = new Rectangle2D.Double(140, 140, 200, 200);
		shapes.add(shape);
		selectedShapes.add(shape);
		repaint();
		gui.jpContent.grabFocus();
	}

	private void addPulley() {
		Rope rope = new Rope();
		ropes.add(rope);
		gui.jpContent.grabFocus();
	}

	private void clearGui() {
		ropes.clear();
		entities.clear();
		shapes.clear();
		selectedShapes.clear();
		repaint();
		gui.jpContent.grabFocus();
	}

	private void solve() {
		gui.jpContent.grabFocus();
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_DELETE) {
			int size = selectedShapes.size();
			for (int i = 0; i < size; i++) {
				Shape shape = selectedShapes.remove(0);
				shapes.remove(shape);
			}
			repaint();
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

	private boolean right(Shape shape, MouseEvent e) {
		double x = shape.getBounds().getMaxX() - 10, y = shape.getBounds().getMinY() - 10;
		Shape newShape = new Rectangle2D.Double(x, y, 20, shape.getBounds().getHeight() + 20);
		if (newShape.contains(e.getPoint())) {
			return true;
		}
		return false;
	}

	private boolean bottom(Shape shape, MouseEvent e) {
		double x = shape.getBounds().getMinX() - 10, y = shape.getBounds().getMaxY() - 10;
		Shape newShape = new Rectangle2D.Double(x, y, shape.getBounds().getWidth() + 20, 20);
		if (newShape.contains(e.getPoint())) {
			return true;
		}
		return false;
	}

	private boolean left(Shape shape, MouseEvent e) {
		double x = shape.getBounds().getMinX() - 10, y = shape.getBounds().getMinY() - 10;
		Shape newShape = new Rectangle2D.Double(x, y, 20, shape.getBounds().getHeight() + 20);
		if (newShape.contains(e.getPoint())) {
			return true;
		}
		return false;
	}

	private boolean top(Shape shape, MouseEvent e) {
		double x = shape.getBounds().getMinX() - 10, y = shape.getBounds().getMinY() - 10;
		Shape newShape = new Rectangle2D.Double(x, y, shape.getBounds().getWidth() + 20, 20);
		if (newShape.contains(e.getPoint())) {
			return true;
		}
		return false;
	}
	
	private void resizeRectangle(Shape shape, MouseEvent e, int i) {
		Shape rectangle = null;
		if ((bottom(shape, e) && right(shape, e) && direction == 0) || direction == 4) {
			direction = 4;
			rectangle = new Rectangle2D.Double(shape.getBounds().x, shape.getBounds().y,
					mouseX - shape.getBounds().getMinX(),
					mouseY - shape.getBounds().getMinY());
			this.setCursor(new Cursor(Cursor.SE_RESIZE_CURSOR));
		} else if ((bottom(shape, e) && left(shape, e) && direction == 0) || direction == 6) {
			direction = 6;
			rectangle = new Rectangle2D.Double(mouseX, shape.getBounds().y,
					shape.getBounds().getMaxX() - mouseX, mouseY - shape.getBounds().getMinY());
			this.setCursor(new Cursor(Cursor.SW_RESIZE_CURSOR));
		} else if ((top(shape, e) && right(shape, e) && direction == 0) || direction == 2) {
			direction = 2;
			rectangle = new Rectangle2D.Double(shape.getBounds().x, mouseY,
					mouseX - shape.getBounds().getMinX(), shape.getBounds().getMaxY() - mouseY);
			this.setCursor(new Cursor(Cursor.NE_RESIZE_CURSOR));
		} else if ((top(shape, e) && left(shape, e) && direction == 0) || direction == 8) {
			direction = 8;
			rectangle = new Rectangle2D.Double(mouseX, mouseY, shape.getBounds().getMaxX() - mouseX,
					shape.getBounds().getMaxY() - mouseY);
			this.setCursor(new Cursor(Cursor.NW_RESIZE_CURSOR));
		} else if ((right(shape, e) && direction == 0) || direction == 3) {
			direction = 3;
			rectangle = new Rectangle2D.Double(shape.getBounds().x, shape.getBounds().y,
					mouseX - shape.getBounds().getMinX(), shape.getBounds().getHeight());
			this.setCursor(new Cursor(Cursor.E_RESIZE_CURSOR));
		} else if ((left(shape, e)  && direction == 0) || direction == 7) {
			direction = 7;
			rectangle = new Rectangle2D.Double(mouseX, shape.getBounds().getBounds().y,
					shape.getBounds().getMaxX() - mouseX, shape.getBounds().getHeight());
			this.setCursor(new Cursor(Cursor.W_RESIZE_CURSOR));
		} else if ((bottom(shape, e) && direction == 0) || direction == 5) {
			direction = 5;
			rectangle = new Rectangle2D.Double(shape.getBounds().x, shape.getBounds().y,
					shape.getBounds().getWidth(), mouseY - shape.getBounds().getMinY());
			this.setCursor(new Cursor(Cursor.S_RESIZE_CURSOR));
		} else if ((top(shape, e) && direction == 0) || direction == 1) {
			direction = 1;
			rectangle = new Rectangle2D.Double(shape.getBounds().x, mouseY, shape.getBounds().getWidth(),
					shape.getBounds().getMaxY() - mouseY);
			this.setCursor(new Cursor(Cursor.N_RESIZE_CURSOR));
		}
		try {
			if (rectangle.getBounds().getWidth() < 20 || rectangle.getBounds().getHeight() < 20) {
				resizedShape = shape;
			} else if (rectangle != null && direction != 0) {
				index = i;
				resizedShape = rectangle;
				shapes.set(i, rectangle);
				int pos = selectedShapes.indexOf(shape);
				if (pos != -1) {
					selectedShapes.set(pos, rectangle);
				} else {
					selectedShapes.add(rectangle);
				}
			}
		} catch (NullPointerException e1) {
			// ignore
		} 
	}
	
	private void resizeLine(Shape shape, MouseEvent e, int i) {
		Shape line = null;
		if ((right(shape, e) && direction == 0) || direction == 3) {
			direction = 3;
			line = new Line2D.Double(shape.getBounds().x, shape.getBounds().y, mouseX, shape.getBounds().y);
			this.setCursor(new Cursor(Cursor.E_RESIZE_CURSOR));
		} else if ((left(shape, e) && direction == 0) || direction == 7) {
			direction = 7;
			line = new Line2D.Double(mouseX, shape.getBounds().y, shape.getBounds().getMaxX(),
					shape.getBounds().y);
			this.setCursor(new Cursor(Cursor.W_RESIZE_CURSOR));
		}
		try {
			if (line.getBounds().getWidth() < 20) {
				resizedShape = shape;
			} else if (line != null && direction != 0){
				index = i;
				resizedShape = line;
				shapes.set(i, line);
				int pos = selectedShapes.indexOf(shape);
				if (pos != -1) {
					selectedShapes.set(pos, line);
				} else {
					selectedShapes.add(line);
				}
			}
		} catch (NullPointerException e1) {
			// ignore
		}
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		int prevX = mouseX;
		int prevY = mouseY;
		mouseX = e.getPoint().x;
		mouseY = e.getPoint().y;
		if (SwingUtilities.isRightMouseButton(e)) {
			for (int i = 0; i < selectedShapes.size(); i++) {
				Shape shape = selectedShapes.get(i);
				if (!shape.contains(shape.getBounds().getCenterX(), shape.getBounds().getCenterY(), 1, 1)) { // line2d
					Shape line = new Line2D.Double(shape.getBounds().x + mouseX - prevX,
							shape.getBounds().y + mouseY - prevY, shape.getBounds().x + mouseX - prevX + shape.getBounds().getWidth(),
							shape.getBounds().y + mouseY - prevY);
					selectedShapes.set(i, line);
					int pos = shapes.indexOf(shape);
					shapes.set(pos, line);
				} else { // if shape is a rectangle2d
					Shape rectangle = new Rectangle2D.Double(shape.getBounds().x + mouseX - prevX,
							shape.getBounds().y + mouseY - prevY, shape.getBounds().getWidth(),
							shape.getBounds().getHeight());
					selectedShapes.set(i, rectangle);
					int pos = shapes.indexOf(shape);
					shapes.set(pos, rectangle);
				}
			}
		} else if (SwingUtilities.isLeftMouseButton(e)) {
			if (resizedShape != null && direction > 0 && index > -1) {
				if (!resizedShape.contains(resizedShape.getBounds().getCenterX(),
						resizedShape.getBounds().getCenterY(), 1, 1)) { // if shape is line2d
					resizeLine(resizedShape, e, index);
				} else { // if shape is a rectangle2d
					resizeRectangle(resizedShape, e, index);
				}
			} else {
				for (int i = 0; i < shapes.size(); i++) {
					if (!shapes.get(i).contains(shapes.get(i).getBounds().getCenterX(),
							shapes.get(i).getBounds().getCenterY(), 1, 1)) { // if shape is line2d
						resizeLine(shapes.get(i), e, i);
					} else { // if shape is a rectangle2d
						if (isCloseTo(shapes.get(i), e)) {
							resizeRectangle(shapes.get(i), e, i);
						}
					}
				}
			}
		}
		repaint();
	}

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

	@Override
	public void mousePressed(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON1) {
			Shape addedShape = null;
			for (int i = shapes.size() - 1; i >= 0; i--) {
				if (!shapes.get(i).contains(shapes.get(i).getBounds().getCenterX(),
						shapes.get(i).getBounds().getCenterY(), 1, 1)) {// if shape is line2d
					if (isCloseTo(shapes.get(i), e)) {
						if (!selectedShapes.contains(shapes.get(i))) {
							if (e.isControlDown()) {
								List<String> data = Arrays.asList("Tension");
								try {
									String tensionS = new InputField("Rope Details - Leave blank if unknown", data, i).data.get(0);
									if (tensionS.length() > 0) {
										double tension = Double.parseDouble(tensionS);
										if (tension > 0) {
											ropes.get(i).setTension(tension);
										} else {
											gui.showInputError("Tension must be greater than 0.", "Error");
										}
									} else {
										ropes.get(i).setTension(-1);
									}
								} catch (NumberFormatException error) {
									gui.showInputError("Tension must be a double.", "Error");
								}
							} else {
								selectedShapes.add(shapes.get(i));
								addedShape = shapes.get(i);
								repaint();
							}
							break;
						}
					}
				} else {// if shape is rectangle2d
					if (shapes.get(i).contains(e.getPoint())) {
						if (!selectedShapes.contains(shapes.get(i))) {
							if (e.isControlDown()) {
								List<String> data = Arrays.asList("Weight", "Down force", "Up force", "Left force", "Right force");
								data = new InputField("Object Details - Leave blank if unknown", data, i).data;
								for (int j = 0; j < data.size(); j++) {
									if (data.get(j).length() > 0 && j == 0) {
										try {
											double mass = Double.parseDouble(data.get(j));
											if (mass > 0) {
												entities.get(i).setMass(mass);
											} else {
												gui.showInputError("Weight must be greater than 0.", "Error");
											}
										} catch (NumberFormatException error) {
											gui.showInputError("Weight must be a double.", "Error");
										}
									} else if (j == 0) {
										entities.get(i).setMass(-1);
									} else if (data.get(j).length() > 0 && j == 1) {
										try {
											double downForce = Double.parseDouble(data.get(j));
											entities.get(i).setDownForce(downForce);
										} catch (NumberFormatException error) {
											gui.showInputError("Down force must be a double.", "Error");
										}
									} else if (j == 1) {
										entities.get(i).setDownForce(-1);
									} else if (data.get(j).length() > 0 && j == 2) {
										try {
											double upForce = Double.parseDouble(data.get(j));
											entities.get(i).setUpForce(upForce);
										} catch (NumberFormatException error) {
											gui.showInputError("Up force must be a double.", "Error");
										}
									}  else if (j == 2) {
										entities.get(i).setUpForce(-1);
									} else if (data.get(j).length() > 0 && j == 3) {
										try {
											double rightForce = Double.parseDouble(data.get(j));
											entities.get(i).setRightForce(rightForce);
										} catch (NumberFormatException error) {
											gui.showInputError("Right force must be a double.", "Error");
										}
									}  else if (j == 3) {
										entities.get(i).setRightForce(-1);
									} else if (data.get(j).length() > 0 && j == 4) {
										try {
											double leftForce = Double.parseDouble(data.get(j));
											entities.get(i).setLeftForce(leftForce);
										} catch (NumberFormatException error) {
											gui.showInputError("Left force must be a double.", "Error");
										}
									}  else if (j == 4) {
										entities.get(i).setLeftForce(-1);
									} 
								}
							} else {
								selectedShapes.add(shapes.get(i));
								addedShape = shapes.get(i);
								repaint();
								break;
							}
						}
					}
				}
			}
			if (addedShape == null) {
				for (int i = 0; i < selectedShapes.size(); i++) {
					for (int j = shapes.size() - 1; j >= 0; j--) {
						if (!shapes.get(j).contains(shapes.get(j).getBounds().getCenterX(),
								shapes.get(j).getBounds().getCenterY(), 1, 1)) {// if shape is line2d
							if (isCloseTo(shapes.get(j), e)) {
								if (e.isControlDown()) {
									List<String> data = Arrays.asList("Tension");
									try {
										String tensionS = new InputField("Rope Details - Leave blank if unknown", data, j).data.get(0);
										if (tensionS.length() > 0) {
											double tension = Double.parseDouble(tensionS);
											if (tension > 0) {
												ropes.get(i).setTension(tension);
											} else {
												gui.showInputError("Tension must be greater than 0.", "Error");
											}
										} else {
											ropes.get(i).setTension(-1);
										}
									} catch (NumberFormatException error) {
										gui.showInputError("Tension must be a double.", "Error");
									}
								} else {
									if (selectedShapes.get(i) == shapes.get(j)) {
										selectedShapes.remove(shapes.get(j));
									}
									repaint();
									break;
								}
							}
						} else { // if shape is rectangle2d
							if (shapes.get(j).contains(e.getPoint())) {
								if (e.isControlDown()) {
									List<String> data = Arrays.asList("Weight", "Down force", "Up force", "Left force", "Right force");
									new InputField("Object Details - Leave blank if unknown", data, j);
									for (int k = 0; k < data.size(); k++) {
										if (data.get(k).length() > 0 && k == 0) {
											try {
												double mass = Double.parseDouble(data.get(k));
												if (mass > 0) {
													entities.get(j).setMass(mass);
												} else {
													gui.showInputError("Weight must be greater than 0.", "Error");
												}
											} catch (NumberFormatException error) {
												gui.showInputError("Weight must be a double.", "Error");
											}
										} else if (k == 0) {
											entities.get(j).setMass(-1);
										} else if (data.get(k).length() > 0 && k == 1) {
											try {
												double downForce = Double.parseDouble(data.get(k));
												entities.get(j).setDownForce(downForce);
											} catch (NumberFormatException error) {
												gui.showInputError("Down force must be a double.", "Error");
											}
										} else if (k == 1) {
											entities.get(j).setDownForce(-1);
										} else if (data.get(k).length() > 0 && k == 2) {
											try {
												double upForce = Double.parseDouble(data.get(k));
												entities.get(j).setUpForce(upForce);
											} catch (NumberFormatException error) {
												gui.showInputError("Up force must be a double.", "Error");
											}
										}  else if (k == 2) {
											entities.get(j).setUpForce(-1);
										} else if (data.get(k).length() > 0 && k == 3) {
											try {
												double rightForce = Double.parseDouble(data.get(k));
												entities.get(j).setRightForce(rightForce);
											} catch (NumberFormatException error) {
												gui.showInputError("Right force must be a double.", "Error");
											}
										}  else if (k == 3) {
											entities.get(j).setRightForce(-1);
										} else if (data.get(k).length() > 0 && k == 4) {
											try {
												double leftForce = Double.parseDouble(data.get(k));
												entities.get(j).setLeftForce(leftForce);
											} catch (NumberFormatException error) {
												gui.showInputError("Left force must be a double.", "Error");
											}
										}  else if (k == 4) {
											entities.get(j).setLeftForce(-1);
										} 
									}
								} else {
									if (selectedShapes.get(i) == shapes.get(j)) {
										selectedShapes.remove(shapes.get(j));
									}
									repaint();
									break;
								}
							}
						}
					}
				}
			}
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		resizedShape = null;
		direction = 0;
		index = -1;
		this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
	}
}