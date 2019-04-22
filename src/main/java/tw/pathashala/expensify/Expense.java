package tw.pathashala.expensify;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

//Represents the cost of an event
class Expense {
    static private Double defaultRatio = 1.0;

    void Settle(List<Bill> bills) {
        int perFriendAmount;
        int friendsWithBillSettled;
        int totalFriends;
        Double ratio;
        Friend friendBillNotSettled;

        for (Bill bill : bills) {
            totalFriends = bill.paidFor().size();
            friendsWithBillSettled = 0;

            while (friendsWithBillSettled < totalFriends) {
                friendBillNotSettled = bill.paidFor().get(friendsWithBillSettled);
                ratio = this.getExpenseRatioForFriend(bill, friendBillNotSettled);
                perFriendAmount = (int) (ratio * bill.getAmount());
                if (Double.compare(ratio, defaultRatio) == 0) {
                    perFriendAmount /= totalFriends;
                }
                settleIndividualBill(bill, perFriendAmount, friendsWithBillSettled);
                friendsWithBillSettled++;
            }
        }
    }

    private Double getExpenseRatioForFriend(Bill bill, Friend friend) {
        Map<Friend, Double> friendExpenseRatioMapper = bill.getExpenseRatio();
        Double ratio = defaultRatio;
        if (friendExpenseRatioMapper.containsKey(friend)) {
            ratio = friendExpenseRatioMapper.get(friend);
        }
        return ratio;
    }


    void findWhoHasToPayWhom(List<Friend> group) {
        int i = 0, j = 0;
        List<Friend> friendsWhoWillPay = new LinkedList<>();
        List<Friend> friendsWhoWillReceive = new LinkedList<>();

        bifurcateAndSortPayersAndReceivers(friendsWhoWillPay, friendsWhoWillReceive, group);

        int lastReceiver = friendsWhoWillReceive.size() - 1;
        Friend lastReceivingFriend = friendsWhoWillReceive.get(lastReceiver);

        while (lastReceivingFriend.getWalletAmount() != 0) {
            Friend payingFriend = friendsWhoWillPay.get(i);
            Friend receivingFriend = friendsWhoWillReceive.get(j);

            if (Math.abs(payingFriend.getWalletAmount()) > receivingFriend.getWalletAmount()) {
                payWhenPayerDebtIsMoreThanReceiverCredit(friendsWhoWillPay, friendsWhoWillReceive, i, j);
                j++;
            } else {
                payWhenPayerDebtIsLessThanReceiverCredit(friendsWhoWillPay, friendsWhoWillReceive, i, j);
                i++;
                if (receivingFriend.getWalletAmount() == 0) {
                    j++;
                }
            }
        }
    }

    private void settleIndividualBill(Bill bill, int perHeadAmount, int friendsWithBillSettled) {
        List<Friend> friends = bill.paidFor();
        Friend friend = friends.get(friendsWithBillSettled);
        friend.setWalletAmount(friend.getWalletAmount() - perHeadAmount);
    }

    private void bifurcateAndSortPayersAndReceivers(List<Friend> FriendsWhoWillPay, List<Friend> FriendsWhoWillReceive, List<Friend> group) {
        for (Friend friend : group) {
            if (friend.getWalletAmount() > 0) {
                FriendsWhoWillReceive.add(friend);
            } else {
                FriendsWhoWillPay.add(friend);
            }
        }

        Collections.sort(FriendsWhoWillPay, new Friend());
        Collections.sort(FriendsWhoWillReceive, new Friend());
    }

    private void payWhenPayerDebtIsMoreThanReceiverCredit(List<Friend> friendsWhoWillPay, List<Friend> friendsWhoWillReceive, int i, int j) {
        Friend payingFriend = friendsWhoWillPay.get(i);
        Friend receivingFriend = friendsWhoWillReceive.get(j);
        payingFriend.setWalletAmount(payingFriend.getWalletAmount() + receivingFriend.getWalletAmount());
        Amount amount = new Amount(receivingFriend.getWalletAmount(), receivingFriend.getName());
        payingFriend.amountsToPay().add(amount);
        receivingFriend.setWalletAmount(0);
    }

    private void payWhenPayerDebtIsLessThanReceiverCredit(List<Friend> friendsWhoWillPay, List<Friend> friendsWhoWillReceive, int i, int j) {
        Friend payingFriend = friendsWhoWillPay.get(i);
        Friend receivingFriend = friendsWhoWillReceive.get(j);
        receivingFriend.setWalletAmount(receivingFriend.getWalletAmount() + payingFriend.getWalletAmount());
        Amount amount = new Amount(Math.abs(payingFriend.getWalletAmount()), receivingFriend.getName());
        payingFriend.amountsToPay().add(amount);
        payingFriend.setWalletAmount(0);
    }
}