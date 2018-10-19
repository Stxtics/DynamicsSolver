package project;

import javax.swing.SwingUtilities;
/** This is the main class of the program that starts up everything.
 * It creates a new Gui and suppresses warnings for unused because I do not use the Gui in this class.
 **/
public class DynamicsSolver {
    @SuppressWarnings("unused")
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                Gui gui = new Gui();
            }
        });
    }
}