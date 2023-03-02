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

class AddInstructionTest {
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
  void executeValid() {
    registers.set(EAX, 5);
    registers.set(EBX, 6);
    Instruction instruction = new AddInstruction(null, EAX, EBX);
    instruction.execute(machine);
    Assertions.assertEquals(11, machine.getRegisters().get(EAX));
  }

  @Test
  void executeValidTwo() {
    registers.set(EAX, -5);
    registers.set(EBX, 6);
    Instruction instruction = new AddInstruction(null, EAX, EBX);
    instruction.execute(machine);
    Assertions.assertEquals(1, machine.getRegisters().get(EAX));
  }

  @Test
  void executeToStringNoLabel() {
    registers.set(EAX, 6);
    registers.set(EBX, 5);
    Instruction instruction = new AddInstruction(null, EAX, EBX);
    String toStringResult = instruction.toString();
    Assertions.assertEquals("add EAX EBX", toStringResult);
  }

  @Test
  void executeToStringWithLabel() {
    registers.set(EAX, 6);
    registers.set(EBX, 5);
    Instruction instruction = new AddInstruction("test", EAX, EBX);
    String toStringResult = instruction.toString();
    Assertions.assertEquals("test: add EAX EBX", toStringResult);
  }

  //Java will treat an unset int as a 0, as int is a primitive and so can't be set to null
  //Here we confirm that this ends up being treated as such when doing an addition
  @Test
  void attemptToUseNullRegister() {
    registers.set(EAX, 1);
    Instruction instruction = new AddInstruction(null, EAX, EBX);
    instruction.execute(machine);
    Assertions.assertEquals(1, registers.get(EAX));
  }

  @Test
  void executeMaxIntegerOverflow() {
    registers.set(EAX, 2147483647);
    registers.set(EBX, 1);
    Instruction instruction = new AddInstruction(null, EAX, EBX);
    Exception exception = assertThrows(ArithmeticException.class, () -> instruction.execute(machine));
    String expectedMessage = """
            The addition of values 2147483647 and 1 stored in the registers EAX and EBX cannot be performed.
            This will lead to a value overflow in the EAX register.
            The maximum value which can be summed to is 2,147,483,647""";
    String actualMessage = exception.getMessage();
    Assertions.assertEquals(expectedMessage, actualMessage);
  }

  @Test
  void executeMinIntUnderflow() {
    registers.set(EAX, -2147483648);
    registers.set(EBX, -1);
    Instruction instruction = new AddInstruction(null, EAX, EBX);
    Exception exception = assertThrows(ArithmeticException.class, () -> instruction.execute(machine));
    String expectedMessage = """
            The addition of values -2147483648 and -1 stored in the registers EAX and EBX cannot be performed.
            This will lead to a value underflow in the EAX register.
            The minimum value which can be summed to is -2,147,483,648""";
    String actualMessage = exception.getMessage();
    Assertions.assertEquals(expectedMessage, actualMessage);
  }

}