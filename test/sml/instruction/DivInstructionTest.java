package sml.instruction;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sml.Instruction;
import sml.Machine;
import sml.Registers;

import static sml.Registers.Register.*;

public class DivInstructionTest {
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

}
