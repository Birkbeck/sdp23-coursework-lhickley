package sml;

import sml.instruction.*;

import java.util.ArrayList;
import java.util.Arrays;

import static sml.Registers.Register;

/**
 * The InstructionFactory class is responsible for creating instances of Instruction objects based on the
 * given opcode and arguments.
 *
 * It uses the singleton design pattern to ensure that there is only one instance of the factory.
 *
 * The create method takes a label, an opcode, and an ArrayList of arguments, and returns an Instruction object
 * based on the opcode and arguments.
 *
 * The class throws a RuntimeException if an invalid opcode or an incorrect number or type of arguments is provided.
 *
 * The class throws a RuntimeException if an invalid register value or integer value is provided.
 *
 * The class uses the Registers enum to ensure that only valid register values are accepted.
 *
 * @author lhickley
 */

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

    /**
     * Creates an Instruction object based on the given opcode and arguments.
     * @param label the label associated with the instruction
     * @param opcode the opcode for the instruction
     * @param args an ArrayList of Strings representing the arguments for the instruction
     * @return an Instruction object based on the opcode and arguments
     * @throws RuntimeException if the opcode is invalid or the number or type of arguments is incorrect
     * @throws RuntimeException if an invalid register value or integer value is provided
     * @return
     */
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

