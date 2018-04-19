package view.ActionListeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.swing.JFileChooser;

import view.JAlphaNotationGUI;

public class JButtonSaveViewActionListener implements ActionListener {

	private JAlphaNotationGUI gui;
	public JButtonSaveViewActionListener(JAlphaNotationGUI gui) {
		this.gui = gui;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		JFileChooser fc = new JFileChooser();
		int returnVal = fc.showSaveDialog(fc);
		
		File f = new File(fc.getSelectedFile().getPath());
		try {
			FileOutputStream os = new FileOutputStream(f);
			os.write(gui.GetTextSource().getBytes());			
			os.close();
		} catch (FileNotFoundException e1) {
			
		} catch(IOException e2) {
			
		}
	}
}
