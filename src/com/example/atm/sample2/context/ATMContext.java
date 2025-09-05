package com.example.atm.sample2.context;

import com.example.atm.sample2.transaction.TransactionProcessor;
import com.example.atm.sample2.user.UserAction;
import com.example.atm.sample2.state.*;
import com.example.atm.sample2.validator.CardValidator;
import com.example.atm.sample2.validator.PinValidator;

import java.util.Map;

/**
 *
 */
public class ATMContext {

    /**
     * The current state of the ATM.
     */
    private ATMState currentState;

    /**
     * ATM data properties.
     */
    private double availableBalance;
    private double atmCash;
    private boolean cardValid;
    private boolean pinCorrect;

    /**
     * Validator services. Very useful unit-testing wise as nice small single purpose units/classes.
     */
    private final CardValidator cardValidator;
    private final PinValidator pinValidator;
    private final TransactionProcessor transactionProcessor;

    /**
     * A map of all the supported (possible) states
     */
    private final Map<Class<? extends ATMState>, ATMStateType> stateMap = Map.of(
            IdleState.class, ATMStateType.IDLE,
            CardInsertedState.class, ATMStateType.CARD_INSERTED,
            PinVerifiedState.class, ATMStateType.PIN_VERIFIED,
            TransactionInProgressState.class, ATMStateType.TRANSACTION_IN_PROGRESS,
            OutOfServiceState.class, ATMStateType.OUT_OF_SERVICE
    );

    /**
     * Constructor
     *
     * @param cardValidator        validates cards
     * @param pinValidator         validates PIN codes
     * @param transactionProcessor handles transactions
     * @param atmCash              is the amount of cash/money the ATM has left.
     */
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

    /**
     * Processes user actions
     *
     * @param action from the user
     * @param params fraom the user
     */
    public void processAction(UserAction action, String... params) {
        currentState.handleAction(this, action, params);
    }

    /**
     * Sets state.
     *
     * @param state to set
     */
    public void setState(ATMState state) {
        this.currentState = state;
    }

    /**
     * Returns the type of the current state.
     *
     * @return the type of the current state
     */
    public ATMStateType getCurrentStateType() {
        return stateMap.getOrDefault(currentState.getClass(), null);
    }

    /**
     * Returns the available balance.
     *
     * @return available balance
     */
    public double getAvailableBalance() {
        return availableBalance;
    }

    /**
     * Set the available balance.
     *
     * @param balance to set
     */
    public void setAvailableBalance(double balance) {
        this.availableBalance = balance;
    }

    /**
     * Returns the ATM's remaining cash amount.
     *
     * @return cash left in the ATM
     */
    public double getAtmCash() {
        return atmCash;
    }

    /**
     * Sets the ATM cash amount.
     *
     * @param cash to set
     */
    public void setAtmCash(double cash) {
        this.atmCash = cash;
    }

    /**
     * Returns the validity of the card.
     *
     * @return true if card is valid
     */
    public boolean isCardValid() {
        return cardValid;
    }

    /**
     * Set card validity.
     *
     * @param valid is card has valid card number.
     */
    public void setCardValid(boolean valid) {
        this.cardValid = valid;
    }

    /**
     * Returns the correctness of the PIN.
     *
     * @return tru if PIN is correct
     */
    public boolean isPinCorrect() {
        return pinCorrect;
    }

    /**
     * Sets the correctness of the PIN code.
     *
     * @param correct if value is true
     */
    public void setPinCorrect(boolean correct) {
        this.pinCorrect = correct;
    }

    /**
     * Returns the card validator.
     *
     * @return card validator
     */
    public CardValidator getCardValidator() {
        return cardValidator;
    }

    /**
     * Returns PIN validator.
     *
     * @return PIN validator
     */
    public PinValidator getPinValidator() {
        return pinValidator;
    }

    /**
     * Returns the transaction processor.
     *
     * @return transaction processor
     */
    public TransactionProcessor getTransactionProcessor() {
        return transactionProcessor;
    }
}