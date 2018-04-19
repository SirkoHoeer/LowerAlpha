package controllers.Actionlisteners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import model.JModel;
import view.JAlphaNotationGUI;

public class JButtonLoadControllerActionListener implements ActionListener {

	
	private JAlphaNotationGUI gui;
	private JModel model;
	
	public JButtonLoadControllerActionListener(JAlphaNotationGUI gui, JModel model) {
		this.gui = gui;
		this.model = model;
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {

	}
}
