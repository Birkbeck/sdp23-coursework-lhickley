package sml;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * This class ....
 * <p>
 * The translator of a <b>S</b><b>M</b>al<b>L</b> program.
 *
 * @author ...
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

        while (line.length() > 0){
            String word = scan();
            wordList.add(word);
        }

        try {
            return instructionFactory.create(label, opcode, wordList);
        } catch (IllegalArgumentException e) {
            System.out.println("Parameters supplied for " + opcode + " were incorrect");
        } catch (RuntimeException e) {
            //Exceptions here are thrown from our class below, so we want to just report those messages pleasantly.
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("Unknown instruction: " + opcode);
        }

        return null;
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