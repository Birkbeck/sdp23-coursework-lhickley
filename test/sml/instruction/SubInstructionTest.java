package sml.instruction;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sml.Instruction;
import sml.Machine;
import sml.Registers;

import static sml.Registers.Register.*;

public class SubInstructionTest {
    private Machine machine;
    private Registers registers;

    @BeforeEach
    void setUp() {
        machine = new Machine(new Registers());
        registers = machine.getRegisters();
        //...
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
        Instruction instruction = new SubInstruction(null, EAX, EBX);
        instruction.execute(machine);
        Assertions.assertEquals(1, machine.getRegisters().get(EAX));
    }

    @Test
    void executeValidPositiveSubNegative() {
        registers.set(EAX, 6);
        registers.set(EBX, -5);
        Instruction instruction = new SubInstruction(null, EAX, EBX);
        instruction.execute(machine);
        Assertions.assertEquals(11, machine.getRegisters().get(EAX));
    }

    @Test
    void executeValidNegativeSubPositive() {
        registers.set(EAX, -6);
        registers.set(EBX, 5);
        Instruction instruction = new SubInstruction(null, EAX, EBX);
        instruction.execute(machine);
        Assertions.assertEquals(-11, machine.getRegisters().get(EAX));
    }

    @Test
    void executeValidNegativeSubNegative() {
        registers.set(EAX, -6);
        registers.set(EBX, -5);
        Instruction instruction = new SubInstruction(null, EAX, EBX);
        instruction.execute(machine);
        Assertions.assertEquals(-1, machine.getRegisters().get(EAX));
    }

    @Test
    void executeToStringNoLabel() {
        registers.set(EAX, 6);
        registers.set(EBX, 5);
        Instruction instruction = new SubInstruction(null, EAX, EBX);
        String toStringResult = instruction.toString();
        Assertions.assertEquals("sub EAX EBX", toStringResult);
    }

    @Test
    void executeToStringWithLabel() {
        registers.set(EAX, 6);
        registers.set(EBX, 5);
        Instruction instruction = new SubInstruction("test", EAX, EBX);
        String toStringResult = instruction.toString();
        Assertions.assertEquals("test: sub EAX EBX", toStringResult);
    }
}
