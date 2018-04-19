package controllers.Actionlisteners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.File;
import java.io.FileInputStream;


import javax.swing.JFileChooser;

import model.JModel;
import view.JAlphaNotationGUI;

public class JMenuItemLoadControllerActionListener implements ActionListener {

	private JAlphaNotationGUI gui;
	private JModel model;
	
	public JMenuItemLoadControllerActionListener(JAlphaNotationGUI gui, JModel model) {
		this.gui = gui;
		this.model = model;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if (JAlphaNotationGUI.DEBUG) {
			System.out.println("Menu Item Load has been clicked.");
		}
		JFileChooser fc = new JFileChooser();
		int returnVal = fc.showOpenDialog(fc);
		
		try {
			File file = new File(fc.getSelectedFile().getPath());
			FileInputStream fs = new FileInputStream(file);
			
			byte[] bField = new byte[(int)file.length()];
			fs.read(bField);
			
			String source = new String(bField, "UTF-8");
			
			fs.close();			
			
			gui.SetTextSource(source);
			
		} catch (Exception e2) {
			
		}
		
	}
}
