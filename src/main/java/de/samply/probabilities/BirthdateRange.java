package de.samply.probabilities;

import java.time.LocalDate;
import java.util.Random;

public class BirthdateRange {
  private final LocalDate startDate;
  private final LocalDate endDate;

  public BirthdateRange (int randomAge){
    startDate = LocalDate.of(LocalDate.now().getYear()-randomAge-1,LocalDate.now().getMonth(),LocalDate.now().getDayOfMonth());
    endDate = LocalDate.of(LocalDate.now().getYear()-randomAge,LocalDate.now().getMonth(),LocalDate.now().getDayOfMonth());
  }
  public LocalDate generateBirthdateInRange(){
    long startEpochDay = startDate.toEpochDay();
    long endEpochDay = endDate.toEpochDay();
    long randomDate = startEpochDay + new Random().nextInt((int) (endEpochDay - startEpochDay));
    return LocalDate.ofEpochDay(randomDate);
  }
}
