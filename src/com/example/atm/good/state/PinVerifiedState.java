package com.example.atm.good.state;

import com.example.atm.good.context.ATMContext;
import com.example.atm.good.user.UserAction;

/**
 * PIN Verified State
 */
public class PinVerifiedState implements ATMState {

    @Override
    public void handleAction(ATMContext context, UserAction action, String... params) {
        switch (action) {

            case SELECT_WITHDRAWAL:
                doSelectWithdrawal(context);
                break;

            case EJECT_CARD:
                doEjectCard(context);
                break;

            default:
                throw new IllegalArgumentException("Invalid action. Select withdrawal or eject card.");
        }
    }

    /**
     * Ejects the card.
     *
     * @param context of the ATM
     */
    private void doEjectCard(ATMContext context) {
        context.setCardValid(false);
        context.setPinCorrect(false);
        context.setState(new IdleState());
        System.out.println("Card ejected. ATM is idle.");
    }

    private void doSelectWithdrawal(ATMContext context) {
        context.setState(new TransactionInProgressState());
        System.out.println("Withdrawal selected. Enter amount.");
    }
}