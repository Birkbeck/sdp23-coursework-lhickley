package sml.instruction;

import sml.Instruction;
import sml.Machine;
import sml.RegisterName;
import sml.Registers;

import java.util.Objects;

/**
 * The OutInstruction class represents an instruction to output the value of a register to the console
 *
 * @author lhickley
 */

public class OutInstruction extends Instruction {

    private final RegisterName source;

    public static final String OP_CODE = "out";

    public OutInstruction(String label, RegisterName source) {
        super(label, OP_CODE);
        this.source = source;
    }

    /**
     * Outputs the value of the register specified by the instruction to the console.
     * @param m the machine on which the instruction is to be executed
     * @return the value by which to update the program counter after the instruction has executed
     */
    @Override
    public int execute(Machine m) {
        Registers registers = m.getRegisters();
        int registerValue = registers.get(source);
        System.out.println(registerValue);
        return NORMAL_PROGRAM_COUNTER_UPDATE;
    }

    @Override
    public String toString() {
        return getLabelString() + getOpcode() + " " + source;
    }

    /**
     * Returns whether this OutInstruction is equal to the specified object.
     *
     * Two OutInstructions are equal if they have the same label (or lack thereof) and the same source register.
     *
     * @param o the object to compare to this OutInstruction
     * @return true if this OutInstruction is equal to the specified object, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OutInstruction that = (OutInstruction) o;
        if (label != null) {
            return label.equals(that.label) && source.equals(that.source);
        } else {
            return source.equals(that.source);
        }
    }

    /**
     * Returns the hash code for this OutInstruction.
     * The hash code is based on the opcode, label (or lack thereof), and source register of the instruction.
     *
     * @return the hash code for this OutInstruction
     */
    @Override
    public int hashCode() {
        if (label != null) {
            return Objects.hash(label, opcode, source);
        } else {
            return Objects.hash(opcode, source);
        }
    }
}
