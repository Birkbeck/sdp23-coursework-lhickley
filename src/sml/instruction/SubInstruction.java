package sml.instruction;

import sml.*;

import java.util.Objects;

/**
 * The SubInstruction class represents an instruction that subtracts one value in a register from another value in a
 * register and stores the result in the second register.
 *
 * It extends the Instruction class and implements the UnderOverFlowHandling interface.
 *
 * @author lhickley
 */

public class SubInstruction extends Instruction implements UnderOverFlowHandling {
    private final RegisterName result;
    private final RegisterName source;

    public static final String OP_CODE = "sub";

    public SubInstruction(String label, RegisterName result, RegisterName source) {
        super(label, OP_CODE);
        this.result = result;
        this.source = source;
    }

    /**
     * Executes the SubInstruction by subtracting the value in one register from the value in another, and storing
     * the result in the register specified by the 'result' attribute.
     *
     * If overflow or underflow occurs, the appropriate action is taken according to the implementation of the
     * UnderOverFlowHandling interface.
     *
     * @param m the Machine object that this instruction executes for
     * @return an integer representing the next instruction to execute
     */
    @Override
    public int execute(Machine m) {
        int value1 = m.getRegisters().get(result);
        int value2 = m.getRegisters().get(source);
        int res = value1 - value2;
        UnderOverFlowHelper helper = (a, b, c) -> ((a ^ b) & (a ^ c)) < 0;
        handleOverUnderFlow(value1, value2, res, result.toString(), source.toString(), opcode, helper);
        m.getRegisters().set(result, res);
        return NORMAL_PROGRAM_COUNTER_UPDATE;
    }

    @Override
    public String toString() {
        return getLabelString() + getOpcode() + " " + result + " " + source;
    }

    /**
     * Returns whether this SubInstruction is equal to the specified object. Two SubInstructions are equal
     * if they have the same label (or no label), opcode, result register name, and source register name.
     * @param o the object to compare this SubInstruction with
     * @return true if the specified object is equal to this SubInstruction, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SubInstruction that = (SubInstruction) o;
        if (this.label != null) {
            return label.equals(that.label) && result.equals(that.result) && source.equals(that.source);
        } else {
            return result.equals(that.result) && source.equals(that.source);
        }
    }

    /**
     * Returns a hash code value for the {@code SubInstruction} object.
     * The hash code is based on the opcode, result register, and source register.
     * If the instruction has a non-null label, it is also included in the hash code calculation.
     * @return the hash code value for this {@code SubInstruction} object
     */
    @Override
    public int hashCode() {
        if (label != null) {
            return Objects.hash(label, opcode, result, source);
        } else {
            return Objects.hash(opcode, result, source);
        }
    }
}
