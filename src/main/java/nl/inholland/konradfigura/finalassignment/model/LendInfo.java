package nl.inholland.konradfigura.finalassignment.model;

import java.io.Serializable;
import java.time.LocalDate;

public record LendInfo(Member lender, LocalDate date) implements Serializable {  }
