package model;

import java.util.Stack;
import java.util.ArrayList;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.SwingUtilities;

public class VM extends Thread {

    //machine settings
    private int status;                                                     // status der Virtuellen Maschine
    private int programCounter;
    private boolean runnig;
    private int proof;

    private JStack<Integer> stack;
    private Stack<Integer> callStack;
    private ArrayList<Instruction> progCode;
    private Integer[] memory;
    private Integer[] register;
    private int operant1;
    private int operant2;
    private long timeOut;

    private Program alphaProgramm;

    protected JRunnableInvokePlay RunnableInvokePlay;
    protected JRunnableEnableButtons RunnableEnableButtons;
    
    private boolean m_bVMStatePaused;
    

    //Konstruktor
    public VM(Program __prog) {
        this.proof = 0;
        this.programCounter = 0;
        this.alphaProgramm = __prog;
        this.stack = new JStack<Integer>();
        this.callStack = new Stack<Integer>();
        this.memory = new Integer[this.alphaProgramm.getMemSize()];
        this.register = new Integer[this.alphaProgramm.getRegSize()];
        this.timeOut = 200;
        this.runnig = false;
        this.m_bVMStatePaused = true;
    }

    public void setRunnableInvokePlay(JRunnableInvokePlay run) {
        this.RunnableInvokePlay = run;
    }

    public void setRunnableEnableButtons(JRunnableEnableButtons run) {
        this.RunnableEnableButtons = run;
    }

    public void loadProgam() {
        this.programCounter = 0;
        this.progCode = new ArrayList<>(this.alphaProgramm.getProgCode());
        this.programCounter = this.alphaProgramm.getEntryPoint();
    }

    //machine control
    public void step() {
        if (programCounter < progCode.size()) {
            workingInstraktion(progCode.get(programCounter));
            if (this.status != VM.TERMINATED) {
                programCounter++;
            }
        }
    }

    public Integer[] getMemory() {
        return memory;
    }

    public Integer[] getRegister() {
        return register;
    }

    public JStack<Integer> getStack() {
        return stack;
    }

    public int getProgramCounter() {
        return this.programCounter;
    }

    public long getTimeOut() {
        return timeOut;
    }

    public void setTimeOut(long timeOut) {
        this.timeOut = timeOut;
    }

    public int getStatus() {
        return status;
    }

    @Override
    public void run() {
        this.runnig = true;
        for (; programCounter <= progCode.size(); programCounter++) {

            try {
                Thread.sleep(this.timeOut);
            } catch (InterruptedException ex) {
                Logger.getLogger(VM.class.getName()).log(Level.SEVERE, null, ex);
            }

            if (this.runnig == false) {
                return;
            }

            if (this.status == VM.TERMINATED) {
                SwingUtilities.invokeLater(RunnableEnableButtons);
                return;
            }

            if (progCode.get(programCounter).isBreakPoint() == true) {
                return;
            }

            if (programCounter <= progCode.size() && this.status != VM.TERMINATED) {
                workingInstraktion(progCode.get(programCounter));
            } else {
                return;
            }

            SwingUtilities.invokeLater(RunnableInvokePlay);
        }
    }

    public void setStop(boolean stop) {
        this.runnig = stop;
    }
    
    //written afterwards
    public boolean getStop() {
    	return this.runnig;    	
    }
    
    public void setPause() {
    	this.suspend();
    	this.m_bVMStatePaused = true;
    }
    
    public void setResume() {
    	this.resume();
    	this.m_bVMStatePaused = false;
    }
    
    public void setVMStatePaused(boolean b) {
    	this.m_bVMStatePaused = b;
    }
    
    public boolean getVMStatePaused() {
    	return this.m_bVMStatePaused;
    }

