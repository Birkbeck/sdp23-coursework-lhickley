package sml;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class InstructionFactoryTest {
    private InstructionFactory instructionFactory;
    private final ArrayList<String> args = new ArrayList<>();
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @BeforeEach
    void setUp() {
        instructionFactory = InstructionFactory.getInstance();
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @AfterEach
    void tearDown() {
        instructionFactory = null;
    }

    @Test
    void incorrectNumberOfArgumentsOutNone() {
        Exception exception = assertThrows(RuntimeException.class, () -> instructionFactory.create(null, "out", args));

        String expectedMessage = "For out instructions, only one argument is accepted after the opcode";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void incorrectNumberOfArgumentsOutOverOne() {
        args.add("EAX");
        args.add("EBX");
        Exception exception = assertThrows(RuntimeException.class, () -> instructionFactory.create(null, "out", args));

        String expectedMessage = "For out instructions, only one argument is accepted after the opcode";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void incorrectNumberOfArgumentsAllOtherNone() {
        Exception exception = assertThrows(RuntimeException.class, () -> instructionFactory.create(null, "add", args));

        String expectedMessage = "For add instructions, only two arguments are accepted after the opcode";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void incorrectNumberOfArgumentsAllOtherOne() {
        args.add("EAX");
        Exception exception = assertThrows(RuntimeException.class, () -> instructionFactory.create(null, "add", args));

        String expectedMessage = "For add instructions, only two arguments are accepted after the opcode";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void incorrectNumberOfArgumentsAllOtherOverTwo() {
        args.add("EAX");
        args.add("EBX");
        args.add("ECX");
        Exception exception = assertThrows(RuntimeException.class, () -> instructionFactory.create(null, "add", args));

        String expectedMessage = "For add instructions, only two arguments are accepted after the opcode";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void illegalRegisterPassedToConstructor() {
        args.add("EAX");
        args.add("ZZZ");
        instructionFactory.create(null, "add", args);
        Assertions.assertEquals("Illegal arguments passed for 'add'\n" +
                "Permitted register values are: \n" +
                "[EAX, EBX, ECX, EDX, ESP, EBP, ESI, EDI]", outputStreamCaptor.toString().trim());
    }

    @Test
    void unknownOpcode() {
        args.add("EAX");
        args.add("EBX");
        instructionFactory.create(null, "test", args);
        Assertions.assertEquals("Illegal arguments passed for 'test'\n" +
        "Unknown instruction 'test'", outputStreamCaptor.toString().trim());
    }

    @Test
    void nonIntegerPassedToMov() {
        args.add("EAX");
        args.add("test");
        instructionFactory.create(null, "mov", args);
        Assertions.assertEquals("Illegal arguments passed for 'mov'\n" +
        "Non integer value passed where integer value expected", outputStreamCaptor.toString().trim());
    }


}
