package controllers.Actionlisteners;

import controller.JController;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;


import model.CompileException;

import model.JModel;
import model.JStack;
import view.JAlphaNotationGUI;

public class JButtonCompileControllerActionListener implements ActionListener {

    private JAlphaNotationGUI gui;
    private JModel model;
    private JController controller;
    
    public JButtonCompileControllerActionListener(JAlphaNotationGUI gui, JModel model, JController con) {
        this.gui = gui;
        this.model = model;
        this.controller = con;
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        String source = this.gui.GetTextSource();       
        
        
        String[] arrSource = source.split("\\r?\\n");
        //arrSource = Arrays.copyOf(arrSource, arrSource.length + 1);
                
        for (int j = 0; j < arrSource.length; j++) { // -1
            if (arrSource[j].equals("")) {
                arrSource[j] = " ";
            } else {
                //arrSource[j] = "[" + (j + 1) + "] \t" + arrSource[j];
            }
        }
        
        //arrSource[arrSource.length - 1] = " ";        
        
        try {
            this.model.compile(source);
            this.gui.ToggleButtonCompile(true);

            //gui.SetListMemorey(model.getMemory());
            //gui.SetListRegister(model.getRegister());
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
            

        } catch (CompileException e) {
            
            controller.setCompileSuccessfull(false);
            this.gui.ToggleButtonCompile(false);

            int errorline = e.getErrorLine();            
            
            gui.AppendTextConsole(e.getMessage() + " at line " + e.getErrorLine());

            gui.setListRuntimeDebugCompileError(arrSource, e.getErrorLine(), e.getMessage());

            System.out.println(e.getMessage());
        } catch (Exception e) {
        	e.printStackTrace();
        } finally {

        }

    }
}
