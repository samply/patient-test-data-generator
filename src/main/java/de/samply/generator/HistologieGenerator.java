package de.samply.generator;
import de.samply.model.DiagnoseTyp;
import de.samply.model.HistologieTyp;
import de.samply.model.OBDS;
import de.samply.model.OBDS;
import de.samply.model.OBDS.MengeMelder;
import de.samply.model.OBDS.MengePatient.Patient;
import de.samply.model.OBDS.MengePatient.Patient.MengeMeldung;
import de.samply.model.OBDS.MengePatient.Patient.MengeMeldung.Meldung;
import de.samply.model.TumorzuordnungTyp;
import de.samply.probabilities.ProbabilityType;
import de.samply.random.RandomValuesGenerator;
import java.math.BigInteger;
import java.util.Random;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
@Slf4j
@Component
public class HistologieGenerator {
  private int count = 1;
  public HistologieTyp createHistologie(){
    HistologieTyp histologie = new HistologieTyp();
    histologie.setHistologieID(""+ this.count++);
    histologie.setLKUntersucht(this.generateLKUntersucht());
    histologie.setLKBefallen(this.generateLKBefallen(histologie));
    histologie.setSentinelLKUntersucht(this.generateSentinelLKUntersucht());
    histologie.setSentinelLKBefallen(this.generateSentinelLKBefallen(histologie));
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
}