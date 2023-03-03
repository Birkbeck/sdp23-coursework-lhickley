package sml.instruction;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sml.Instruction;
import sml.Machine;
import sml.Registers;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static sml.Registers.Register.EAX;
import static sml.Registers.Register.EBX;

public class OutInstructionTest {

    private Machine machine;
    private Registers registers;

    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @BeforeEach
    void setUp() {
        machine = new Machine(new Registers());
        registers = machine.getRegisters();
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @AfterEach
    void tearDown() {
        machine = null;
        registers = null;
    }

    @Test
    void executeFilledRegister() {
        registers.set(EAX, 1);
        Instruction instruction = new OutInstruction(null, EAX);
        instruction.execute(machine);
        Assertions.assertEquals("1", outputStreamCaptor.toString().trim());
    }

    @Test
    void executeNullRegister() {
        Instruction instruction = new OutInstruction(null, EAX);
        instruction.execute(machine);
        Assertions.assertEquals("0", outputStreamCaptor.toString().trim());
    }

    @Test
    void executeToStringNoLabel() {
        registers.set(EAX, 6);
        Instruction instruction = new OutInstruction(null, EAX);
        String toStringResult = instruction.toString();
        Assertions.assertEquals("out EAX", toStringResult);
    }

    @Test
    void executeToStringWithLabel() {
        registers.set(EAX, 6);
        Instruction instruction = new OutInstruction("test", EAX);
        String toStringResult = instruction.toString();
        Assertions.assertEquals("test: out EAX", toStringResult);
    }

    @Test
    void hashCodeNoLabelsSymmetryTest() {
        Instruction instruction1 = new OutInstruction(null, EAX);
        Instruction instruction2 = new OutInstruction(null, EAX);
        Assertions.assertEquals(instruction1.hashCode(), instruction2.hashCode());
    }

    @Test
    void hashCodeWithIdenticalLabelsSymmetryTest() {
        Instruction instruction1 = new OutInstruction("test", EAX);
        Instruction instruction2 = new OutInstruction("test", EAX);
        Assertions.assertEquals(instruction1.hashCode(), instruction2.hashCode());
    }

    @Test
    void hashCodeWithOneLabelsAsymmetryTest() {
        Instruction instruction1 = new OutInstruction("test", EAX);
        Instruction instruction2 = new OutInstruction(null, EAX);
        Assertions.assertNotEquals(instruction1.hashCode(), instruction2.hashCode());
    }

    @Test
    void hashCodeWithBothLabelsAsymmetryTest() {
        Instruction instruction1 = new OutInstruction("test", EAX);
        Instruction instruction2 = new OutInstruction("dif", EAX);
        Assertions.assertNotEquals(instruction1.hashCode(), instruction2.hashCode());
    }

    @Test
    void hashCodeWithDifferingRegisterAsymmetryTest() {
        Instruction instruction1 = new OutInstruction(null, EAX);
        Instruction instruction2 = new OutInstruction(null, EBX);
        Assertions.assertNotEquals(instruction1.hashCode(), instruction2.hashCode());
    }

    @Test
    void hashCodeOtherClassAsymmetryTest() {
        Instruction instruction1 = new OutInstruction(null, EAX);
        Instruction instruction2 = new AddInstruction(null, EAX, EBX);
        Assertions.assertNotEquals(instruction1.hashCode(), instruction2.hashCode());
    }

    @Test
    void equalsNoLabelsSymmetryTest() {
        Instruction instruction1 = new OutInstruction(null, EAX);
        Instruction instruction2 = new OutInstruction(null, EAX);
        Assertions.assertTrue(instruction1.equals(instruction2));
    }

    @Test
    void equalsWithIdenticalLabelsSymmetryTest() {
        Instruction instruction1 = new OutInstruction("test", EAX);
        Instruction instruction2 = new OutInstruction("test", EAX);
        Assertions.assertTrue(instruction1.equals(instruction2));
    }

    @Test
    void equalsWithOneLabelsAsymmetryTest() {
        Instruction instruction1 = new OutInstruction("test", EAX);
        Instruction instruction2 = new OutInstruction(null, EAX);
        Assertions.assertFalse(instruction1.equals(instruction2));
    }

    @Test
    void equalsWithBothLabelsAsymmetryTest() {
        Instruction instruction1 = new OutInstruction("test", EAX);
        Instruction instruction2 = new OutInstruction("dif", EAX);
        Assertions.assertFalse(instruction1.equals(instruction2));
    }

}
