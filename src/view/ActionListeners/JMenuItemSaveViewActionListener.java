package view.ActionListeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFileChooser;

import view.JAlphaNotationGUI;

public class JMenuItemSaveViewActionListener implements ActionListener {

	private JAlphaNotationGUI gui;
	public JMenuItemSaveViewActionListener(JAlphaNotationGUI gui) {
		this.gui = gui;		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		JFileChooser fc = new JFileChooser();
		int returnVal = fc.showSaveDialog(fc);		
	}
}
