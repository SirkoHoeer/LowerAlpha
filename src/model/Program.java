package model;

import java.util.ArrayList;
import model.Instruction;

public class Program {

	private ArrayList<Instruction> progCode;
    private int memSize;
    private int regSize;
    private int entryPoint;


    public Program(int regSize,int memSize) 
    {
    	progCode = new ArrayList<Instruction>();
        this.memSize = memSize;
        this.regSize = regSize;
    }

    public boolean shouldBreak(int _pc)
    {
    	progCode.get(_pc).setBreakPoint(true);
        return true;
    }

    public int getRegSize() 
    {
        return this.regSize;
    }

    public int getMemSize() 
    {
        return this.memSize;
    }

    public void setEntryPoint(int entryPoint)
    {
		this.entryPoint = entryPoint;
	}
    
    public int getEntryPoint()
    {
		return this.entryPoint;
	}
    
    public void addInstruction(Instruction inst) 
    {
    	this.progCode.add(inst);
    }

    public ArrayList<Instruction> getProgCode() 
    {
        return progCode;
    }
}
