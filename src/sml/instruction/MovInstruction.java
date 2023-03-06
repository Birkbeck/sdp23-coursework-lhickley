package sml.instruction;

import sml.Instruction;
import sml.Machine;
import sml.RegisterName;
import sml.Registers;

import java.util.Objects;

/**
 * The MovInstruction class represents an instruction in a machine language program that sets a register to a given value.
 *
 * @author lhickley
 */

public class MovInstruction extends Instruction {

    private final RegisterName registerToSet;
    private final int value;

    public static final String OP_CODE = "mov";

    public MovInstruction(String label, RegisterName registerToSet, int value) {
        super(label, OP_CODE);
        this.registerToSet = registerToSet;
        this.value = value;
    }

    /**
     * Executes this MovInstruction on the given machine, setting the specified register to the specified value.
     * @param m the machine on which to execute this instruction
     * @return the amount by which to increment the program counter after executing this instruction
     */
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

    /**
     * Compares this MovInstruction to the specified object.
     *
     * Two MulInstruction objects are considered equal if they have the same label (or no label) and the same
     * register to set, and value
     *
     * @param o the object to compare to this instruction
     * @return true if the given object is equal to this instruction, false otherwise
     *
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MovInstruction that = (MovInstruction) o;
        if (label != null) {
            return label.equals(that.label) && value == that.value && registerToSet.equals(that.registerToSet);
        } else {
            return value == that.value && registerToSet.equals(that.registerToSet);
        }
    }

    /**
     * Returns a hash code value for the {@code MovInstruction} object.
     * The hash code is based on the opcode, registerToSet, and value to set.
     * If the instruction has a non-null label, it is also included in the hash code calculation.
     * @return the hash code value for this {@code MovInstruction} object
     */
    @Override
    public int hashCode() {
        if (label != null) {
            return Objects.hash(label, opcode, registerToSet, value);
        } else {
            return Objects.hash(opcode, registerToSet, value);
        }
    }
}
