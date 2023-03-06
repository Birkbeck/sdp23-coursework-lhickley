package sml;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;

import static sml.Registers.Register.*;

public class MachineTest {
    private Machine machine;
    private Registers registers;
    private String baseTestFilePath;

    @BeforeEach
    void setUp() {
        machine = new Machine(new Registers());
        registers = machine.getRegisters();
        baseTestFilePath = "./sdp23-coursework-lhickley/test/resources/";
    }

    @AfterEach
    void tearDown() {
        machine = null;
        registers = null;
    }

    @Test
    void executeMachineCorrectlyNoLabels() throws IOException {
        String fileLocation = new File(baseTestFilePath + "readAndTranslateMixedInstructionNoLabelsTestResource.txt").getAbsolutePath();
        Translator translator = new Translator(fileLocation, InstructionFactory.getInstance());
        translator.readAndTranslate(machine.getLabels(), machine.getProgram());
        machine.execute();
        Assertions.assertEquals(3, registers.get(EAX));
        Assertions.assertEquals(3, registers.get(EBX));
        Assertions.assertEquals(1, registers.get(ECX));
    }

    @Test
    void executeMachineCorrectlyMixedLabels() throws IOException {
        String fileLocation = new File(baseTestFilePath + "readAndTranslateMixedInstructionMixedLabelsTestResource.txt").getAbsolutePath();
        Translator translator = new Translator(fileLocation, InstructionFactory.getInstance());
        translator.readAndTranslate(machine.getLabels(), machine.getProgram());
        machine.execute();
        Assertions.assertEquals(3, registers.get(EAX));
        Assertions.assertEquals(3, registers.get(EBX));
        Assertions.assertEquals(1, registers.get(ECX));
    }

    @Test
    void executeMachineCorrectlyAllLabels() throws IOException {
        String fileLocation = new File(baseTestFilePath + "readAndTranslateMixedInstructionMixedLabelsTestResource.txt").getAbsolutePath();
        Translator translator = new Translator(fileLocation, InstructionFactory.getInstance());
        translator.readAndTranslate(machine.getLabels(), machine.getProgram());
        machine.execute();
        Assertions.assertEquals(3, registers.get(EAX));
        Assertions.assertEquals(3, registers.get(EBX));
        Assertions.assertEquals(1, registers.get(ECX));
    }

    @Test
    void executeMachineTestMixOfInstructions() throws IOException {
        String fileLocation = new File(baseTestFilePath + "MixedInstructions.sml").getAbsolutePath();
        Translator translator = new Translator(fileLocation, InstructionFactory.getInstance());
        translator.readAndTranslate(machine.getLabels(), machine.getProgram());
        ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStreamCaptor));
        machine.execute();
        Assertions.assertEquals("720\n2", outputStreamCaptor.toString().trim());
    }

    @Test
    void executeMachineBrokenInputFirstPosition() throws IOException {
        String fileLocation = new File(baseTestFilePath + "PoorlyFormedInputFirstPosition.sml").getAbsolutePath();
        Translator translator = new Translator(fileLocation, InstructionFactory.getInstance());
        ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStreamCaptor));
        translator.readAndTranslate(machine.getLabels(), machine.getProgram());
        machine.execute();
        String expectedMessage = """
                An exception occurred while reading the program for the file.  Details:
                Illegal arguments passed for 'mov'
                Permitted register values are:\s
                [EAX, EBX, ECX, EDX, ESP, EBP, ESI, EDI]
                The program may become corrupted and the input file should be reviewed""";
        Assertions.assertEquals(expectedMessage, outputStreamCaptor.toString().trim());
    }

    @Test
    void executeMachineBrokenInputSecondPosition() throws IOException {
        String fileLocation = new File(baseTestFilePath + "PoorlyFormedInputSecondPosition.sml").getAbsolutePath();
        Translator translator = new Translator(fileLocation, InstructionFactory.getInstance());
        ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStreamCaptor));
        translator.readAndTranslate(machine.getLabels(), machine.getProgram());
        machine.execute();
        String expectedMessage = """
                An exception occurred while reading the program for the file.  Details:
                Illegal arguments passed for 'mov'
                Permitted register values are:\s
                [EAX, EBX, ECX, EDX, ESP, EBP, ESI, EDI]
                The program may become corrupted and the input file should be reviewed""";
        Assertions.assertEquals(expectedMessage, outputStreamCaptor.toString().trim());
    }

    @Test
    void hashCodeSymmetry() throws IOException {
        Machine m1 = new Machine(new Registers());
        Machine m2 = new Machine(new Registers());
        String fileLocation = new File(baseTestFilePath + "MixedInstructions.sml").getAbsolutePath();
        Translator translator = new Translator(fileLocation, InstructionFactory.getInstance());
        translator.readAndTranslate(m1.getLabels(), m1.getProgram());
        translator.readAndTranslate(m2.getLabels(), m2.getProgram());
        Assertions.assertEquals(m1.hashCode(), m2.hashCode());
    }

    @Test
    void equalsSymmetry() throws IOException {
        Machine m1 = new Machine(new Registers());
        Machine m2 = new Machine(new Registers());
        String fileLocation = new File(baseTestFilePath + "MixedInstructions.sml").getAbsolutePath();
        Translator translator = new Translator(fileLocation, InstructionFactory.getInstance());
        translator.readAndTranslate(m1.getLabels(), m1.getProgram());
        translator.readAndTranslate(m2.getLabels(), m2.getProgram());
        Assertions.assertTrue(m1.equals(m2));
    }

    @Test
    void hashCodeAsymmetry() throws IOException {
        Machine m1 = new Machine(new Registers());
        Machine m2 = new Machine(new Registers());
        String fileLocation1 = new File(baseTestFilePath + "MixedInstructions.sml").getAbsolutePath();
        Translator translator1 = new Translator(fileLocation1, InstructionFactory.getInstance());
        String fileLocation2 = new File(baseTestFilePath + "readAndTranslateMixedInstructionMixedLabelsTestResource.txt").getAbsolutePath();
        Translator translator2 = new Translator(fileLocation2, InstructionFactory.getInstance());
        translator1.readAndTranslate(m1.getLabels(), m1.getProgram());
        translator2.readAndTranslate(m2.getLabels(), m2.getProgram());
        Assertions.assertNotEquals(m1.hashCode(), m2.hashCode());
    }

    @Test
    void equalsAsymmetry() throws IOException {
        Machine m1 = new Machine(new Registers());
        Machine m2 = new Machine(new Registers());
        String fileLocation1 = new File(baseTestFilePath + "MixedInstructions.sml").getAbsolutePath();
        Translator translator1 = new Translator(fileLocation1, InstructionFactory.getInstance());
        String fileLocation2 = new File(baseTestFilePath + "readAndTranslateMixedInstructionMixedLabelsTestResource.txt").getAbsolutePath();
        Translator translator2 = new Translator(fileLocation2, InstructionFactory.getInstance());
        translator1.readAndTranslate(m1.getLabels(), m1.getProgram());
        translator2.readAndTranslate(m2.getLabels(), m2.getProgram());
        Assertions.assertFalse(m1.equals(m2));
    }

}
