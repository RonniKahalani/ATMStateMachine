package com.example.atm.sample2.state;

import com.example.atm.sample2.context.ATMContext;
import com.example.atm.sample2.user.UserAction;

/**
 * Transaction in Progress State
 */
public class TransactionInProgressState implements ATMState {

    @Override
    public void handleAction(ATMContext context, UserAction action, String... params) {
        switch (action) {

            case WITHDRAW_AMOUNT:
                if (params.length == 0) {
                    throw new IllegalArgumentException("Missing parameter: amount.");
                }

                double amount;
                try {
                    amount = Double.parseDouble(params[0]);
                    doWithdrawAmount(context, amount);

                } catch (NumberFormatException e) {
                    context.setState(new PinVerifiedState());
                    throw new IllegalArgumentException("Invalid amount entered.");
                }
                break;

            case EJECT_CARD:
                doEjectCard(context);
                break;
            default:
                throw new IllegalArgumentException("Invalid action. Enter amount or eject card.");
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

    /**
     * Withdraws the amount from the balance.
     *
     * @param context of the ATM
     * @param amount  amount to be withdrawn
     */
    private void doWithdrawAmount(ATMContext context, double amount) {
        if (context.getTransactionProcessor().performWithdrawal(context, amount)) {
            System.out.println("Withdrawal successful: $" + amount);
            System.out.println("Remaining balance: $" + context.getAvailableBalance());
            context.setState(new PinVerifiedState());
        } else {
            throw new IllegalArgumentException("Withdrawal failed: Insufficient funds or ATM cash.");
        }
    }
}
