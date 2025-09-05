package com.example.atm;

/**
 * Class to represent an ATM with state management
 */
public class ATM {
    public static final String CURRENT_STATE = "State: ";
    private ATMState currentState;
    private double availableBalance; // Simulated account balance
    private double atmCash; // Cash available in ATM
    private boolean cardValid;
    private boolean pinCorrect;

    /**
     * Constructor to initialize the ATM with cash
     *
     * @param atmCash
     */
    public ATM(double atmCash) {
        this.currentState = ATMState.IDLE;
        this.atmCash = atmCash;
        this.availableBalance = 1000.0; // Simulated starting balance
        this.cardValid = false;
        this.pinCorrect = false;
    }

    /**
     * Process user action based on current state
     *
     * @param action
     * @param params
     */
    public void processAction(UserAction action, String... params) {
        switch (currentState) {
            case IDLE:
                handleIdleState(action, params);
                break;
            case CARD_INSERTED:
                handleCardInsertedState(action, params);
                break;
            case PIN_VERIFIED:
                handlePinVerifiedState(action, params);
                break;
            case TRANSACTION_IN_PROGRESS:
                handleTransactionInProgressState(action, params);
                break;
            case OUT_OF_SERVICE:
                handleOutOfServiceState(action);
                break;
        }
    }

    /**
     * Handle actions in IDLE state
     *
     * @param action
     * @param params
     */
    private void handleIdleState(UserAction action, String[] params) {
        switch (action) {
            case INSERT_CARD:
                if (params.length > 0 && validateCard(params[0])) {
                    cardValid = true;
                    currentState = ATMState.CARD_INSERTED;
                    printToConsole("Card inserted successfully. Please enter PIN.");
                } else {
                    printToConsole("Invalid card. Please try again.");
                }
                break;
            case SHUTDOWN:
                currentState = ATMState.OUT_OF_SERVICE;
                printToConsole("ATM is now out of service.");
                break;
            default:
                printToConsole("Invalid action. Please insert card.");
        }
    }

    /**
     * Handle actions in CARD_INSERTED state
     *
     * @param action
     * @param params
     */
    private void handleCardInsertedState(UserAction action, String[] params) {
        switch (action) {
            case ENTER_PIN:
                if (params.length > 0 && validatePin(params[0])) {
                    pinCorrect = true;
                    currentState = ATMState.PIN_VERIFIED;
                    printToConsole("PIN verified. Select transaction.");
                } else {
                    printToConsole("Invalid PIN. Try again or eject card.");
                }
                break;
            case EJECT_CARD:
                cardValid = false;
                currentState = ATMState.IDLE;
                printToConsole("Card ejected. ATM is idle.");
                break;
            default:
                printToConsole("Invalid action. Please enter PIN or eject card.");
        }
    }

    /**
     * Handle actions in PIN_VERIFIED state
     *
     * @param action
     * @param params
     */
    private void handlePinVerifiedState(UserAction action, String[] params) {
        switch (action) {
            case SELECT_WITHDRAWAL:
                currentState = ATMState.TRANSACTION_IN_PROGRESS;
                printToConsole("Withdrawal selected. Enter amount.");
                break;
            case EJECT_CARD:
                cardValid = false;
                pinCorrect = false;
                currentState = ATMState.IDLE;
                printToConsole("Card ejected. ATM is idle.");
                break;
            default:
                printToConsole("Invalid action. Select withdrawal or eject card.");
        }
    }

    /**
     * Handle actions in TRANSACTION_IN_PROGRESS state
     *
     * @param action
     * @param params
     */
    private void handleTransactionInProgressState(UserAction action, String[] params) {
        switch (action) {
            case WITHDRAW_AMOUNT:
                if (params.length > 0) {
                    try {
                        double amount = Double.parseDouble(params[0]);
                        if (performWithdrawal(amount)) {
                            printToConsole("Withdrawal successful: $" + amount);
                            printToConsole("Remaining balance: $" + availableBalance);
                        } else {
                            printToConsole("Withdrawal failed: Insufficient funds or ATM cash.");
                        }
                    } catch (NumberFormatException e) {
                        printToConsole("Invalid amount entered.");
                    }
                }
                currentState = ATMState.PIN_VERIFIED;
                break;
            case EJECT_CARD:
                cardValid = false;
                pinCorrect = false;
                currentState = ATMState.IDLE;
                printToConsole("Card ejected. ATM is idle.");
                break;
            default:
                printToConsole("Invalid action. Enter amount or eject card.");
        }
    }

    /**
     * Handle actions in OUT_OF_SERVICE state
     *
     * @param action
     */
    private void handleOutOfServiceState(UserAction action) {
        printToConsole("ATM is out of service. No actions allowed.");
    }

    /**
     * Validate card number (simple check)
     *
     * @param cardNumber
     * @return
     */
    private boolean validateCard(String cardNumber) {
        // Simplified validation (e.g., card number length check)
        return cardNumber != null && cardNumber.length() == 16;
    }

    /**
     * Validate PIN (simple check)
     *
     * @param pin
     * @return
     */
    // Simulate PIN validation
    private boolean validatePin(String pin) {
        // Simplified PIN check (e.g., hardcoded PIN "1234")
        return pin != null && pin.equals("1234");
    }

    /**
     * Perform withdrawal if funds are sufficient
     *
     * @param amount
     * @return
     */
    private boolean performWithdrawal(double amount) {
        if (amount <= 0 || amount > availableBalance || amount > atmCash) {
            return false;
        }
        availableBalance -= amount;
        atmCash -= amount;
        if (atmCash <= 0) {
            currentState = ATMState.OUT_OF_SERVICE;
            printToConsole("ATM is out of cash. Moving to out-of-service state.");
        }
        return true;
    }

    /**
     * Get the current state of the ATM (for testing/debugging)
     *
     * @return
     */
    public ATMState getCurrentState() {
        return currentState;
    }

    /**
     * Prints the current state to the console
     */
    public void printCurrentState() {
        System.out.printf("%s%s [%s]\n", ConsoleColors.GREEN, CURRENT_STATE, currentState);
    }

    /**
     * Prints the current state to the console
     */
    public void printToConsole(String message) {
        System.out.printf("%s%s\n", ConsoleColors.RESET, message);
    }

    /**
     * Main method to simulate ATM operations
     *
     * @param args
     */
    public static void main(String[] args) {
        ATM atm = new ATM(5000.0); // ATM with $5000 cash

        // Simulate ATM usage
        atm.printCurrentState();
        atm.processAction(UserAction.INSERT_CARD, "1234567890123456"); // Valid card
        atm.printCurrentState();
        atm.processAction(UserAction.ENTER_PIN, "1234"); // Valid PIN
        atm.printCurrentState();
        atm.processAction(UserAction.SELECT_WITHDRAWAL);
        atm.printCurrentState();
        atm.processAction(UserAction.WITHDRAW_AMOUNT, "200"); // Withdraw $200
        atm.printCurrentState();
        atm.processAction(UserAction.EJECT_CARD);
        atm.printCurrentState();
        atm.processAction(UserAction.SHUTDOWN);
        atm.printCurrentState();
    }
}

/**
 * Color codes for console texts
 */
class ConsoleColors {
    public static final String RESET = "\u001B[0m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String BLUE = "\u001B[34m";
    public static final String PURPLE = "\u001B[35m";
    public static final String CYAN = "\u001B[36m";
    public static final String WHITE = "\u001B[37m";
    // Add more colors as needed
}