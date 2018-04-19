package view.ActionListeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import view.JAlphaNotationGUI;

public class JButtonPlayViewActionListener implements ActionListener {

	
	private JAlphaNotationGUI gui;
	public JButtonPlayViewActionListener(JAlphaNotationGUI gui) {
		this.gui = gui;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		gui.setEnableButtonStepInto(false);
		gui.setEnableButtonCompile(false);
		gui.setEnableButtonPlay(false);			
	}
}
