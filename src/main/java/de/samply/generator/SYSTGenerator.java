package de.samply.generator;

import de.samply.model.DatumTagOderMonatGenauTyp;
import de.samply.model.DatumTagOderMonatOderJahrOderNichtGenauTyp;
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
  private MengeNebenwirkung nebenwirkungen;
  public SYSTGenerator(RandomValuesGenerator randomValuesGenerator) throws DatatypeConfigurationException {
    this.nebenwirkungGenerator = new NebenwirkungGenerator(randomValuesGenerator);
    this.nebenwirkungen = new MengeNebenwirkung();
  }

  public SYSTTyp createSYST(XMLGregorianCalendar birthdate){
    SYSTTyp syst = new SYSTTyp();
    syst.setProtokoll(this.generateProtokoll());
    syst.setNebenwirkungen(this.generateNebewirkungen());
    syst.setBeginn(generateBeginnDatum(birthdate));
    syst.setEnde(syst.getBeginn().getValue());
    return syst;
  }
  private String generateProtokoll() {
    String protokoll = "SYST_Protokoll" + this.count++;
    return protokoll;
  }

  private DatumTagOderMonatGenauTyp generateBeginnDatum(XMLGregorianCalendar birthdate) {
    DatumTagOderMonatGenauTyp beginnDatum = new DatumTagOderMonatGenauTyp();
    Random randomDays = new Random();
    int daysToAdd = randomDays.nextInt(18262);
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
  private Date generateEndeDatum(XMLGregorianCalendar beginn){
    Date endeDatum = new Date(beginn.getYear(),beginn.getMonth(),beginn.getDay());
    return endeDatum;
  }

  private NebenwirkungTyp generateNebewirkungen(){
    NebenwirkungTyp  nebenwirkungen= new NebenwirkungTyp();
    nebenwirkungen.setMengeNebenwirkung(new MengeNebenwirkung());
    nebenwirkungen.getMengeNebenwirkung().getNebenwirkung().add(this.nebenwirkungGenerator.createNebenwirkung());
    return nebenwirkungen;
  }
}