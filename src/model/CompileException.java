package model;


public class CompileException extends RuntimeException
{
	private int errorLine;
	
	public CompileException(int line, String message) 
	{
		super(message);
		errorLine = line;
	}

	public int getErrorLine()
	{
		return errorLine;
	}
}