package sml;

import sml.instruction.*;

import java.util.ArrayList;
import java.util.Arrays;

import static sml.Registers.Register;

//TODO Add some error handling to all of this

public class InstructionFactory {

    private static InstructionFactory instance = null;

    private InstructionFactory() {
    }

    public static InstructionFactory getInstance() {
        if (instance == null) {
            instance = new InstructionFactory();
        }
        return instance;
    }

    public Instruction create(String label, String opcode, ArrayList<String> args) {
        try {
            if (opcode.equals("out")) {
                if (args.size() != 1) {
                    throw new RuntimeException("For out instructions, only one argument is accepted after the opcode");
                }
            } else {
                if (args.size() != 2) {
                    throw new RuntimeException("for " + opcode + " instructions, only two arguments are accepted after the opcode");
                }
            }
            switch (opcode) {
                case "add":
                    return new AddInstruction(label, Register.valueOf(args.get(0)), Register.valueOf(args.get(1)));
                case "sub":
                    return new SubInstruction(label, Register.valueOf(args.get(0)), Register.valueOf(args.get(1)));
                case "mul":
                    return new MulInstruction(label, Register.valueOf(args.get(0)), Register.valueOf(args.get(1)));
                case "div":
                    return new DivInstruction(label, Register.valueOf(args.get(0)), Register.valueOf(args.get(1)));
                case "out":
                    return new OutInstruction(label, Register.valueOf(args.get(0)));
                case "mov":
                    return new MovInstruction(label, Register.valueOf(args.get(0)), Integer.parseInt(args.get(1)));
                case "jnz":
                    return new JnzInstruction(label, Register.valueOf(args.get(0)), args.get(1));
                default:
                    throw new IllegalArgumentException("Unknown instruction: " + opcode);
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Illegal arguments passed for " + opcode);
            System.out.println("Permitted register values are: ");
            System.out.println(Arrays.toString(Register.values()));
        }
        return null;
    }
}

