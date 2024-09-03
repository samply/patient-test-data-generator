package de.samply.generator;

import de.samply.model.AktivitaetsTyp;
import de.samply.model.STTyp.MengeBestrahlung.Bestrahlung;
import de.samply.model.STTyp.MengeBestrahlung.Bestrahlung.Applikationsart;
import de.samply.model.STTyp.MengeBestrahlung.Bestrahlung.Applikationsart.Kontakt;
import de.samply.model.STTyp.MengeBestrahlung.Bestrahlung.Applikationsart.Metabolisch;
import de.samply.model.STTyp.MengeBestrahlung.Bestrahlung.Applikationsart.Perkutan;
import de.samply.model.STTyp.MengeBestrahlung.Bestrahlung.Applikationsart.Sonstige;
import de.samply.model.StrahlendosisTyp;
import de.samply.model.ZielgebietTyp;
import de.samply.probabilities.ProbabilityType;
import de.samply.random.RandomValuesGenerator;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Random;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class BestrahlungGenerator {
  private final RandomValuesGenerator randomValuesGenerator;

  public BestrahlungGenerator(RandomValuesGenerator randomValuesGenerator) {
    this.randomValuesGenerator = randomValuesGenerator;
  }

  public Bestrahlung createBestrahlung(XMLGregorianCalendar birthdate){
    Bestrahlung bestrahlung = new Bestrahlung();
    if(birthdate.getYear()<2024){
      bestrahlung.setBeginn(generateBeginnDatum(birthdate));
      bestrahlung.setEnde(generateEndeDatum(bestrahlung.getBeginn()));
    }
    bestrahlung.setApplikationsart(generateApplikationsart());
    return bestrahlung;
  }

  private XMLGregorianCalendar generateBeginnDatum(XMLGregorianCalendar birthdate) {
    Random randomDays = new Random();
    Date currentDate = new Date();
    int daysToAdd = randomDays.nextInt(((currentDate.getYear() + 1900) - birthdate.getYear()) * 365);
    GregorianCalendar minGregorianCalendar = new GregorianCalendar();
    minGregorianCalendar.set(birthdate.getYear(), birthdate.getMonth(), birthdate.getDay());
    minGregorianCalendar.add(Calendar.DAY_OF_MONTH, daysToAdd);
    XMLGregorianCalendar beginn;
    try {
      beginn = DatatypeFactory.newInstance().newXMLGregorianCalendar(minGregorianCalendar);
    } catch (DatatypeConfigurationException e) {
      throw new RuntimeException(e);
    }
    return beginn;
  }
  private XMLGregorianCalendar generateEndeDatum(XMLGregorianCalendar beginnDatum){
    Random randomDays = new Random();
    int daysToAdd = randomDays.nextInt(30);
    XMLGregorianCalendar ende;
    GregorianCalendar minGregorianCalendar = new GregorianCalendar();
    minGregorianCalendar.set(beginnDatum.getYear(),beginnDatum.getMonth(),beginnDatum.getDay());
    minGregorianCalendar.add(Calendar.DAY_OF_MONTH,daysToAdd);
    try {
      ende = DatatypeFactory.newInstance().newXMLGregorianCalendar(minGregorianCalendar);
    } catch (DatatypeConfigurationException e) {
      throw new RuntimeException(e);
    }
    return ende;
  }
  private Applikationsart generateApplikationsart(){
    Applikationsart applikationsart = new Applikationsart();
    String applikation = this.randomValuesGenerator.generate(ProbabilityType.APPLIKATIONSART);

    if (applikation.equals("P")){
      applikationsart.setPerkutan(new Perkutan());
      applikationsart.getPerkutan().setZielgebiet(new ZielgebietTyp());
      applikationsart.getPerkutan().getZielgebiet().setCodeVersion2021(generateCodeVersion());
      applikationsart.getPerkutan().setGesamtdosis(new StrahlendosisTyp());
      applikationsart.getPerkutan().getGesamtdosis().setEinheit(generateEinheit());
      applikationsart.getPerkutan().getGesamtdosis().setDosis(generateDosis());
      applikationsart.getPerkutan().setEinzeldosis(new StrahlendosisTyp());
      applikationsart.getPerkutan().getEinzeldosis().setEinheit(generateEinheit());
      applikationsart.getPerkutan().getEinzeldosis().setDosis(generateDosis());

    } else if (applikation.equals("K")) {
      applikationsart.setKontakt(new Kontakt());
      applikationsart.getKontakt().setZielgebiet(new ZielgebietTyp());
      applikationsart.getKontakt().getZielgebiet().setCodeVersion2021(generateCodeVersion());
      applikationsart.getKontakt().setGesamtdosis(new StrahlendosisTyp());
      applikationsart.getKontakt().getGesamtdosis().setEinheit(generateEinheit());
      applikationsart.getKontakt().getGesamtdosis().setDosis(generateDosis());
      applikationsart.getKontakt().setEinzeldosis(new StrahlendosisTyp());
      applikationsart.getKontakt().getEinzeldosis().setEinheit(generateEinheit());
      applikationsart.getKontakt().getEinzeldosis().setDosis(generateDosis());

    } else if (applikation.equals("M")) {
      applikationsart.setMetabolisch(new Metabolisch());
      applikationsart.getMetabolisch().setZielgebiet(new ZielgebietTyp());
      applikationsart.getMetabolisch().getZielgebiet().setCodeVersion2021(generateCodeVersion());
      applikationsart.getMetabolisch().setGesamtdosis(new AktivitaetsTyp());
      applikationsart.getMetabolisch().getGesamtdosis().setEinheit(generateEinheit());
      applikationsart.getMetabolisch().getGesamtdosis().setDosis(generateDosis());
      applikationsart.getMetabolisch().setEinzeldosis(new AktivitaetsTyp());
      applikationsart.getMetabolisch().getEinzeldosis().setDosis(generateDosis());
      applikationsart.getMetabolisch().getEinzeldosis().setEinheit(generateEinheit());

    } else {
      applikationsart.setSonstige(new Sonstige());
      applikationsart.getSonstige().setZielgebiet(new ZielgebietTyp());
      applikationsart.getSonstige().getZielgebiet().setCodeVersion2021(generateCodeVersion());
      applikationsart.getSonstige().setGesamtdosis(new StrahlendosisTyp());
      applikationsart.getSonstige().getGesamtdosis().setEinheit(generateEinheit());
      applikationsart.getSonstige().getGesamtdosis().setDosis(generateDosis());
      applikationsart.getSonstige().setEinzeldosis(new StrahlendosisTyp());
      applikationsart.getSonstige().getEinzeldosis().setEinheit(generateEinheit());
      applikationsart.getSonstige().getEinzeldosis().setDosis(generateDosis());
    }
    return applikationsart;
  }

  private String generateCodeVersion(){
    return this.randomValuesGenerator.generate(ProbabilityType.ST_ZIELGEBIET);
  }
  private String generateEinheit(){
    return this.randomValuesGenerator.generate(ProbabilityType.EINHEIT);
  }
  private BigDecimal generateDosis(){
    BigDecimal einheit;
    Random rand = new Random();
    int randUpper = rand.nextInt(100);
    einheit = new BigDecimal(randUpper).setScale(1, RoundingMode.HALF_UP);
    return einheit;
  }
}
