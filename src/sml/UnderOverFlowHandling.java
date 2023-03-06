package sml;

/**
 * Interface defining a default method to handle overflow and underflow during arithmetic operations.
 *
 * Implementations can use this method to check for over/underflow and throw an {@code ArithmeticException} if necessary.
 *
 * @author lhickley
 */
public interface UnderOverFlowHandling {
    /**
     * Default method to handle over/underflow during arithmetic operations.
     * Throws an {@code ArithmeticException} if the operation will result in an overflow or underflow.
     * @param value1 the first value in the arithmetic operation
     * @param value2 the second value in the arithmetic operation
     * @param res the result of the arithmetic operation
     * @param result the name of the register where the result is stored
     * @param source the name of the register where the second value is stored
     * @param opcode the opcode for the arithmetic operation
     * @param helper an implementation of {@code UnderOverFlowHelper} to perform the check for overflow/underflow
     */
    default void handleOverUnderFlow(int value1, int value2, int res, String result, String source, String opcode, UnderOverFlowHelper helper) {
        if (helper.doOperation(value1, value2, res)) {
            if (res < 0) {
                throw new ArithmeticException("The combination of values " + value1 + " and " + value2 + " stored in the " +
                        "registers " + result + " and " + source + " using the opcode '" + opcode + "' cannot be performed.\nThis will lead to a value " +
                        "overflow in the " + result + " register.\nThe maximum value which can be stored is 2,147,483,647"
                );
            } else {
                throw new ArithmeticException("The combination of values " + value1 + " and " + value2 + " stored in the " +
                        "registers " + result + " and " + source + " using the opcode '" + opcode + "' cannot be performed.\nThis will lead to a value " +
                        "underflow in the " + result + " register.\nThe minimum value which can be stored is -2,147,483,648"
                );
            }
        }
    }
}
