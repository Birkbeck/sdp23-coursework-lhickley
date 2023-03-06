package sml.instruction;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sml.Instruction;
import sml.Machine;
import sml.Registers;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static sml.Registers.Register.*;

public class DivInstructionTest {
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
    void executeValidBothPositive() {
        registers.set(EAX, 30);
        registers.set(EBX, 5);
        Instruction instruction = new DivInstruction(null, EAX, EBX);
        instruction.execute(machine);
        Assertions.assertEquals(6, machine.getRegisters().get(EAX));
    }

    @Test
    void executeValidNegativeOverPositive() {
        registers.set(EAX, -30);
        registers.set(EBX, 5);
        Instruction instruction = new DivInstruction(null, EAX, EBX);
        instruction.execute(machine);
        Assertions.assertEquals(-6, machine.getRegisters().get(EAX));
    }

    @Test
    void executeValidPositiveOverNegative() {
        registers.set(EAX, 30);
        registers.set(EBX, -5);
        Instruction instruction = new DivInstruction(null, EAX, EBX);
        instruction.execute(machine);
        Assertions.assertEquals(-6, machine.getRegisters().get(EAX));
    }

    @Test
    void executeValidBothNegative() {
        registers.set(EAX, -30);
        registers.set(EBX, -5);
        Instruction instruction = new DivInstruction(null, EAX, EBX);
        instruction.execute(machine);
        Assertions.assertEquals(6, machine.getRegisters().get(EAX));
    }

    @Test
    void executeValidRoundsDown() {
        registers.set(EAX, 34);
        registers.set(EBX, 5);
        Instruction instruction = new DivInstruction(null, EAX, EBX);
        instruction.execute(machine);
        Assertions.assertEquals(6, machine.getRegisters().get(EAX));
    }

    @Test
    void executeToStringNoLabel() {
        registers.set(EAX, 6);
        registers.set(EBX, 5);
        Instruction instruction = new DivInstruction(null, EAX, EBX);
        String toStringResult = instruction.toString();
        Assertions.assertEquals("div EAX EBX", toStringResult);
    }

    @Test
    void executeToStringWithLabel() {
        registers.set(EAX, 6);
        registers.set(EBX, 5);
        Instruction instruction = new DivInstruction("test", EAX, EBX);
        String toStringResult = instruction.toString();
        Assertions.assertEquals("test: div EAX EBX", toStringResult);
    }

    //Java will treat an unset int as a 0, as int is a primitive and so can't be set to null
    //Here we confirm that this ends up being treated as such when doing a division
    @Test
    void attemptToUseNullRegisterDenominator() {
        registers.set(EAX, 1);
        Instruction instruction = new DivInstruction(null, EAX, EBX);
        Exception exception = assertThrows(RuntimeException.class, () -> instruction.execute(machine));
        String expectedMessage = """
                The program is attempting to divide by zero in the register EBX.
                This is not permitted, as it is not a valid arithmetic operation.
                Please confirm that the register EBX has been correctly set.""";
        String actualMessage = exception.getMessage();
        Assertions.assertEquals(expectedMessage, actualMessage);
    }

    //Java will treat an unset int as a 0, as int is a primitive and so can't be set to null
    //Here we confirm that this ends up being treated as such when doing a division
    @Test
    void attemptToUseNullRegisterNumerator() {
        registers.set(EBX, 1);
        Instruction instruction = new DivInstruction(null, EAX, EBX);
        instruction.execute(machine);
        Assertions.assertEquals(0, registers.get(EAX));
    }

