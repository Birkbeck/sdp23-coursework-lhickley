package sml;

import sml.instruction.*;
import static sml.Registers.Register;

//TODO Add some error handling to all of this

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
            case "mul":
                return new MulInstruction(label, Register.valueOf(args[0]), Register.valueOf(args[1]));
            case "div":
                return new DivInstruction(label, Register.valueOf(args[0]), Register.valueOf(args[1]));
            case "out":
                return new OutInstruction(label, Register.valueOf(args[0]));
            case "mov":
                return new MovInstruction(label, Register.valueOf(args[0]), Integer.parseInt(args[1]));
            case "jnz":
                return new JnzInstruction(label, Register.valueOf(args[0]), args[1]);
            default:
                throw new IllegalArgumentException("Unknown instruction: " + opcode);
        }
    }

}