    private int workingInstraktion(Instruction i) {        
        switch (i.flags[1]) {
            case Instruction.FLAG_REGISTER:
                try {
                    this.proof = register[i.addrs[1]];
                } catch (Exception e) {
                    System.out.println("model.VM.workingInstraktion(1)");
                    this.restart();
                }
                break;
                
            case Instruction.FLAG_DIRECT_MEM:
                break;

            case Instruction.FLAG_IN_REG_MEM:
                break;

            case Instruction.FLAG_IN_MEM_MEM:
                break;        
                        
            default:
                break;
        }
        
        switch (i.op) {
            case Instruction.OP_NOP:
                break;
            case Instruction.OP_STORE:
                if (i.flags[1] == Instruction.FLAG_CONSTANT) {
                    switch (i.flags[0]) {
                        case Instruction.FLAG_REGISTER:
                            this.register[i.addrs[0]] = i.addrs[1];
                            break;
                        case Instruction.FLAG_DIRECT_MEM:
                            this.memory[i.addrs[0]] = i.addrs[1];
                            break;
                        case Instruction.FLAG_IN_REG_MEM:
                            this.register[memory[i.addrs[0]]] = i.addrs[1];
                            break;
                        case Instruction.FLAG_IN_MEM_MEM:
                            this.memory[memory[i.addrs[0]]] = i.addrs[1];
                            break;
                        default:
                            break;
                    }
                } else if (i.flags[1] == Instruction.FLAG_REGISTER) {
                    switch (i.flags[0]) {
                        case Instruction.FLAG_REGISTER:
                            this.register[i.addrs[0]] = register[i.addrs[1]];
                            break;
                        case Instruction.FLAG_DIRECT_MEM:
                            this.memory[i.addrs[0]] = register[i.addrs[1]];
                            break;
                        case Instruction.FLAG_IN_REG_MEM:
                            this.register[memory[i.addrs[0]]] = register[i.addrs[1]];
                            break;
                        case Instruction.FLAG_IN_MEM_MEM:
                            this.memory[memory[i.addrs[0]]] = register[i.addrs[1]];
                            break;
                        default:
                            break;
                    }
                } else if (i.flags[1] == Instruction.FLAG_DIRECT_MEM) {
                    switch (i.flags[0]) {
                        case Instruction.FLAG_REGISTER:
                            this.register[i.addrs[0]] = memory[i.addrs[1]];
                            break;
                        case Instruction.FLAG_DIRECT_MEM:
                            this.memory[i.addrs[0]] = memory[i.addrs[1]];
                            break;
                        case Instruction.FLAG_IN_REG_MEM:
                            this.register[memory[i.addrs[0]]] = memory[i.addrs[1]];
                            break;
                        case Instruction.FLAG_IN_MEM_MEM:
                            this.memory[memory[i.addrs[0]]] = memory[i.addrs[1]];
                            break;
                        default:
                            break;
                    }
                } else if (i.flags[1] == Instruction.FLAG_IN_REG_MEM) {
                    switch (i.flags[0]) {
                        case Instruction.FLAG_REGISTER:
                            this.register[i.addrs[0]] = register[memory[i.addrs[1]]];
                            break;
                        case Instruction.FLAG_DIRECT_MEM:
                            this.memory[i.addrs[0]] = register[memory[i.addrs[1]]];
                            break;
                        case Instruction.FLAG_IN_REG_MEM:
                            this.register[memory[i.addrs[0]]] = register[memory[i.addrs[1]]];

                            break;
                        case Instruction.FLAG_IN_MEM_MEM:
                            this.memory[memory[i.addrs[0]]] = register[memory[i.addrs[1]]];
                            break;
                        default:
                            break;
                    }
                } else if (i.flags[1] == Instruction.FLAG_IN_MEM_MEM) {
                    switch (i.flags[0]) {
                        case Instruction.FLAG_REGISTER:
                            this.register[i.addrs[0]] = memory[memory[i.addrs[1]]];
                            break;
                        case Instruction.FLAG_DIRECT_MEM:
                            this.memory[i.addrs[0]] = memory[memory[i.addrs[1]]];
                            break;
                        case Instruction.FLAG_IN_REG_MEM:
                            this.register[memory[i.addrs[0]]] = memory[memory[i.addrs[1]]];

                            break;
                        case Instruction.FLAG_IN_MEM_MEM:
                            this.memory[memory[i.addrs[0]]] = memory[memory[i.addrs[1]]];
                            break;
                        default:
                            break;
                    }
                }

                break;
            case Instruction.OP_ADD:
                // Add with Const
                if (i.flags[0] == Instruction.FLAG_REGISTER) {
                    if (i.flags[2] == Instruction.FLAG_CONSTANT) {
                        if (i.flags[1] == Instruction.FLAG_CONSTANT) {
                            this.register[i.addrs[0]] = i.addrs[1] + i.addrs[2];
                        } else if (i.flags[1] == Instruction.FLAG_REGISTER) {
                            this.register[i.addrs[0]] = register[i.addrs[1]] + i.addrs[2];
                        } else if (i.flags[1] == Instruction.FLAG_DIRECT_MEM) {
                            this.register[i.addrs[0]] = memory[i.addrs[1]] + i.addrs[2];
                        } else if (i.flags[1] == Instruction.FLAG_IN_REG_MEM) {
                            this.register[i.addrs[0]] = register[memory[i.addrs[1]]] + i.addrs[2];
                        } else if (i.flags[1] == Instruction.FLAG_IN_MEM_MEM) {
                            this.register[i.addrs[0]] = memory[memory[i.addrs[1]]] + i.addrs[2];
                        }
                    } // Add with Reg Reference
                    else if (i.flags[2] == Instruction.FLAG_REGISTER) {
                        if (i.flags[1] == Instruction.FLAG_CONSTANT) {
                            this.register[i.addrs[0]] = i.addrs[1] + register[i.addrs[2]];
                        } else if (i.flags[1] == Instruction.FLAG_REGISTER) {
                            this.register[i.addrs[0]] = register[i.addrs[1]] + register[i.addrs[2]];
                        } else if (i.flags[1] == Instruction.FLAG_DIRECT_MEM) {
                            this.register[i.addrs[0]] = memory[i.addrs[1]] + register[i.addrs[2]];
                        } else if (i.flags[1] == Instruction.FLAG_IN_REG_MEM) {
                            this.register[i.addrs[0]] = register[memory[i.addrs[1]]] + register[i.addrs[2]];
                        } else if (i.flags[1] == Instruction.FLAG_IN_MEM_MEM) {
                            this.register[i.addrs[0]] = memory[memory[i.addrs[1]]] + register[i.addrs[2]];
                        }
                    } // Add with Mem Reference
                    else if (i.flags[2] == Instruction.FLAG_DIRECT_MEM) {
                        if (i.flags[1] == Instruction.FLAG_CONSTANT) {
                            this.register[i.addrs[0]] = i.addrs[1] + memory[i.addrs[2]];
                        } else if (i.flags[1] == Instruction.FLAG_REGISTER) {
                            this.register[i.addrs[0]] = register[i.addrs[1]] + memory[i.addrs[2]];
                        } else if (i.flags[1] == Instruction.FLAG_DIRECT_MEM) {
                            this.register[i.addrs[0]] = memory[i.addrs[1]] + memory[i.addrs[2]];
                        } else if (i.flags[1] == Instruction.FLAG_IN_REG_MEM) {
                            this.register[i.addrs[0]] = register[memory[i.addrs[1]]] + memory[i.addrs[2]];
                        } else if (i.flags[1] == Instruction.FLAG_IN_MEM_MEM) {
                            this.register[i.addrs[0]] = memory[memory[i.addrs[1]]] + memory[i.addrs[2]];
                        }
                    } // Add with Reg(Mem) Reference
                    else if (i.flags[2] == Instruction.FLAG_IN_REG_MEM) {
                        if (i.flags[1] == Instruction.FLAG_CONSTANT) {
                            this.register[i.addrs[0]] = i.addrs[1] + register[memory[i.addrs[2]]];
                        } else if (i.flags[1] == Instruction.FLAG_REGISTER) {
                            this.register[i.addrs[0]] = register[i.addrs[1]] + register[memory[i.addrs[2]]];
                        } else if (i.flags[1] == Instruction.FLAG_DIRECT_MEM) {
                            this.register[i.addrs[0]] = memory[i.addrs[1]] + register[memory[i.addrs[2]]];
                        } else if (i.flags[1] == Instruction.FLAG_IN_REG_MEM) {
                            this.register[i.addrs[0]] = register[memory[i.addrs[1]]] + register[memory[i.addrs[2]]];
                        } else if (i.flags[1] == Instruction.FLAG_IN_MEM_MEM) {
                            this.register[i.addrs[0]] = memory[memory[i.addrs[1]]] + register[memory[i.addrs[2]]];
                        }
                    } // Add with Mem(Mem) Reference
                    else if (i.flags[2] == Instruction.FLAG_IN_MEM_MEM) {
                        if (i.flags[1] == Instruction.FLAG_CONSTANT) {
                            this.register[i.addrs[0]] = i.addrs[1] + memory[memory[i.addrs[2]]];
                        } else if (i.flags[1] == Instruction.FLAG_REGISTER) {
                            this.register[i.addrs[0]] = register[i.addrs[1]] + memory[memory[i.addrs[2]]];
                        } else if (i.flags[1] == Instruction.FLAG_DIRECT_MEM) {
                            this.register[i.addrs[0]] = memory[i.addrs[1]] + memory[memory[i.addrs[2]]];
                        } else if (i.flags[1] == Instruction.FLAG_IN_REG_MEM) {
                            this.register[i.addrs[0]] = register[memory[i.addrs[1]]] + memory[memory[i.addrs[2]]];
                        } else if (i.flags[1] == Instruction.FLAG_IN_MEM_MEM) {
                            this.register[i.addrs[0]] = memory[memory[i.addrs[1]]] + memory[memory[i.addrs[2]]];
                        }
                    }
                } else if (i.flags[0] == Instruction.FLAG_DIRECT_MEM) {
                    if (i.flags[2] == Instruction.FLAG_CONSTANT) {
                        if (i.flags[1] == Instruction.FLAG_CONSTANT) {
                            this.memory[i.addrs[0]] = i.addrs[1] + i.addrs[2];
                        } else if (i.flags[1] == Instruction.FLAG_REGISTER) {
                            this.memory[i.addrs[0]] = register[i.addrs[1]] + i.addrs[2];
                        } else if (i.flags[1] == Instruction.FLAG_DIRECT_MEM) {
                            this.memory[i.addrs[0]] = memory[i.addrs[1]] + i.addrs[2];
                        } else if (i.flags[1] == Instruction.FLAG_IN_REG_MEM) {
                            this.memory[i.addrs[0]] = register[memory[i.addrs[1]]] + i.addrs[2];
                        } else if (i.flags[1] == Instruction.FLAG_IN_MEM_MEM) {
                            this.memory[i.addrs[0]] = memory[memory[i.addrs[1]]] + i.addrs[2];
                        }
                    } // Add with Reg Reference
                    else if (i.flags[2] == Instruction.FLAG_REGISTER) {
                        if (i.flags[1] == Instruction.FLAG_CONSTANT) {
                            this.memory[i.addrs[0]] = i.addrs[1] + register[i.addrs[2]];
                        } else if (i.flags[1] == Instruction.FLAG_REGISTER) {
                            this.memory[i.addrs[0]] = register[i.addrs[1]] + register[i.addrs[2]];
                        } else if (i.flags[1] == Instruction.FLAG_DIRECT_MEM) {
                            this.memory[i.addrs[0]] = memory[i.addrs[1]] + register[i.addrs[2]];
                        } else if (i.flags[1] == Instruction.FLAG_IN_REG_MEM) {
                            this.memory[i.addrs[0]] = register[memory[i.addrs[1]]] + register[i.addrs[2]];
                        } else if (i.flags[1] == Instruction.FLAG_IN_MEM_MEM) {
                            this.memory[i.addrs[0]] = memory[memory[i.addrs[1]]] + register[i.addrs[2]];
                        }
                    } // Add with Mem Reference
                    else if (i.flags[2] == Instruction.FLAG_DIRECT_MEM) {
                        if (i.flags[1] == Instruction.FLAG_CONSTANT) {
                            this.memory[i.addrs[0]] = i.addrs[1] + memory[i.addrs[2]];
                        } else if (i.flags[1] == Instruction.FLAG_REGISTER) {
                            this.memory[i.addrs[0]] = register[i.addrs[1]] + memory[i.addrs[2]];
                        } else if (i.flags[1] == Instruction.FLAG_DIRECT_MEM) {
                            this.memory[i.addrs[0]] = memory[i.addrs[1]] + memory[i.addrs[2]];
                        } else if (i.flags[1] == Instruction.FLAG_IN_REG_MEM) {
                            this.memory[i.addrs[0]] = register[memory[i.addrs[1]]] + memory[i.addrs[2]];
                        } else if (i.flags[1] == Instruction.FLAG_IN_MEM_MEM) {
                            this.memory[i.addrs[0]] = memory[memory[i.addrs[1]]] + memory[i.addrs[2]];
                        }
                    } // Add with Reg(Mem) Reference
                    else if (i.flags[2] == Instruction.FLAG_IN_REG_MEM) {
                        if (i.flags[1] == Instruction.FLAG_CONSTANT) {
                            this.memory[i.addrs[0]] = i.addrs[1] + register[memory[i.addrs[2]]];
                        } else if (i.flags[1] == Instruction.FLAG_REGISTER) {
                            this.memory[i.addrs[0]] = register[i.addrs[1]] + register[memory[i.addrs[2]]];
                        } else if (i.flags[1] == Instruction.FLAG_DIRECT_MEM) {
                            this.memory[i.addrs[0]] = memory[i.addrs[1]] + register[memory[i.addrs[2]]];
                        } else if (i.flags[1] == Instruction.FLAG_IN_REG_MEM) {
                            this.memory[i.addrs[0]] = register[memory[i.addrs[1]]] + register[memory[i.addrs[2]]];
                        } else if (i.flags[1] == Instruction.FLAG_IN_MEM_MEM) {
                            this.memory[i.addrs[0]] = memory[memory[i.addrs[1]]] + register[memory[i.addrs[2]]];
                        }
                    } // Add with Mem(Mem) Reference
                    else if (i.flags[2] == Instruction.FLAG_IN_MEM_MEM) {
                        if (i.flags[1] == Instruction.FLAG_CONSTANT) {
                            this.memory[i.addrs[0]] = i.addrs[1] + memory[memory[i.addrs[2]]];
                        } else if (i.flags[1] == Instruction.FLAG_REGISTER) {
                            this.memory[i.addrs[0]] = register[i.addrs[1]] + memory[memory[i.addrs[2]]];
                        } else if (i.flags[1] == Instruction.FLAG_DIRECT_MEM) {
                            this.memory[i.addrs[0]] = memory[i.addrs[1]] + memory[memory[i.addrs[2]]];
                        } else if (i.flags[1] == Instruction.FLAG_IN_REG_MEM) {
                            this.memory[i.addrs[0]] = register[memory[i.addrs[1]]] + memory[memory[i.addrs[2]]];
                        } else if (i.flags[1] == Instruction.FLAG_IN_MEM_MEM) {
                            this.memory[i.addrs[0]] = memory[memory[i.addrs[1]]] + memory[memory[i.addrs[2]]];
                        }
                    }
                }

                break;
            case Instruction.OP_SUB:
                // Sub with Const
                if (i.flags[0] == Instruction.FLAG_REGISTER) {
                    if (i.flags[2] == Instruction.FLAG_CONSTANT) {
                        if (i.flags[1] == Instruction.FLAG_CONSTANT) {
                            this.register[i.addrs[0]] = i.addrs[1] - i.addrs[2];
                        } else if (i.flags[1] == Instruction.FLAG_REGISTER) {
                            this.register[i.addrs[0]] = register[i.addrs[1]] - i.addrs[2];
                        } else if (i.flags[1] == Instruction.FLAG_DIRECT_MEM) {
                            this.register[i.addrs[0]] = memory[i.addrs[1]] - i.addrs[2];
                        } else if (i.flags[1] == Instruction.FLAG_IN_REG_MEM) {
                            this.register[i.addrs[0]] = register[memory[i.addrs[1]]] - i.addrs[2];
                        } else if (i.flags[1] == Instruction.FLAG_IN_MEM_MEM) {
                            this.register[i.addrs[0]] = memory[memory[i.addrs[1]]] - i.addrs[2];
                        }
                    } // Sub with Reg Reference
                    else if (i.flags[2] == Instruction.FLAG_REGISTER) {
                        if (i.flags[1] == Instruction.FLAG_CONSTANT) {
                            this.register[i.addrs[0]] = i.addrs[1] - register[i.addrs[2]];
                        } else if (i.flags[1] == Instruction.FLAG_REGISTER) {
                            this.register[i.addrs[0]] = register[i.addrs[1]] - register[i.addrs[2]];
                        } else if (i.flags[1] == Instruction.FLAG_DIRECT_MEM) {
                            this.register[i.addrs[0]] = memory[i.addrs[1]] - register[i.addrs[2]];
                        } else if (i.flags[1] == Instruction.FLAG_IN_REG_MEM) {
                            this.register[i.addrs[0]] = register[memory[i.addrs[1]]] - register[i.addrs[2]];
                        } else if (i.flags[1] == Instruction.FLAG_IN_MEM_MEM) {
                            this.register[i.addrs[0]] = memory[memory[i.addrs[1]]] - register[i.addrs[2]];
                        }
                    } // Sub with Mem Reference
                    else if (i.flags[2] == Instruction.FLAG_DIRECT_MEM) {
                        if (i.flags[1] == Instruction.FLAG_CONSTANT) {
                            this.register[i.addrs[0]] = i.addrs[1] - memory[i.addrs[2]];
                        } else if (i.flags[1] == Instruction.FLAG_REGISTER) {
                            this.register[i.addrs[0]] = register[i.addrs[1]] - memory[i.addrs[2]];
                        } else if (i.flags[1] == Instruction.FLAG_DIRECT_MEM) {
                            this.register[i.addrs[0]] = memory[i.addrs[1]] - memory[i.addrs[2]];
                        } else if (i.flags[1] == Instruction.FLAG_IN_REG_MEM) {
                            this.register[i.addrs[0]] = register[memory[i.addrs[1]]] - memory[i.addrs[2]];
                        } else if (i.flags[1] == Instruction.FLAG_IN_MEM_MEM) {
                            this.register[i.addrs[0]] = memory[memory[i.addrs[1]]] - memory[i.addrs[2]];
                        }
                    } // Sub with Reg(Mem) Reference
                    else if (i.flags[2] == Instruction.FLAG_IN_REG_MEM) {
                        if (i.flags[1] == Instruction.FLAG_CONSTANT) {
                            this.register[i.addrs[0]] = i.addrs[1] - register[memory[i.addrs[2]]];
                        } else if (i.flags[1] == Instruction.FLAG_REGISTER) {
                            this.register[i.addrs[0]] = register[i.addrs[1]] - register[memory[i.addrs[2]]];
                        } else if (i.flags[1] == Instruction.FLAG_DIRECT_MEM) {
                            this.register[i.addrs[0]] = memory[i.addrs[1]] - register[memory[i.addrs[2]]];
                        } else if (i.flags[1] == Instruction.FLAG_IN_REG_MEM) {
                            this.register[i.addrs[0]] = register[memory[i.addrs[1]]] - register[memory[i.addrs[2]]];
                        } else if (i.flags[1] == Instruction.FLAG_IN_MEM_MEM) {
                            this.register[i.addrs[0]] = memory[memory[i.addrs[1]]] - register[memory[i.addrs[2]]];
                        }
                    } // Sub with Mem(Mem) Reference
                    else if (i.flags[2] == Instruction.FLAG_IN_MEM_MEM) {
                        if (i.flags[1] == Instruction.FLAG_CONSTANT) {
                            this.register[i.addrs[0]] = i.addrs[1] - memory[memory[i.addrs[2]]];
                        } else if (i.flags[1] == Instruction.FLAG_REGISTER) {
                            this.register[i.addrs[0]] = register[i.addrs[1]] - memory[memory[i.addrs[2]]];
                        } else if (i.flags[1] == Instruction.FLAG_DIRECT_MEM) {
                            this.register[i.addrs[0]] = memory[i.addrs[1]] - memory[memory[i.addrs[2]]];
                        } else if (i.flags[1] == Instruction.FLAG_IN_REG_MEM) {
                            this.register[i.addrs[0]] = register[memory[i.addrs[1]]] - memory[memory[i.addrs[2]]];
                        } else if (i.flags[1] == Instruction.FLAG_IN_MEM_MEM) {
                            this.register[i.addrs[0]] = memory[memory[i.addrs[1]]] - memory[memory[i.addrs[2]]];
                        }
                    }
                } else if (i.flags[0] == Instruction.FLAG_DIRECT_MEM) {
                    if (i.flags[2] == Instruction.FLAG_CONSTANT) {
                        if (i.flags[1] == Instruction.FLAG_CONSTANT) {
                            this.memory[i.addrs[0]] = i.addrs[1] - i.addrs[2];
                        } else if (i.flags[1] == Instruction.FLAG_REGISTER) {
                            this.memory[i.addrs[0]] = register[i.addrs[1]] - i.addrs[2];
                        } else if (i.flags[1] == Instruction.FLAG_DIRECT_MEM) {
                            this.memory[i.addrs[0]] = memory[i.addrs[1]] - i.addrs[2];
                        } else if (i.flags[1] == Instruction.FLAG_IN_REG_MEM) {
                            this.memory[i.addrs[0]] = register[memory[i.addrs[1]]] - i.addrs[2];
                        } else if (i.flags[1] == Instruction.FLAG_IN_MEM_MEM) {
                            this.memory[i.addrs[0]] = memory[memory[i.addrs[1]]] - i.addrs[2];
                        }
                    } // Sub with Reg Reference
                    else if (i.flags[2] == Instruction.FLAG_REGISTER) {
                        if (i.flags[1] == Instruction.FLAG_CONSTANT) {
                            this.memory[i.addrs[0]] = i.addrs[1] - register[i.addrs[2]];
                        } else if (i.flags[1] == Instruction.FLAG_REGISTER) {
                            this.memory[i.addrs[0]] = register[i.addrs[1]] - register[i.addrs[2]];
                        } else if (i.flags[1] == Instruction.FLAG_DIRECT_MEM) {
                            this.memory[i.addrs[0]] = memory[i.addrs[1]] - register[i.addrs[2]];
                        } else if (i.flags[1] == Instruction.FLAG_IN_REG_MEM) {
                            this.memory[i.addrs[0]] = register[memory[i.addrs[1]]] - register[i.addrs[2]];
                        } else if (i.flags[1] == Instruction.FLAG_IN_MEM_MEM) {
                            this.memory[i.addrs[0]] = memory[memory[i.addrs[1]]] - register[i.addrs[2]];
                        }
                    } // Sub with Mem Reference
                    else if (i.flags[2] == Instruction.FLAG_DIRECT_MEM) {
                        if (i.flags[1] == Instruction.FLAG_CONSTANT) {
                            this.memory[i.addrs[0]] = i.addrs[1] - memory[i.addrs[2]];
                        } else if (i.flags[1] == Instruction.FLAG_REGISTER) {
                            this.memory[i.addrs[0]] = register[i.addrs[1]] - memory[i.addrs[2]];
                        } else if (i.flags[1] == Instruction.FLAG_DIRECT_MEM) {
                            this.memory[i.addrs[0]] = memory[i.addrs[1]] - memory[i.addrs[2]];
                        } else if (i.flags[1] == Instruction.FLAG_IN_REG_MEM) {
                            this.memory[i.addrs[0]] = register[memory[i.addrs[1]]] - memory[i.addrs[2]];
                        } else if (i.flags[1] == Instruction.FLAG_IN_MEM_MEM) {
                            this.memory[i.addrs[0]] = memory[memory[i.addrs[1]]] - memory[i.addrs[2]];
                        }
                    } // Sub with Reg(Mem) Reference
                    else if (i.flags[2] == Instruction.FLAG_IN_REG_MEM) {
                        if (i.flags[1] == Instruction.FLAG_CONSTANT) {
                            this.memory[i.addrs[0]] = i.addrs[1] - register[memory[i.addrs[2]]];
                        } else if (i.flags[1] == Instruction.FLAG_REGISTER) {
                            this.memory[i.addrs[0]] = register[i.addrs[1]] - register[memory[i.addrs[2]]];
                        } else if (i.flags[1] == Instruction.FLAG_DIRECT_MEM) {
                            this.memory[i.addrs[0]] = memory[i.addrs[1]] - register[memory[i.addrs[2]]];
                        } else if (i.flags[1] == Instruction.FLAG_IN_REG_MEM) {
                            this.memory[i.addrs[0]] = register[memory[i.addrs[1]]] - register[memory[i.addrs[2]]];
                        } else if (i.flags[1] == Instruction.FLAG_IN_MEM_MEM) {
                            this.memory[i.addrs[0]] = memory[memory[i.addrs[1]]] - register[memory[i.addrs[2]]];
                        }
                    } // Sub with Mem(Mem) Reference
                    else if (i.flags[2] == Instruction.FLAG_IN_MEM_MEM) {
                        if (i.flags[1] == Instruction.FLAG_CONSTANT) {
                            this.memory[i.addrs[0]] = i.addrs[1] - memory[memory[i.addrs[2]]];
                        } else if (i.flags[1] == Instruction.FLAG_REGISTER) {
                            this.memory[i.addrs[0]] = register[i.addrs[1]] - memory[memory[i.addrs[2]]];
                        } else if (i.flags[1] == Instruction.FLAG_DIRECT_MEM) {
                            this.memory[i.addrs[0]] = memory[i.addrs[1]] - memory[memory[i.addrs[2]]];
                        } else if (i.flags[1] == Instruction.FLAG_IN_REG_MEM) {
                            this.memory[i.addrs[0]] = register[memory[i.addrs[1]]] - memory[memory[i.addrs[2]]];
                        } else if (i.flags[1] == Instruction.FLAG_IN_MEM_MEM) {
                            this.memory[i.addrs[0]] = memory[memory[i.addrs[1]]] - memory[memory[i.addrs[2]]];
                        }
                    }
                }
                break;
            case Instruction.OP_MUL:
                // Mul with Const
                if (i.flags[0] == Instruction.FLAG_REGISTER) {
                    if (i.flags[2] == Instruction.FLAG_CONSTANT) {
                        if (i.flags[1] == Instruction.FLAG_CONSTANT) {
                            this.register[i.addrs[0]] = i.addrs[1] * i.addrs[2];
                        } else if (i.flags[1] == Instruction.FLAG_REGISTER) {
                            this.register[i.addrs[0]] = register[i.addrs[1]] * i.addrs[2];
                        } else if (i.flags[1] == Instruction.FLAG_DIRECT_MEM) {
                            this.register[i.addrs[0]] = memory[i.addrs[1]] * i.addrs[2];
                        } else if (i.flags[1] == Instruction.FLAG_IN_REG_MEM) {
                            this.register[i.addrs[0]] = register[memory[i.addrs[1]]] * i.addrs[2];
                        } else if (i.flags[1] == Instruction.FLAG_IN_MEM_MEM) {
                            this.register[i.addrs[0]] = memory[memory[i.addrs[1]]] * i.addrs[2];
                        }
                    } // Mul with Reg Reference
                    else if (i.flags[2] == Instruction.FLAG_REGISTER) {
                        if (i.flags[1] == Instruction.FLAG_CONSTANT) {
                            this.register[i.addrs[0]] = i.addrs[1] * register[i.addrs[2]];
                        } else if (i.flags[1] == Instruction.FLAG_REGISTER) {
                            this.register[i.addrs[0]] = register[i.addrs[1]] * register[i.addrs[2]];
                        } else if (i.flags[1] == Instruction.FLAG_DIRECT_MEM) {
                            this.register[i.addrs[0]] = memory[i.addrs[1]] * register[i.addrs[2]];
                        } else if (i.flags[1] == Instruction.FLAG_IN_REG_MEM) {
                            this.register[i.addrs[0]] = register[memory[i.addrs[1]]] * register[i.addrs[2]];
                        } else if (i.flags[1] == Instruction.FLAG_IN_MEM_MEM) {
                            this.register[i.addrs[0]] = memory[memory[i.addrs[1]]] * register[i.addrs[2]];
                        }
                    } // Mul with Mem Reference
                    else if (i.flags[2] == Instruction.FLAG_DIRECT_MEM) {
                        if (i.flags[1] == Instruction.FLAG_CONSTANT) {
                            this.register[i.addrs[0]] = i.addrs[1] * memory[i.addrs[2]];
                        } else if (i.flags[1] == Instruction.FLAG_REGISTER) {
                            this.register[i.addrs[0]] = register[i.addrs[1]] * memory[i.addrs[2]];
                        } else if (i.flags[1] == Instruction.FLAG_DIRECT_MEM) {
                            this.register[i.addrs[0]] = memory[i.addrs[1]] * memory[i.addrs[2]];
                        } else if (i.flags[1] == Instruction.FLAG_IN_REG_MEM) {
                            this.register[i.addrs[0]] = register[memory[i.addrs[1]]] * memory[i.addrs[2]];
                        } else if (i.flags[1] == Instruction.FLAG_IN_MEM_MEM) {
                            this.register[i.addrs[0]] = memory[memory[i.addrs[1]]] * memory[i.addrs[2]];
                        }
                    } // Mul with Reg(Mem) Reference
                    else if (i.flags[2] == Instruction.FLAG_IN_REG_MEM) {
                        if (i.flags[1] == Instruction.FLAG_CONSTANT) {
                            this.register[i.addrs[0]] = i.addrs[1] * register[memory[i.addrs[2]]];
                        } else if (i.flags[1] == Instruction.FLAG_REGISTER) {
                            this.register[i.addrs[0]] = register[i.addrs[1]] * register[memory[i.addrs[2]]];
                        } else if (i.flags[1] == Instruction.FLAG_DIRECT_MEM) {
                            this.register[i.addrs[0]] = memory[i.addrs[1]] * register[memory[i.addrs[2]]];
                        } else if (i.flags[1] == Instruction.FLAG_IN_REG_MEM) {
                            this.register[i.addrs[0]] = register[memory[i.addrs[1]]] * register[memory[i.addrs[2]]];
                        } else if (i.flags[1] == Instruction.FLAG_IN_MEM_MEM) {
                            this.register[i.addrs[0]] = memory[memory[i.addrs[1]]] * register[memory[i.addrs[2]]];
                        }
                    } // Mul with Mem(Mem) Reference
                    else if (i.flags[2] == Instruction.FLAG_IN_MEM_MEM) {
                        if (i.flags[1] == Instruction.FLAG_CONSTANT) {
                            this.register[i.addrs[0]] = i.addrs[1] * memory[memory[i.addrs[2]]];
                        } else if (i.flags[1] == Instruction.FLAG_REGISTER) {
                            this.register[i.addrs[0]] = register[i.addrs[1]] * memory[memory[i.addrs[2]]];
                        } else if (i.flags[1] == Instruction.FLAG_DIRECT_MEM) {
                            this.register[i.addrs[0]] = memory[i.addrs[1]] * memory[memory[i.addrs[2]]];
                        } else if (i.flags[1] == Instruction.FLAG_IN_REG_MEM) {
                            this.register[i.addrs[0]] = register[memory[i.addrs[1]]] * memory[memory[i.addrs[2]]];
                        } else if (i.flags[1] == Instruction.FLAG_IN_MEM_MEM) {
                            this.register[i.addrs[0]] = memory[memory[i.addrs[1]]] * memory[memory[i.addrs[2]]];
                        }
                    }
                } else if (i.flags[0] == Instruction.FLAG_DIRECT_MEM) {
                    if (i.flags[2] == Instruction.FLAG_CONSTANT) {
                        if (i.flags[1] == Instruction.FLAG_CONSTANT) {
                            this.memory[i.addrs[0]] = i.addrs[1] * i.addrs[2];
                        } else if (i.flags[1] == Instruction.FLAG_REGISTER) {
                            this.memory[i.addrs[0]] = register[i.addrs[1]] * i.addrs[2];
                        } else if (i.flags[1] == Instruction.FLAG_DIRECT_MEM) {
                            this.memory[i.addrs[0]] = memory[i.addrs[1]] * i.addrs[2];
                        } else if (i.flags[1] == Instruction.FLAG_IN_REG_MEM) {
                            this.memory[i.addrs[0]] = register[memory[i.addrs[1]]] * i.addrs[2];
                        } else if (i.flags[1] == Instruction.FLAG_IN_MEM_MEM) {
                            this.memory[i.addrs[0]] = memory[memory[i.addrs[1]]] * i.addrs[2];
                        }
                    } // Mul with Reg Reference
                    else if (i.flags[2] == Instruction.FLAG_REGISTER) {
                        if (i.flags[1] == Instruction.FLAG_CONSTANT) {
                            this.memory[i.addrs[0]] = i.addrs[1] * register[i.addrs[2]];
                        } else if (i.flags[1] == Instruction.FLAG_REGISTER) {
                            this.memory[i.addrs[0]] = register[i.addrs[1]] * register[i.addrs[2]];
                        } else if (i.flags[1] == Instruction.FLAG_DIRECT_MEM) {
                            this.memory[i.addrs[0]] = memory[i.addrs[1]] * register[i.addrs[2]];
                        } else if (i.flags[1] == Instruction.FLAG_IN_REG_MEM) {
                            this.memory[i.addrs[0]] = register[memory[i.addrs[1]]] * register[i.addrs[2]];
                        } else if (i.flags[1] == Instruction.FLAG_IN_MEM_MEM) {
                            this.memory[i.addrs[0]] = memory[memory[i.addrs[1]]] * register[i.addrs[2]];
                        }
                    } // Mul with Mem Reference
                    else if (i.flags[2] == Instruction.FLAG_DIRECT_MEM) {
                        if (i.flags[1] == Instruction.FLAG_CONSTANT) {
                            this.memory[i.addrs[0]] = i.addrs[1] * memory[i.addrs[2]];
                        } else if (i.flags[1] == Instruction.FLAG_REGISTER) {
                            this.memory[i.addrs[0]] = register[i.addrs[1]] * memory[i.addrs[2]];
                        } else if (i.flags[1] == Instruction.FLAG_DIRECT_MEM) {
                            this.memory[i.addrs[0]] = memory[i.addrs[1]] * memory[i.addrs[2]];
                        } else if (i.flags[1] == Instruction.FLAG_IN_REG_MEM) {
                            this.memory[i.addrs[0]] = register[memory[i.addrs[1]]] * memory[i.addrs[2]];
                        } else if (i.flags[1] == Instruction.FLAG_IN_MEM_MEM) {
                            this.memory[i.addrs[0]] = memory[memory[i.addrs[1]]] * memory[i.addrs[2]];
                        }
                    } // Mul with Reg(Mem) Reference
                    else if (i.flags[2] == Instruction.FLAG_IN_REG_MEM) {
                        if (i.flags[1] == Instruction.FLAG_CONSTANT) {
                            this.memory[i.addrs[0]] = i.addrs[1] * register[memory[i.addrs[2]]];
                        } else if (i.flags[1] == Instruction.FLAG_REGISTER) {
                            this.memory[i.addrs[0]] = register[i.addrs[1]] * register[memory[i.addrs[2]]];
                        } else if (i.flags[1] == Instruction.FLAG_DIRECT_MEM) {
                            this.memory[i.addrs[0]] = memory[i.addrs[1]] * register[memory[i.addrs[2]]];
                        } else if (i.flags[1] == Instruction.FLAG_IN_REG_MEM) {
                            this.memory[i.addrs[0]] = register[memory[i.addrs[1]]] * register[memory[i.addrs[2]]];
                        } else if (i.flags[1] == Instruction.FLAG_IN_MEM_MEM) {
                            this.memory[i.addrs[0]] = memory[memory[i.addrs[1]]] * register[memory[i.addrs[2]]];
                        }
                    } // Mul with Mem(Mem) Reference
                    else if (i.flags[2] == Instruction.FLAG_IN_MEM_MEM) {
                        if (i.flags[1] == Instruction.FLAG_CONSTANT) {
                            this.memory[i.addrs[0]] = i.addrs[1] * memory[memory[i.addrs[2]]];
                        } else if (i.flags[1] == Instruction.FLAG_REGISTER) {
                            this.memory[i.addrs[0]] = register[i.addrs[1]] * memory[memory[i.addrs[2]]];
                        } else if (i.flags[1] == Instruction.FLAG_DIRECT_MEM) {
                            this.memory[i.addrs[0]] = memory[i.addrs[1]] * memory[memory[i.addrs[2]]];
                        } else if (i.flags[1] == Instruction.FLAG_IN_REG_MEM) {
                            this.memory[i.addrs[0]] = register[memory[i.addrs[1]]] * memory[memory[i.addrs[2]]];
                        } else if (i.flags[1] == Instruction.FLAG_IN_MEM_MEM) {
                            this.memory[i.addrs[0]] = memory[memory[i.addrs[1]]] * memory[memory[i.addrs[2]]];
                        }
                    }
                }

                break;
            case Instruction.OP_DIV:
                // Div with Const
                if (i.flags[0] == Instruction.FLAG_REGISTER) {
                    if (i.flags[2] == Instruction.FLAG_CONSTANT && i.addrs[2] != 0) {
                        if (i.flags[1] == Instruction.FLAG_CONSTANT) {
                            this.register[i.addrs[0]] = i.addrs[1] / i.addrs[2];
                        } else if (i.flags[1] == Instruction.FLAG_REGISTER) {
                            this.register[i.addrs[0]] = register[i.addrs[1]] / i.addrs[2];
                        } else if (i.flags[1] == Instruction.FLAG_DIRECT_MEM) {
                            this.register[i.addrs[0]] = memory[i.addrs[1]] / i.addrs[2];
                        } else if (i.flags[1] == Instruction.FLAG_IN_REG_MEM) {
                            this.register[i.addrs[0]] = register[memory[i.addrs[1]]] / i.addrs[2];
                        } else if (i.flags[1] == Instruction.FLAG_IN_MEM_MEM) {
                            this.register[i.addrs[0]] = memory[memory[i.addrs[1]]] / i.addrs[2];
                        }
                    } // Div with Reg Reference
                    else if (i.flags[2] == Instruction.FLAG_REGISTER && register[i.addrs[2]] != 0) {
                        if (i.flags[1] == Instruction.FLAG_CONSTANT) {
                            this.register[i.addrs[0]] = i.addrs[1] / register[i.addrs[2]];
                        } else if (i.flags[1] == Instruction.FLAG_REGISTER) {
                            this.register[i.addrs[0]] = register[i.addrs[1]] / register[i.addrs[2]];
                        } else if (i.flags[1] == Instruction.FLAG_DIRECT_MEM) {
                            this.register[i.addrs[0]] = memory[i.addrs[1]] / register[i.addrs[2]];
                        } else if (i.flags[1] == Instruction.FLAG_IN_REG_MEM) {
                            this.register[i.addrs[0]] = register[memory[i.addrs[1]]] / register[i.addrs[2]];
                        } else if (i.flags[1] == Instruction.FLAG_IN_MEM_MEM) {
                            this.register[i.addrs[0]] = memory[memory[i.addrs[1]]] / register[i.addrs[2]];
                        }
                    } // Div with Mem Reference
                    else if (i.flags[2] == Instruction.FLAG_DIRECT_MEM && memory[i.addrs[2]] != 0) {
                        if (i.flags[1] == Instruction.FLAG_CONSTANT) {
                            this.register[i.addrs[0]] = i.addrs[1] / memory[i.addrs[2]];
                        } else if (i.flags[1] == Instruction.FLAG_REGISTER) {
                            this.register[i.addrs[0]] = register[i.addrs[1]] / memory[i.addrs[2]];
                        } else if (i.flags[1] == Instruction.FLAG_DIRECT_MEM) {
                            this.register[i.addrs[0]] = memory[i.addrs[1]] / memory[i.addrs[2]];
                        } else if (i.flags[1] == Instruction.FLAG_IN_REG_MEM) {
                            this.register[i.addrs[0]] = register[memory[i.addrs[1]]] / memory[i.addrs[2]];
                        } else if (i.flags[1] == Instruction.FLAG_IN_MEM_MEM) {
                            this.register[i.addrs[0]] = memory[memory[i.addrs[1]]] / memory[i.addrs[2]];
                        }
                    } // Div with Reg(Mem) Reference
                    else if (i.flags[2] == Instruction.FLAG_IN_REG_MEM && register[memory[i.addrs[2]]] != 0) {
                        if (i.flags[1] == Instruction.FLAG_CONSTANT) {
                            this.register[i.addrs[0]] = i.addrs[1] / register[memory[i.addrs[2]]];
                        } else if (i.flags[1] == Instruction.FLAG_REGISTER) {
                            this.register[i.addrs[0]] = register[i.addrs[1]] / register[memory[i.addrs[2]]];
                        } else if (i.flags[1] == Instruction.FLAG_DIRECT_MEM) {
                            this.register[i.addrs[0]] = memory[i.addrs[1]] / register[memory[i.addrs[2]]];
                        } else if (i.flags[1] == Instruction.FLAG_IN_REG_MEM) {
                            this.register[i.addrs[0]] = register[memory[i.addrs[1]]] / register[memory[i.addrs[2]]];
                        } else if (i.flags[1] == Instruction.FLAG_IN_MEM_MEM) {
                            this.register[i.addrs[0]] = memory[memory[i.addrs[1]]] / register[memory[i.addrs[2]]];
                        }
                    } // Div with Mem(Mem) Reference
                    else if (i.flags[2] == Instruction.FLAG_IN_MEM_MEM && memory[memory[i.addrs[2]]] != 0) {
                        if (i.flags[1] == Instruction.FLAG_CONSTANT) {
                            this.register[i.addrs[0]] = i.addrs[1] / memory[memory[i.addrs[2]]];
                        } else if (i.flags[1] == Instruction.FLAG_REGISTER) {
                            this.register[i.addrs[0]] = register[i.addrs[1]] / memory[memory[i.addrs[2]]];
                        } else if (i.flags[1] == Instruction.FLAG_DIRECT_MEM) {
                            this.register[i.addrs[0]] = memory[i.addrs[1]] / memory[memory[i.addrs[2]]];
                        } else if (i.flags[1] == Instruction.FLAG_IN_REG_MEM) {
                            this.register[i.addrs[0]] = register[memory[i.addrs[1]]] / memory[memory[i.addrs[2]]];
                        } else if (i.flags[1] == Instruction.FLAG_IN_MEM_MEM) {
                            this.register[i.addrs[0]] = memory[memory[i.addrs[1]]] / memory[memory[i.addrs[2]]];
                        }
                    } else {
                        this.register[i.addrs[0]] = 0;
                    }
                } else if (i.flags[0] == Instruction.FLAG_DIRECT_MEM) {
                    if (i.flags[2] == Instruction.FLAG_CONSTANT && i.addrs[2] != 0) {
                        if (i.flags[1] == Instruction.FLAG_CONSTANT) {
                            this.memory[i.addrs[0]] = i.addrs[1] / i.addrs[2];
                        } else if (i.flags[1] == Instruction.FLAG_REGISTER) {
                            this.memory[i.addrs[0]] = register[i.addrs[1]] / i.addrs[2];
                        } else if (i.flags[1] == Instruction.FLAG_DIRECT_MEM) {
                            this.memory[i.addrs[0]] = memory[i.addrs[1]] / i.addrs[2];
                        } else if (i.flags[1] == Instruction.FLAG_IN_REG_MEM) {
                            this.memory[i.addrs[0]] = register[memory[i.addrs[1]]] / i.addrs[2];
                        } else if (i.flags[1] == Instruction.FLAG_IN_MEM_MEM) {
                            this.memory[i.addrs[0]] = memory[memory[i.addrs[1]]] / i.addrs[2];
                        }
                    } // Div with Reg Reference
                    else if (i.flags[2] == Instruction.FLAG_REGISTER && register[i.addrs[2]] != 0) {
                        if (i.flags[1] == Instruction.FLAG_CONSTANT) {
                            this.memory[i.addrs[0]] = i.addrs[1] / register[i.addrs[2]];
                        } else if (i.flags[1] == Instruction.FLAG_REGISTER) {
                            this.memory[i.addrs[0]] = register[i.addrs[1]] / register[i.addrs[2]];
                        } else if (i.flags[1] == Instruction.FLAG_DIRECT_MEM) {
                            this.memory[i.addrs[0]] = memory[i.addrs[1]] / register[i.addrs[2]];
                        } else if (i.flags[1] == Instruction.FLAG_IN_REG_MEM) {
                            this.memory[i.addrs[0]] = register[memory[i.addrs[1]]] / register[i.addrs[2]];
                        } else if (i.flags[1] == Instruction.FLAG_IN_MEM_MEM) {
                            this.memory[i.addrs[0]] = memory[memory[i.addrs[1]]] / register[i.addrs[2]];
                        }
                    } // Div with Mem Reference
                    else if (i.flags[2] == Instruction.FLAG_DIRECT_MEM && memory[i.addrs[2]] != 0) {
                        if (i.flags[1] == Instruction.FLAG_CONSTANT) {
                            this.memory[i.addrs[0]] = i.addrs[1] / memory[i.addrs[2]];
                        } else if (i.flags[1] == Instruction.FLAG_REGISTER) {
                            this.memory[i.addrs[0]] = register[i.addrs[1]] / memory[i.addrs[2]];
                        } else if (i.flags[1] == Instruction.FLAG_DIRECT_MEM) {
                            this.memory[i.addrs[0]] = memory[i.addrs[1]] / memory[i.addrs[2]];
                        } else if (i.flags[1] == Instruction.FLAG_IN_REG_MEM) {
                            this.memory[i.addrs[0]] = register[memory[i.addrs[1]]] / memory[i.addrs[2]];
                        } else if (i.flags[1] == Instruction.FLAG_IN_MEM_MEM) {
                            this.memory[i.addrs[0]] = memory[memory[i.addrs[1]]] / memory[i.addrs[2]];
                        }
                    } // Div with Reg(Mem) Reference
                    else if (i.flags[2] == Instruction.FLAG_IN_REG_MEM && register[memory[i.addrs[2]]] != 0) {
                        if (i.flags[1] == Instruction.FLAG_CONSTANT) {
                            this.memory[i.addrs[0]] = i.addrs[1] / register[memory[i.addrs[2]]];
                        } else if (i.flags[1] == Instruction.FLAG_REGISTER) {
                            this.memory[i.addrs[0]] = register[i.addrs[1]] / register[memory[i.addrs[2]]];
                        } else if (i.flags[1] == Instruction.FLAG_DIRECT_MEM) {
                            this.memory[i.addrs[0]] = memory[i.addrs[1]] / register[memory[i.addrs[2]]];
                        } else if (i.flags[1] == Instruction.FLAG_IN_REG_MEM) {
                            this.memory[i.addrs[0]] = register[memory[i.addrs[1]]] / register[memory[i.addrs[2]]];
                        } else if (i.flags[1] == Instruction.FLAG_IN_MEM_MEM) {
                            this.memory[i.addrs[0]] = memory[memory[i.addrs[1]]] / register[memory[i.addrs[2]]];
                        }
                    } // Div with Mem(Mem) Reference
                    else if (i.flags[2] == Instruction.FLAG_IN_MEM_MEM && memory[memory[i.addrs[2]]] != 0) {
                        if (i.flags[1] == Instruction.FLAG_CONSTANT) {
                            this.memory[i.addrs[0]] = i.addrs[1] / memory[memory[i.addrs[2]]];
                        } else if (i.flags[1] == Instruction.FLAG_REGISTER) {
                            this.memory[i.addrs[0]] = register[i.addrs[1]] / memory[memory[i.addrs[2]]];
                        } else if (i.flags[1] == Instruction.FLAG_DIRECT_MEM) {
                            this.memory[i.addrs[0]] = memory[i.addrs[1]] / memory[memory[i.addrs[2]]];
                        } else if (i.flags[1] == Instruction.FLAG_IN_REG_MEM) {
                            this.memory[i.addrs[0]] = register[memory[i.addrs[1]]] / memory[memory[i.addrs[2]]];
                        } else if (i.flags[1] == Instruction.FLAG_IN_MEM_MEM) {
                            this.memory[i.addrs[0]] = memory[memory[i.addrs[1]]] / memory[memory[i.addrs[2]]];
                        }
                    } else {
                        this.memory[i.addrs[0]] = 0;
                    }
                }

                break;
            case Instruction.OP_MOD:
                if (i.flags[0] == Instruction.FLAG_REGISTER) {
                    if (i.flags[2] == Instruction.FLAG_CONSTANT && i.addrs[2] != 0) {
                        if (i.flags[1] == Instruction.FLAG_CONSTANT) {
                            this.register[i.addrs[0]] = i.addrs[1] % i.addrs[2];
                        } else if (i.flags[1] == Instruction.FLAG_REGISTER) {
                            this.register[i.addrs[0]] = register[i.addrs[1]] % i.addrs[2];
                        } else if (i.flags[1] == Instruction.FLAG_DIRECT_MEM) {
                            this.register[i.addrs[0]] = memory[i.addrs[1]] % i.addrs[2];
                        } else if (i.flags[1] == Instruction.FLAG_IN_REG_MEM) {
                            this.register[i.addrs[0]] = register[memory[i.addrs[1]]] % i.addrs[2];
                        } else if (i.flags[1] == Instruction.FLAG_IN_MEM_MEM) {
                            this.register[i.addrs[0]] = memory[memory[i.addrs[1]]] % i.addrs[2];
                        }
                    } // Mod with Reg Reference
                    else if (i.flags[2] == Instruction.FLAG_REGISTER && register[i.addrs[2]] != 0) {
                        if (i.flags[1] == Instruction.FLAG_CONSTANT) {
                            this.register[i.addrs[0]] = i.addrs[1] % register[i.addrs[2]];
                        } else if (i.flags[1] == Instruction.FLAG_REGISTER) {
                            this.register[i.addrs[0]] = register[i.addrs[1]] % register[i.addrs[2]];
                        } else if (i.flags[1] == Instruction.FLAG_DIRECT_MEM) {
                            this.register[i.addrs[0]] = memory[i.addrs[1]] % register[i.addrs[2]];
                        } else if (i.flags[1] == Instruction.FLAG_IN_REG_MEM) {
                            this.register[i.addrs[0]] = register[memory[i.addrs[1]]] % register[i.addrs[2]];
                        } else if (i.flags[1] == Instruction.FLAG_IN_MEM_MEM) {
                            this.register[i.addrs[0]] = memory[memory[i.addrs[1]]] % register[i.addrs[2]];
                        }
                    } // Mod with Mem Reference
                    else if (i.flags[2] == Instruction.FLAG_DIRECT_MEM && memory[i.addrs[2]] != 0) {
                        if (i.flags[1] == Instruction.FLAG_CONSTANT) {
                            this.register[i.addrs[0]] = i.addrs[1] % memory[i.addrs[2]];
                        } else if (i.flags[1] == Instruction.FLAG_REGISTER) {
                            this.register[i.addrs[0]] = register[i.addrs[1]] % memory[i.addrs[2]];
                        } else if (i.flags[1] == Instruction.FLAG_DIRECT_MEM) {
                            this.register[i.addrs[0]] = memory[i.addrs[1]] % memory[i.addrs[2]];
                        } else if (i.flags[1] == Instruction.FLAG_IN_REG_MEM) {
                            this.register[i.addrs[0]] = register[memory[i.addrs[1]]] % memory[i.addrs[2]];
                        } else if (i.flags[1] == Instruction.FLAG_IN_MEM_MEM) {
                            this.register[i.addrs[0]] = memory[memory[i.addrs[1]]] % memory[i.addrs[2]];
                        }
                    } // Mod with Reg(Mem) Reference
                    else if (i.flags[2] == Instruction.FLAG_IN_REG_MEM && register[memory[i.addrs[2]]] != 0) {
                        if (i.flags[1] == Instruction.FLAG_CONSTANT) {
                            this.register[i.addrs[0]] = i.addrs[1] % register[memory[i.addrs[2]]];
                        } else if (i.flags[1] == Instruction.FLAG_REGISTER) {
                            this.register[i.addrs[0]] = register[i.addrs[1]] % register[memory[i.addrs[2]]];
                        } else if (i.flags[1] == Instruction.FLAG_DIRECT_MEM) {
                            this.register[i.addrs[0]] = memory[i.addrs[1]] % register[memory[i.addrs[2]]];
                        } else if (i.flags[1] == Instruction.FLAG_IN_REG_MEM) {
                            this.register[i.addrs[0]] = register[memory[i.addrs[1]]] % register[memory[i.addrs[2]]];
                        } else if (i.flags[1] == Instruction.FLAG_IN_MEM_MEM) {
                            this.register[i.addrs[0]] = memory[memory[i.addrs[1]]] % register[memory[i.addrs[2]]];
                        }
                    } // Mod with Mem(Mem) Reference
                    else if (i.flags[2] == Instruction.FLAG_IN_MEM_MEM && memory[memory[i.addrs[2]]] != 0) {
                        if (i.flags[1] == Instruction.FLAG_CONSTANT) {
                            this.register[i.addrs[0]] = i.addrs[1] % memory[memory[i.addrs[2]]];
                        } else if (i.flags[1] == Instruction.FLAG_REGISTER) {
                            this.register[i.addrs[0]] = register[i.addrs[1]] % memory[memory[i.addrs[2]]];
                        } else if (i.flags[1] == Instruction.FLAG_DIRECT_MEM) {
                            this.register[i.addrs[0]] = memory[i.addrs[1]] % memory[memory[i.addrs[2]]];
                        } else if (i.flags[1] == Instruction.FLAG_IN_REG_MEM) {
                            this.register[i.addrs[0]] = register[memory[i.addrs[1]]] % memory[memory[i.addrs[2]]];
                        } else if (i.flags[1] == Instruction.FLAG_IN_MEM_MEM) {
                            this.register[i.addrs[0]] = memory[memory[i.addrs[1]]] % memory[memory[i.addrs[2]]];
                        }
                    }
                } else if(i.flags[0] == Instruction.FLAG_DIRECT_MEM){
                    if (i.flags[2] == Instruction.FLAG_CONSTANT && i.addrs[2] != 0) {
                        if (i.flags[1] == Instruction.FLAG_CONSTANT) {
                            this.memory[i.addrs[0]] = i.addrs[1] % i.addrs[2];
                        } else if (i.flags[1] == Instruction.FLAG_REGISTER) {
                            this.memory[i.addrs[0]] = register[i.addrs[1]] % i.addrs[2];
                        } else if (i.flags[1] == Instruction.FLAG_DIRECT_MEM) {
                            this.memory[i.addrs[0]] = memory[i.addrs[1]] % i.addrs[2];
                        } else if (i.flags[1] == Instruction.FLAG_IN_REG_MEM) {
                            this.memory[i.addrs[0]] = register[memory[i.addrs[1]]] % i.addrs[2];
                        } else if (i.flags[1] == Instruction.FLAG_IN_MEM_MEM) {
                            this.memory[i.addrs[0]] = memory[memory[i.addrs[1]]] % i.addrs[2];
                        }
                    } // Mod with Reg Reference
                    else if (i.flags[2] == Instruction.FLAG_REGISTER && register[i.addrs[2]] != 0) {
                        if (i.flags[1] == Instruction.FLAG_CONSTANT) {
                            this.memory[i.addrs[0]] = i.addrs[1] % register[i.addrs[2]];
                        } else if (i.flags[1] == Instruction.FLAG_REGISTER) {
                            this.memory[i.addrs[0]] = register[i.addrs[1]] % register[i.addrs[2]];
                        } else if (i.flags[1] == Instruction.FLAG_DIRECT_MEM) {
                            this.memory[i.addrs[0]] = memory[i.addrs[1]] % register[i.addrs[2]];
                        } else if (i.flags[1] == Instruction.FLAG_IN_REG_MEM) {
                            this.memory[i.addrs[0]] = register[memory[i.addrs[1]]] % register[i.addrs[2]];
                        } else if (i.flags[1] == Instruction.FLAG_IN_MEM_MEM) {
                            this.memory[i.addrs[0]] = memory[memory[i.addrs[1]]] % register[i.addrs[2]];
                        }
                    } // Mod with Mem Reference
                    else if (i.flags[2] == Instruction.FLAG_DIRECT_MEM && memory[i.addrs[2]] != 0) {
                        if (i.flags[1] == Instruction.FLAG_CONSTANT) {
                            this.memory[i.addrs[0]] = i.addrs[1] % memory[i.addrs[2]];
                        } else if (i.flags[1] == Instruction.FLAG_REGISTER) {
                            this.memory[i.addrs[0]] = register[i.addrs[1]] % memory[i.addrs[2]];
                        } else if (i.flags[1] == Instruction.FLAG_DIRECT_MEM) {
                            this.memory[i.addrs[0]] = memory[i.addrs[1]] % memory[i.addrs[2]];
                        } else if (i.flags[1] == Instruction.FLAG_IN_REG_MEM) {
                            this.memory[i.addrs[0]] = register[memory[i.addrs[1]]] % memory[i.addrs[2]];
                        } else if (i.flags[1] == Instruction.FLAG_IN_MEM_MEM) {
                            this.memory[i.addrs[0]] = memory[memory[i.addrs[1]]] % memory[i.addrs[2]];
                        }
                    } // Mod with Reg(Mem) Reference
                    else if (i.flags[2] == Instruction.FLAG_IN_REG_MEM && register[memory[i.addrs[2]]] != 0) {
                        if (i.flags[1] == Instruction.FLAG_CONSTANT) {
                            this.memory[i.addrs[0]] = i.addrs[1] % register[memory[i.addrs[2]]];
                        } else if (i.flags[1] == Instruction.FLAG_REGISTER) {
                            this.memory[i.addrs[0]] = register[i.addrs[1]] % register[memory[i.addrs[2]]];
                        } else if (i.flags[1] == Instruction.FLAG_DIRECT_MEM) {
                            this.memory[i.addrs[0]] = memory[i.addrs[1]] % register[memory[i.addrs[2]]];
                        } else if (i.flags[1] == Instruction.FLAG_IN_REG_MEM) {
                            this.memory[i.addrs[0]] = register[memory[i.addrs[1]]] % register[memory[i.addrs[2]]];
                        } else if (i.flags[1] == Instruction.FLAG_IN_MEM_MEM) {
                            this.memory[i.addrs[0]] = memory[memory[i.addrs[1]]] % register[memory[i.addrs[2]]];
                        }
                    } // Mod with Mem(Mem) Reference
                    else if (i.flags[2] == Instruction.FLAG_IN_MEM_MEM && memory[memory[i.addrs[2]]] != 0) {
                        if (i.flags[1] == Instruction.FLAG_CONSTANT) {
                            this.memory[i.addrs[0]] = i.addrs[1] % memory[memory[i.addrs[2]]];
                        } else if (i.flags[1] == Instruction.FLAG_REGISTER) {
                            this.memory[i.addrs[0]] = register[i.addrs[1]] % memory[memory[i.addrs[2]]];
                        } else if (i.flags[1] == Instruction.FLAG_DIRECT_MEM) {
                            this.memory[i.addrs[0]] = memory[i.addrs[1]] % memory[memory[i.addrs[2]]];
                        } else if (i.flags[1] == Instruction.FLAG_IN_REG_MEM) {
                            this.memory[i.addrs[0]] = register[memory[i.addrs[1]]] % memory[memory[i.addrs[2]]];
                        } else if (i.flags[1] == Instruction.FLAG_IN_MEM_MEM) {
                            this.memory[i.addrs[0]] = memory[memory[i.addrs[1]]] % memory[memory[i.addrs[2]]];
                        }
                    }
                }        
                break;
            case Instruction.OP_IF_EQ:
                if (i.flags[0] == Instruction.FLAG_REGISTER && i.flags[1] == Instruction.FLAG_CONSTANT) {
                    if (register[i.addrs[0]] == i.addrs[1]) {
                        programCounter = i.addrs[2] - 1;
                    }
                } else if (i.flags[0] == Instruction.FLAG_REGISTER && i.flags[1] == Instruction.FLAG_REGISTER) {
                    if (Objects.equals(register[i.addrs[0]], register[i.addrs[1]])) {
                        programCounter = i.addrs[2] - 1;
                    }
                } else if (i.flags[0] == Instruction.FLAG_REGISTER && i.flags[1] == Instruction.FLAG_IN_REG_MEM) {
                    if (Objects.equals(register[i.addrs[0]], register[memory[i.addrs[1]]])) {
                        programCounter = i.addrs[2] - 1;
                    }
                } else if (i.flags[0] == Instruction.FLAG_REGISTER && i.flags[1] == Instruction.FLAG_IN_MEM_MEM) {
                    if (Objects.equals(register[i.addrs[0]], memory[memory[i.addrs[1]]])) {
                        programCounter = i.addrs[2] - 1;
                    }
                } else if (i.flags[0] == Instruction.FLAG_REGISTER && i.flags[1] == Instruction.FLAG_DIRECT_MEM) {
                    if (Objects.equals(register[i.addrs[0]], memory[i.addrs[1]])) {
                        programCounter = i.addrs[2] - 1;
                    }
                }
                break;
            case Instruction.OP_IF_G:
                if (i.flags[0] == Instruction.FLAG_REGISTER && i.flags[1] == Instruction.FLAG_CONSTANT) {
                    if (register[i.addrs[0]] > i.addrs[1]) {
                        programCounter = i.addrs[2] - 1;
                    }
                } else if (i.flags[0] == Instruction.FLAG_REGISTER && i.flags[1] == Instruction.FLAG_REGISTER) {
                    if (register[i.addrs[0]] > register[i.addrs[1]]) {
                        programCounter = i.addrs[2] - 1;
                    }
                } else if (i.flags[0] == Instruction.FLAG_REGISTER && i.flags[1] == Instruction.FLAG_IN_REG_MEM) {
                    if (register[i.addrs[0]] > register[memory[i.addrs[1]]]) {
                        programCounter = i.addrs[2] - 1;
                    }
                } else if (i.flags[0] == Instruction.FLAG_REGISTER && i.flags[1] == Instruction.FLAG_IN_MEM_MEM) {
                    if (register[i.addrs[0]] > memory[memory[i.addrs[1]]]) {
                        programCounter = i.addrs[2] - 1;
                    }
                } else if (i.flags[0] == Instruction.FLAG_REGISTER && i.flags[1] == Instruction.FLAG_DIRECT_MEM) {
                    if (register[i.addrs[0]] > memory[i.addrs[1]]) {
                        programCounter = i.addrs[2] - 1;
                    }
                }
                break;
            case Instruction.OP_IF_L:
                if (i.flags[0] == Instruction.FLAG_REGISTER && i.flags[1] == Instruction.FLAG_CONSTANT) {
                    if (register[i.addrs[0]] < i.addrs[1]) {
                        programCounter = i.addrs[2] - 1;
                    }
                } else if (i.flags[0] == Instruction.FLAG_REGISTER && i.flags[1] == Instruction.FLAG_REGISTER) {
                    if (register[i.addrs[0]] < register[i.addrs[1]]) {
                        programCounter = i.addrs[2] - 1;
                    }
                } else if (i.flags[0] == Instruction.FLAG_REGISTER && i.flags[1] == Instruction.FLAG_IN_REG_MEM) {
                    if (register[i.addrs[0]] < register[memory[i.addrs[1]]]) {
                        programCounter = i.addrs[2] - 1;
                    }
                } else if (i.flags[0] == Instruction.FLAG_REGISTER && i.flags[1] == Instruction.FLAG_IN_MEM_MEM) {
                    if (register[i.addrs[0]] < memory[memory[i.addrs[1]]]) {
                        programCounter = i.addrs[2] - 1;
                    }
                } else if (i.flags[0] == Instruction.FLAG_REGISTER && i.flags[1] == Instruction.FLAG_DIRECT_MEM) {
                    if (register[i.addrs[0]] < memory[i.addrs[1]]) {
                        programCounter = i.addrs[2] - 1;
                    }
                }
                break;
            case Instruction.OP_GOTO:
                this.programCounter = (i.addrs[0] - 1);
                break;
            case Instruction.OP_PUSH:
                stack.push(register[0]);
                break;
            case Instruction.OP_POP:
                register[0] = stack.pop();
                break;
            case Instruction.OP_STACK_ADD:
                operant1 = stack.pop();
                operant2 = stack.pop();
                operant1 = operant1 + operant2;
                stack.push(operant1);
                break;
            case Instruction.OP_STACK_SUB:
                operant2 = stack.pop();
                operant1 = stack.pop();
                operant1 = operant1 - operant2;
                stack.push(operant1);
                break;
            case Instruction.OP_STACK_MUL:
                operant1 = stack.pop();
                operant2 = stack.pop();
                operant1 = operant1 * operant2;
                stack.push(operant1);
                break;
            case Instruction.OP_STACK_DIV:
                operant2 = stack.pop();
                operant1 = stack.pop();
                if (operant2 != 0) {
                    operant1 = operant1 / operant2;
                    stack.push(operant1);
                } else {
                    stack.push(0);
                }
                break;
            case Instruction.OP_STACK_MOD:
                operant1 = stack.pop();
                operant2 = stack.pop();
                operant1 = operant2 % operant1;
                stack.push(operant1);
                break;
            case Instruction.OP_CALL:
                this.callStack.push(programCounter);
                this.programCounter = (i.addrs[0] - 1);
                break;
            case Instruction.OP_RETURN:
                if (this.callStack.empty()) {
                    this.status = VM.TERMINATED;
                    //TODO Remove debug output
                    System.out.println("End of progam");
                } else {
                    this.programCounter = callStack.pop();
                }
                break;
            default:
                System.out.println("Bad Instruktion ..."); //TODO throw exception with message?
                break;
        }

        return 0;
    }

    public void restart() {
        this.programCounter = alphaProgramm.getEntryPoint();	
        this.stack = new JStack<Integer>();
        this.callStack = new Stack<Integer>();
        this.memory = new Integer[this.alphaProgramm.getMemSize()];
        this.register = new Integer[this.alphaProgramm.getRegSize()];
        this.timeOut = 1;
    }

    public static final int PAUSE = 0;
    public static final int RUNNING = 1;
    public static final int TERMINATED = -1;

    public int getLineIndex() {
        return this.progCode.get(this.programCounter).getLineIndex();
    }

}
