package view.ActionListeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.swing.JFileChooser;

import view.JAlphaNotationGUI;

public class JLoadViewActionListener implements ActionListener {


	private JAlphaNotationGUI gui;
	public JLoadViewActionListener(JAlphaNotationGUI gui) {
		this.gui = gui;
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		JFileChooser fc = new JFileChooser();
		int returnVal = fc.showOpenDialog(fc);
		if(returnVal != JFileChooser.APPROVE_OPTION){
			return;
		}
		String path = fc.getSelectedFile().getAbsolutePath();
		gui.SetCurrentFilePath(path);

		try {
			String source = new String(Files.readAllBytes(Paths.get(path)), StandardCharsets.UTF_8);
			gui.SetTextSource(source);
		} catch (IOException e2) {

		}

	}
}
