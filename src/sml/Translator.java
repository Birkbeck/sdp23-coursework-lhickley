package sml;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * This class does the work of reading from a file and translating the contents of that file to a program for
 * a machine to execute.
 *
 * Sets the contents of 'labels' and 'program' for a given machine instance, from an input file.
 *
 * Contains flows to ensure the program does not become corrupted, or interacts with those handled at a lower level
 * to allow for a graceful termination of a corrupted program from an input file.
 *
 * Consists of methods for reading from a file and converting to an instruction set, and helper methods for doing so.
 *
 * <p>
 * The translator of a <b>S</b><b>M</b>al<b>L</b> program.
 *
 * @author lhickley
 */
public final class Translator {

    private final String fileName; // source file of SML code

    // line contains the characters in the current line that's not been processed yet
    private String line = "";

    private final InstructionFactory instructionFactory;

    public Translator(String fileName, InstructionFactory instructionFactory) {
        this.fileName = fileName;
        this.instructionFactory = instructionFactory;
    }

    // translate the small program in the file into lab (the labels) and
    // prog (the program)
    // return "no errors were detected"

    public void readAndTranslate(Labels labels, List<Instruction> program) throws IOException {
        try (var sc = new Scanner(new File(fileName), StandardCharsets.UTF_8)) {
            labels.reset();
            program.clear();

            // Each iteration processes line and reads the next input line into "line"
            while (sc.hasNextLine()) {
                line = sc.nextLine();
                String label = getLabel();

                try {
                    Instruction instruction = getInstruction(label);
                    if (instruction != null) {
                        if (label != null)
                            try {
                                labels.addLabel(label, program.size());
                            } catch (RuntimeException e) {
                                System.out.println("Duplicate labels are not permitted.");
                                System.out.println("The label '" + label + "' has been supplied earlier in the program source file.");
                            }
                        program.add(instruction);
                    }
                } catch (Exception e) {
                    System.out.println("An exception occurred while reading the program for the file.  Details:\n" + e.getMessage());
                    System.out.println("The program may become corrupted and the input file should be reviewed");
                    break;
                }
            }
        }
    }

    /**
     * Translates the current line into an instruction with the given label
     *
     * @param label the instruction label
     * @return the new instruction
     * <p>
     * The input line should consist of a single SML instruction,
     * with its label already removed.
     */
    private Instruction getInstruction(String label) {
        if (line.isEmpty())
            return null;

        String opcode = scan();

        ArrayList<String> wordList = new ArrayList<>();

        while (line.length() > 0) {
            String word = scan();
            wordList.add(word);
        }

        return instructionFactory.create(label, opcode, wordList);
    }


    private String getLabel() {
        String word = scan();
        if (word.endsWith(":"))
            return word.substring(0, word.length() - 1);

        // undo scanning the word
        line = word + " " + line;
        return null;
    }

    /*
     * Return the first word of line and remove it from line.
     * If there is no word, return "".
     */
    private String scan() {
        line = line.trim();
        ArrayList<String> splitLine = new ArrayList<>(Arrays.asList(line.split(" ")));
        if (splitLine.size() > 0) {
            String word = splitLine.remove(0);
            line = String.join(" ", splitLine);
            return word;
        }
        return line;
    }
}