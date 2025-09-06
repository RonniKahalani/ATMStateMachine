package com.example.atm.good.validator;

/**
 * Card Validator Implementation
 */
public class CardValidatorImpl implements CardValidator {

    @Override
    public boolean validateCard(String cardNumber) {
        // Simplified validation (e.g., card number length check)
        return cardNumber != null && cardNumber.length() == 16;
    }
}