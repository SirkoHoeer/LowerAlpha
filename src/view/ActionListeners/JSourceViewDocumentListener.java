package view.ActionListeners;

import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

import view.JAlphaNotationGUI;

public class JSourceViewDocumentListener implements DocumentListener {

	private JAlphaNotationGUI gui;
	public JSourceViewDocumentListener(JAlphaNotationGUI gui) {
		this.gui = gui;
	}
	
	@Override
	public void changedUpdate(DocumentEvent e) {
		
	}
	
	private Document document;
	private String source;
	
	private int alpha;
	private int alpha_second;
	private int alpha_third;
	
	private int ro;
	private int ro_second;
	private int ro_third;
	
	private int _if;
	private int _ifeq;
	private int _ifl;
	private int _ifg;
	
	private int divide;
	
	private int gamma;
	
	private int tau;	
	

	@Override
	public void insertUpdate(DocumentEvent e) {
		gui.ToggleButtonCompile(false);
		
		document = e.getDocument();
		source = "";
		
		try {
			source = document.getText(0, document.getLength());
			
			if (source.contains("alpha")) {
				alpha = source.indexOf("alpha");
				
				SwingUtilities.invokeLater(RunAlpha);				
				System.out.println("alpha found");				
			}
			
			if (source.contains("a,")) {
				alpha_second = source.indexOf("a,");
				
				SwingUtilities.invokeLater(RunAlphaSecond);
				System.out.println("alpha second found");
			}
			
			if (source.contains("a;")) {
				alpha_third = source.indexOf("a;");
				
				SwingUtilities.invokeLater(RunAlphaThird);
				System.out.println("alpha third found");
			}
			
			if (source.contains("rho")) {
				ro = source.indexOf("rho");				
				
				SwingUtilities.invokeLater(RunRo);
				System.out.println("rho found");
			}
			
			if (source.contains("r,")) {
				ro_second = source.indexOf("r,");				
				
				SwingUtilities.invokeLater(RunRoSecond);
				System.out.println("rho second found");
			}
			
			if (source.contains("r;")) {
				ro_third = source.indexOf("r;");
				
				SwingUtilities.invokeLater(RunRoThird);
				System.out.println("rho third found");
			}
			
			if (source.contains("gamma")) {
				gamma = source.indexOf("gamma");
				
				SwingUtilities.invokeLater(RunGamma);
				System.out.println("gamma found");
			}
			
			if (source.contains("tau")) {
				tau = source.indexOf("tau");
				
				SwingUtilities.invokeLater(RunTau);
				System.out.println("tau found");
			}
			
			if (source.contains("/")) {
				divide = source.indexOf("/");
				
				SwingUtilities.invokeLater(RunDivide);
				System.out.println("divide found");
			}
			
			if (source.contains("if:")) {
				_if = source.indexOf("if:");
				
				SwingUtilities.invokeLater(RunIf);								
				System.out.println("if found");				
			}
			
			if (source.contains("if=")) {
				_ifeq = source.indexOf("if=");				
				
				SwingUtilities.invokeLater(RunIfeq);
				System.out.println("if equal");				
			}
			
			if (source.contains("if<")) {
				_ifl = source.indexOf("if<");				
				
				SwingUtilities.invokeLater(RunIfl);
				System.out.println("if less");				
			}
			
			if (source.contains("if>")) {
				_ifg = source.indexOf("if>");				
				
				SwingUtilities.invokeLater(RunIfg);
				System.out.println("if greater");				
			}
			
			
			
		} catch(Exception e1) {
			
		}		
	}

	@Override
	public void removeUpdate(DocumentEvent e) {
		
		gui.ToggleButtonCompile(false);
		
		/*
		document = e.getDocument();
		source = "";
		
		try {
			source = document.getText(0, document.getLength());
			if (source.contains("alpha")) {		
				alpha = source.indexOf("alpha");
				source = source.replace("alpha", "α");
				
				SwingUtilities.invokeLater(RunAlpha);
				System.out.println("alpha found");
			}
			
			if (source.contains("ro")) {
				ro = source.indexOf("ro");
				source = source.replace("ro", "ρ");
				
				SwingUtilities.invokeLater(RunRo);				
				System.out.println("ro found");
			}
			
			
			
		} catch(Exception e1) {
			
		}
		*/		
	}
	
