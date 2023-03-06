package sml.instruction;

import sml.*;

import java.util.Objects;

/**
 * Represents an instruction that multiplies the value of two registers and stores the result in the first register.
 *
 * Implements the UnderOverFlowHandling interface for handling overflow and underflow situations.
 *
 * @author lhickley
 */

public class MulInstruction extends Instruction implements UnderOverFlowHandling {
    private final RegisterName result;
    private final RegisterName source;

    public static final String OP_CODE = "mul";

    public MulInstruction(String label, RegisterName result, RegisterName source) {
        super(label, OP_CODE);
        this.result = result;
        this.source = source;
    }

    /**
     * Executes the multiplication instruction, multiplies the value in the result register by the value in the source
     * register and stores the result in the result register.
     *
     * Handles overflow and underflow situations.
     *
     * @param m the Machine object that is executing the instruction
     * @return the program counter increment
     */
    @Override
    public int execute(Machine m) {
        int value1 = m.getRegisters().get(result);
        int value2 = m.getRegisters().get(source);
        int res = value1 * value2;
        UnderOverFlowHelper helper = (a, b, c) -> (int)(a * b) != ((long)a * (long)b);
        handleOverUnderFlow(value1, value2, res, result.toString(), source.toString(), opcode, helper);
        m.getRegisters().set(result, res);
        return NORMAL_PROGRAM_COUNTER_UPDATE;
    }

    @Override
    public String toString() {
        return getLabelString() + getOpcode() + " " + result + " " + source;
    }

    /**
     * Determines whether the specified object is equal to this MulInstruction object.
     *
     * Two MulInstruction objects are considered equal if they have the same label (or lack thereof), result register,
     * and source register.
     *
     * @param o the object to compare to this MulInstruction object
     * @return true if the specified object is equal to this MulInstruction object, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MulInstruction that = (MulInstruction) o;
        if (this.label != null) {
            return label.equals(that.label) && result.equals(that.result) && source.equals(that.source);
        } else {
            return result.equals(that.result) && source.equals(that.source);
        }
    }

    /**
     * Returns a hash code value for the {@code MulInstruction} object.
     * The hash code is based on the opcode, result register, and source register.
     * If the instruction has a non-null label, it is also included in the hash code calculation.
     * @return the hash code value for this {@code MulInstruction} object
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
