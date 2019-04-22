package tw.pathashala.expensify;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

class ExpenseTest {

    void arrangeTestInputDataForFourFriends(List<Friend> friendGroup, List<Friend> firstFriendGroupBillAmountWasPaidFor, List<Friend> secondFriendGroupBillAmountWasPaidFor, List<Friend> thirdFriendGroupBillAmountWasPaidFor, List<Bill> bills) {

        Friend firstFriend = new Friend("A", 100);
        Friend secondFriend = new Friend("B", 500);
        Friend thirdFriend = new Friend("C", 0);
        Friend fourthFriend = new Friend("D", 300);

        friendGroup.add(firstFriend);
        friendGroup.add(secondFriend);
        friendGroup.add(thirdFriend);
        friendGroup.add(fourthFriend);

        firstFriendGroupBillAmountWasPaidFor.add(firstFriend);
        firstFriendGroupBillAmountWasPaidFor.add(secondFriend);
        firstFriendGroupBillAmountWasPaidFor.add(thirdFriend);
        firstFriendGroupBillAmountWasPaidFor.add(fourthFriend);

        secondFriendGroupBillAmountWasPaidFor.add(thirdFriend);
        secondFriendGroupBillAmountWasPaidFor.add(fourthFriend);

        thirdFriendGroupBillAmountWasPaidFor.add(firstFriend);
        thirdFriendGroupBillAmountWasPaidFor.add(secondFriend);

        Bill bill1 = new Bill(100, firstFriendGroupBillAmountWasPaidFor);
        Bill bill2 = new Bill(500, secondFriendGroupBillAmountWasPaidFor);
        Bill bill3 = new Bill(300, thirdFriendGroupBillAmountWasPaidFor);

        bills.add(bill1);
        bills.add(bill2);
        bills.add(bill3);
    }

    void arrangeTestOutputDataForFourFriends(List<Integer> settledAmountOutputList) {
        settledAmountOutputList.add(-75);
        settledAmountOutputList.add(325);
        settledAmountOutputList.add(-275);
        settledAmountOutputList.add(25);
    }

    @Test
    @DisplayName("Test settlement of total expense for a group of 4 friends with 3 bills to settle")
    void settleExpenseForFourFriends() {
        List<Bill> bills = new LinkedList<>();
        List<Friend> friendGroup = new LinkedList<>();
        List<Integer> settledAmountOutputList = new LinkedList<>();
        List<Friend> firstFriendGroupBillAmountWasPaidFor = new LinkedList<>();
        List<Friend> secondFriendGroupBillAmountWasPaidFor = new LinkedList<>();
        List<Friend> thirdFriendGroupBillAmountWasPaidFor = new LinkedList<>();
        arrangeTestOutputDataForFourFriends(settledAmountOutputList);
        arrangeTestInputDataForFourFriends(friendGroup, firstFriendGroupBillAmountWasPaidFor, secondFriendGroupBillAmountWasPaidFor, thirdFriendGroupBillAmountWasPaidFor, bills);
        Expense expense = new Expense();

        expense.Settle(bills);

        for (int i = 0; i < friendGroup.size(); i++) {
            Assertions.assertEquals(settledAmountOutputList.get(i), friendGroup.get(i).getWalletAmount());
        }
    }

    @Test
    @DisplayName("Test settlement of total expense for a group of 2 friends with only 1 bill to settle")
    void settleExpenseForTwoFriendsOnly() {
        List<Bill> bills = new LinkedList<>();
        List<Friend> friendGroup = new LinkedList<>();
        List<Integer> settledAmountOutputList = new LinkedList<>();
        List<Friend> firstFriendGroupBillAmountWasPaidFor = new LinkedList<>();
        settledAmountOutputList.add(50);
        settledAmountOutputList.add(-50);
        Friend firstFriend = new Friend("A", 100);
        Friend secondFriend = new Friend("B", 0);
        friendGroup.add(firstFriend);
        friendGroup.add(secondFriend);
        firstFriendGroupBillAmountWasPaidFor.add(firstFriend);
        firstFriendGroupBillAmountWasPaidFor.add(secondFriend);
        Bill bill1 = new Bill(100, firstFriendGroupBillAmountWasPaidFor);
        bills.add(bill1);
        Expense expense = new Expense();

        expense.Settle(bills);

        for (int i = 0; i < friendGroup.size(); i++) {
            Assertions.assertEquals(settledAmountOutputList.get(i), friendGroup.get(i).getWalletAmount());
        }
    }

    @Test
    @DisplayName("Test settlement of total expense for a single friend who paid single bill for only himself")
    void settleExpenseForFriendWhoPaidForHimselfOnly() {
        int settledAmountOutputList = 0;
        List<Bill> bills = new LinkedList<>();
        List<Friend> friendGroup = new LinkedList<>();
        List<Friend> friendGroupBillAmountWasPaidFor = new LinkedList<>();
        Friend firstFriend = new Friend("A", 100);
        friendGroup.add(firstFriend);
        friendGroupBillAmountWasPaidFor.add(firstFriend);
        Bill bill = new Bill(100, friendGroupBillAmountWasPaidFor);
        bills.add(bill);
        Expense expense = new Expense();

        expense.Settle(bills);

        Assertions.assertEquals(settledAmountOutputList, friendGroup.get(0).getWalletAmount());
    }

