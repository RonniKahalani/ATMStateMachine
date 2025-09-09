package com.example.atm.good;

import com.example.atm.good.context.ATMContext;
import com.example.atm.good.transaction.TransactionProcessorImpl;
import com.example.atm.good.user.UserAction;
import com.example.atm.good.validator.CardValidatorImpl;
import com.example.atm.good.validator.PinValidatorImpl;
import com.example.atm.util.ConsoleColors;

/**
 * ATM Demo main class acting as the user interface.
 */
public class ATMMain {
    public static void main(String[] args) {

        // Initialize ATM with validators and $5000 cash
        ATMContext atm = new ATMContext(new CardValidatorImpl(), new PinValidatorImpl(), new TransactionProcessorImpl(), 5000.0);

        // Simulate ATM usage scenario
        printCurrentState(atm);
        atm.processAction(UserAction.INSERT_CARD, "1234567890123456"); // Valid card
        printCurrentState(atm);
        atm.processAction(UserAction.ENTER_PIN, "1234"); // Valid PIN
        printCurrentState(atm);
        atm.processAction(UserAction.SELECT_WITHDRAWAL);
        printCurrentState(atm);
        atm.processAction(UserAction.WITHDRAW_AMOUNT, "200"); // Withdraw $200
        printCurrentState(atm);
        atm.processAction(UserAction.EJECT_CARD);
        printCurrentState(atm);
        atm.processAction(UserAction.SHUTDOWN);
        printCurrentState(atm);
    }

    public static void printCurrentState(ATMContext context) {
        System.out.printf("%sState [%s]%s\n", ConsoleColors.GREEN.getCode(), context.getCurrentStateType().toString(), ConsoleColors.RESET.getCode());
    }
}