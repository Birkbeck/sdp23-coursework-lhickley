package sml.instruction;

import sml.*;

import java.util.Objects;

/**
 * Represents a Jnz (Jump if not zero) instruction.
 *
 * This instruction checks the value in a given register and jumps to the specified label if the value is not zero.
 * Otherwise, the program counter is updated normally.
 *
 * @author lhickley
 */

public class JnzInstruction extends Instruction {

    private final RegisterName registerToCheck;
    private final String labelToJumpTo;

    public static final String OP_CODE = "jnz";

    public JnzInstruction(String label, RegisterName registerToCheck, String labelToJumpTo) {
        super(label, OP_CODE);
        this.registerToCheck = registerToCheck;
        this.labelToJumpTo = labelToJumpTo;
    }

    /**
     * Executes this JnzInstruction on the given machine.
     *
     * This instruction checks the value in the register named by {@code registerToCheck}.
     * If the value is zero, the program counter is updated normally.
     * Otherwise, the program counter is set to the address corresponding to the label specified by {@code labelToJumpTo}.
     *
     * @param m the machine on which to execute this instruction
     * @return the updated program counter value
     */
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

    /**
     * Determines whether this JnzInstruction is equal to the given object.
     *
     * This method returns true if and only if the given object is a JnzInstruction with the same label
     * (or lack thereof), register to check and label to jump to as this JnzInstruction.
     *
     * @param o the object to compare this JnzInstruction to
     * @return true if this JnzInstruction is equal to the given object; false otherwise
     */
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

    /**
     * Returns a hash code value for the object. The hash code is generated based on the label, opcode,
     * register to check, and label to jump to.
     * If the label is null, it is not included in the hash code calculation.
     * @return the hash code value for this JnzInstruction
     */
    @Override
    public int hashCode() {
        if (label != null) {
            return Objects.hash(label, opcode, registerToCheck, labelToJumpTo);
        } else {
            return Objects.hash(opcode, registerToCheck, labelToJumpTo);
        }
    }
}
