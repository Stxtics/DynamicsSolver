package project;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class SelectionField extends JPanel {

    public ArrayList<String> selections = new ArrayList<>();

    public SelectionField(String title, Double time, Double distance, Double acceleration, Double iVelocity, Double fVelocity, Double coFriction) {
        super(new GridLayout(0, 2));
        selections.clear();
        ArrayList<Checkbox> checkboxes = new ArrayList<>();
        if (time == null) {
            Checkbox checkbox = new Checkbox("Time", null, false);
            checkboxes.add(checkbox);
            this.add(checkbox);
        }
        if (distance == null) {
            Checkbox checkbox = new Checkbox("Distance", null, false);
            checkboxes.add(checkbox);
            this.add(checkbox);
        }
        if (acceleration == null) {
            Checkbox checkbox = new Checkbox("Acceleration", null, false);
            checkboxes.add(checkbox);
            this.add(checkbox);
        }
        if (iVelocity == null) {
            Checkbox checkbox = new Checkbox("Initial Velocity", null, false);
            checkboxes.add(checkbox);
            this.add(checkbox);
        }
        if (fVelocity == null) {
            Checkbox checkbox = new Checkbox("Final Velocity", null, false);
            checkboxes.add(checkbox);
            this.add(checkbox);
        }
        if (coFriction == null) {
            Checkbox checkbox = new Checkbox("μ", null, false);
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
        }
    }
}
