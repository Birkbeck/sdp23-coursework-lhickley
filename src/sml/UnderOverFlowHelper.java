package sml;

/**
 * The UnderOverFlowHelper interface provides a method to be implemented by classes that need to determine if an arithmetic
 * operation will result in an underflow or overflow.
 *
 * The doOperation method takes in three integer values and returns a boolean indicating if the arithmetic operation on
 * those values will result in an underflow or overflow.
 *
 * @author lhickley
 */
public interface UnderOverFlowHelper {
    /**
     * Determines if the arithmetic operation on the given integer values will result in an underflow or overflow.
     * @param a the first integer value
     * @param b the second integer value
     * @param c the result of the arithmetic operation on a and b
     * @return true if the operation will result in an underflow or overflow, false otherwise
     */
    boolean doOperation(int a, int b, int c);
}
