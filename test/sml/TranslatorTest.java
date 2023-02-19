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

    @Test
    void readAndTranslateMulInstructionNoLabel() throws Exception {
        String fileLocation = new File(baseTestFilePath + "readAndTranslateMulInstructionNoLabelTestResource.txt").getAbsolutePath();
        Translator translator = new Translator(fileLocation);
        translator.readAndTranslate(machine.getLabels(), machine.getProgram());
        Assertions.assertEquals("[mul EAX EBX]", machine.getProgram().toString());
    }

    @Test
    void readAndTranslateMulInstructionWithLabel() throws Exception {
        String fileLocation = new File(baseTestFilePath + "readAndTranslateMulInstructionWithLabelTestResource.txt").getAbsolutePath();
        Translator translator = new Translator(fileLocation);
        translator.readAndTranslate(machine.getLabels(), machine.getProgram());
        Assertions.assertEquals("[test: mul EAX EBX]", machine.getProgram().toString());
    }

    @Test
    void readAndTranslateDivInstructionNoLabel() throws Exception {
        String fileLocation = new File(baseTestFilePath + "readAndTranslateDivInstructionNoLabelTestResource.txt").getAbsolutePath();
        Translator translator = new Translator(fileLocation);
        translator.readAndTranslate(machine.getLabels(), machine.getProgram());
        Assertions.assertEquals("[div EAX EBX]", machine.getProgram().toString());
    }

    @Test
    void readAndTranslateDivInstructionWithLabel() throws Exception {
        String fileLocation = new File(baseTestFilePath + "readAndTranslateDivInstructionWithLabelTestResource.txt").getAbsolutePath();
        Translator translator = new Translator(fileLocation);
        translator.readAndTranslate(machine.getLabels(), machine.getProgram());
        Assertions.assertEquals("[test: div EAX EBX]", machine.getProgram().toString());
    }

    @Test
    void readAndTranslateOutInstructionNoLabel() throws Exception {
        String fileLocation = new File(baseTestFilePath + "readAndTranslateOutInstructionNoLabelTestResource.txt").getAbsolutePath();
        Translator translator = new Translator(fileLocation);
        translator.readAndTranslate(machine.getLabels(), machine.getProgram());
        Assertions.assertEquals("[out EAX]", machine.getProgram().toString());
    }

    @Test
    void readAndTranslateOutInstructionWithLabel() throws Exception {
        String fileLocation = new File(baseTestFilePath + "readAndTranslateOutInstructionWithLabelTestResource.txt").getAbsolutePath();
        Translator translator = new Translator(fileLocation);
        translator.readAndTranslate(machine.getLabels(), machine.getProgram());
        Assertions.assertEquals("[test: out EAX]", machine.getProgram().toString());
    }

    @Test
    void readAndTranslateMovInstructionNoLabel() throws Exception {
        String fileLocation = new File(baseTestFilePath + "readAndTranslateMovInstructionNoLabelTestResource.txt").getAbsolutePath();
        Translator translator = new Translator(fileLocation);
        translator.readAndTranslate(machine.getLabels(), machine.getProgram());
        Assertions.assertEquals("[mov EAX 1]", machine.getProgram().toString());
    }

    @Test
    void readAndTranslateMovInstructionWithLabel() throws Exception {
        String fileLocation = new File(baseTestFilePath + "readAndTranslateMovInstructionWithLabelTestResource.txt").getAbsolutePath();
        Translator translator = new Translator(fileLocation);
        translator.readAndTranslate(machine.getLabels(), machine.getProgram());
        Assertions.assertEquals("[test: mov EAX 1]", machine.getProgram().toString());
    }

    @Test
    void readAndTranslateJnzInstructionNoLabel() throws Exception {
        String fileLocation = new File(baseTestFilePath + "readAndTranslateJnzInstructionNoLabelTestResource.txt").getAbsolutePath();
        Translator translator = new Translator(fileLocation);
        translator.readAndTranslate(machine.getLabels(), machine.getProgram());
        Assertions.assertEquals("[jnz EAX testLabel]", machine.getProgram().toString());
    }

    @Test
    void readAndTranslateJnzInstructionWithLabel() throws Exception {
        String fileLocation = new File(baseTestFilePath + "readAndTranslateJnzInstructionWithLabelTestResource.txt").getAbsolutePath();
        Translator translator = new Translator(fileLocation);
        translator.readAndTranslate(machine.getLabels(), machine.getProgram());
        Assertions.assertEquals("[test: jnz EAX testLabel]", machine.getProgram().toString());
    }

    @Test
    void readAndTranslateMixedInstructionNoLabels() throws Exception {
        String fileLocation = new File(baseTestFilePath + "readAndTranslateMixedInstructionNoLabelsTestResource.txt").getAbsolutePath();
        Translator translator = new Translator(fileLocation);
        translator.readAndTranslate(machine.getLabels(), machine.getProgram());
        Assertions.assertEquals("[mov EAX 1, mov EBX 3, add EAX EBX, mov ECX 1, sub EAX ECX]", machine.getProgram().toString());
    }

    @Test
    void readAndTranslateMixedInstructionAllLabels() throws Exception {
        String fileLocation = new File(baseTestFilePath + "readAndTranslateMixedInstructionAllLabelsTestResource.txt").getAbsolutePath();
        Translator translator = new Translator(fileLocation);
        translator.readAndTranslate(machine.getLabels(), machine.getProgram());
        Assertions.assertEquals("[test1: mov EAX 1, test2: mov EBX 3, test3: add EAX EBX, test4: mov ECX 1, test5: sub EAX ECX]", machine.getProgram().toString());
    }

    @Test
    void readAndTranslateMixedInstructionMixedLabels() throws Exception {
        String fileLocation = new File(baseTestFilePath + "readAndTranslateMixedInstructionMixedLabelsTestResource.txt").getAbsolutePath();
        Translator translator = new Translator(fileLocation);
        translator.readAndTranslate(machine.getLabels(), machine.getProgram());
        Assertions.assertEquals("[test1: mov EAX 1, mov EBX 3, add EAX EBX, test4: mov ECX 1, test5: sub EAX ECX]", machine.getProgram().toString());
    }

}
