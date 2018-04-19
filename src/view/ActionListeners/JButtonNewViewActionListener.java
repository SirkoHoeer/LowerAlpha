package view.ActionListeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import view.JAlphaNotationGUI;

public class JButtonNewViewActionListener implements ActionListener {

	private JAlphaNotationGUI gui;
	public JButtonNewViewActionListener(JAlphaNotationGUI gui) {
		this.gui = gui;
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		gui.SetTextSource("");
		gui.SetTextConsole("");
		
	    //TODO clear by update, runtime exception null pointer
	    //-> emptry string as argument : might conflict with buttons (pause were enabled)
	    //this.gui.SetListMemorey(null);
	    //this.gui.SetListRegister(null);
	    //this.gui.SetListStack(null);
	    //this.gui.SetListRuntimeDebug(null, 0);
		
		this.gui.SetSelectedAfterCompile(0);
		
		String[] nll = new String[1];
		nll[0] = "\n";
		gui.SetListStack(nll);
        gui.SetListMemorey(nll);
        gui.SetListRegister(nll);	
        gui.SetListRuntimeDebug(nll, 0);	
	}
}
