package view.ActionListeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;

import view.JAlphaNotationGUI;

public class JMenuItemLanguageViewActionListener implements ActionListener {
	
	public JMenuItemLanguageViewActionListener(JAlphaNotationGUI gui) {
		this.gui = gui;
	}
	
	private JAlphaNotationGUI gui;
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		
		List<String> items = new ArrayList<String>();
		
		for (int i = 0; i < gui.getGUITranslations().size(); i++) {
			items.add(gui.getGUITranslations().get(i).getTranslationName());			
		}
		
		//DefaultComboBoxModel model = new DefaultComboBoxModel(items.toArray());		
		JComboBox cb = new JComboBox<>(items.toArray());
		int result = JOptionPane.showConfirmDialog(null, cb, "Language", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
		
		if (result == JOptionPane.OK_OPTION) {
			gui.SetLanguage(cb.getSelectedIndex());			
		}
		
		
	}
	
}
