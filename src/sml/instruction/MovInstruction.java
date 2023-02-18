package sml.instruction;

import sml.Instruction;
import sml.Machine;
import sml.RegisterName;
import sml.Registers;

public class MovInstruction extends Instruction {

    private final RegisterName registerToSet;
    private final int value;

    public static final String OP_CODE = "mov";

    public MovInstruction(String label, RegisterName registerToSet, int value) {
        super(label, OP_CODE);
        this.registerToSet = registerToSet;
        this.value = value;
    }

    @Override
    public int execute(Machine m) {
        Registers registers = m.getRegisters();
        registers.set(registerToSet, value);
        return NORMAL_PROGRAM_COUNTER_UPDATE;
    }

    @Override
    public String toString() {
        return getLabelString() + getOpcode() + " " + registerToSet + " " + value;
    }
}
