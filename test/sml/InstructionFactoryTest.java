package sml;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class InstructionFactoryTest {
    private InstructionFactory instructionFactory;
    private ArrayList<String> args = new ArrayList<>();

    @BeforeEach
    void setUp() {
        instructionFactory = InstructionFactory.getInstance();
    }

    @AfterEach
    void tearDown() {
        instructionFactory = null;
    }

    @Test
    void incorrectNumberOfArgumentsOut() {
        Exception exception = assertThrows(RuntimeException.class, () -> instructionFactory.create(null, "out", args));

        String expectedMessage = "For out instructions, only one argument is accepted after the opcode";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void incorrectNumberOfArgumentsAllOther() {
        Exception exception = assertThrows(RuntimeException.class, () -> instructionFactory.create(null, "add", args));

        String expectedMessage = "For add instructions, only two arguments are accepted after the opcode";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

}
