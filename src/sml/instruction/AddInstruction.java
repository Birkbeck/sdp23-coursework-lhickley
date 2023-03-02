package sml.instruction;

import sml.Instruction;
import sml.Machine;
import sml.RegisterName;
import sml.UnderOverFlowHandling;

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
		handleOverUnderFlow(value1, value2, res, result.toString(), source.toString());
		/*if (((value1 ^ res) & (value2 ^ res)) < 0) {
			if (res < 0) {
				throw new ArithmeticException("The addition of values " + value1 + " and " + value2 + " stored in the " +
						"registers " + result + " and " + source + " cannot be performed.\nThis will lead to a value " +
						"overflow in the " + result + " register.\nThe maximum value which can be summed to is 2,147,483,647"
				);
			} else {
				throw new ArithmeticException("The addition of values " + value1 + " and " + value2 + " stored in the " +
						"registers " + result + " and " + source + " cannot be performed.\nThis will lead to a value " +
						"underflow in the " + result + " register.\nThe minimum value which can be summed to is -2,147,483,648"
				);
			}
		}*/
		m.getRegisters().set(result, value1 + value2);
		return NORMAL_PROGRAM_COUNTER_UPDATE;
	}

	@Override
	public String toString() {
		return getLabelString() + getOpcode() + " " + result + " " + source;
	}
}
