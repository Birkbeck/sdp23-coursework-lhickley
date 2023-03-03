package sml;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static sml.Registers.Register.*;

public class RegistersTest {

    @Test
    void equalsNoValuesSymmetryTest() {
        Registers registers1 = new Registers();
        Registers registers2 = new Registers();
        Assertions.assertTrue(registers1.equals(registers2));
    }

    @Test
    void equalsWithValuesSymmetryTest() {
        Registers registers1 = new Registers();
        Registers registers2 = new Registers();
        registers1.set(EAX, 1);
        registers2.set(EAX, 1);
        Assertions.assertTrue(registers1.equals(registers2));
    }

    @Test
    void equalsWithValuesAsymmetryTest() {
        Registers registers1 = new Registers();
        Registers registers2 = new Registers();
        registers1.set(EAX, 1);
        registers2.set(EAX, 2);
        Assertions.assertFalse(registers1.equals(registers2));
    }

    @Test
    void equalsWithRegistersAsymmetryTest() {
        Registers registers1 = new Registers();
        Registers registers2 = new Registers();
        registers1.set(EAX, 1);
        registers2.set(EBX, 1);
        Assertions.assertFalse(registers1.equals(registers2));
    }

}
