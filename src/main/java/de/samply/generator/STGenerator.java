package de.samply.generator;
import de.samply.model.HistologieTyp;
import de.samply.model.OBDS;
import de.samply.model.OBDS;
import de.samply.model.OBDS.MengeMelder;
import de.samply.model.OBDS.MengePatient.Patient;
import de.samply.model.OBDS.MengePatient.Patient.MengeMeldung;
import de.samply.model.OBDS.MengePatient.Patient.MengeMeldung.Meldung;
import de.samply.model.STTyp;
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
public class STGenerator {
  private final DatatypeFactory datatypeFactory = DatatypeFactory.newInstance();
  private final RandomValuesGenerator randomValuesGenerator;
  public STGenerator(RandomValuesGenerator randomValuesGenerator) throws DatatypeConfigurationException {
    this.randomValuesGenerator = randomValuesGenerator;
  }

  public STTyp createST() {
    STTyp st = new STTyp();
    st.setIntention(this.generateIntention());
    st.setStellungOP(this.generateStellungOP());
    st.setEndeGrund(this.generateEndeGrund());
    return st;
  }

  private String generateIntention(){
    String intention = this.randomValuesGenerator.generate(ProbabilityType.INTENTION);
    return intention;
  }

  private String generateStellungOP(){
    String stellungOP = this.randomValuesGenerator.generate(ProbabilityType.STELLUNG_OP);
    return stellungOP;
  }
  private String generateEndeGrund(){
    String endeGrund = this.randomValuesGenerator.generate(ProbabilityType.ENDE_GRUND);
    return endeGrund;
  }
}
