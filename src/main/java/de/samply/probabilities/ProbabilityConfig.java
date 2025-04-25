package de.samply.probabilities;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.samply.app.PatientTestDataGeneratorConst;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

@Configuration
public class ProbabilityConfig {
    private final Map<String, List<DistributionUnit>> distributions;

    public ProbabilityConfig(
            @Value(PatientTestDataGeneratorConst.PROBABILITY_CONFIG_PATH_SV) String configPath) throws IOException {
        this.distributions = createDistributions(createProbabilityConfig(configPath));
    }

    private Map<String, List<Probability>> createProbabilityConfig(String configPath) throws IOException {
        try (InputStream inputStream = Files.newInputStream(Paths.get(configPath))) {
            return createProbabilityConfig(inputStream);
        }
    }

    private Map<String, List<Probability>> createProbabilityConfig(InputStream inputStream) throws IOException {
        return new ObjectMapper().readValue(inputStream, new TypeReference<Map<String, List<Probability>>>() {
        });
    }

    private Map<String, List<DistributionUnit>> createDistributions(Map<String, List<Probability>> probabilityConfig) {
        Map<String, List<DistributionUnit>> result = new HashMap<>();
        probabilityConfig.forEach((key, probabilities) -> {
            List<DistributionUnit> distributionUnits = new ArrayList<>();
            result.put(key, distributionUnits);
            AtomicReference<Double> reference = new AtomicReference<>(0.0);
            probabilities.forEach(probability -> {
                reference.set(reference.get() + probability.getProbability());
                distributionUnits.add(new DistributionUnit(reference.get(), probability));
            });
        });
        return result;
    }

    public Optional<List<DistributionUnit>> getDistribution(ProbabilityType type) {
        return Optional.ofNullable(this.distributions.get(type.toString()));
    }
}