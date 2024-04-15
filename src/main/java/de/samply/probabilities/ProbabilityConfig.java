package de.samply.probabilities;


import com.fasterxml.jackson.databind.ObjectMapper;
import de.samply.app.PatientTestDataGeneratorConst;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Configuration
public class ProbabilityConfig {

    private final Map<String, List<Probability>> probabilityConfig;


    public ProbabilityConfig(
            @Value(PatientTestDataGeneratorConst.PROBABILITY_CONFIG_PATH_SV) String configPath) throws IOException {
        this.probabilityConfig = createProbabilityConfig(configPath);
    }

    private Map<String, List<Probability>> createProbabilityConfig(String configPath) throws IOException {
        try (InputStream inputStream = Files.newInputStream(Paths.get(configPath))) {
            return createProbabilityConfig(inputStream);
        }
    }

    private Map<String, List<Probability>> createProbabilityConfig(InputStream inputStream) throws IOException {
        return new ObjectMapper().readValue(inputStream, Map.class);
    }

    public Optional<List<Probability>> getProbabilities(ProbabilityType probabilityType) {
        return Optional.ofNullable(probabilityConfig.get(probabilityType.toString()));
    }


}
