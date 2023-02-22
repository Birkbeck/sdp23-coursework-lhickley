package sml;

import sml.instruction.*;
import static sml.Registers.Register;

public class InstructionFactory {

    private static InstructionFactory instance = null;

    private InstructionFactory() {}

    public static InstructionFactory getInstance() {
        if (instance == null) {
            instance = new InstructionFactory();
        }
        return instance;
    }

    public Instruction create(String label, String opcode, String[] args) {
        switch (opcode) {
            case "add":
                return new AddInstruction(label, Register.valueOf(args[0]), Register.valueOf(args[1]));
            case "sub":
                return new SubInstruction(label, Register.valueOf(args[0]), Register.valueOf(args[1]));
            // TODO Add the other instructions
            default:
                throw new IllegalArgumentException("Unknown instruction: " + opcode);
        }
    }

}
