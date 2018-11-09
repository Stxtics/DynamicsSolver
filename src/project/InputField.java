package project;

import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import java.util.ArrayList;
import java.util.List;

/**
 * The InputField class is used when the user wants to enter data for
 * a Rope or Entity. It works by having a title and an array list of strings passed
 * in to create the correct field.
 */
@SuppressWarnings("serial")
public class InputField extends JPanel {
    // data is used to store the data that the user enters into the input field
    public List<String> data = new ArrayList<>();

    /**
     * Creates a new InputField with the specified parameters to make it work.
     * It uses a JOptionPane with JTextFields to allow the user to enter data.
     */

    public InputField(String title, List<String> options, List<Double> values) {
        super(new GridLayout(0, 2));
        List<JTextField> fields = new ArrayList<>();
        data.clear();
        for (int i = 0; i < options.size(); i++) {
            super.add(new JLabel(options.get(i) + ": "));
            fields.add(new JTextField(10));
            if (values.get(i) != 0.0 && values.get(i) != -1.0) {
                fields.get(i).setText(values.get(i).toString());
            }
            super.add(fields.get(i));
        }
        int result = JOptionPane.showConfirmDialog(null, this, title, JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            for (JTextField field : fields) {
                this.data.add(field.getText());
            }
        }
    }

    public InputField(String title, String option, double value) {
        super(new GridLayout(0, 2));
        JTextField field = new JTextField(10);
        data.clear();
        super.add(new JLabel(option + ": "));
        if (value != 0.0 && value != -1.0) {
            field.setText(String.valueOf(value));
        }
        super.add(field);
        int result = JOptionPane.showConfirmDialog(null, this, title, JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            data.add(field.getText());
        }
    }
}