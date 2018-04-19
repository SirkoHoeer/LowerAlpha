package model;

public class Token  {
	
	private int lineNumber;
	private int instructionIndex;
	private int regExpIndex;
	public String addrs[];
	public String op;
	
	public Token(int lineNumber,int instructionIndex, int regExpIndex)
	{
		addrs = new String[3];	
		this.lineNumber = lineNumber;
		this.instructionIndex = instructionIndex;
		this.regExpIndex = regExpIndex;
	}

	public int getLineNumber()
	{
		return lineNumber;
	}

	public int getInstructionIndex()
	{
		return instructionIndex;
	}
	
	public int getRegExpIndex()
	{
		return regExpIndex;
	}
}
