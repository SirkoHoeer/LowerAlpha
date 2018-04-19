package view.ActionListeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import view.JAlphaNotationGUI;

public class JMenuItemExitViewActionListener implements ActionListener {

	private JAlphaNotationGUI gui;
	public JMenuItemExitViewActionListener(JAlphaNotationGUI gui) {
		this.gui = gui;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		System.exit(0);
	}
}
