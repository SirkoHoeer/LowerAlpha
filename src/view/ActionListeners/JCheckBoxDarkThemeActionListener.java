package view.ActionListeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import view.JAlphaNotationGUI;

public class JCheckBoxDarkThemeActionListener implements ActionListener {

	
	private JAlphaNotationGUI gui;
	public JCheckBoxDarkThemeActionListener(JAlphaNotationGUI gui) {
		this.gui = gui;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		
		if (!gui.getCheckBoxDarkTheme().isSelected()) {
			gui.ColorNormalTheme();
		} else {
			gui.ColorDarkTheme();
		}
	}
}
