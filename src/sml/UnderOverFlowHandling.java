package sml;

public interface UnderOverFlowHandling {
    default void handleOverUnderFlow(int value1, int value2, int res, String result, String source) {
        if (((value1 ^ res) & (value2 ^ res)) < 0) {
            if (res < 0) {
                throw new ArithmeticException("The addition of values " + value1 + " and " + value2 + " stored in the " +
                        "registers " + result + " and " + source + " cannot be performed.\nThis will lead to a value " +
                        "overflow in the " + result + " register.\nThe maximum value which can be summed to is 2,147,483,647"
                );
            } else {
                throw new ArithmeticException("The addition of values " + value1 + " and " + value2 + " stored in the " +
                        "registers " + result + " and " + source + " cannot be performed.\nThis will lead to a value " +
                        "underflow in the " + result + " register.\nThe minimum value which can be summed to is -2,147,483,648"
                );
            }
        }
    }
}