    @Test
    void attemptToUseNullRegisterNumeratorAndDenominator() {
        Instruction instruction = new DivInstruction(null, EAX, EBX);
        Exception exception = assertThrows(RuntimeException.class, () -> instruction.execute(machine));
        String expectedMessage = """
                The program is attempting to divide by zero in the register EBX.
                This is not permitted, as it is not a valid arithmetic operation.
                Please confirm that the register EBX has been correctly set.""";
        String actualMessage = exception.getMessage();
        Assertions.assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void hashCodeNoLabelsSymmetryTest() {
        Instruction instruction1 = new DivInstruction(null, EAX, EBX);
        Instruction instruction2 = new DivInstruction(null, EAX, EBX);
        Assertions.assertEquals(instruction1.hashCode(), instruction2.hashCode());
    }

    @Test
    void hashCodeWithIdenticalLabelsSymmetryTest() {
        Instruction instruction1 = new DivInstruction("test", EAX, EBX);
        Instruction instruction2 = new DivInstruction("test", EAX, EBX);
        Assertions.assertEquals(instruction1.hashCode(), instruction2.hashCode());
    }

    @Test
    void hashCodeWithOneLabelsAsymmetryTest() {
        Instruction instruction1 = new DivInstruction("test", EAX, EBX);
        Instruction instruction2 = new DivInstruction(null, EAX, EBX);
        Assertions.assertNotEquals(instruction1.hashCode(), instruction2.hashCode());
    }

    @Test
    void hashCodeWithBothLabelsAsymmetryTest() {
        Instruction instruction1 = new DivInstruction("test", EAX, EBX);
        Instruction instruction2 = new DivInstruction("dif", EAX, EBX);
        Assertions.assertNotEquals(instruction1.hashCode(), instruction2.hashCode());
    }

    @Test
    void hashCodeWithDifferingRegisterAsymmetryTest() {
        Instruction instruction1 = new DivInstruction(null, EAX, EBX);
        Instruction instruction2 = new DivInstruction(null, EAX, ECX);
        Assertions.assertNotEquals(instruction1.hashCode(), instruction2.hashCode());
    }

    @Test
    void hashCodeOtherClassAsymmetryTest() {
        Instruction instruction1 = new DivInstruction(null, EAX, EBX);
        Instruction instruction2 = new AddInstruction(null, EAX, EBX);
        Assertions.assertNotEquals(instruction1.hashCode(), instruction2.hashCode());
    }

    @Test
    void equalsNoLabelsSymmetryTest() {
        Instruction instruction1 = new DivInstruction(null, EAX, EBX);
        Instruction instruction2 = new DivInstruction(null, EAX, EBX);
        Assertions.assertTrue(instruction1.equals(instruction2));
    }

    @Test
    void equalsWithIdenticalLabelsSymmetryTest() {
        Instruction instruction1 = new DivInstruction("test", EAX, EBX);
        Instruction instruction2 = new DivInstruction("test", EAX, EBX);
        Assertions.assertTrue(instruction1.equals(instruction2));
    }

    @Test
    void equalsWithOneLabelsAsymmetryTest() {
        Instruction instruction1 = new DivInstruction("test", EAX, EBX);
        Instruction instruction2 = new DivInstruction(null, EAX, EBX);
        Assertions.assertFalse(instruction1.equals(instruction2));
    }

    @Test
    void equalsWithBothLabelsAsymmetryTest() {
        Instruction instruction1 = new DivInstruction("test", EAX, EBX);
        Instruction instruction2 = new DivInstruction("dif", EAX, EBX);
        Assertions.assertFalse(instruction1.equals(instruction2));
    }

    @Test
    void equalsWithDifferingRegisterAsymmetryTest() {
        Instruction instruction1 = new DivInstruction(null, EAX, EBX);
        Instruction instruction2 = new DivInstruction(null, EAX, ECX);
        Assertions.assertFalse(instruction1.equals(instruction2));
    }

    @Test
    void equalsOtherClassAsymmetryTest() {
        Instruction instruction1 = new DivInstruction(null, EAX, EBX);
        Instruction instruction2 = new AddInstruction(null, EAX, EBX);
        Assertions.assertFalse(instruction1.equals(instruction2));
    }
}
