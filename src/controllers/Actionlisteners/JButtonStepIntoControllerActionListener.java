package controllers.Actionlisteners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import model.JModel;
import model.JStack;
import view.JAlphaNotationGUI;

public class JButtonStepIntoControllerActionListener implements ActionListener {

	private JAlphaNotationGUI gui;
	private JModel model;

	public JButtonStepIntoControllerActionListener(JAlphaNotationGUI gui,
			JModel model) {
		this.gui = gui;
		this.model = model;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (JAlphaNotationGUI.DEBUG) {
			System.out.println("Button Step-Into has been clicked. controller");
		}
		model.Step();

		HashMap<String, Integer> memoryToList = new HashMap<>();
		for (Entry<String, Integer> entry : this.model.getMemoryLabelMap().entrySet()) {
			memoryToList.put(entry.getKey(),this.model.getMemory().get(entry.getKey()));
		}
		for (Entry<String, Integer> entry : this.model.getMemory().entrySet()) {
			memoryToList.putIfAbsent(entry.getKey(), entry.getValue());
		}

		String[] memory = new String[memoryToList.size()];
		String[] register = new String[this.model.getRegisterLabelMap().size()];

		int counter = 0;
		for (Entry<String, Integer> entry : memoryToList.entrySet()) {
			memory[counter] = "ρ(" + entry.getKey() + ") := "
					+ this.model.getMemory().get(entry.getKey());
			counter++;
		}

		counter = 0;
		for (Entry<String, Integer> entry : this.model.getRegisterLabelMap().entrySet()) {
			register[counter] = entry.getKey() + " := "
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
		gui.SetListMemory(memory);
		gui.SetListRegister(register);
		//gui.SetListRuntimeDebug(arrSource, pc);
		gui.SetListRuntimeDebug(arrSource, ln);

	}
}
