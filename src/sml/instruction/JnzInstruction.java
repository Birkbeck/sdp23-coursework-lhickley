package sml.instruction;

import sml.*;

public class JnzInstruction extends Instruction {

    private final RegisterName registerToCheck;
    private final String labelToJumpTo;

    public static final String OP_CODE = "jnz";

    public JnzInstruction(String label, RegisterName registerToCheck, String labelToJumpTo) {
        super(label, OP_CODE);
        this.registerToCheck = registerToCheck;
        this.labelToJumpTo = labelToJumpTo;
    }

    @Override
    public int execute(Machine m) {
        Registers registers = m.getRegisters();
        int registerValue = registers.get(registerToCheck);
        if (registerValue == 0) {
            return NORMAL_PROGRAM_COUNTER_UPDATE;
        } else {
            Labels labels = m.getLabels();
            return labels.getAddress(labelToJumpTo);
        }
    }

    @Override
    public String toString() {
        return getLabelString() + getOpcode() + " " + registerToCheck + " " + labelToJumpTo;
    }
}
