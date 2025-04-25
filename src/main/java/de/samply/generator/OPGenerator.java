package de.samply.generator;

import de.samply.model.OPTyp;
import de.samply.model.RTyp;
import de.samply.model.ResidualstatusTyp;
import de.samply.probabilities.ProbabilityType;
import de.samply.random.RandomValuesGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
@Slf4j
@Component
public class OPGenerator {

  private final RandomValuesGenerator randomValuesGenerator;
  public OPGenerator(RandomValuesGenerator randomValuesGenerator) {
    this.randomValuesGenerator = randomValuesGenerator;
  }

  public OPTyp createOP(){
    OPTyp op = new OPTyp();
    op.setResidualstatus(new ResidualstatusTyp());
    op.getResidualstatus().setGesamtbeurteilungResidualstatus(generateRandomResidualStatus());
    op.getResidualstatus().setLokaleBeurteilungResidualstatus(generateRandomResidualStatus());
    return op;
  }

  private RTyp generateRandomResidualStatus(){
    String rs = randomValuesGenerator.generate(ProbabilityType.RESIDUALSTATUS);
    return RTyp.fromValue(rs);
  }
}