    @Test
    @DisplayName("Test settlement of total expense for a group of 3 friends with 1 bill only involving only 2 friends")
    void settleExpenseForFriendWhoPaidForOtherTwoFriendsExceptHimself() {
        List<Bill> bills = new LinkedList<>();
        List<Friend> friendGroup = new LinkedList<>();
        List<Integer> settledAmountOutputList = new LinkedList<>();
        List<Friend> friendGroupBillAmountWasPaidFor = new LinkedList<>();
        Friend firstFriend = new Friend("A", 50);
        Friend secondFriend = new Friend("B", 0);
        Friend thirdFriend = new Friend("C", 0);
        settledAmountOutputList.add(50);
        settledAmountOutputList.add(-25);
        settledAmountOutputList.add(-25);
        friendGroup.add(firstFriend);
        friendGroup.add(secondFriend);
        friendGroup.add(thirdFriend);
        friendGroupBillAmountWasPaidFor.add(secondFriend);
        friendGroupBillAmountWasPaidFor.add(thirdFriend);
        Bill bill = new Bill(50, friendGroupBillAmountWasPaidFor);
        bills.add(bill);
        Expense expense = new Expense();

        expense.Settle(bills);

        for (int i = 0; i < friendGroup.size(); i++) {
            Assertions.assertEquals(settledAmountOutputList.get(i), friendGroup.get(i).getWalletAmount());
        }
    }

    @Test
    @DisplayName("Test settlement of total expense for a group of 2 friends with 2 bills")
    void settleExpenseForTwoFriendsWhenTwoBillsPaidBySameFriend() {
        List<Bill> bills = new LinkedList<>();
        List<Friend> friendGroup = new LinkedList<>();
        List<Friend> firstFriendGroupBillAmountWasPaidFor = new LinkedList<>();
        List<Friend> secondFriendGroupBillAmountWasPaidFor = new LinkedList<>();
        Friend firstFriend = new Friend("A", 100);
        firstFriend.paidAgain(100);
        Friend secondFriend = new Friend("B", 0);
        friendGroup.add(firstFriend);
        friendGroup.add(secondFriend);
        firstFriendGroupBillAmountWasPaidFor.add(firstFriend);
        firstFriendGroupBillAmountWasPaidFor.add(secondFriend);
        secondFriendGroupBillAmountWasPaidFor.add(secondFriend);
        Bill bill1 = new Bill(100, firstFriendGroupBillAmountWasPaidFor);
        Bill bill2 = new Bill(100, secondFriendGroupBillAmountWasPaidFor);
        bills.add(bill1);
        bills.add(bill2);
        Expense expense = new Expense();
        List<Integer> settledAmountOutputList = new LinkedList<>();
        settledAmountOutputList.add(150);
        settledAmountOutputList.add(-150);

        expense.Settle(bills);

        for (int i = 0; i < friendGroup.size(); i++) {
            Assertions.assertEquals(settledAmountOutputList.get(i), friendGroup.get(i).getWalletAmount());
        }
    }

    @Test
    @DisplayName("Test settlement of total expense and finds out of group of 4 friends with 3 bills which friend has to pay whom")
    void whoHasToPayWhomForFourFriends() {
        List<Bill> bills = new LinkedList<>();
        List<Friend> friendGroup = new LinkedList<>();
        List<Amount> expectedOutputList = new LinkedList<>();
        List<Friend> firstFriendGroupBillAmountWasPaidFor = new LinkedList<>();
        List<Friend> secondFriendGroupBillAmountWasPaidFor = new LinkedList<>();
        List<Friend> thirdFriendGroupBillAmountWasPaidFor = new LinkedList<>();
        arrangeTestInputDataForFourFriends(friendGroup, firstFriendGroupBillAmountWasPaidFor, secondFriendGroupBillAmountWasPaidFor, thirdFriendGroupBillAmountWasPaidFor, bills);
        expectedOutputList.add(new Amount(75, "B"));
        expectedOutputList.add(new Amount(25, "D"));
        expectedOutputList.add(new Amount(250, "B"));
        Expense expense = new Expense();
        expense.Settle(bills);
        int i = 0, j = 0;

        expense.findWhoHasToPayWhom(friendGroup);

        while (i < friendGroup.size()) {
            int amountsAFriendHasToPay = friendGroup.get(i).amountsToPay().size();
            if (amountsAFriendHasToPay != 0) {
                int k = 0;
                while (k < amountsAFriendHasToPay) {
                    Assertions.assertEquals(expectedOutputList.get(j), friendGroup.get(i).amountsToPay().get(k));
                    k++;
                    j++;
                }
            }
            i++;
        }
    }

