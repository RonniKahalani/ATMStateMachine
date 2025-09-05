package com.example.atm.sample2.validator;

/**
 * PIN Validator Implementation
 */
public class PinValidatorImpl implements PinValidator {

    @Override
    public boolean validatePin(String pin) {
        // Simplified PIN check (e.g., hardcoded PIN "1234")
        return pin != null && pin.equals("1234");
    }
}