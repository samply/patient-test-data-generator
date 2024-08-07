package de.samply.generator;

import de.samply.model.NebenwirkungTyp;
import de.samply.model.NebenwirkungTyp.MengeNebenwirkung;
import de.samply.model.SYSTTyp;
import de.samply.random.RandomValuesGenerator;
import java.util.Date;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
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

  public SYSTTyp createSYST(){
    SYSTTyp syst = new SYSTTyp();
    syst.setProtokoll(this.generateProtokoll());
    syst.setNebenwirkungen(this.generateNebewirkungen());

    return syst;
  }

  private String generateProtokoll() {
    String protokoll = "SYST_Protokoll" + this.count++;
    return protokoll;
  }

  /*private DatumTagOderMonatOderJahrOderNichtGenauTyp generateBeginnDatum(){
    DatumTagOderMonatOderJahrOderNichtGenauTyp beginnDatum;
    
    return beginnDatum;
  } */
  private Date generateEndeDatum(){
    Date endeDatum = new Date();
    return endeDatum;
  }

  private NebenwirkungTyp generateNebewirkungen(){
    NebenwirkungTyp  nebenwirkungen= new NebenwirkungTyp();
    nebenwirkungen.setMengeNebenwirkung(new MengeNebenwirkung());
    nebenwirkungen.getMengeNebenwirkung().getNebenwirkung().add(this.nebenwirkungGenerator.createNebenwirkung());
    return nebenwirkungen;
  }
}