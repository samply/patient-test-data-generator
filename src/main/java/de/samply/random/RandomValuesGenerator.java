package de.samply.random;

import de.samply.probabilities.DistributionUnit;
import de.samply.probabilities.Probability;
import de.samply.probabilities.ProbabilityConfig;
import de.samply.probabilities.ProbabilityType;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import org.springframework.stereotype.Service;

@Service
public class RandomValuesGenerator {

  private final ProbabilityConfig probabilityConfig;


  public RandomValuesGenerator(ProbabilityConfig probabilityConfig) {
    this.probabilityConfig = probabilityConfig;
  }

  public String generate(@NotNull ProbabilityType probabilityType) {
    Optional<List<DistributionUnit>> distribution = this.probabilityConfig.getDistribution(
        probabilityType);
    if (distribution.isEmpty()) {
      throw new RandomValuesGeneratorException(
          "Probability type " + probabilityType + " not found");
    }
    return generate(distribution.get(), probabilityType);
  }

  private String generate(List<DistributionUnit> distributionUnits,
      ProbabilityType probabilityType) {
    double randomNumber = new Random().nextDouble();
    for (DistributionUnit distributionUnit : distributionUnits) {
      if (randomNumber < distributionUnit.maxValue()) {
        return fetchValue(distributionUnit.probability(), probabilityType);
      }
    }
    throw new RandomValuesGeneratorException(
        "Probability type " + probabilityType + " not configured for whole distribution");
  }

  private String fetchValue(Probability probability, ProbabilityType probabilityType) {
    //TODO
    Random random = new Random();
    // If probability is an individual value
    if (probability.getEnumValue() != null) {
      return probability.getEnumValue();
    }
    if (probability.getIntegerValue() != null) {
      return "" + probability.getIntegerValue();
    }
    if (probability.getDoubleValue() != null) {
      return "" + probability.getDoubleValue();
    }
    // If probability is an interval
    if (probability.getMinIntegerValue() != null && probability.getMaxIntegerValue() != null) {
      // TODO: Generate random integer between min and max
      return "" + random.nextInt(
          probability.getMaxIntegerValue() - probability.getMinIntegerValue() + 1)
          + probability.getMinIntegerValue();
    }
    if (probability.getMinDoubleValue() != null && probability.getMaxDoubleValue() != null) {
      // TODO: Generate random integer between min and max
      return "" + random.nextDouble(
          probability.getMaxDoubleValue() - probability.getMinDoubleValue() + 1)
          + probability.getMinDoubleValue();
    }
    throw new RandomValuesGeneratorException(
        "Probability type " + probabilityType + " is not well defined");
  }


}
