package sml;

/**
 * Represents an abstract instruction.
 *
 * This is the abstract form of a base unit of work in the program
 *
 * Defines the common methods across all instructions for the program
 *
 * Must be subclassed to give a concrete instruction
 *
 * @author lhickley
 */
public abstract class Instruction {
	protected final String label;
	protected final String opcode;

	/**
	 * Constructor: an instruction with a label and an opcode
	 * (opcode must be an operation of the language)
	 *
	 * @param label optional label (can be null)
	 * @param opcode operation name
	 */
	public Instruction(String label, String opcode) {
		this.label = label;
		this.opcode = opcode;
	}

	public String getLabel() {
		return label;
	}

	public String getOpcode() {
		return opcode;
	}

	public static int NORMAL_PROGRAM_COUNTER_UPDATE = -1;

	/**
	 * Executes the instruction in the given machine.
	 *
	 * @param machine the machine the instruction runs on
	 * @return the new program counter (for jump instructions)
	 *          or NORMAL_PROGRAM_COUNTER_UPDATE to indicate that
	 *          the instruction with the next address is to be executed
	 */

	public abstract int execute(Machine machine);

	protected String getLabelString() {
		return (getLabel() == null) ? "" : getLabel() + ": ";
	}

	/* An abstract method only has a method signature
	 * It does not have a body (so, then, also does not have an implementation)
	 * This forces any class implementing this abstract class to also implement this method
	 */
	@Override
	public abstract String toString();

	/**
	 * Implemented as abstract to enforce implementation in subclasses
	 * There are too many particulars about each subclass to make a default method sensible.
	 * @param o
	 * @return
	 */
	@Override
	public abstract boolean equals(Object o);

	/**
	 * Implemented as abstract to enforce implementation in subclasses
	 * There are too many particulars about each subclass to make a default method sensible.
	 * @return
	 */
	@Override
	public abstract int hashCode();

}
