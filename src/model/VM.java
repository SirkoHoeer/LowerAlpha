package model;

import java.util.HashMap;
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
    private HashMap<String, Integer> memory;
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
        this.memory = new HashMap<String, Integer>();
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

    public HashMap<String, Integer> getMemory() {
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
                System.out.println(progCode.get(programCounter));
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

    private interface IntOperator {
        int op(int a, int b);
    }

    private void performArithmetic(Instruction i, IntOperator op) {
        int[] value = new int[2];
        for (int j = 1; j <= 2; j++) {
            switch (i.flags[j]) {
                case Instruction.FLAG_CONSTANT:
                    value[j - 1] = Integer.parseInt(i.addrs[j]);
                    break;
                case Instruction.FLAG_REGISTER:
                    value[j - 1] = register[Integer.parseInt(i.addrs[j])];
                    break;
                case Instruction.FLAG_DIRECT_MEM:
                    value[j - 1] = memory.get(String.valueOf(i.addrs[j]));
                    break;
                case Instruction.FLAG_IN_REG_MEM:
                    value[j - 1] = memory.get(String.valueOf(register[Integer.parseInt(i.addrs[j])]));
                    break;
                case Instruction.FLAG_IN_MEM_MEM:
                    value[j - 1] = memory.get(String.valueOf(memory.get(String.valueOf(i.addrs[j]))));
                    break;
                default:
                    break;
            }
        }
        int sum = op.op(value[0], value[1]);
        if (i.flags[0] == Instruction.FLAG_REGISTER) {
            this.register[Integer.parseInt(i.addrs[0])] = sum;
        } else if (i.flags[0] == Instruction.FLAG_DIRECT_MEM) {
            this.memory.put(i.addrs[0], sum);
        }
    }

    private int workingInstraktion(Instruction i) {
        switch (i.flags[1]) {
            case Instruction.FLAG_REGISTER:
                try {
                    this.proof = register[Integer.parseInt(i.addrs[1])];
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
            case Instruction.OP_STORE: {
                int value = 0;
                if (i.flags[1] == Instruction.FLAG_CONSTANT) {
                    value = Integer.parseInt(i.addrs[1]);
                } else if (i.flags[1] == Instruction.FLAG_REGISTER) {
                    value = register[Integer.parseInt(i.addrs[1])];
                } else if (i.flags[1] == Instruction.FLAG_DIRECT_MEM) {
                    value = memory.get(String.valueOf(i.addrs[1]));
                } else if (i.flags[1] == Instruction.FLAG_IN_REG_MEM) {
                    value = memory.get(String.valueOf(register[Integer.parseInt(i.addrs[1])]));
                } else if (i.flags[1] == Instruction.FLAG_IN_MEM_MEM) {
                    value = memory.get(String.valueOf(memory.get(String.valueOf(i.addrs[1]))));
                }
                switch (i.flags[0]) {
                    case Instruction.FLAG_REGISTER:
                        this.register[Integer.parseInt(i.addrs[0])] = value;
                        break;
                    case Instruction.FLAG_DIRECT_MEM:
                        this.memory.put(String.valueOf(i.addrs[0]), value);
                        break;
                    case Instruction.FLAG_IN_REG_MEM:
                        this.memory.put(String.valueOf(register[Integer.parseInt(i.addrs[0])]), value);
                        break;
                    case Instruction.FLAG_IN_MEM_MEM:
                        this.memory.put(String.valueOf(memory.get(i.addrs[0])), value);
                        break;
                    default:
                        break;
                }
                break;
            }
            case Instruction.OP_ADD: {
                performArithmetic(i, Integer::sum);
                break;
            }
            case Instruction.OP_SUB: {
                performArithmetic(i, (a, b) -> a - b);
                break;
            }
            case Instruction.OP_MUL: {
                performArithmetic(i, (a, b) -> a * b);
                break;
            }
            case Instruction.OP_DIV: {
                performArithmetic(i, (a, b) -> a / b);
                break;
            }
            case Instruction.OP_MOD:
                performArithmetic(i, (a, b) -> a % b);
                break;
            case Instruction.OP_IF_EQ:
                if (i.flags[0] == Instruction.FLAG_REGISTER && i.flags[1] == Instruction.FLAG_CONSTANT) {
                    if (register[Integer.parseInt(i.addrs[0])] == Integer.parseInt(i.addrs[1])) {
                        programCounter = Integer.parseInt(i.addrs[2]) - 1;
                    }
                } else if (i.flags[0] == Instruction.FLAG_REGISTER && i.flags[1] == Instruction.FLAG_REGISTER) {
                    if (Objects.equals(register[Integer.parseInt(i.addrs[0])], register[Integer.parseInt(i.addrs[1])])) {
                        programCounter = Integer.parseInt(i.addrs[2]) - 1;
                    }
                } else if (i.flags[0] == Instruction.FLAG_REGISTER && i.flags[1] == Instruction.FLAG_IN_REG_MEM) {
                    if (Objects.equals(register[Integer.parseInt(i.addrs[0])], memory.get(String.valueOf(register[Integer.parseInt(i.addrs[1])])))) {
                        programCounter = Integer.parseInt(i.addrs[2]) - 1;
                    }
                } else if (i.flags[0] == Instruction.FLAG_REGISTER && i.flags[1] == Instruction.FLAG_IN_MEM_MEM) {
                    if (Objects.equals(register[Integer.parseInt(i.addrs[0])], memory.get(String.valueOf(memory.get(String.valueOf(register[Integer.parseInt(i.addrs[1])])))))) {
                        programCounter = Integer.parseInt(i.addrs[2]) - 1;
                    }
                } else if (i.flags[0] == Instruction.FLAG_REGISTER && i.flags[1] == Instruction.FLAG_DIRECT_MEM) {
                    if (Objects.equals(register[Integer.parseInt(i.addrs[0])], memory.get(i.addrs[1]))) {
                        programCounter = Integer.parseInt(i.addrs[2]) - 1;
                    }
                }
                break;
            case Instruction.OP_IF_G:
                if (i.flags[0] == Instruction.FLAG_REGISTER && i.flags[1] == Instruction.FLAG_CONSTANT) {
                    if (register[Integer.parseInt(i.addrs[0])] >  Integer.parseInt(i.addrs[1])) {
                        programCounter = Integer.parseInt(i.addrs[2]) - 1;
                    }
                } else if (i.flags[0] == Instruction.FLAG_REGISTER && i.flags[1] == Instruction.FLAG_REGISTER) {
                    if (register[Integer.parseInt(i.addrs[0])] > register[Integer.parseInt(i.addrs[1])]) {
                        programCounter = Integer.parseInt(i.addrs[2]) - 1;
                    }
                } else if (i.flags[0] == Instruction.FLAG_REGISTER && i.flags[1] == Instruction.FLAG_IN_REG_MEM) {
                    if (register[Integer.parseInt(i.addrs[0])] > memory.get(String.valueOf(register[Integer.parseInt(i.addrs[1])]))) {
                        programCounter = Integer.parseInt(i.addrs[2]) - 1;
                    }
                } else if (i.flags[0] == Instruction.FLAG_REGISTER && i.flags[1] == Instruction.FLAG_IN_MEM_MEM) {
                    if (register[Integer.parseInt(i.addrs[0])] > memory.get(String.valueOf(memory.get(i.addrs[1])))) {
                        programCounter = Integer.parseInt(i.addrs[2]) - 1;
                    }
                } else if (i.flags[0] == Instruction.FLAG_REGISTER && i.flags[1] == Instruction.FLAG_DIRECT_MEM) {
                    if (register[Integer.parseInt(i.addrs[0])] >  memory.get(i.addrs[1])) {
                        programCounter = Integer.parseInt(i.addrs[2]) - 1;
                    }
                }
                break;
            case Instruction.OP_IF_L:
                if (i.flags[0] == Instruction.FLAG_REGISTER && i.flags[1] == Instruction.FLAG_CONSTANT) {
                    if (register[Integer.parseInt(i.addrs[0])] <  Integer.parseInt(i.addrs[1])) {
                        programCounter = Integer.parseInt(i.addrs[2]) - 1;
                    }
                } else if (i.flags[0] == Instruction.FLAG_REGISTER && i.flags[1] == Instruction.FLAG_REGISTER) {
                    if (register[Integer.parseInt(i.addrs[0])] < register[Integer.parseInt(i.addrs[1])]) {
                        programCounter = Integer.parseInt(i.addrs[2]) - 1;
                    }
                } else if (i.flags[0] == Instruction.FLAG_REGISTER && i.flags[1] == Instruction.FLAG_IN_REG_MEM) {
                    if (register[Integer.parseInt(i.addrs[0])] < memory.get(String.valueOf(register[Integer.parseInt(i.addrs[1])]))) {
                        programCounter = Integer.parseInt(i.addrs[2]) - 1;
                    }
                } else if (i.flags[0] == Instruction.FLAG_REGISTER && i.flags[1] == Instruction.FLAG_IN_MEM_MEM) {
                    if (register[Integer.parseInt(i.addrs[0])] < memory.get(String.valueOf(memory.get(i.addrs[1])))) {
                        programCounter = Integer.parseInt(i.addrs[2]) - 1;
                    }
                } else if (i.flags[0] == Instruction.FLAG_REGISTER && i.flags[1] == Instruction.FLAG_DIRECT_MEM) {
                    if (register[Integer.parseInt(i.addrs[0])] < memory.get(i.addrs[1])) {
                        programCounter = Integer.parseInt(i.addrs[2]) - 1;
                    }
                }
                break;
            case Instruction.OP_GOTO:
                this.programCounter = (Integer.parseInt(i.addrs[0]) - 1);
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
                this.programCounter = ( Integer.parseInt(i.addrs[0]) - 1);
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
        this.memory = new HashMap<>();
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
