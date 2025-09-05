package com.example.atm.sample2;

import com.example.atm.sample2.context.ATMContext;
import com.example.atm.sample2.transaction.TransactionProcessor;
import com.example.atm.sample2.transaction.TransactionProcessorImpl;
import com.example.atm.sample2.user.UserAction;
import com.example.atm.sample2.validator.CardValidator;
import com.example.atm.sample2.validator.CardValidatorImpl;
import com.example.atm.sample2.validator.PinValidator;
import com.example.atm.sample2.validator.PinValidatorImpl;

public class ATMDemo {
    public static void main(String[] args) {
        // Initialize services
        CardValidator cardValidator = new CardValidatorImpl();
        PinValidator pinValidator = new PinValidatorImpl();
        TransactionProcessor transactionProcessor = new TransactionProcessorImpl();

        // Initialize ATM with $5000 cash
        ATMContext atm = new ATMContext(cardValidator, pinValidator, transactionProcessor, 5000.0);

        // Simulate ATM usage
        System.out.println("Current state: " + atm.getCurrentStateType());
        atm.processAction(UserAction.INSERT_CARD, "1234567890123456"); // Valid card
        System.out.println("Current state: " + atm.getCurrentStateType());
        atm.processAction(UserAction.ENTER_PIN, "1234"); // Valid PIN
        System.out.println("Current state: " + atm.getCurrentStateType());
        atm.processAction(UserAction.SELECT_WITHDRAWAL);
        System.out.println("Current state: " + atm.getCurrentStateType());
        atm.processAction(UserAction.WITHDRAW_AMOUNT, "200"); // Withdraw $200
        System.out.println("Current state: " + atm.getCurrentStateType());
        atm.processAction(UserAction.EJECT_CARD);
        System.out.println("Current state: " + atm.getCurrentStateType());
        atm.processAction(UserAction.SHUTDOWN);
        System.out.println("Current state: " + atm.getCurrentStateType());
    }
}