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



}
