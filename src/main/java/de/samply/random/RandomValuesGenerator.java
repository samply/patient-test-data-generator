package de.samply.random;

import de.samply.probabilities.DistributionUnit;
import de.samply.probabilities.Probability;
import de.samply.probabilities.ProbabilityConfig;
import de.samply.probabilities.ProbabilityType;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class RandomValuesGenerator {

    private final ProbabilityConfig probabilityConfig;


    public RandomValuesGenerator(ProbabilityConfig probabilityConfig) {
        this.probabilityConfig = probabilityConfig;
    }

    public String generate(@NotNull ProbabilityType probabilityType) {
        Optional<List<DistributionUnit>> distribution = this.probabilityConfig.getDistribution(probabilityType);
        if (distribution.isEmpty()) {
            throw new RandomValuesGeneratorException("Probability type " + probabilityType + " not found");
        }
        return generate(distribution.get(), probabilityType);
    }

    private String generate(List<DistributionUnit> distributionUnits, ProbabilityType probabilityType) {
        double randomNumber = new Random().nextDouble();
        for (DistributionUnit distributionUnit : distributionUnits) {
            if (randomNumber < distributionUnit.maxValue()) {
                return fetchValue(distributionUnit.probability(), probabilityType);
            }
        }
        throw new RandomValuesGeneratorException("Probability type " + probabilityType + " not configured for whole distribution");
    }

    private String fetchValue(Probability probability, ProbabilityType probabilityType) {
        //TODO
        // If probability is an individual value
        if (probability.getEnumValue() != null) {
            return probability.getEnumValue();
        }
        // ...
        // If probability is an interval
        if (probability.getMinIntegerValue() != null && probability.getMaxIntegerValue() != null) {
            // TODO: Generate random integer between min and max
        }
        // ...
        throw new RandomValuesGeneratorException("Probability type " + probabilityType + " is not well defined");
    }


}
