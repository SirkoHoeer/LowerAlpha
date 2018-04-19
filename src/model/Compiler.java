package model;

import model.CompileException;
import java.io.*;
import java.util.*;
import java.util.regex.*;

public class Compiler {
	private Pattern labelRegExp;
	private Pattern constRegExp;
	private ArrayList<Pattern> syntaxRegExp;
	
	private HashMap<String, Integer> labelMap;
	
	private int registerLabelIndex;
	private HashMap<String, Integer> registerLabelMap;
	
	private int memoryLabelIndex;
	private HashMap<String, Integer> memoryLabelMap;
		
	private String convertMemoryAddress(String _exp)
	{
		int beginIndex = _exp.indexOf("ρ(ρ(");
		if(beginIndex < 0)
		{
			beginIndex = _exp.indexOf("ρ(")+2;
		}
		else
			beginIndex+=4;
		int endIndex = _exp.indexOf(')', beginIndex);
		String address = _exp.substring(beginIndex, endIndex);
		return address;
	}
	
	public Compiler()
	{
		syntaxRegExp = new ArrayList<Pattern>();
		labelRegExp = Pattern.compile("(\\w+):");
		constRegExp = Pattern.compile("(-\\d+)|(\\d+)");
	}
	
	public void load(InputStream is) throws Exception
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
		String line;
		while( (line = br.readLine()) != null)
		{	
			//Abfrage ob aktuelle Zeile ein Kommentar ist
			if(line.length() > 0 && line.charAt(0) != '#')
			{
				Pattern p = Pattern.compile(line);
				syntaxRegExp.add(p);
			}
		}
		br.close();
	}
	
	public Program compile(String _source) throws CompileException 
	{
		//initialize Compiler
		labelMap = new HashMap<String, Integer>();
		memoryLabelIndex = 0;
		memoryLabelMap = new HashMap<String, Integer>();
		registerLabelIndex = 1;
		registerLabelMap = new HashMap<String, Integer>();
		registerLabelMap.put("α", 0); //add alpha register by default
		
		int lineIndex = 0;
		int instructionIndex = 0;
		StringReader s = new StringReader(_source);
		BufferedReader br = new BufferedReader(s);
		
		//Erstellen von Tokens
		ArrayList<Token> tokens = new ArrayList<Token>();
		try
		{
			String line;
			while( (line = br.readLine()) != null )
			{
				line = lexicalAnalysis(line, lineIndex, instructionIndex);
				if(line.length() > 0)
				{
					Token t = checkSyntax(line,lineIndex, instructionIndex);
					checkAddresses(t);
					tokens.add(t);
					instructionIndex++;
				}
				lineIndex++;
			}
			br.close();
			
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		
		Program prog = new Program(registerLabelMap.size(), memoryLabelMap.size());
		
		//Set program entry point
		if(labelMap.containsKey("main") == true)
		{
			prog.setEntryPoint(labelMap.get("main"));
		}
		else
		{
			prog.setEntryPoint(0);
		}
		
		//Instruction encoding
		for(Token t : tokens)
		{
			Instruction inst = new Instruction();
			inst.op = encodeOpCode(t);
			inst.flags = encodeFlag(t);
			inst.addrs = addressLookup(t, inst.flags);
			inst.setLineIndex(t.getLineNumber());
			inst.setInstructionIndex(t.getInstructionIndex());
			prog.addInstruction(inst);
		}
		
		//checks if program ends with return
		boolean mainReturn = false;
		for(int i = prog.getEntryPoint(); i < prog.getProgCode().size(); i++)
		{
			if(prog.getProgCode().get(i).op == Instruction.OP_RETURN)
			{
				mainReturn = true;
				break;
			}
		}
		if(!mainReturn)
		{
			throw new CompileException(prog.getEntryPoint(), "Main funktion has to end with return");
		}
		
		return prog;
	}
	
	private String lexicalAnalysis(String line, int lineIndex, int instructionIndex) throws CompileException
	{
		//TODO Sollte der ZeilenIndex ab eins oder ab null anfangen?
		//Entfernen von Whitespace
		 line = line.replaceAll("\\s", "");
		 
		//Entfernen von Kommentaren
		int commentIndex = line.indexOf('#');
		if(commentIndex >= 0)
		{
			line = line.substring(0, commentIndex);
		}
		
		//Trenne String am ersten Doppelpunkt
		int labelIndex = line.indexOf(":");
		if(labelIndex == 0)
		{
			throw new CompileException(lineIndex, "Empty label is not allowed!");	
		}
		
		if(labelIndex > 0)
		{
			String label = line.substring(0, labelIndex+1);
			Matcher m = labelRegExp.matcher(label);
			if(m.matches())
			{
				line = line.substring(labelIndex+1, line.length());
				if(line.length() <= 0)
				{
					throw new CompileException(lineIndex, "Missing expression after Label!");	
				}
				else if(labelMap.get(label) != null)
				{
					throw new CompileException(lineIndex, "Double defined label!");	
				}
				else
				{
					labelMap.put(label.substring(0, labelIndex), instructionIndex);
				}
			}
		}
		return line;
	}
	
	private Token checkSyntax(String line, int lineIndex, int instructionIndex) throws CompileException
	{
		Matcher m = null;
		int regExpIndex = 0;
		for(regExpIndex = 0; regExpIndex < syntaxRegExp.size(); regExpIndex++)
		{
			m = syntaxRegExp.get(regExpIndex).matcher(line);
			if(m.matches())
			{
				break;
			}
		}
		if(m == null || !m.matches())
		{
			throw new CompileException(lineIndex, "Syntax error!");
		}
		
		//Encode instructions
		Token t = new Token(lineIndex, instructionIndex, regExpIndex);
		
		//Zerlegen der Zeile
		if(regExpIndex == 1 || regExpIndex == 2 || regExpIndex == 6)
		{
			t.op = m.group("op");
		}
		
		//TODO mit ifs lösen statt mit trycatch
		try
		{
			t.addrs[0] = m.group("adr1");
			t.addrs[1] = m.group("adr2");
			t.addrs[2] = m.group("adr3");
		} 
		catch (Exception e)
		{
			// TODO: handle exception
		}
		
		return t;
	}
	
	private void checkAddresses(Token t)
	{
		//TODO vereinfachen und umbennen
		if(t.getRegExpIndex() != 0 && t.getRegExpIndex() != 1)
			return;
		/*if(t.addrs[0] == null)
		{
			break;	
		}*/
		else if(t.addrs[0].charAt(0) == 'α')
		{
			if(registerLabelMap.get(t.addrs[0]) == null)
			{
				registerLabelMap.put(t.addrs[0], registerLabelIndex); //fügt das Pseudonym ggf hinzu
				registerLabelIndex++;
			}
		}
		else if(t.addrs[0].charAt(0) == 'ρ') //Speicherzugriff
		{
			if(t.addrs[0].length() > 3 && t.addrs[0].charAt(2) == 'ρ')//indirekter Speicherzugriff durch Speicherstelle
			{ 
				String addr = convertMemoryAddress(t.addrs[0]);
				if(memoryLabelMap.get(addr) == null) //prüft ob das Pseudonym bekannt ist
				{
					memoryLabelMap.put(addr, memoryLabelIndex); //fügt das Pseudonym ggf hinzu
					memoryLabelIndex++;
				}
			}
			else if(t.addrs[0].length() > 3 && t.addrs[0].charAt(2) == 'α')//indirekter Speicherzugriff durch Regsiter
			{
				String addr = convertMemoryAddress(t.addrs[0]);
				if(registerLabelMap.get(addr) == null)
				{
					registerLabelMap.put(addr, registerLabelIndex); //fügt das Pseudonym ggf hinzu
					registerLabelIndex++;
				}
			}
			else //direkter Speicherzugriff
			{
				String addr = convertMemoryAddress(t.addrs[0]);
				if(memoryLabelMap.get(addr) == null) //prüft ob das Pseudonym bekannt ist
				{
					memoryLabelMap.put(addr, memoryLabelIndex); //fügt das Pseudonym ggf hinzu
					memoryLabelIndex++;
				}
			}
		} 
	}
	
	private byte encodeOpCode(Token token) throws CompileException
	{
		byte opCode = -1;
		switch (token.getRegExpIndex()) 
		{
		case 0: //store operation
			opCode = Instruction.OP_STORE;
			break;
		case 1: //arithmetic instruction
			if(token.op.equals("+"))
			{
				opCode = Instruction.OP_ADD;
			}
			else if(token.op.equals("-"))
			{
				opCode = Instruction.OP_SUB;
			}
			else if(token.op.equals("*"))
			{
				opCode = Instruction.OP_MUL;
			}
			else if(token.op.equals("÷"))
			{
				opCode = Instruction.OP_DIV;
			}
			else if(token.op.equals("%"))
			{
				opCode = Instruction.OP_MOD;
			}
			break;
		case 2: //conditional jump
			if(token.op.equals("<"))
			{
				opCode = Instruction.OP_IF_L;
			}
			else if(token.op.equals(">"))
			{
				opCode = Instruction.OP_IF_G;
			}
			else if(token.op.equals("="))
			{
				opCode = Instruction.OP_IF_EQ;
			}
			break;
		case 3: //jump
			opCode = Instruction.OP_GOTO;
			break;
		case 4: //push
			opCode = Instruction.OP_PUSH;
			break;
		case 5: //pop
			opCode = Instruction.OP_POP;
			break;
		case 6: //stack arithmetic operation
			if(token.op.equals("+"))
			{
				opCode = Instruction.OP_STACK_ADD;
			}
			else if(token.op.equals("-"))
			{
				opCode = Instruction.OP_STACK_SUB;
			}
			else if(token.op.equals("*"))
			{
				opCode = Instruction.OP_STACK_MUL;
			}
			else if(token.op.equals("÷"))
			{
				opCode = Instruction.OP_STACK_DIV;
			}
			else if(token.op.equals("%"))
			{
				opCode = Instruction.OP_STACK_MOD;
			}
			break;
		case 7: //call operation
			opCode = Instruction.OP_CALL;
			break;
		case 8: //return operation
			opCode = Instruction.OP_RETURN;
			break;
		default:
			throw new CompileException(token.getLineNumber(), "Crititcal Error: Unkown regular Expression");
		}
		return opCode;
	}
	
	private int[] encodeFlag(Token t)
	{
		int flagIndex = 0;
		int flags[] = new int[3];
		flags[0] = Instruction.FLAG_INVAILD;
		flags[1] = Instruction.FLAG_INVAILD;
		flags[2] = Instruction.FLAG_INVAILD;
		
		for(int i = 0; i < 3; i++)
		{
			if(t.addrs[i] == null )
			{
				break;
			}
			if(t.addrs[i].charAt(0) == 'α')
			{
				flags[flagIndex] = Instruction.FLAG_REGISTER;
				flagIndex++;
			}
			else if(t.addrs[i].charAt(0) == 'ρ')
			{
				if(t.addrs[i].length() > 3 && t.addrs[i].charAt(2) == 'ρ')
				{
					flags[flagIndex] = Instruction.FLAG_IN_MEM_MEM;
					flagIndex++;
				}
				else if(t.addrs[i].length() > 3 && t.addrs[i].charAt(2) == 'α')
				{
					flags[flagIndex] = Instruction.FLAG_IN_REG_MEM;
					flagIndex++;
				}
				else
				{
					flags[flagIndex] = Instruction.FLAG_DIRECT_MEM;
					flagIndex++;
				}
			}
			else if(constRegExp.matcher(t.addrs[i]).matches() == true)
			{
				flags[flagIndex] = Instruction.FLAG_CONSTANT;
				flagIndex++;
			}
			else
			{
				flags[flagIndex] = Instruction.FLAG_LABEL;
				flagIndex++;
			}
		}
		
		return flags;
	}
	
	private int[] addressLookup(Token token, int flags[])
	{
		int addresses[] = new int [3];
		for(int i= 0; i < 3; i++)
		{
			switch (flags[i])
			{
			case Instruction.FLAG_REGISTER:
				if( registerLabelMap.get(token.addrs[i]) == null)
					throw new CompileException(token.getLineNumber(), "Register has never been initialized!");
				addresses[i] = registerLabelMap.get(token.addrs[i]);
				break;
			case Instruction.FLAG_IN_MEM_MEM:
				if( memoryLabelMap.get(convertMemoryAddress(token.addrs[i])) == null)
					throw new CompileException(token.getLineNumber(), "Memory has never been initialized!");
				addresses[i] = memoryLabelMap.get(convertMemoryAddress(token.addrs[i]));
				break;
			case Instruction.FLAG_IN_REG_MEM:
				if( registerLabelMap.get(convertMemoryAddress(token.addrs[i])) == null)
					throw new CompileException(token.getLineNumber(), "Register has never been initialized!");
				addresses[i] = registerLabelMap.get(convertMemoryAddress(token.addrs[i]));
				break;
			case Instruction.FLAG_DIRECT_MEM:
				if( memoryLabelMap.get(convertMemoryAddress(token.addrs[i])) == null)
					throw new CompileException(token.getLineNumber(), "Memory has never been initialized!");
				addresses[i] = memoryLabelMap.get(convertMemoryAddress(token.addrs[i]));
				break;
			case Instruction.FLAG_CONSTANT:
				addresses[i] = Integer.parseInt(token.addrs[i]);
				break;
			case Instruction.FLAG_LABEL:
				if(labelMap.get(token.addrs[i]) == null)
					throw new CompileException(token.getLineNumber(), "Undefined Label!");
				addresses[i] = labelMap.get(token.addrs[i]);
				break;
			default:
				break;
			}
		}
		return addresses;
	}
	
	public HashMap<String, Integer> getRegisterLabelMap() 
	{
		return registerLabelMap;
	}
	
	public HashMap<String, Integer> getMemoryLabelMap() 
	{
		return memoryLabelMap;
	}

	public void setRegisterLabelMap(HashMap<String, Integer> registerLabelMap) 
	{
		this.registerLabelMap = registerLabelMap;
	}	

	public void setMemoryLabelMap(HashMap<String, Integer> memoryLabelMap) 
	{
		this.memoryLabelMap = memoryLabelMap;
	}
}
