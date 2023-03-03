package sml.instruction;

import sml.Instruction;
import sml.Machine;
import sml.RegisterName;

import java.util.Objects;

/**
 * Represents an instruction that divides the value of two registers and stores the result in the first register.
 *
 * @author lhickley
 */
public class DivInstruction extends Instruction {
    private final RegisterName result;
    private final RegisterName source;

    public static final String OP_CODE = "div";

    public DivInstruction(String label, RegisterName result, RegisterName source) {
        super(label, OP_CODE);
        this.result = result;
        this.source = source;
    }


    /**
     * Performs the division operation on the specified registers and stores the result in the result register. If
     * the source register contains a value of 0, a runtime exception is thrown.
     * @param m the machine on which the instruction is being executed
     * @return the amount by which to update the program counter
     * @throws RuntimeException if the source register contains a value of 0
     */
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

    /**
     * Compares the DivInstruction object to another object to determine if they are equal.
     *
     * Two DivInstruction objects are considered equal if they have the same label (or no label) and the same
     * result and source registers.
     *
     * @param o the object to compare to
     * @return true if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DivInstruction that = (DivInstruction) o;
        if (this.label != null) {
            return label.equals(that.label) && result.equals(that.result) && source.equals(that.source);
        } else {
            return result.equals(that.result) && source.equals(that.source);
        }
    }

    /**
     * Returns a hash code value for the {@code DivInstruction} object.
     * The hash code is based on the opcode, result register, and source register.
     * If the instruction has a non-null label, it is also included in the hash code calculation.
     * @return the hash code value for this {@code DivInstruction} object
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
