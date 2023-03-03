package sml;

import sml.instruction.*;

import java.util.ArrayList;
import java.util.Arrays;

import static sml.Registers.Register;

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
                    throw new RuntimeException("For " + opcode + " instructions, only two arguments are accepted after the opcode");
                }
            }
            return switch (opcode) {
                case "add" -> new AddInstruction(label, Register.valueOf(args.get(0)), Register.valueOf(args.get(1)));
                case "sub" -> new SubInstruction(label, Register.valueOf(args.get(0)), Register.valueOf(args.get(1)));
                case "mul" -> new MulInstruction(label, Register.valueOf(args.get(0)), Register.valueOf(args.get(1)));
                case "div" -> new DivInstruction(label, Register.valueOf(args.get(0)), Register.valueOf(args.get(1)));
                case "out" -> new OutInstruction(label, Register.valueOf(args.get(0)));
                case "mov" -> new MovInstruction(label, Register.valueOf(args.get(0)), Integer.parseInt(args.get(1)));
                case "jnz" -> new JnzInstruction(label, Register.valueOf(args.get(0)), args.get(1));
                default -> throw new IllegalArgumentException("Unknown instruction '" + opcode + "'");
            };
        } catch (IllegalArgumentException e) {
            String eString = e.toString();
            String errorMessage = "Illegal arguments passed for '" + opcode + "'\n";
            if (eString.contains("No enum constant")) {
                errorMessage = errorMessage + "Permitted register values are: \n" + Arrays.toString(Register.values());
            } else if (eString.contains("Unknown instruction")) {
                errorMessage = errorMessage + "Unknown instruction '" + opcode + "'";
            } else if (eString.contains("NumberFormatException")) {
                errorMessage = errorMessage + "Value passed was not an acceptable integer value.\n" +
                        "Value stored in registers must be between -2,147,483,648 and 2,147,483,647 inclusive.";
            }
            throw new RuntimeException(errorMessage);
        }
    }
}

