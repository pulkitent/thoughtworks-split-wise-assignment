package tw.pathashala.expensify;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

//Represents a real world person
public class Friend implements Comparator<Friend> {
    private String name;
    private int walletAmount;
    private List<Amount> amountsToPay;

    Friend() {
        //Default as to create a object with default values for Comparator
    }

    Friend(String name, int amountPaid) {
        this.name = name;
        this.walletAmount = amountPaid;
        this.amountsToPay = new LinkedList<>();
    }

    void paidAgain(int amountPaid) {
        this.walletAmount += amountPaid;
    }

    List<Amount> amountsToPay() {
        return this.amountsToPay;
    }

    int getWalletAmount() {
        return this.walletAmount;
    }

    void setWalletAmount(int amount) {
        this.walletAmount = amount;
    }

    String getName() {
        return this.name;
    }

    @Override
    public int compare(Friend friend, Friend otherFriend) {
        if (friend.walletAmount >= otherFriend.walletAmount) {
            return 1;
        }
        return -1;
    }
}