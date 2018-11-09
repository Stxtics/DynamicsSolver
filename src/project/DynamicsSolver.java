package project;

/** This is the main class of the program that starts up everything.
 * It creates a new Gui and suppresses warnings for unused because I do not use the Gui in this class.
 **/
public class DynamicsSolver {
    @SuppressWarnings("unused")
    public static void main(String[] args) {
        Gui gui = new Gui();
        new Controller(gui);
    }
}