package com.example.atm.sample2.state;

import com.example.atm.sample2.context.ATMContext;
import com.example.atm.sample2.user.UserAction;

/**
 * Card Inserted State
 */
public class CardInsertedState implements ATMState {

    @Override
    public void handleAction(ATMContext context, UserAction action, String... params) {
        switch (action) {

            case ENTER_PIN:
                if (params.length == 0) {
                    throw new IllegalArgumentException("Missing parameter: pin.");
                }
                doEnterPin(context, params[0]);
                break;

            case EJECT_CARD:
                doEjectCard(context);
                break;

            default:
                throw new IllegalArgumentException("Invalid action. Please enter PIN or eject card.");
        }
    }

    /**
     * Executes the ENTER_PIN activity.
     *
     * @param context of the ATM
     * @param pin     for the card
     */
    private void doEnterPin(ATMContext context, String pin) {
        if (pin != null && !pin.isEmpty() && context.getPinValidator().validatePin(pin)) {
            context.setPinCorrect(true);
            context.setState(new PinVerifiedState());
            System.out.println("PIN verified. Select transaction.");
        } else {
            throw new IllegalArgumentException("Invalid PIN. Try again or eject card.");
        }
    }

    /**
     * Executes the EJECT_CARD activity.
     *
     * @param context of the ATM
     */
    private void doEjectCard(ATMContext context) {
        context.setCardValid(false);
        context.setState(new IdleState());
        System.out.println("Card ejected. ATM is idle.");
    }
}