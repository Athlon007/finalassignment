package nl.inholland.konradfigura.finalassignment.model;

public record OverdueResponse(boolean isOverdue, long overdueDays, double fine) {
}
