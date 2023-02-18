package sml.instruction;

import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sml.Instruction;
import sml.Machine;
import sml.Registers;

import static sml.Registers.Register.EAX;

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
}
