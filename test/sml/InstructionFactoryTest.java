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
        Exception exception = assertThrows(RuntimeException.class, () -> instructionFactory.create(null, "add", args));
        String expectedMessage = """
                Illegal arguments passed for 'add'
                Permitted register values are:\s
                [EAX, EBX, ECX, EDX, ESP, EBP, ESI, EDI]""";
        Assertions.assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void unknownOpcode() {
        args.add("EAX");
        args.add("EBX");
        Exception exception = assertThrows(RuntimeException.class, () -> instructionFactory.create(null, "test", args));
        String expectedMessage = "Illegal arguments passed for 'test'\nUnknown instruction 'test'";
        Assertions.assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void nonIntegerPassedToMov() {
        args.add("EAX");
        args.add("test");
        Exception exception = assertThrows(RuntimeException.class, () -> instructionFactory.create(null, "mov", args));
        String expectedMessage = """
                Illegal arguments passed for 'mov'
                Value passed was not an acceptable integer value.
                Value stored in registers must be between -2,147,483,648 and 2,147,483,647 inclusive.""";
        Assertions.assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void correctMovCreation() {
        args.add("EAX");
        args.add("1");
        Instruction instruction = instructionFactory.create(null, "mov", args);
        Assertions.assertEquals("class sml.instruction.MovInstruction", instruction.getClass().toString());
    }

    @Test
    void overflowIntegerPassToMov() {
        args.add("EAX");
        args.add("2147483648");
        Exception exception = assertThrows(RuntimeException.class, () -> instructionFactory.create(null, "mov", args));
        String expectedMessage = """
                Illegal arguments passed for 'mov'
                Value passed was not an acceptable integer value.
                Value stored in registers must be between -2,147,483,648 and 2,147,483,647 inclusive.""";
        Assertions.assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void underflowIntegerPassToMov() {
        args.add("EAX");
        args.add("-2147483649");
        Exception exception = assertThrows(RuntimeException.class, () -> instructionFactory.create(null, "mov", args));
        String expectedMessage = """
                Illegal arguments passed for 'mov'
                Value passed was not an acceptable integer value.
                Value stored in registers must be between -2,147,483,648 and 2,147,483,647 inclusive.""";
        Assertions.assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void correctAddCreation() {
        args.add("EAX");
        args.add("EBX");
        Instruction instruction = instructionFactory.create(null, "add", args);
        Assertions.assertEquals("class sml.instruction.AddInstruction", instruction.getClass().toString());
    }

    @Test
    void correctSubCreation() {
        args.add("EAX");
        args.add("EBX");
        Instruction instruction = instructionFactory.create(null, "sub", args);
        Assertions.assertEquals("class sml.instruction.SubInstruction", instruction.getClass().toString());
    }

    @Test
    void correctMulCreation() {
        args.add("EAX");
        args.add("EBX");
        Instruction instruction = instructionFactory.create(null, "mul", args);
        Assertions.assertEquals("class sml.instruction.MulInstruction", instruction.getClass().toString());
    }

    @Test
    void correctDivCreation() {
        args.add("EAX");
        args.add("EBX");
        Instruction instruction = instructionFactory.create(null, "div", args);
        Assertions.assertEquals("class sml.instruction.DivInstruction", instruction.getClass().toString());
    }

    @Test
    void correctOutCreation() {
        args.add("EAX");
        Instruction instruction = instructionFactory.create(null, "out", args);
        Assertions.assertEquals("class sml.instruction.OutInstruction", instruction.getClass().toString());
    }

    @Test
    void correctJnzCreation() {
        args.add("EAX");
        args.add("test");
        Instruction instruction = instructionFactory.create(null, "jnz", args);
        Assertions.assertEquals("class sml.instruction.JnzInstruction", instruction.getClass().toString());
    }


}
