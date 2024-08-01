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
import java.util.Random;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
@Slf4j
@Component
public class MeldungGenerator {
  private final DatatypeFactory datatypeFactory = DatatypeFactory.newInstance();
  private final HistologieGenerator histologieGenerator;
  private int count;
  private final int numberOfHistologie = 3;
  public MeldungGenerator() throws DatatypeConfigurationException {
    this.count = 1;
    this.histologieGenerator = new HistologieGenerator();
  }

  public Meldung createMeldung(){
    Meldung meldung = new Meldung();
    meldung.setMeldungID(""+this.count);
    meldung.setTumorzuordnung(createTumorzuordnung());
    meldung.setDiagnose(this.createDiagnose());
    meldung.getDiagnose().setHistologie(this.histologieGenerator.createHistologie());
    this.count++;
    return meldung;
  }

  public TumorzuordnungTyp createTumorzuordnung(){
    TumorzuordnungTyp tumorzuordnungTyp = new TumorzuordnungTyp();
    tumorzuordnungTyp.setTumorID(""+this.count);
    return tumorzuordnungTyp;
  }

  public DiagnoseTyp createDiagnose(){
    DiagnoseTyp diagnose = new DiagnoseTyp();
    return diagnose;
  }
}
