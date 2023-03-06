package sml;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class LabelsTest {
    private Labels labels;

    @BeforeEach
    void setUp() {
        labels = new Labels();
    }

    @AfterEach
    void tearDown() {
        labels = null;
    }

    @Test
    void addADuplicateLabel() {
        labels.addLabel("test", 1);
        Exception exception = assertThrows(RuntimeException.class, () -> labels.addLabel("test", 2));
        String expectedMessage = "We cannot add a duplicate label for an instruction!";
        Assertions.assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void getNonExtantLabelAddress() {
        Exception exception = assertThrows(RuntimeException.class, () -> labels.getAddress("test"));
        String expectedMessage = "The label test does not exist!  Please check the file containing the instruction set.";
        Assertions.assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void toStringTest() {
        labels.addLabel("test1", 1);
        labels.addLabel("test2", 2);
        labels.addLabel("test3", 3);
        labels.addLabel("test4", 4);
        Assertions.assertEquals("[test4 -> 4, test2 -> 2, test3 -> 3, test1 -> 1]", labels.toString());
    }

    @Test
    void hashCodeNoValuesSymmetryTest() {
        Labels labels1 = new Labels();
        Labels labels2 = new Labels();
        Assertions.assertEquals(labels1.hashCode(), labels2.hashCode());
    }

    @Test
    void hashCodeWithValuesSymmetryTest() {
        Labels labels1 = new Labels();
        Labels labels2 = new Labels();
        labels1.addLabel("test", 1);
        labels2.addLabel("test", 1);
        Assertions.assertEquals(labels1.hashCode(), labels2.hashCode());
    }

    @Test
    void hashCodeWithValuesAsymmetryTest() {
        Labels labels1 = new Labels();
        Labels labels2 = new Labels();
        labels1.addLabel("test", 1);
        labels2.addLabel("test", 2);
        Assertions.assertNotEquals(labels1.hashCode(), labels2.hashCode());
    }

    @Test
    void equalsNoValuesSymmetryTest() {
        Labels labels1 = new Labels();
        Labels labels2 = new Labels();
        Assertions.assertTrue(labels1.equals(labels2));
    }

    @Test
    void equalsWithValuesSymmetryTest() {
        Labels labels1 = new Labels();
        Labels labels2 = new Labels();
        labels1.addLabel("test", 1);
        labels2.addLabel("test", 1);
        Assertions.assertTrue(labels1.equals(labels2));
    }

    @Test
    void equalsWithValuesAsymmetryTest() {
        Labels labels1 = new Labels();
        Labels labels2 = new Labels();
        labels1.addLabel("test", 1);
        labels2.addLabel("test", 2);
        Assertions.assertFalse(labels1.equals(labels2));
    }

}
