package model;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map.Entry;

import view.JAlphaNotationGUI;

public class JRunnableInvokePlay implements Runnable {

	
	private JAlphaNotationGUI gui;
	private JModel model;
	
	public JRunnableInvokePlay(JAlphaNotationGUI gui, JModel model) {
		this.gui = gui;
		this.model = model;
	}
	
	@Override
	public void run() {
		
		try {
			
			String[] memory = new String[this.model.getMemoryLabelMap().size()];
			String[] register = new String[this.model.getRegisterLabelMap().size()];

			int counter = 0;
			for (Entry<String, Integer> entry : this.model.getMemoryLabelMap().entrySet()) {
				memory[counter] = "ρ(" + entry.getKey().toString() + ") := "
						+ this.model.getMemory()[entry.getValue()];
				counter++;
			}

			counter = 0;
			for (Entry<String, Integer> entry : this.model.getRegisterLabelMap().entrySet()) {
				register[counter] = entry.getKey().toString() + " := "
						+ this.model.getRegister()[entry.getValue()];
				counter++;
			}

			JStack<Integer> stack = model.getStack();

			String[] sStack = new String[stack.getSize()];
			for (int i = 0; i < sStack.length; i++) {
				sStack[i] = "[" + i + "] =  " + stack.getItemOfList(i).toString();
			}

			Collections.reverse(Arrays.asList(sStack));

			String source = this.gui.GetTextSource();

			String[] arrSource = source.split("\\r?\\n");
			
			//arrSource = Arrays.copyOf(arrSource, arrSource.length + 1);

			//int pc = model.getProgramCounter();
			//System.out.println(pc);
			// pc++;
			int ln = model.getLineIndex();
			
			for (int j = 0; j < arrSource.length; j++) { // - 1
				if (arrSource[j].equals("")) {
					arrSource[j] = " ";
				} else {
					//arrSource[j] = "[" + (j + 1) + "] \t" + arrSource[j];
				}
			}
			
			//arrSource[arrSource.length - 1] = " ";
			
			
			gui.SetListStack(sStack);
			gui.SetListMemorey(memory);
			gui.SetListRegister(register);
			//gui.SetListRuntimeDebug(arrSource, pc);
			gui.SetListRuntimeDebug(arrSource, ln);

			
		} catch (Exception e) {

		}
	}
}
