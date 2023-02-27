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
            System.out.println("Illegal arguments passed for '" + opcode + "'");
            if (eString.contains("No enum constant")) {
                System.out.println("Permitted register values are: ");
                System.out.println(Arrays.toString(Register.values()));
            } else if (eString.contains("Unknown instruction")) {
                System.out.println("Unknown instruction '" + opcode + "'");
            } else if (eString.contains("NumberFormatException")) {
                System.out.println("Non integer value passed where integer value expected");
            }
        }
        return null;
    }
}

