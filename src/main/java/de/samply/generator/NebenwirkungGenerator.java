package de.samply.generator;

import de.samply.model.NebenwirkungTyp.MengeNebenwirkung;
import de.samply.model.NebenwirkungTyp.MengeNebenwirkung.Nebenwirkung;
import de.samply.model.NebenwirkungTyp.MengeNebenwirkung.Nebenwirkung.Art;
import de.samply.probabilities.ProbabilityType;
import de.samply.random.RandomValuesGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class NebenwirkungGenerator {

  private final RandomValuesGenerator randomValuesGenerator;
  public NebenwirkungGenerator(RandomValuesGenerator randomValuesGenerator){
    this.randomValuesGenerator = randomValuesGenerator;
  }

  public Nebenwirkung createNebenwirkung(){
    Nebenwirkung nebenwirkung = new Nebenwirkung();
    nebenwirkung.setGrad(this.generateGrad());
    nebenwirkung.setVersion(generateVersion());
    nebenwirkung.setArt(generateArt());
    return nebenwirkung;
  }

  private String generateGrad(){
    String grad = this.randomValuesGenerator.generate(ProbabilityType.NEBENWIRKUNG_GRAD);
    return grad;
  }
  private String generateVersion(){
    String version = this.randomValuesGenerator.generate(ProbabilityType.NEBENWIRKUNG_VERSION);
    return version;
  }
  private Art generateArt(){
    Art art = new Art();
    art.setBezeichnung("Nebenwirkung_Art1");
    return art;
  }
}
