package view.ActionListeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;

import javax.swing.JFileChooser;

import view.JAlphaNotationGUI;

public class JButtonLoadViewActionListener implements ActionListener {

	
	private JAlphaNotationGUI gui;
	public JButtonLoadViewActionListener(JAlphaNotationGUI gui) {
		this.gui = gui;
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
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
