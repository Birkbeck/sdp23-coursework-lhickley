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
import static sml.Registers.Register.*;
import static sml.Registers.Register.EBX;

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
    void executeJumpToNonExtantLabel() {
        registers.set(EAX, 5);
        Instruction instruction = new JnzInstruction(null, EAX, "test");
        Exception exception = assertThrows(RuntimeException.class, () -> instruction.execute(machine));

        String expectedMessage = "The label test does not exist!  Please check the file containing the instruction set.";
        String actualMessage = exception.getMessage();

        Assertions.assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void hashCodeNoLabelsSymmetryTest() {
        Instruction instruction1 = new JnzInstruction(null, EAX, "test");
        Instruction instruction2 = new JnzInstruction(null, EAX, "test");
        Assertions.assertEquals(instruction1.hashCode(), instruction2.hashCode());
    }

    @Test
    void hashCodeWithIdenticalLabelsSymmetryTest() {
        Instruction instruction1 = new JnzInstruction("test", EAX, "test");
        Instruction instruction2 = new JnzInstruction("test", EAX, "test");
        Assertions.assertEquals(instruction1.hashCode(), instruction2.hashCode());
    }

    @Test
    void hashCodeWithOneLabelsAsymmetryTest() {
        Instruction instruction1 = new JnzInstruction("test", EAX, "test");
        Instruction instruction2 = new JnzInstruction(null, EAX, "test");
        Assertions.assertNotEquals(instruction1.hashCode(), instruction2.hashCode());
    }

    @Test
    void hashCodeWithBothLabelsAsymmetryTest() {
        Instruction instruction1 = new JnzInstruction("test", EAX, "test");
        Instruction instruction2 = new JnzInstruction("dif", EAX, "test");
        Assertions.assertNotEquals(instruction1.hashCode(), instruction2.hashCode());
    }

    @Test
    void hashCodeWithDifferingLabelJumpAsymmetryTest() {
        Instruction instruction1 = new JnzInstruction(null, EAX, "test");
        Instruction instruction2 = new JnzInstruction(null, EAX, "dif");
        Assertions.assertNotEquals(instruction1.hashCode(), instruction2.hashCode());
    }

    @Test
    void hashCodeOtherClassAsymmetryTest() {
        Instruction instruction1 = new JnzInstruction(null, EAX, "test");
        Instruction instruction2 = new AddInstruction(null, EAX, EBX);
        Assertions.assertNotEquals(instruction1.hashCode(), instruction2.hashCode());
    }

    @Test
    void equalsNoLabelsSymmetryTest() {
        Instruction instruction1 = new JnzInstruction(null, EAX, "test");
        Instruction instruction2 = new JnzInstruction(null, EAX, "test");
        Assertions.assertTrue(instruction1.equals(instruction2));
    }

    @Test
    void equalsWithIdenticalLabelsSymmetryTest() {
        Instruction instruction1 = new JnzInstruction("test", EAX, "test");
        Instruction instruction2 = new JnzInstruction("test", EAX, "test");
        Assertions.assertTrue(instruction1.equals(instruction2));
    }

    @Test
    void equalsWithOneLabelsAsymmetryTest() {
        Instruction instruction1 = new JnzInstruction("test", EAX, "test");
        Instruction instruction2 = new JnzInstruction(null, EAX, "test");
        Assertions.assertFalse(instruction1.equals(instruction2));
    }

    @Test
    void equalsWithBothLabelsAsymmetryTest() {
        Instruction instruction1 = new JnzInstruction("test", EAX, "test");
        Instruction instruction2 = new JnzInstruction("dif", EAX, "test");
        Assertions.assertFalse(instruction1.equals(instruction2));
    }
}
