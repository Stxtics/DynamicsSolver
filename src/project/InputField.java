package project;

import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class InputField extends JPanel {
	public List<String> data = new ArrayList<>();;
	
	public InputField(String title, List<String> options, int index) {
		super(new GridLayout(0, 2));
		List<JTextField> fields = new ArrayList<>();
		data.clear();
		for (int i = 0; i < options.size(); i++) {
			super.add(new JLabel(options.get(i) + ": "));
			fields.add(new JTextField(10));
			super.add(fields.get(i));			
		}
		int result = JOptionPane.showConfirmDialog(null, this, title, JOptionPane.OK_CANCEL_OPTION);
		if (result == JOptionPane.OK_OPTION) {
			for (JTextField field : fields) {
				this.data.add(field.getText());
			}
		}
	}
}
