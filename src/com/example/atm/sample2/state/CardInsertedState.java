package com.example.atm.sample2.state;

import com.example.atm.sample2.context.ATMContext;
import com.example.atm.sample2.user.UserAction;

// Card Inserted State
public class CardInsertedState implements ATMState {
    @Override
    public void handleAction(ATMContext context, UserAction action, String... params) {
        switch (action) {
            case ENTER_PIN:
                if (params.length > 0 && context.getPinValidator().validatePin(params[0])) {
                    context.setPinCorrect(true);
                    context.setState(new PinVerifiedState());
                    System.out.println("PIN verified. Select transaction.");
                } else {
                    System.out.println("Invalid PIN. Try again or eject card.");
                }
                break;
            case EJECT_CARD:
                context.setCardValid(false);
                context.setState(new IdleState());
                System.out.println("Card ejected. ATM is idle.");
                break;
            default:
                System.out.println("Invalid action. Please enter PIN or eject card.");
        }
    }
}