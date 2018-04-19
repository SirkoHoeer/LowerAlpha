package controllers.Actionlisteners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;

import controller.JController;
import model.CompileException;
import model.JModel;
import model.JStack;
import view.JAlphaNotationGUI;

public class JButtonStopControllerActionListener implements ActionListener {

	private JAlphaNotationGUI gui;
	private JModel model;
	private JController controller;

	public JButtonStopControllerActionListener(JAlphaNotationGUI gui, JModel model, JController controller) {
		this.gui = gui;
		this.model = model;
		this.controller = controller;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		model.setStop(false);

		String source = this.gui.GetTextSource();

		String[] arrSource = source.split("\\r?\\n");
		// arrSource = Arrays.copyOf(arrSource, arrSource.length + 1);

		for (int j = 0; j < arrSource.length; j++) { // -1
			if (arrSource[j].equals("")) {
				arrSource[j] = " ";
			} else {
				// arrSource[j] = "[" + (j + 1) + "] \t" + arrSource[j];
			}
		}

		// arrSource[arrSource.length - 1] = " ";

		try {
			this.model.compile(source);
			this.gui.ToggleButtonCompile(true);

			// gui.SetListMemorey(model.getMemory());
			// gui.SetListRegister(model.getRegister());
			String[] memory = new String[this.model.getMemoryLabelMap().size()];
			String[] register = new String[this.model.getRegisterLabelMap().size()];

			int counter = 0;
			for (Map.Entry<String, Integer> entry : this.model.getMemoryLabelMap().entrySet()) {
				memory[counter] = "ρ(" + entry.getKey().toString() + ") := " + this.model.getMemory()[entry.getValue()];
				counter++;
			}

			counter = 0;
			for (Map.Entry<String, Integer> entry : this.model.getRegisterLabelMap().entrySet()) {
				register[counter] = entry.getKey().toString() + " := " + this.model.getRegister()[entry.getValue()];
				counter++;
			}

			JStack<Integer> stack = model.getStack();

			String[] sStack = new String[stack.getSize()];
			for (int i = 0; i < sStack.length; i++) {
				sStack[i] = "[" + i + "] =  " + stack.getItemOfList(i).toString();
			}

			Collections.reverse(Arrays.asList(sStack));

			int pc = model.getProgramCounter();

			gui.SetListStack(sStack);
			gui.SetListMemorey(memory);
			gui.SetListRegister(register);

			gui.SetSelectedAfterCompile(1);

			gui.SetListRuntimeDebug(arrSource, pc);

			controller.setCompileSuccessfull(true);

		} catch (CompileException ce) {

			controller.setCompileSuccessfull(false);
			this.gui.ToggleButtonCompile(false);

			int errorline = ce.getErrorLine();

			gui.AppendTextConsole(ce.getMessage() + " at line " + ce.getErrorLine());

			gui.setListRuntimeDebugCompileError(arrSource, ce.getErrorLine(), ce.getMessage());

			System.out.println(ce.getMessage());
		} catch (Exception e1) {
			e1.printStackTrace();
		} finally {

		}

	}
}
