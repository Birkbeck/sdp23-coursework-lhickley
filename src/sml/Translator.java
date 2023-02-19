package sml;

import sml.instruction.*;

import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static sml.Registers.Register;

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

    public Translator(String fileName) {
        this.fileName =  fileName;
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
                        labels.addLabel(label, program.size());
                    program.add(instruction);
                }
            }
        }
    }

    private Class getClass(String className, String packageName) {
        try {
            return Class.forName(packageName + "."
                    + className.substring(0, className.lastIndexOf('.')));
        } catch (ClassNotFoundException e) {
            // handle the exception
        }
        return null;
    }

    public Set<Class> findAllClassesUsingClassLoader(String packageName) throws ClassNotFoundException {
        //Class clazz = Class.forName("sml.Instruction");
        //ClassLoader classLoader = clazz.getClassLoader();
        InputStream stream = ClassLoader.getSystemClassLoader()
                .getResourceAsStream(packageName.replaceAll("[.]", "/"));
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        return reader.lines()
                .filter(line -> line.endsWith(".class"))
                .map(line -> getClass(line, packageName))
                .collect(Collectors.toSet());
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

        //System.out.println(line);

        String opcode = scan();
        try {

            //System.out.println(opcode);

            Set<Class> allClasses = findAllClassesUsingClassLoader("sml.instruction");

            //System.out.println(allClasses);

            for (Class clazz : allClasses) {
                //System.out.println(clazz);
                //System.out.println(clazz.getClass());
                //System.out.println(clazz.getName());
                Class instruction = Class.forName(clazz.getName());
                System.out.println(clazz.getName());
                //System.out.println(instruction.getClass());
                Method[] methods = instruction.getMethods();
                //System.out.println(instruction.getName());
                //System.out.println(methods);
                for (Method method : methods) {
                    //System.out.println(method);
                }
                /*Method opCodeMethod = clazz.getMethod("getOpcode", null);
                System.out.println(opCodeMethod);
                Method[] methods = clazz.getMethods();
                for (Method method : methods) {
                    System.out.println(method);
                }

                 */
            }

            System.out.println("*****");

            Class testIns = Class.forName("sml.instruction.AddInstruction");

            Method[] methods = testIns.getMethods();

            /*System.out.println(testIns.getName());

            for (Method method : methods) {
                System.out.println(method);
            }

             */

            System.out.println("*****");

            /*Instruction instruction = allClasses.stream()
                            .filter(c -> c.getOpcode() == opcode)

             */
            URL classStream = Class.class.getResource("sml/instruction");

            System.out.println(classStream);

            //classStream.



            //System.out.println(allClasses);

            //() -> sml.instruction

            /*for (Instruction instruction : sml.instruction.*) {

            }*/

            //Class<? extends Instruction> clazz = Class.class.getDeclaredField(opcode);

            //Field field = Class.class.getDeclaredField();
            //System.out.println(field);

        } catch (Exception e) {

        }
        /*try {
            Class<Instruction> instructionClass = (Class<Instruction>) Class.forName("sml.Instruction");
            Class<? extends Instruction> instructionWeWant = instructionClass.OP_CODE;
        } catch (Exception e) {

        }

         */

        return null;
        /*
        switch (opcode) {
            case AddInstruction.OP_CODE -> {
                String r = scan();
                String s = scan();
                return new AddInstruction(label, Register.valueOf(r), Register.valueOf(s));
            }
            case SubInstruction.OP_CODE -> {
                String r = scan();
                String s = scan();
                return new SubInstruction(label, Register.valueOf(r), Register.valueOf(s));
            }
            case MulInstruction.OP_CODE -> {
                String r = scan();
                String s = scan();
                return new MulInstruction(label, Register.valueOf(r), Register.valueOf(s));
            }
            case DivInstruction.OP_CODE -> {
                String r = scan();
                String s = scan();
                return new DivInstruction(label, Register.valueOf(r), Register.valueOf(s));
            }
            case OutInstruction.OP_CODE -> {
                String s = scan();
                return new OutInstruction(label, Register.valueOf(s));
            }
            case MovInstruction.OP_CODE -> {
                String r = scan();
                int v = Integer.parseInt(scan());
                return new MovInstruction(label, Register.valueOf(r), v);
            }
            case JnzInstruction.OP_CODE -> {
                String r = scan();
                String l = scan();
                return new JnzInstruction(label, Register.valueOf(r),l);
            }

            // TODO: Then, replace the switch by using the Reflection API

            // TODO: Next, use dependency injection to allow this machine class
            //       to work with different sets of opcodes (different CPUs)

            default -> {
                System.out.println("Unknown instruction: " + opcode);
            }
        }
        return null;

         */
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

        for (int i = 0; i < line.length(); i++)
            if (Character.isWhitespace(line.charAt(i))) {
                String word = line.substring(0, i);
                line = line.substring(i);
                return word;
            }

        return line;
    }
}