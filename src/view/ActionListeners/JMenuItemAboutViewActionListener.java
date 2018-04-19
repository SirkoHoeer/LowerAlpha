package view.ActionListeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;



import javax.swing.JOptionPane;

import view.JAlphaNotationGUI;

public class JMenuItemAboutViewActionListener implements ActionListener {

	private JAlphaNotationGUI gui;	
	public JMenuItemAboutViewActionListener(JAlphaNotationGUI gui) {
		this.gui = gui;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {		
		
		String display = "Developed by Jan Mueller, Sirko Höer & Maximilian S.";
		display += "\n\nBackend developers: Jan Mueller, Sirko Höer & Maximilian S.";
		display += "\nFrontend developer: Maximilian S.";
		display += "\n\nTrnslation German: Maximilian S.";
		display += "\nTranslation English: Maximilian S.";
		display += "\nTranslation Russian: Евгения У.";
		display += "\nTranslation Japanese: Julia H.";
		display += "\nTranslation Polish: Kinga K.";
		display += "\nTranslation French: exceptionptr";
		
		JOptionPane.showMessageDialog(null, display);		
	}
}
