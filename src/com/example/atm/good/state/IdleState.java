package com.example.atm.good.state;

import com.example.atm.good.context.ATMContext;
import com.example.atm.good.user.UserAction;

/**
 * Idle State
 */
public class IdleState implements ATMState {

    @Override
    public void handleAction(ATMContext context, UserAction action, String... params) {
        switch (action) {

            case INSERT_CARD:
                if (params.length == 0) {
                    throw new IllegalArgumentException("Missing parameter: card number.");
                }
                doInsertCard(context, params[0]);
                break;

            case SHUTDOWN:
                doShutdown(context);
                break;

            default:
                throw new IllegalArgumentException("Invalid action. Please insert card.");
        }
    }

    /**
     * Shuts down the ATM.
     *
     * @param context of the ATM
     */
    private void doShutdown(ATMContext context) {
        context.setState(new OutOfServiceState());
        System.out.println("ATM is now out of service.");
    }

    /**
     * Validates the card number.
     *
     * @param context of the ATM
     * @param cardNumber to validate
     */
    private void doInsertCard(ATMContext context, String cardNumber) {
        System.out.println("User inserts card with card number: " + cardNumber);
        if (context.getCardValidator().validateCard(cardNumber)) {
            context.setCardValid(true);
            context.setState(new CardInsertedState());
            System.out.println("Valid card inserted successfully. Please enter PIN.");
        } else {
            throw new IllegalArgumentException("Invalid card. Please try again.");
        }
    }
}
