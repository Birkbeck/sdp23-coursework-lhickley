package sml.instruction;

import sml.Instruction;
import sml.Machine;
import sml.RegisterName;

public class DivInstruction extends Instruction {
    private final RegisterName result;
    private final RegisterName source;

    public static final String OP_CODE = "div";

    public DivInstruction(String label, RegisterName result, RegisterName source) {
        super(label, OP_CODE);
        this.result = result;
        this.source = source;
    }

    @Override
    public int execute(Machine m) {
        int value1 = m.getRegisters().get(result);
        int value2 = m.getRegisters().get(source);
        if (value2 == 0) {
            throw new RuntimeException("The program is attempting to divide by zero in the register " + source + ".\n" +
                    "This is not permitted, as it is not a valid arithmetic operation.\n" +
                    "Please confirm that the register " + source + " has been correctly set.");
        }
        m.getRegisters().set(result, value1 / value2);
        return NORMAL_PROGRAM_COUNTER_UPDATE;
    }

    @Override
    public String toString() {
        return getLabelString() + getOpcode() + " " + result + " " + source;
    }
}
