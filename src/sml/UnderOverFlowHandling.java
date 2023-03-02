package sml;

import java.util.function.Function;
import java.util.function.IntBinaryOperator;

public interface UnderOverFlowHandling {
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
