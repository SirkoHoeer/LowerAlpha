package view.ActionListeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import view.JAlphaNotationGUI;

public class JMenuItemNewViewActionListener implements ActionListener {	
	
	private JAlphaNotationGUI gui;
	public JMenuItemNewViewActionListener(JAlphaNotationGUI gui) {
		this.gui = gui;		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		gui.SetTextSource("");
		gui.SetTextConsole("");
	}
}
