package sml.instruction;

import sml.*;

import java.util.Objects;

/**
 * The AddInstruction class represents an instruction that adds two values from registers and stores the result in
 * the first register.
 *
 * It extends the Instruction class and implements the UnderOverFlowHandling interface.
 *
 * @author lhickley
 */

public class AddInstruction extends Instruction implements UnderOverFlowHandling {
	private final RegisterName result;
	private final RegisterName source;

	public static final String OP_CODE = "add";

	public AddInstruction(String label, RegisterName result, RegisterName source) {
		super(label, OP_CODE);
		this.result = result;
		this.source = source;
	}

	/**
	 * Executes the AddInstruction by adding the values from two registers and storing the result in the register specified
	 * by the 'result' attribute.
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
		int res = value1 + value2;
		UnderOverFlowHelper helper = (a, b, c) -> ((a ^ c) & (b ^ c)) < 0;
		handleOverUnderFlow(value1, value2, res, result.toString(), source.toString(), opcode, helper);
		m.getRegisters().set(result, res);
		return NORMAL_PROGRAM_COUNTER_UPDATE;
	}

	@Override
	public String toString() {
		return getLabelString() + getOpcode() + " " + result + " " + source;
	}

	/**
	 * Determines whether a label exists, if it does then use this with all the other fields for the instance to
	 * compare equality.  If it does not, use all the fields.
	 *
	 * @param o the object to compare to
	 * @return a boolean defining whether the objects are equal
	 */
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		AddInstruction that = (AddInstruction) o;
		if (this.label != null) {
			return label.equals(that.label) && result.equals(that.result) && source.equals(that.source);
		} else {
			return result.equals(that.result) && source.equals(that.source);
		}
	}

	/**
	 * Determines whether a label exists, if it does then use this with all the other fields for the instance to
	 * construct the hashcode.  If it does not, use all the fields.
	 *
	 * @return int the hash code for the object
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
