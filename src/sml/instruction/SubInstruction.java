package sml.instruction;

import sml.*;

public class SubInstruction extends Instruction implements UnderOverFlowHandling {
    private final RegisterName result;
    private final RegisterName source;

    public static final String OP_CODE = "sub";

    public SubInstruction(String label, RegisterName result, RegisterName source) {
        super(label, OP_CODE);
        this.result = result;
        this.source = source;
    }

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
}
