package de.samply.generator;

import de.samply.model.DatumTagOderMonatOderJahrOderNichtGenauTyp;
import de.samply.probabilities.BirthdateRange;
import de.samply.probabilities.ProbabilityType;
import de.samply.random.RandomValuesGenerator;
import de.samply.model.OBDS.MengePatient.Patient;
import de.samply.model.PatientenStammdatenMelderTyp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class PatientGenerator {

  private final RandomValuesGenerator randomValuesGenerator;

  public PatientGenerator(RandomValuesGenerator randomValuesGenerator) {
    this.randomValuesGenerator = randomValuesGenerator;
  }


  public Patient createPatient() {
    //TODO
    Patient patient = new Patient();
    patient.setPatientenStammdaten(createPatientStammdaten());

    return patient;
  }

  private PatientenStammdatenMelderTyp createPatientStammdaten() {
    //TODO
    PatientenStammdatenMelderTyp patientenStammdaten = new PatientenStammdatenMelderTyp();
    patientenStammdaten.setGeschlecht(generateGeschlecht());
    patientenStammdaten.setGeburtsdatum(generateGeburtsdatum());
    return patientenStammdaten;
  }

  private String generateGeschlecht() {
    return randomValuesGenerator.generate(ProbabilityType.GENDER);
  }
  private int generateAlter(){
    return Integer.parseInt(randomValuesGenerator.generate(ProbabilityType.AGE));
  }
  private String generateGeburtsdatum(){
    BirthdateRange randomBirthdate = new BirthdateRange(this.generateAlter());
    return  "" + randomBirthdate.generateBirthdateInRange();
  }


}