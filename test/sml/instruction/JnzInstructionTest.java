package sml.instruction;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sml.Instruction;
import sml.Labels;
import sml.Machine;
import sml.Registers;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static sml.Registers.Register.EAX;

public class JnzInstructionTest {
    private Machine machine;
    private Registers registers;

    @BeforeEach
    void setUp() {
        machine = new Machine(new Registers());
        registers = machine.getRegisters();
    }

    @AfterEach
    void tearDown() {
        machine = null;
        registers = null;
    }

    @Test
    void executeValidZeroRegister() {
        registers.set(EAX, 0);
        Instruction instruction = new JnzInstruction(null, EAX, "test");
        int programCounterInstruction = instruction.execute(machine);
        Assertions.assertEquals(-1, programCounterInstruction);
    }

    @Test
    void executeValidNonZeroRegister() {
        registers.set(EAX, 5);
        Labels labels = machine.getLabels();
        labels.addLabel("test", 2);
        Instruction instruction = new JnzInstruction(null, EAX, "test");
        int programCounterInstruction = instruction.execute(machine);
        Assertions.assertEquals(2, programCounterInstruction);
    }

    @Test
    void executeToStringNoLabel() {
        Instruction instruction = new JnzInstruction(null, EAX, "jumpToTest");
        String toStringResult = instruction.toString();
        Assertions.assertEquals("jnz EAX jumpToTest", toStringResult);
    }

    @Test
    void executeToStringWithLabel() {
        Instruction instruction = new JnzInstruction("test", EAX, "jumpToTest");
        String toStringResult = instruction.toString();
        Assertions.assertEquals("test: jnz EAX jumpToTest", toStringResult);
    }

    @Test
    void executeJumpToNonExtantRegister() {
        registers.set(EAX, 5);
        Instruction instruction = new JnzInstruction(null, EAX, "test");
        Exception exception = assertThrows(RuntimeException.class, () -> instruction.execute(machine));

        String expectedMessage = "The label test does not exist!  Please check the file containing the instruction set.";
        String actualMessage = exception.getMessage();

        Assertions.assertEquals(expectedMessage, actualMessage);
    }
}
