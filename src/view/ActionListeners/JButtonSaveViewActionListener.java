package view.ActionListeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.nio.charset.StandardCharsets;

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
		try (OutputStreamWriter writer =
					 new OutputStreamWriter(new FileOutputStream(f), StandardCharsets.UTF_8)) {
			writer.write(gui.GetTextSource());
		} catch (FileNotFoundException e1) {

		} catch (IOException e2) {

		}
	}
}
