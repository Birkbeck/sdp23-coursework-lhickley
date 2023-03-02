package sml.instruction;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sml.Machine;
import sml.Registers;
import sml.Instruction;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static sml.Registers.Register.*;

public class MulInstructionTest {
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
        registers.set(EAX, 6);
        registers.set(EBX, 5);
        Instruction instruction = new MulInstruction(null, EAX, EBX);
        instruction.execute(machine);
        Assertions.assertEquals(30, machine.getRegisters().get(EAX));
    }

    @Test
    void executeValidNegativeAndPositive() {
        registers.set(EAX, 6);
        registers.set(EBX, -5);
        Instruction instruction = new MulInstruction(null, EAX, EBX);
        instruction.execute(machine);
        Assertions.assertEquals(-30, machine.getRegisters().get(EAX));
    }

    @Test
    void executeValidBothNegative() {
        registers.set(EAX, -6);
        registers.set(EBX, -5);
        Instruction instruction = new MulInstruction(null, EAX, EBX);
        instruction.execute(machine);
        Assertions.assertEquals(30, machine.getRegisters().get(EAX));
    }

    @Test
    void executeToStringNoLabel() {
        registers.set(EAX, 6);
        registers.set(EBX, 5);
        Instruction instruction = new MulInstruction(null, EAX, EBX);
        String toStringResult = instruction.toString();
        Assertions.assertEquals("mul EAX EBX", toStringResult);
    }

    @Test
    void executeToStringWithLabel() {
        registers.set(EAX, 6);
        registers.set(EBX, 5);
        Instruction instruction = new MulInstruction("test", EAX, EBX);
        String toStringResult = instruction.toString();
        Assertions.assertEquals("test: mul EAX EBX", toStringResult);
    }

    //Java will treat an unset int as a 0, as int is a primitive and so can't be set to null
    //Here we confirm that this ends up being treated as such when doing a multiplication
    @Test
    void attemptToUseNullRegister() {
        registers.set(EAX, 1);
        Instruction instruction = new MulInstruction(null, EAX, EBX);
        instruction.execute(machine);
        Assertions.assertEquals(0, registers.get(EAX));
    }

    @Test
    void executeMaxIntOverflow() {
        registers.set(EAX, 2147483647);
        registers.set(EBX, 2);
        Instruction instruction = new MulInstruction(null, EAX, EBX);
        Exception exception = assertThrows(ArithmeticException.class, () -> instruction.execute(machine));
        String expectedMessage = """
            The combination of values 2147483647 and 2 stored in the registers EAX and EBX using the opcode 'mul' cannot be performed.
            This will lead to a value overflow in the EAX register.
            The maximum value which can be stored is 2,147,483,647""";
        String actualMessage = exception.getMessage();
        Assertions.assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void executeMinIntUnderflow() {
        registers.set(EAX, -2147483648);
        registers.set(EBX, 2);
        Instruction instruction = new MulInstruction(null, EAX, EBX);
        Exception exception = assertThrows(ArithmeticException.class, () -> instruction.execute(machine));
        String expectedMessage = """
            The combination of values -2147483648 and 2 stored in the registers EAX and EBX using the opcode 'mul' cannot be performed.
            This will lead to a value underflow in the EAX register.
            The minimum value which can be stored is -2,147,483,648""";
        String actualMessage = exception.getMessage();
        Assertions.assertEquals(expectedMessage, actualMessage);
    }

}
