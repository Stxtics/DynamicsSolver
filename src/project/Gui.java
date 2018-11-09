package project;

import java.awt.*;

/**
 * The gui of the program. It is a JFrame that contains two JPanels where one contains buttons
 * and one where the shapes are drawn.
 */

import javax.swing.*;

@SuppressWarnings("serial")
public class Gui extends JFrame {
	/**
	 * Declaration of buttons and JPanels. The buttons are used for performing tasks like
	 * adding shapes, clearing shapes, and solving the problem.
	 */
	JButton addRope = new JButton("Rope");
	JButton addPulley = new JButton("Pulley");
	JButton addObject = new JButton("Object");
	JButton clearGui = new JButton("Clear");
	JButton solve = new JButton("Solve");
	JPanel jpButtons;
    Content content;

	/**
	 * Constructor for the Gui class. This is called from the main class.
	 * It uses the constructor for JFrame to make a new JFrame then sets some options for it.
	 */
	public Gui() {
		super("Dynamics Solver");
		layoutComponents();
		this.setSize((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth(),
				(int) Toolkit.getDefaultToolkit().getScreenSize().getHeight());
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setFocusable(true);
		this.content.grabFocus();
	}

	/**
	 * This is a method that is called from the controller when the user has entered an incorrect value.
	 * The parameter message is the text that is shown, the title in the windows title. 
	 * This works by creating a JOptionPane.
	 */
	public void showInputError(String message, String title) {
		JOptionPane.showMessageDialog(null, message, title, JOptionPane.INFORMATION_MESSAGE);
	}

	
	/**
	 * This method is called from the constructor of this class. It initialises the JPanels .
	 * Adds the buttons to the JPanel that contains the buttons. Adds the JPanels to the JFrame.
	 * Adds a controller to the Gui.
	 */
	public void layoutComponents() {
        content = new Content();
		this.setLayout(new BorderLayout());
		content.setBackground(Color.WHITE);
		jpButtons = new JPanel(new FlowLayout());
		jpButtons.setBackground(Color.WHITE);
		jpButtons.add(addRope);
		jpButtons.add(addPulley);
		jpButtons.add(addObject);
		jpButtons.add(clearGui);
		jpButtons.add(solve);
		this.add(jpButtons, BorderLayout.NORTH);
		this.add(content, BorderLayout.CENTER);
		this.getContentPane().add(content, BorderLayout.CENTER);
		this.pack();
	}
}