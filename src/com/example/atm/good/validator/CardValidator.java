package com.example.atm.good.validator;

/**
 * Interface for card validation
 */
public interface CardValidator {
    boolean validateCard(String cardNumber);
}