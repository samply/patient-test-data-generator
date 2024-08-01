package de.samply.generator;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import de.samply.model.DatumTagOderMonatOderJahrOderNichtGenauTyp;
import de.samply.model.OBDS;
import de.samply.model.OBDS.MengeMelder;
import de.samply.model.OBDS.MengePatient.Patient;
import de.samply.model.OBDS.MengePatient.Patient.MengeMeldung;
import de.samply.model.PatientenStammdatenMelderTyp;
import de.samply.probabilities.BirthdateRange;
import de.samply.probabilities.ProbabilityType;
import de.samply.random.RandomValuesGenerator;
import java.time.ZoneId;
import java.util.GregorianCalendar;
import java.util.Random;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class PatientGenerator {
  private int count = 1;
  private final RandomValuesGenerator randomValuesGenerator;
  private final DatatypeFactory datatypeFactory = DatatypeFactory.newInstance();
  private final MeldungGenerator meldungGenerator;
  private final int numberOfMeldung = 5;

  public PatientGenerator(RandomValuesGenerator randomValuesGenerator)
      throws DatatypeConfigurationException {
    this.randomValuesGenerator = randomValuesGenerator;
    this.meldungGenerator = new MeldungGenerator();
  }


  public Patient createPatient() {
    //TODO

    Patient patient = new Patient();
    patient.setPatientID("testpatient" + this.count++);
    patient.setMengeMeldung(createMengeMeldung());
    patient.setPatientenStammdaten(createPatientStammdaten());
    return patient;
  }

  private PatientenStammdatenMelderTyp createPatientStammdaten() {
    //TODO
    PatientenStammdatenMelderTyp patientenStammdaten = new PatientenStammdatenMelderTyp();

    patientenStammdaten.setGeschlecht(generateGeschlecht());
    patientenStammdaten.setGeburtsdatum(generateGeburtsdatum());
    if(patientenStammdaten.getGeschlecht().equals("S")||patientenStammdaten.getGeschlecht().equals("U")){
      Random zufallsgeschlecht = new Random();
      if(zufallsgeschlecht.nextBoolean()){
        patientenStammdaten.setVornamen(generateWeiblichVorname());
      } else {
        patientenStammdaten.setVornamen(generateMaennlichVorname());
      }
    } else {
       if(patientenStammdaten.getGeschlecht().equals("W")){
        patientenStammdaten.setVornamen(generateWeiblichVorname());
      } else {
        patientenStammdaten.setVornamen(generateMaennlichVorname());
      }
    }
    patientenStammdaten.setNachname(generateNachname());
    return patientenStammdaten;
  }

  private String generateGeschlecht() {
    return randomValuesGenerator.generate(ProbabilityType.GENDER);
  }

  private int generateAlter() {
    return Integer.valueOf(randomValuesGenerator.generate(ProbabilityType.AGE));
  }

  private DatumTagOderMonatOderJahrOderNichtGenauTyp generateGeburtsdatum() {
    BirthdateRange randomBirthdate = new BirthdateRange(this.generateAlter());
    XMLGregorianCalendar xmlGregorianCalendar = datatypeFactory.newXMLGregorianCalendar(
        GregorianCalendar.from(
            randomBirthdate.generateBirthdateInRange().atStartOfDay(ZoneId.systemDefault())));
    DatumTagOderMonatOderJahrOderNichtGenauTyp result = new DatumTagOderMonatOderJahrOderNichtGenauTyp();
    result.setValue(xmlGregorianCalendar);
    return result;
  }
  private String generateWeiblichVorname(){
    return randomValuesGenerator.generate(ProbabilityType.FIRST_NAME_FEMALE);
  }
  private String generateMaennlichVorname(){
    return randomValuesGenerator.generate(ProbabilityType.FIRST_NAME_MALE);
  }
  private String generateNachname(){
    return randomValuesGenerator.generate(ProbabilityType.LAST_NAME);
  }

  private MengeMeldung createMengeMeldung(){
    MengeMeldung mengeMeldung = new MengeMeldung();
    for (int i = 0; i <= this.numberOfMeldung; i++){
      mengeMeldung.getMeldung().add(this.meldungGenerator.createMeldung());
    }
    return mengeMeldung;
  }
}