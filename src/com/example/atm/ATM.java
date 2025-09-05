package com.example.atm;

/**
 * Class to represent an ATM with state management
 */
public class ATM {
    public static final String CURRENT_STATE = "Current state: ";
    private ATMState currentState;
    private double availableBalance; // Simulated account balance
    private double atmCash; // Cash available in com.example.atm.ATM
    private boolean cardValid;
    private boolean pinCorrect;

    /**
     * Constructor to initialize the ATM with cash
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
     * @param action
     * @param params
     */
    private void handleIdleState(UserAction action, String[] params) {
        switch (action) {
            case INSERT_CARD:
                if (params.length > 0 && validateCard(params[0])) {
                    cardValid = true;
                    currentState = ATMState.CARD_INSERTED;
                    System.out.println("Card inserted successfully. Please enter PIN.");
                } else {
                    System.out.println("Invalid card. Please try again.");
                }
                break;
            case SHUTDOWN:
                currentState = ATMState.OUT_OF_SERVICE;
                System.out.println("com.example.atm.ATM is now out of service.");
                break;
            default:
                System.out.println("Invalid action. Please insert card.");
        }
    }

    /**
     * Handle actions in CARD_INSERTED state
     * @param action
     * @param params
     */
    private void handleCardInsertedState(UserAction action, String[] params) {
        switch (action) {
            case ENTER_PIN:
                if (params.length > 0 && validatePin(params[0])) {
                    pinCorrect = true;
                    currentState = ATMState.PIN_VERIFIED;
                    System.out.println("PIN verified. Select transaction.");
                } else {
                    System.out.println("Invalid PIN. Try again or eject card.");
                }
                break;
            case EJECT_CARD:
                cardValid = false;
                currentState = ATMState.IDLE;
                System.out.println("Card ejected. com.example.atm.ATM is idle.");
                break;
            default:
                System.out.println("Invalid action. Please enter PIN or eject card.");
        }
    }

    /**
     * Handle actions in PIN_VERIFIED state
     * @param action
     * @param params
     */
    private void handlePinVerifiedState(UserAction action, String[] params) {
        switch (action) {
            case SELECT_WITHDRAWAL:
                currentState = ATMState.TRANSACTION_IN_PROGRESS;
                System.out.println("Withdrawal selected. Enter amount.");
                break;
            case EJECT_CARD:
                cardValid = false;
                pinCorrect = false;
                currentState = ATMState.IDLE;
                System.out.println("Card ejected. com.example.atm.ATM is idle.");
                break;
            default:
                System.out.println("Invalid action. Select withdrawal or eject card.");
        }
    }

    /**
     * Handle actions in TRANSACTION_IN_PROGRESS state
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
                            System.out.println("Withdrawal successful: $" + amount);
                            System.out.println("Remaining balance: $" + availableBalance);
                        } else {
                            System.out.println("Withdrawal failed: Insufficient funds or com.example.atm.ATM cash.");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid amount entered.");
                    }
                }
                currentState = ATMState.PIN_VERIFIED;
                break;
            case EJECT_CARD:
                cardValid = false;
                pinCorrect = false;
                currentState = ATMState.IDLE;
                System.out.println("Card ejected. com.example.atm.ATM is idle.");
                break;
            default:
                System.out.println("Invalid action. Enter amount or eject card.");
        }
    }

    /**
     * Handle actions in OUT_OF_SERVICE state
     * @param action
     */
    private void handleOutOfServiceState(UserAction action) {
        System.out.println("com.example.atm.ATM is out of service. No actions allowed.");
    }

    /**
     * Validate card number (simple check)
     * @param cardNumber
     * @return
     */
    private boolean validateCard(String cardNumber) {
        // Simplified validation (e.g., card number length check)
        return cardNumber != null && cardNumber.length() == 16;
    }

    /**
     * Validate PIN (simple check)
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
            System.out.println("com.example.atm.ATM is out of cash. Moving to out-of-service state.");
        }
        return true;
    }

    /**
     * Get the current state of the ATM (for testing/debugging)
     * @return
     */
    public ATMState getCurrentState() {
        return currentState;
    }

    public void printCurrentState() {
        System.out.println(CURRENT_STATE + currentState);
    }

    /**
     * Main method to simulate ATM operations
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