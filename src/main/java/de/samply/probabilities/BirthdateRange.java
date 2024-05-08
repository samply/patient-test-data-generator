package de.samply.probabilities;

import java.time.LocalDate;
import java.util.Random;

public class BirthdateRange {
  private LocalDate startDate;
  private LocalDate endDate;

  public BirthdateRange (int RandomAge){
    LocalDate startDate = LocalDate.of(LocalDate.now().getYear()-RandomAge-1,LocalDate.now().getMonth(),LocalDate.now().getDayOfMonth()+1);
    LocalDate endDate = LocalDate.of(LocalDate.now().getYear()-RandomAge,LocalDate.now().getMonth(),LocalDate.now().getDayOfMonth());
  }
  public LocalDate generateBirthdateInRange(){
    long startEpochDay = startDate.toEpochDay();
    long endEpochDay = endDate.toEpochDay();
    long randomDate = startEpochDay + new Random().nextInt((int) (endEpochDay - startEpochDay));
    return LocalDate.ofEpochDay(randomDate);
  }
}
