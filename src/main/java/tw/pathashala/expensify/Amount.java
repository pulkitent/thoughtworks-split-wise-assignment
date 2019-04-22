package tw.pathashala.expensify;

import java.util.Objects;

//Represents money to be paid and to whom
public class Amount {
    private int value;
    private String paidTo;

    Amount(int value, String paidTo) {
        this.value = value;
        this.paidTo = paidTo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Amount amount = (Amount) o;
        return value == amount.value &&
                paidTo.equals(amount.paidTo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, paidTo);
    }
}