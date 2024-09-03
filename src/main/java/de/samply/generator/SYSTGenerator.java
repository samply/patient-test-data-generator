package de.samply.generator;

import de.samply.model.DatumTagOderMonatGenauTyp;
import de.samply.model.NebenwirkungTyp;
import de.samply.model.NebenwirkungTyp.MengeNebenwirkung;
import de.samply.model.SYSTTyp;
import de.samply.random.RandomValuesGenerator;
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
public class SYSTGenerator {
  private int count = 0;
  private final DatatypeFactory datatypeFactory = DatatypeFactory.newInstance();
  private final NebenwirkungGenerator nebenwirkungGenerator;

  public SYSTGenerator(RandomValuesGenerator randomValuesGenerator) throws DatatypeConfigurationException {
    this.nebenwirkungGenerator = new NebenwirkungGenerator(randomValuesGenerator);
  }

  public SYSTTyp createSYST(XMLGregorianCalendar birthdate){
    SYSTTyp syst = new SYSTTyp();
    syst.setProtokoll(this.generateProtokoll());
    syst.setNebenwirkungen(this.generateNebewirkungen());
    if(birthdate.getYear()<2024){
      syst.setBeginn(generateBeginnDatum(birthdate));
      syst.setEnde(generateEndeDatum(syst.getBeginn().getValue()));
    }
    return syst;
  }
  private String generateProtokoll() {
    String protokoll = "SYST_Protokoll" + this.count++;
    return protokoll;
  }

  private DatumTagOderMonatGenauTyp generateBeginnDatum(XMLGregorianCalendar birthdate) {
    DatumTagOderMonatGenauTyp beginnDatum = new DatumTagOderMonatGenauTyp();
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
  private XMLGregorianCalendar generateEndeDatum(XMLGregorianCalendar beginn){
    Random randomDays = new Random();
    int daysToAdd = randomDays.nextInt(30);
    GregorianCalendar minGregorianCalendar = new GregorianCalendar();
    minGregorianCalendar.set(beginn.getYear(),beginn.getMonth(),beginn.getDay());
    minGregorianCalendar.add(Calendar.DAY_OF_MONTH,daysToAdd);
    XMLGregorianCalendar endeDatum = null;
    try {
      endeDatum = DatatypeFactory.newInstance().newXMLGregorianCalendar(minGregorianCalendar);
    } catch (DatatypeConfigurationException e) {
      throw new RuntimeException(e);
    }
    return endeDatum;
  }

  private NebenwirkungTyp generateNebewirkungen(){
    NebenwirkungTyp  nebenwirkungen= new NebenwirkungTyp();
    nebenwirkungen.setMengeNebenwirkung(new MengeNebenwirkung());
    nebenwirkungen.getMengeNebenwirkung().getNebenwirkung().add(this.nebenwirkungGenerator.createNebenwirkung());
    return nebenwirkungen;
  }
}