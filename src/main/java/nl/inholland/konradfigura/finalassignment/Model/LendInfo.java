package nl.inholland.konradfigura.finalassignment.Model;

import java.io.Serializable;
import java.time.LocalDate;

public record LendInfo(User lender, LocalDate date) implements Serializable {  }