    @Test
    @DisplayName("Test settlement of total expense and finds out of group of 2 friends with only 1 bill who has to pay whom")
    void whoHasToPayWhomForTwoFriendsWithOnlyOneBill() {
        List<Bill> bills = new LinkedList<>();
        List<Friend> friendGroup = new LinkedList<>();
        List<Friend> firstFriendGroupBillAmountWasPaidFor = new LinkedList<>();
        Friend firstFriend = new Friend("A", 150);
        Friend secondFriend = new Friend("B", 0);
        friendGroup.add(firstFriend);
        friendGroup.add(secondFriend);
        firstFriendGroupBillAmountWasPaidFor.add(firstFriend);
        firstFriendGroupBillAmountWasPaidFor.add(secondFriend);
        Bill bill1 = new Bill(150, firstFriendGroupBillAmountWasPaidFor);
        bills.add(bill1);
        List<Amount> expectedOutputList = new LinkedList<>();
        expectedOutputList.add(new Amount(75, "A"));
        Expense expense = new Expense();
        expense.Settle(bills);
        int i = 0, j = 0;

        expense.findWhoHasToPayWhom(friendGroup);

        while (i < friendGroup.size()) {
            int amountsAFriendHasToPay = friendGroup.get(i).amountsToPay().size();
            if (amountsAFriendHasToPay != 0) {
                int k = 0;
                while (k < amountsAFriendHasToPay) {
                    Assertions.assertEquals(expectedOutputList.get(j), friendGroup.get(i).amountsToPay().get(k));
                    k++;
                    j++;
                }
            }
            i++;
        }
    }

    @Test
    @DisplayName("Test settlement of total expense and finds out whom to pay from group of 2 friends with 2 bills paid by same person")
    void whoHasToPayWhomForTwoFriendsWhenTwoBillsPaidBySameFriend() {
        List<Bill> bills = new LinkedList<>();
        List<Friend> friendGroup = new LinkedList<>();
        List<Friend> firstFriendGroupBillAmountWasPaidFor = new LinkedList<>();
        List<Friend> secondFriendGroupBillAmountWasPaidFor = new LinkedList<>();
        Friend firstFriend = new Friend("A", 100);
        firstFriend.paidAgain(100);
        Friend secondFriend = new Friend("B", 0);
        friendGroup.add(firstFriend);
        friendGroup.add(secondFriend);
        firstFriendGroupBillAmountWasPaidFor.add(firstFriend);
        firstFriendGroupBillAmountWasPaidFor.add(secondFriend);
        secondFriendGroupBillAmountWasPaidFor.add(secondFriend);
        Bill bill1 = new Bill(100, firstFriendGroupBillAmountWasPaidFor);
        Bill bill2 = new Bill(100, secondFriendGroupBillAmountWasPaidFor);
        bills.add(bill1);
        bills.add(bill2);
        Expense expense = new Expense();
        List<Amount> expectedOutputList = new LinkedList<>();
        expectedOutputList.add(new Amount(150, "A"));
        expense.Settle(bills);
        int i = 0, j = 0;

        expense.findWhoHasToPayWhom(friendGroup);

        while (i < friendGroup.size()) {
            int amountsAFriendHasToPay = friendGroup.get(i).amountsToPay().size();
            if (amountsAFriendHasToPay != 0) {
                int k = 0;
                while (k < amountsAFriendHasToPay) {
                    Assertions.assertEquals(expectedOutputList.get(j), friendGroup.get(i).amountsToPay().get(k));
                    k++;
                    j++;
                }
            }
            i++;
        }
    }

    @Test
    @DisplayName("Test settlement of total expense with a group of 2 two with 1 only bill and with bill ratio 75:25")
    void settleExpenseForGroupOfTwoFriendsWithOneBillAndDifferentExpenseRatio() {
        List<Bill> bills = new LinkedList<>();
        List<Friend> friendGroup = new LinkedList<>();
        List<Integer> settledAmountOutputList = new LinkedList<>();
        List<Friend> firstFriendGroupBillAmountWasPaidFor = new LinkedList<>();
        settledAmountOutputList.add(25);
        settledAmountOutputList.add(-25);
        Friend firstFriend = new Friend("A", 100);
        Friend secondFriend = new Friend("B", 0);
        friendGroup.add(firstFriend);
        friendGroup.add(secondFriend);
        firstFriendGroupBillAmountWasPaidFor.add(firstFriend);
        firstFriendGroupBillAmountWasPaidFor.add(secondFriend);
        Bill bill1 = new Bill(100, firstFriendGroupBillAmountWasPaidFor);
        bill1.setExpenseRatio(firstFriend, 0.75);
        bill1.setExpenseRatio(secondFriend, 0.25);
        bills.add(bill1);
        Expense expense = new Expense();

        expense.Settle(bills);

        for (int i = 0; i < friendGroup.size(); i++) {
            Assertions.assertEquals(settledAmountOutputList.get(i), friendGroup.get(i).getWalletAmount());
        }
    }
}