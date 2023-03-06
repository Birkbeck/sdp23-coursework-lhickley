package sml.instruction;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sml.Instruction;
import sml.Machine;
import sml.Registers;

import static sml.Registers.Register.EAX;
import static sml.Registers.Register.EBX;

public class MovInstructionTest {
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
    void executeAddToBlankRegister() {
        Instruction instruction = new MovInstruction(null, EAX, 1);
        instruction.execute(machine);
        Registers registers = machine.getRegisters();
        int registerValue = registers.get(EAX);
        Assertions.assertEquals(1, registerValue);
    }

    @Test
    void executeAddToFullRegister() {
        Registers registers = machine.getRegisters();
        registers.set(EAX, 1);
        Instruction instruction  = new MovInstruction(null, EAX, 2);
        instruction.execute(machine);
        int registerValue = registers.get(EAX);
        Assertions.assertEquals(2, registerValue);
    }

    @Test
    void executeToStringNoLabel() {
        Instruction instruction = new MovInstruction(null, EAX, 6);
        String toStringResult = instruction.toString();
        Assertions.assertEquals("mov EAX 6", toStringResult);
    }

    @Test
    void executeToStringWithLabel() {
        Instruction instruction = new MovInstruction("test", EAX, 5);
        String toStringResult = instruction.toString();
        Assertions.assertEquals("test: mov EAX 5", toStringResult);
    }

    @Test
    void hashCodeNoLabelsSymmetryTest() {
        Instruction instruction1 = new MovInstruction(null, EAX, 1);
        Instruction instruction2 = new MovInstruction(null, EAX, 1);
        Assertions.assertEquals(instruction1.hashCode(), instruction2.hashCode());
    }

    @Test
    void hashCodeWithIdenticalLabelsSymmetryTest() {
        Instruction instruction1 = new MovInstruction("test", EAX, 1);
        Instruction instruction2 = new MovInstruction("test", EAX, 1);
        Assertions.assertEquals(instruction1.hashCode(), instruction2.hashCode());
    }

    @Test
    void hashCodeWithOneLabelsAsymmetryTest() {
        Instruction instruction1 = new MovInstruction("test", EAX, 1);
        Instruction instruction2 = new MovInstruction(null, EAX, 1);
        Assertions.assertNotEquals(instruction1.hashCode(), instruction2.hashCode());
    }

    @Test
    void hashCodeWithBothLabelsAsymmetryTest() {
        Instruction instruction1 = new MovInstruction("test", EAX, 1);
        Instruction instruction2 = new MovInstruction("dif", EAX, 1);
        Assertions.assertNotEquals(instruction1.hashCode(), instruction2.hashCode());
    }

    @Test
    void hashCodeWithDifferingLabelJumpAsymmetryTest() {
        Instruction instruction1 = new MovInstruction(null, EAX, 1);
        Instruction instruction2 = new MovInstruction(null, EAX, 2);
        Assertions.assertNotEquals(instruction1.hashCode(), instruction2.hashCode());
    }

    @Test
    void hashCodeOtherClassAsymmetryTest() {
        Instruction instruction1 = new MovInstruction(null, EAX, 1);
        Instruction instruction2 = new AddInstruction(null, EAX, EBX);
        Assertions.assertNotEquals(instruction1.hashCode(), instruction2.hashCode());
    }

    @Test
    void equalsNoLabelsSymmetryTest() {
        Instruction instruction1 = new MovInstruction(null, EAX, 1);
        Instruction instruction2 = new MovInstruction(null, EAX, 1);
        Assertions.assertTrue(instruction1.equals(instruction2));
    }

    @Test
    void equalsWithIdenticalLabelsSymmetryTest() {
        Instruction instruction1 = new MovInstruction("test", EAX, 1);
        Instruction instruction2 = new MovInstruction("test", EAX, 1);
        Assertions.assertTrue(instruction1.equals(instruction2));
    }

    @Test
    void equalsWithOneLabelsAsymmetryTest() {
        Instruction instruction1 = new MovInstruction("test", EAX, 1);
        Instruction instruction2 = new MovInstruction(null, EAX, 1);
        Assertions.assertFalse(instruction1.equals(instruction2));
    }

    @Test
    void equalsWithBothLabelsAsymmetryTest() {
        Instruction instruction1 = new MovInstruction("test", EAX, 1);
        Instruction instruction2 = new MovInstruction("dif", EAX, 1);
        Assertions.assertFalse(instruction1.equals(instruction2));
    }
}
