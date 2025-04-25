package de.samply.generator;

import de.samply.model.DatumTagOderMonatGenauTyp;
import de.samply.model.DatumTagOderMonatOderJahrOderNichtGenauTyp;
import de.samply.model.HistologieTyp;
import java.math.BigInteger;
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
public class HistologieGenerator {
  private int count = 1;
  public HistologieTyp createHistologie(XMLGregorianCalendar birthdate){
    HistologieTyp histologie = new HistologieTyp();
    histologie.setHistologieID(""+ this.count++);
    histologie.setLKUntersucht(this.generateLKUntersucht());
    histologie.setLKBefallen(this.generateLKBefallen(histologie));
    histologie.setSentinelLKUntersucht(this.generateSentinelLKUntersucht());
    histologie.setSentinelLKBefallen(this.generateSentinelLKBefallen(histologie));
    if(birthdate.getYear()<2024){
      histologie.setTumorHistologiedatum(this.generateTumorHistologieDatum(birthdate));
    }
    return histologie;
  }

  private BigInteger generateLKUntersucht(){
    Random zufallszahl = new Random();
    return BigInteger.valueOf(zufallszahl.nextInt(600)+1);
  }

  private BigInteger generateLKBefallen(HistologieTyp histologie){
    Random zufallszahl = new Random();
    return BigInteger.valueOf(zufallszahl.nextInt(histologie.getLKUntersucht().intValue()));
  }

  private BigInteger generateSentinelLKUntersucht(){
    Random zufallszahl = new Random();
    return BigInteger.valueOf(zufallszahl.nextInt(600)+1);
  }

  private BigInteger generateSentinelLKBefallen(HistologieTyp histologie){
    Random zufallszahl = new Random();
    return BigInteger.valueOf(zufallszahl.nextInt(histologie.getSentinelLKUntersucht().intValue()));
  }

  private DatumTagOderMonatOderJahrOderNichtGenauTyp generateTumorHistologieDatum(XMLGregorianCalendar birthdate){
      DatumTagOderMonatOderJahrOderNichtGenauTyp beginnDatum = new DatumTagOderMonatOderJahrOderNichtGenauTyp();
      Random randomDays = new Random();
      Date currentDate = new Date();
      int daysToAdd = randomDays.nextInt(((currentDate.getYear()+1900) - birthdate.getYear()) * 365);
      XMLGregorianCalendar beginn;
      GregorianCalendar minGregorianCalendar = new GregorianCalendar();
      minGregorianCalendar.set(birthdate.getYear(), birthdate.getMonth(), birthdate.getDay());
      minGregorianCalendar.add(Calendar.DAY_OF_MONTH, daysToAdd);
      try {
        beginn = DatatypeFactory.newInstance().newXMLGregorianCalendar(minGregorianCalendar);
      } catch (DatatypeConfigurationException e) {
        throw new RuntimeException(e);
      }
      beginnDatum.setValue(beginn);
      return beginnDatum;
  }
}
