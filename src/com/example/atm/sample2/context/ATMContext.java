package com.example.atm.sample2.context;

import com.example.atm.sample2.transaction.TransactionProcessor;
import com.example.atm.sample2.user.UserAction;
import com.example.atm.sample2.state.*;
import com.example.atm.sample2.validator.CardValidator;
import com.example.atm.sample2.validator.PinValidator;

public class ATMContext {
    private ATMState currentState;
    private double availableBalance;
    private double atmCash;
    private boolean cardValid;
    private boolean pinCorrect;
    private final CardValidator cardValidator;
    private final PinValidator pinValidator;
    private final TransactionProcessor transactionProcessor;

    // Constructor with dependency injection
    public ATMContext(CardValidator cardValidator, PinValidator pinValidator, TransactionProcessor transactionProcessor, double atmCash) {
        this.cardValidator = cardValidator;
        this.pinValidator = pinValidator;
        this.transactionProcessor = transactionProcessor;
        this.currentState = new IdleState();
        this.atmCash = atmCash;
        this.availableBalance = 1000.0; // Simulated starting balance
        this.cardValid = false;
        this.pinCorrect = false;
    }

    // Process user action
    public void processAction(UserAction action, String... params) {
        currentState.handleAction(this, action, params);
    }

    // Getters and setters
    public void setState(ATMState state) {
        this.currentState = state;
    }

    public ATMStateType getCurrentStateType() {
        if (currentState instanceof IdleState) return ATMStateType.IDLE;
        if (currentState instanceof CardInsertedState) return ATMStateType.CARD_INSERTED;
        if (currentState instanceof PinVerifiedState) return ATMStateType.PIN_VERIFIED;
        if (currentState instanceof TransactionInProgressState) return ATMStateType.TRANSACTION_IN_PROGRESS;
        if (currentState instanceof OutOfServiceState) return ATMStateType.OUT_OF_SERVICE;
        return null;
    }

    public double getAvailableBalance() {
        return availableBalance;
    }

    public void setAvailableBalance(double balance) {
        this.availableBalance = balance;
    }

    public double getAtmCash() {
        return atmCash;
    }

    public void setAtmCash(double cash) {
        this.atmCash = cash;
    }

    public boolean isCardValid() {
        return cardValid;
    }

    public void setCardValid(boolean valid) {
        this.cardValid = valid;
    }

    public boolean isPinCorrect() {
        return pinCorrect;
    }

    public void setPinCorrect(boolean correct) {
        this.pinCorrect = correct;
    }

    public CardValidator getCardValidator() {
        return cardValidator;
    }

    public PinValidator getPinValidator() {
        return pinValidator;
    }

    public TransactionProcessor getTransactionProcessor() {
        return transactionProcessor;
    }
}