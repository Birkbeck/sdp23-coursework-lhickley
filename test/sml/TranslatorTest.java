package sml;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;

public class TranslatorTest {
    private Machine machine;
    private Registers registers;
    private String baseTestFilePath;

    @BeforeEach
    void setUp() {
        machine = new Machine(new Registers());
        registers = machine.getRegisters();
        baseTestFilePath = "./sdp23-coursework-lhickley/test/resources/";
        //...
    }

    @AfterEach
    void tearDown() {
        machine = null;
        registers = null;
    }

    @Test
    void readAndTranslateAddInstructionNoLabel() throws Exception {
        String fileLocation = new File(baseTestFilePath + "readAndTranslateAddInstructionNoLabelTestResource.txt").getAbsolutePath();
        Translator translator = new Translator(fileLocation);
        translator.readAndTranslate(machine.getLabels(), machine.getProgram());
        Assertions.assertEquals("[add EAX EBX]", machine.getProgram().toString());
     }

    @Test
    void readAndTranslateAddInstructionWithLabel() throws Exception {
        String fileLocation = new File(baseTestFilePath + "readAndTranslateAddInstructionWithLabelTestResource.txt").getAbsolutePath();
        Translator translator = new Translator(fileLocation);
        translator.readAndTranslate(machine.getLabels(), machine.getProgram());
        Assertions.assertEquals("[test: add EAX EBX]", machine.getProgram().toString());
    }

     @Test
    void readAndTranslateSubInstructionNoLabel() throws Exception {
         String fileLocation = new File(baseTestFilePath + "readAndTranslateSubInstructionNoLabelTestResource.txt").getAbsolutePath();
         Translator translator = new Translator(fileLocation);
         translator.readAndTranslate(machine.getLabels(), machine.getProgram());
         Assertions.assertEquals("[sub EAX EBX]", machine.getProgram().toString());
     }

    @Test
    void readAndTranslateSubInstructionWithLabel() throws Exception {
        String fileLocation = new File(baseTestFilePath + "readAndTranslateSubInstructionWithLabelTestResource.txt").getAbsolutePath();
        Translator translator = new Translator(fileLocation);
        translator.readAndTranslate(machine.getLabels(), machine.getProgram());
        Assertions.assertEquals("[test: sub EAX EBX]", machine.getProgram().toString());
    }

}