	public Runnable RunIf = new Runnable() {
		@Override
		public void run() {
			try {
				document.remove(_if, 3);
				document.insertString(_if, "if var1 comp var2 then goto label", null);
				gui.SetCaretPositonSourceTextField(_if + 3);
			} catch (Exception e) {

			}
			
		}
	};
	
	public Runnable RunIfeq = new Runnable() {
		@Override
		public void run() {
			try {
				document.remove(_ifeq, 3);
				document.insertString(_ifeq, "if var1 = var2 then goto label", null);
				gui.SetCaretPositonSourceTextField(_ifeq + 3);
			} catch (Exception e) {

			}
			
		}
	};
	
	public Runnable RunIfl = new Runnable() {
		@Override
		public void run() {
			try {
				document.remove(_ifl, 3);
				document.insertString(_ifl, "if var1 < var2 then goto label", null);
				gui.SetCaretPositonSourceTextField(_ifl + 3);
			} catch (Exception e) {

			}
			
		}
	};
	
	public Runnable RunIfg = new Runnable() {
		@Override
		public void run() {
			try {
				document.remove(_ifg, 3);
				document.insertString(_ifg, "if var1 > var2 then goto label", null);
				gui.SetCaretPositonSourceTextField(_ifg + 3);
			} catch (Exception e) {

			}
			
		}
	};
	
	
	public Runnable RunAlpha = new Runnable() {
		@Override
		public void run() {
			try {
				document.remove(alpha, 5);
				document.insertString(alpha, "α", null);				
			} catch (BadLocationException e) {
				e.printStackTrace();
			}						
		}
	};
	
	public Runnable RunAlphaSecond = new Runnable() {		
		@Override
		public void run() {
			try {
				document.remove(alpha_second, 2);
				document.insertString(alpha_second, "α :=", null);				
			} catch (BadLocationException e) {
				e.printStackTrace();
			}			
		}
	};
	
	public Runnable RunAlphaThird = new Runnable() {		
		@Override
		public void run() {
			try {
				document.remove(alpha_third, 2);
				document.insertString(alpha_third, "α", null);				
			} catch (BadLocationException e) {
				e.printStackTrace();
			}			
		}
	};
	
	public Runnable RunRo = new Runnable() {		
		@Override
		public void run() {
			try {
				document.remove(ro, 3);
				document.insertString(ro, "ρ", null);				
			} catch (BadLocationException e) {
				e.printStackTrace();
			}			
		}
	};
	
	public Runnable RunRoSecond = new Runnable() {		
		@Override
		public void run() {
			try {
				document.remove(ro_second, 2);
				document.insertString(ro_second, "ρ() :=", null);
				
				int i = gui.getTextAreaConsole().getCaretPosition();				
				gui.SetCaretPositonSourceTextField(ro_second + 2);				
				
			} catch (BadLocationException e) {
				e.printStackTrace();
			}			
		}
	};
	
	public Runnable RunRoThird = new Runnable() {		
		@Override
		public void run() {
			try {
				document.remove(ro_third, 2);
				document.insertString(ro_third, "ρ()", null);
				gui.SetCaretPositonSourceTextField(ro_third + 2);
				
			} catch (BadLocationException e) {
				e.printStackTrace();
			}			
		}
	};
	
	public Runnable RunGamma = new Runnable() {		
		@Override
		public void run() {
			try {
				document.remove(gamma, 5);
				document.insertString(gamma, "γ", null);				
			} catch (BadLocationException e) {
				e.printStackTrace();
			}			
		}
	};
	
	public Runnable RunTau = new Runnable() {		
		@Override
		public void run() {
			try {
				document.remove(tau, 3);
				document.insertString(tau, "τ", null);				
			} catch (BadLocationException e) {
				e.printStackTrace();
			}			
		}
	};
	
	public Runnable RunDivide = new Runnable() {		
		@Override
		public void run() {
			try {
				document.remove(divide, 1);
				document.insertString(divide, "÷", null);				
			} catch (BadLocationException e) {
				e.printStackTrace();
			}			
		}
	};
	
}
