package model;

import java.util.HashMap;

/**
 * 
 * @author Maximus S.
 *
 */

public class JModel {
	
	protected Program program;
	protected Compiler compiler;
	protected VM vm;
	protected JRunnableInvokePlay RunInvokePlay; 
	protected JRunnableEnableButtons RunEnableButtons;
	
	public JModel() {
		compiler = new Compiler();
		
		try {
			compiler.load(getClass().getResourceAsStream("/rules/three.rules"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void compile(String source) throws Exception {		
		this.program = compiler.compile(source);
		this.vm = new VM(program);
		this.vm.loadProgam();
		this.vm.setRunnableInvokePlay(RunInvokePlay);
		this.vm.setRunnableEnableButtons(RunEnableButtons);
	}
	
	public HashMap<String, Integer> getRegisterLabelMap() {
		return this.compiler.getRegisterLabelMap();
	}
	
	public HashMap<String, Integer> getMemoryLabelMap() {
		return this.compiler.getMemoryLabelMap();
	}	
	
	public Integer[] getMemory() {
        return this.vm.getMemory();
    }

    public Integer[] getRegister() {
        return this.vm.getRegister();
    }

    public JStack<Integer> getStack() {
        return this.vm.getStack();
    }

    public long getTimeOut() {
        return this.vm.getTimeOut();
    }

    public void setTimeOut(long timeOut) {
        this.vm.setTimeOut(timeOut);
    }
    
    public void Step() {
    	this.vm.step();
    }
    
    public int getProgramCounter() {
        return vm.getProgramCounter();
    }
    
    public int getLineIndex() {
    	return vm.getLineIndex();
    }
    
    public void setRunnableInvokePlay(JRunnableInvokePlay run) {
    	this.RunInvokePlay = run;    	
    }        
    
    public void setRunnableEnableButtons(JRunnableEnableButtons run) {
    	this.RunEnableButtons = run;
    }
    
    public void resetVM() {
    	this.vm.restart();
    }
    
    public void setRunnableInvokePlayInVM() {
    	vm.setRunnableInvokePlay(this.RunInvokePlay);
    }
    
    public void setRunnableEnableButtons() {
    	vm.setRunnableEnableButtons(RunEnableButtons);
    }
    
    public void setStop(boolean b) {
    	vm.setStop(b);
    }
    
    //written afterwards
    public boolean getStop() {
    	return vm.getStop();
    }
    
    public void setSuspend() {
    	vm.suspend();
    }
    
    public void setResume() {
    	vm.resume();
    }
    
    
    public void run() {
    	
    	if (!vm.getStop() && vm.getVMStatePaused()) {
    		vm.start();
		} else if(vm.getVMStatePaused()){
			vm.resume();
		}
    	
    	
    }    
}
