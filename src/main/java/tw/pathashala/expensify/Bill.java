package tw.pathashala.expensify;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

//Represents a receipt of expense
class Bill {
    private int amount;
    private List<Friend> paidFor;
    private Map<Friend, Double> expenseRatio;

    Bill(int amount, List<Friend> paidFor) {
        this.amount = amount;
        this.paidFor = paidFor;
        this.expenseRatio = new HashMap<>();
    }

    int getAmount() {
        return this.amount;
    }

    List<Friend> paidFor() {
        return this.paidFor;
    }

    Map<Friend, Double> getExpenseRatio(){
        return this.expenseRatio;
    }

    void setExpenseRatio(Friend friend, double ratio){
        this.expenseRatio.put(friend,ratio);
    }
}