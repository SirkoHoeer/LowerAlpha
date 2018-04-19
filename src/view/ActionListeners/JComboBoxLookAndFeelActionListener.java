package view.ActionListeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import view.JAlphaNotationGUI;

public class JComboBoxLookAndFeelActionListener implements ActionListener {

	
	private JAlphaNotationGUI gui;
	
	public JComboBoxLookAndFeelActionListener(JAlphaNotationGUI gui) {
		this.gui = gui;
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		
		String[] arr = new String[UIManager.getInstalledLookAndFeels().length];
		
		for (int i = 0; i < arr.length; i++) {
			arr[i] = UIManager.getInstalledLookAndFeels()[i].getClassName();
		}
		
		for (int i = 0; i < arr.length; i++) {
			if (arr[i].contains(gui.getComboBoxLookAndFeel().getSelectedItem().toString())) {
				
				try {
					//System.out.println(arr[i]);
					UIManager.setLookAndFeel(arr[i]);
					SwingUtilities.updateComponentTreeUI(gui.getMainFrame());
					break;
					
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				} catch (InstantiationException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (UnsupportedLookAndFeelException e) {
					e.printStackTrace();
				}
				
				
			}
			
			
		}
		
		

	}

}
