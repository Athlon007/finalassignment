package nl.inholland.konradfigura.finalassignment.model;

import java.io.Serializable;
import java.time.LocalDate;

public class LendInfo implements Serializable {

    public LendInfo(Member lender, LocalDate date) {
        this.lender = lender;
        this.date = date;
    }

    public Member getLender() {
        return lender;
    }

    private Member lender;

    public LocalDate getDate() {
        return date;
    }

    private LocalDate date;

    private boolean isPaidOverdue;

    public boolean isPaidOverdue() {
        return isPaidOverdue;
    }

    public void setIsPaidOverdue(boolean value) {
        isPaidOverdue = value;
    }
}
