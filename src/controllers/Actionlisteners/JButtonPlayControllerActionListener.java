package controllers.Actionlisteners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import model.JModel;
import view.JAlphaNotationGUI;

public class JButtonPlayControllerActionListener implements ActionListener {

	
	private JAlphaNotationGUI gui;
	private JModel model;
	
	public JButtonPlayControllerActionListener(JAlphaNotationGUI gui, JModel model) {
		this.gui = gui;
		this.model = model;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		model.run();
		
	}
}
