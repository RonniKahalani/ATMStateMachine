package com.example.atm.bad;

import com.example.atm.util.ConsoleColors;

/**
 * Class to represent an ATM with state management
 */
public class ATM {
    private ATMState currentState;
    private double availableBalance; // Simulated account balance
    private double atmCash; // Cash available in ATM
    private boolean cardValid;
    private boolean pinCorrect;

    /**
     * Constructor to initialize the ATM with cash
     *
     * @param atmCash is the initial amount of money the ATM has available
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
     * @param action from the user
     * @param params from the user
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
     * @param action from the user
     * @param params from the user
     */
    private void handleIdleState(UserAction action, String[] params) {
        switch (action) {
            case INSERT_CARD:
                doInsertCard(params);
                break;
            case SHUTDOWN:
                doShutdown();
                break;
            default:
                printToConsole("Invalid action. Please insert card.");
        }
    }

    /**
     * Handle actions in CARD_INSERTED state
     *
     * @param action from the user
     * @param params from the user
     */
    private void handleCardInsertedState(UserAction action, String[] params) {
        switch (action) {
            case ENTER_PIN:
                doEnterPIN(params);
                break;
            case EJECT_CARD:
                doEjectCard();
                break;
            default:
                printToConsole("Invalid action. Please enter PIN or eject card.");
        }
    }

    /**
     * Handle actions in PIN_VERIFIED state
     *
     * @param action from the user
     * @param params from the user
     */
    private void handlePinVerifiedState(UserAction action, String[] params) {
        switch (action) {
            case SELECT_WITHDRAWAL:
                doSelectWithdrawal();
                break;
            case EJECT_CARD:
                doEjectCard();
                break;
            default:
                printToConsole("Invalid action. Select withdrawal or eject card.");
        }
    }

    /**
     * Handle actions in TRANSACTION_IN_PROGRESS state
     *
     * @param action from the user
     * @param params from the user
     */
    private void handleTransactionInProgressState(UserAction action, String[] params) {
        switch (action) {
            case WITHDRAW_AMOUNT:
                doWithdrawAmount(params);
                break;
            case EJECT_CARD:
                doEjectCard();
                break;
            default:
                printToConsole("Invalid action. Enter amount or eject card.");
        }
    }

    /**
     * Handle actions in OUT_OF_SERVICE state
     *
     * @param action from the user
     */
    private void handleOutOfServiceState(UserAction action) {
        printToConsole("ATM is out of service. No actions allowed.");
    }

    /**
     * Withdraws the amount in the params argument
     *
     * @param params from the user
     */
    private void doWithdrawAmount(String[] params) {
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
    }

    /**
     * Validates the PIN
     *
     * @param params from the user
     */
    private void doEnterPIN(String[] params) {
        if (params.length > 0 && validatePin(params[0])) {
            pinCorrect = true;
            currentState = ATMState.PIN_VERIFIED;
            printToConsole("PIN verified. Select transaction.");
        } else {
            printToConsole("Invalid PIN. Try again or eject card.");
        }
    }

    /**
     * Validates the card
     *
     * @param params from the user
     */
    private void doInsertCard(String[] params) {
        if (params.length > 0 && validateCard(params[0])) {
            cardValid = true;
            currentState = ATMState.CARD_INSERTED;
            printToConsole("Card inserted and card number validated successfully. Please enter PIN.");
        } else {
            printToConsole("Invalid card. Please try again.");
        }
    }

    /**
     * Shuts down the ATM
     */
    private void doShutdown() {
        currentState = ATMState.OUT_OF_SERVICE;
        printToConsole("ATM is now out of service.");
    }


    /**
     * Handles the withdrawal process and amount input.
     */
    private void doSelectWithdrawal() {
        currentState = ATMState.TRANSACTION_IN_PROGRESS;
        printToConsole("Withdrawal selected. Enter amount.");
    }

    /**
     * Ejects the card
     */
    private void doEjectCard() {
        cardValid = false;
        pinCorrect = false;
        currentState = ATMState.IDLE;
        printToConsole("Card ejected. ATM is idle.");
    }

    /**
     * Validate card number (simple check)
     *
     * @param cardNumber to validate
     * @return false is card number is not valid
     */
    private boolean validateCard(String cardNumber) {
        // Simplified validation (e.g., card number length check)
        return cardNumber != null && cardNumber.length() == 16;
    }

    /**
     * Validate PIN (simple check)
     *
     * @param pin code for the card
     * @return true if PIN is valid
     */
    // Simulate PIN validation
    private boolean validatePin(String pin) {
        // Simplified PIN check (e.g., hardcoded PIN "1234")
        return pin != null && pin.equals("1234");
    }

    /**
     * Perform withdrawal if funds are sufficient
     *
     * @param amount to withdraw
     * @return false if amount is less than 0 or bigger than the available balance
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
     * Prints the current state to the console
     */
    public void printCurrentState() {
        System.out.printf("%sState: [%s]\n", ConsoleColors.GREEN.getCode(), currentState);
    }

    /**
     * Prints the current state to the console
     */
    public void printToConsole(String message) {
        System.out.printf("%s%s\n", ConsoleColors.RESET.getCode(), message);
        System.out.printf("Card: %b, PIN: %b\n", cardValid, pinCorrect);
        System.out.println("-------------------------------------------------------");
    }

    /**
     * Main method to simulate ATM operations
     *
     * @param args passed from the command line
     */
    public static void main(String[] args) {
        ATM atm = new ATM(5000.0); // ATM with $5000 cash

        System.out.println("*******************************************************");
        System.out.println("ATM - State Machine Example.");
        System.out.println("*******************************************************");

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

