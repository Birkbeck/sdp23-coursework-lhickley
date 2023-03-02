package sml;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

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

    


}
