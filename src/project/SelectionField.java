package project;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class SelectionField extends JPanel {

    public ArrayList<String> selections = new ArrayList<>();

    public SelectionField(String title, ArrayList<String> options) {
        super(new GridLayout(0, 2));
        selections.clear();
        ArrayList<Checkbox> checkboxes = new ArrayList<>();
        for (String option : options) {
            Checkbox checkbox = new Checkbox(option, null, false);
            checkboxes.add(checkbox);
            this.add(checkbox);
        }
        int result = JOptionPane.showConfirmDialog(null, this, title, JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            for (Checkbox checkbox : checkboxes) {
                if (checkbox.getState()) {
                    selections.add(checkbox.getLabel());
                }
            }
        }
    }

    public SelectionField(String title) {
        super(new GridLayout(0, 2));
        Checkbox checkbox = new Checkbox("Yes/no", null, false);
        this.add(checkbox);
        int result = JOptionPane.showConfirmDialog(null, this, title, JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            if (checkbox.getState()) {
                selections.add(checkbox.getLabel());
            }
        } else if (result == JOptionPane.CANCEL_OPTION) {
            selections.add("False");
        }
    }
}
