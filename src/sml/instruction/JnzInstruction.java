package sml.instruction;

import sml.*;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JnzInstruction that = (JnzInstruction) o;
        if (label != null) {
            return label.equals(that.label) && registerToCheck.equals(that.registerToCheck) && labelToJumpTo.equals(that.labelToJumpTo);
        } else {
            return registerToCheck.equals(that.registerToCheck) && labelToJumpTo.equals(that.labelToJumpTo);
        }
    }

    @Override
    public int hashCode() {
        if (label != null) {
            return Objects.hash(label, opcode, registerToCheck, labelToJumpTo);
        } else {
            return Objects.hash(opcode, registerToCheck, labelToJumpTo);
        }
    }
}
