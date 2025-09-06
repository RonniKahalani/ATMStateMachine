package com.example.atm.bad;

/**
 * Enum to represent ATM states
 */
public enum ATMState {
    IDLE, // Waiting for card
    CARD_INSERTED, // Card inserted, awaiting PIN
    PIN_VERIFIED, // PIN verified, ready for transaction
    TRANSACTION_IN_PROGRESS, // Processing transaction
    OUT_OF_SERVICE // com.example.atm.sample1.ATM is out of service
}