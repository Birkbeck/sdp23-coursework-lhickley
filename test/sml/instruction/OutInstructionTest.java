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

}
