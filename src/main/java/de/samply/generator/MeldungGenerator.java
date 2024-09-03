package de.samply.generator;

import de.samply.model.DiagnoseTyp;
import de.samply.model.OBDS.MengePatient.Patient.MengeMeldung.Meldung;
import de.samply.model.TumorzuordnungTyp;
import de.samply.random.RandomValuesGenerator;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
@Slf4j
@Component
public class MeldungGenerator {
  private final DatatypeFactory datatypeFactory = DatatypeFactory.newInstance();
  private final HistologieGenerator histologieGenerator;
  private final STGenerator stGenerator;
  private final SYSTGenerator systGenerator;
  private final OPGenerator opGenerator;
  private XMLGregorianCalendar birthdate;
  private int count;
  private final int numberOfHistologie = 3;

  public MeldungGenerator(RandomValuesGenerator randomValuesGenerator) throws DatatypeConfigurationException {
    this.count = 1;
    this.histologieGenerator = new HistologieGenerator();
    this.stGenerator = new STGenerator(randomValuesGenerator);
    this.systGenerator = new SYSTGenerator(randomValuesGenerator);
    this.opGenerator = new OPGenerator(randomValuesGenerator);
  }

  public Meldung createMeldung(XMLGregorianCalendar birthdate) {
    Meldung meldung = new Meldung();
    meldung.setMeldungID(""+this.count);
    meldung.setTumorzuordnung(createTumorzuordnung());
    meldung.setDiagnose(this.createDiagnose());
    meldung.setST(this.stGenerator.createST(birthdate));
    meldung.setSYST(this.systGenerator.createSYST(birthdate));
    meldung.getDiagnose().setHistologie(this.histologieGenerator.createHistologie(birthdate));
    meldung.setOP(this.opGenerator.createOP());
    this.count++;
    return meldung;
  }

  private TumorzuordnungTyp createTumorzuordnung(){
    TumorzuordnungTyp tumorzuordnungTyp = new TumorzuordnungTyp();
    tumorzuordnungTyp.setTumorID("" + this.count);
    return tumorzuordnungTyp;
  }

  private DiagnoseTyp createDiagnose(){
    return new DiagnoseTyp();
  }
  
}
