package sml.instruction;

import sml.*;

import java.util.Objects;

// TODO: write a JavaDoc for the class

/**
 * @author
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

	@Override
	public int hashCode() {
		if (label != null) {
			return Objects.hash(label, opcode, result, source);
		} else {
			return Objects.hash(opcode, result, source);
		}
	}
}
