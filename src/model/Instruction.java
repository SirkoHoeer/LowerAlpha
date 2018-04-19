package model;



public class Instruction {
    
	public Instruction()
	{
		flags = new int[3];
		addrs = new int[3];
	}
	        
	public byte op;
	public int flags[];
	public int addrs[];
	private int lineIndex;
	private int instructionIndex;
	private boolean breakPoint;
    
    // flags[0] |  addrs[0] | flags[1] |  addrs[1] | flags[2] |  addrs[2] |
    /* Instruction Byte Field
     * Type TYP_REG
     *   char     by   int     by    int    by    int 
     * +--------------------------------------------------------------+
     * | OpCode  |FL|  Dest   |FL|   Src1  |FL|   Src2  |
     * +--------------------------------------------------------------+
     *
     * Type TYP_IMI
     *   char     by   int      int 
     * +--------------------------------------------------------------+
     * | OpCode  |FL|  Dest   | Value
     * +--------------------------------------------------------------+
     * 
     * Type TYP_BRH
     *   char     by   int     by    int    by  int 
     * +--------------------------------------------------------------+
     * | OpCode  |FL|  Src1   |FL|   Src2  |FL| Offset |
     * +--------------------------------------------------------------+
     *
     * Type TYP_JMP
     *    char    by   int 
     * +--------------------------------------------------------------+
     * | OpCode  |FL|  OffSet 
     * +--------------------------------------------------------------+
     */
    //define constants of OpCode
	
	public void setLineIndex(int lineIndex) 
	{
		this.lineIndex = lineIndex;
	}
	
	public int getLineIndex() 
	{
		return lineIndex;
	}
	
	public void setInstructionIndex(int instructionIndex) 
	{
		this.instructionIndex = instructionIndex;
	}
	
	public int getInstructionIndex() 
	{
		return instructionIndex;
	}
	
	public boolean isBreakPoint()
	{
		return breakPoint;
	}

	public void setBreakPoint(boolean breakPoint)
	{
		this.breakPoint = breakPoint;
	}

	public static final byte OP_NOP			= 0x00;                 // Do noting
	public static final byte OP_STORE 		= 0x01;                 // Store or load from enything to everywhere 
	public static final byte OP_ADD			= 0x02;                 // Addition 
	public static final byte OP_SUB			= 0x03;                 // Subtraction
	public static final byte OP_MUL			= 0x04;                 // Multiplication
	public static final byte OP_DIV			= 0x05;                 // Division
	public static final byte OP_MOD			= 0x06;                 // Modulo
	public static final byte OP_IF_EQ		= 0x07;                 // If Statement equl
	public static final byte OP_IF_L		= 0x13;                 // If Statement lower then
	public static final byte OP_IF_G		= 0x14;			// If Statement greater then		
	public static final byte OP_GOTO		= 0x08;                 // Jump Operation
	public static final byte OP_PUSH		= 0x09;                 // Push Operation
	public static final byte OP_POP			= 0x0a;                 // Pop Operation 
	public static final byte OP_STACK_ADD           = 0x0b;                 // Addition from Stack
	public static final byte OP_STACK_SUB           = 0x0c;                 // Subtraction from Stack
	public static final byte OP_STACK_MUL           = 0x0e;                 // Multiplication from Stack
	public static final byte OP_STACK_DIV           = 0x0f;                 // Division from Stack
	public static final byte OP_STACK_MOD           = 0x10;                 // Modulo Operation from Stack
	public static final byte OP_CALL		= 0x11;                 // Call instruktion
	public static final byte OP_RETURN 		= 0x12;                 // Return instruktion
	
	//define flags
	public static final byte FLAG_INVAILD			= -0x1;
	public static final byte FLAG_REGISTER          = 0x0;                  //Registerzugriff
	public static final byte FLAG_DIRECT_MEM        = 0x1;                  //Directer Speicherzugriff
	public static final byte FLAG_IN_REG_MEM        = 0x2;                  //Indirekter Speicherzugriff mit Register
	public static final byte FLAG_IN_MEM_MEM        = 0x3;                  //Indirekter Speicherzurgiff mit Speicher
	public static final byte FLAG_LABEL             = 0x5;                  //FLAG für LABEL für Sprung Adressen
	public static final byte FLAG_CONSTANT          = 0x6;                  //FLAG für Inhalt ist Constant für Speicher
}