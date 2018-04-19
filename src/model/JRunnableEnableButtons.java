package model;

import view.JAlphaNotationGUI;

public class JRunnableEnableButtons implements Runnable {

	

	private JAlphaNotationGUI gui;
	private JModel model;
	
	public JRunnableEnableButtons(JAlphaNotationGUI gui, JModel model) {
		this.gui = gui;
		this.model = model;
	}
	
	@Override
	public void run() {
		gui.setEnableButtonStepInto(true);
		gui.setEnableButtonCompile(true);
		gui.setEnableButtonPlay(true);		
	}
}
