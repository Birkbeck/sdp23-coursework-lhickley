package sml;

import java.util.*;
import java.util.stream.Collectors;

/**
 * This class represents the structure we use to store Registers and their contents.
 *
 * Defines the permissible RegisterNames for the architecture.
 *
 * This class consists of methods for the interaction with this data structure, such as the setting of register values
 * and the retrieval of values stored in registers.
 *
 * @author lhickley
 */
public final class Registers {
    private final Map<Register, Integer> registers = new HashMap<>();

    public enum Register implements RegisterName {
        EAX, EBX, ECX, EDX, ESP, EBP, ESI, EDI;
    }

    public Registers() {
        clear(); // the class is final
    }

    public void clear() {
        for (Register register : Register.values())
            registers.put(register, 0);
    }

    /**
     * Sets the given register to the value.
     *
     * @param register register name
     * @param value new value
     */
    public void set(RegisterName register, int value) {
        registers.put((Register)register, value);
    }

    /**
     * Returns the value stored in the register.
     *
     * @param register register name
     * @return value
     */
    public int get(RegisterName register) {
        return registers.get((Register)register);
    }

    /**
     * Determines whether the current Registers instance is equal to the given object.
     * @param o the object to compare with the current Registers instance
     * @return true if the current Registers instance is equal to the given object, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (o instanceof Registers r) {
            return registers.equals(r.registers);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return registers.hashCode();
    }

    @Override
    public String toString() {
        return registers.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .map(e -> e.getKey() + " = " + e.getValue())
                .collect(Collectors.joining(", ", "[", "]")) ;
    }
}
