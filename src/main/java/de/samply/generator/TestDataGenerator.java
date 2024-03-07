package de.samply.generator;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import de.samply.app.PatientTestDataGeneratorConst;
import de.samply.model.OBDS;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Component
public class TestDataGenerator {

    private final String temporalDirectory;
    private final String testdataFilenamePrefix;
    private final DateTimeFormatter formatter;
    private XmlMapper xmlMapper = (XmlMapper) new XmlMapper().enable(SerializationFeature.INDENT_OUTPUT);


    public TestDataGenerator(
            @Value(PatientTestDataGeneratorConst.TEMPORAL_DIRECTORY_SV) String temporalDirectory,
            @Value(PatientTestDataGeneratorConst.TEST_DATA_FILENAME_PREFIX_SV) String testdataFilenamePrefix,
            @Value(PatientTestDataGeneratorConst.TEST_DATA_FILENAME_TIME_FORMAT_SV) String timeformat) {
        this.temporalDirectory = temporalDirectory;
        this.testdataFilenamePrefix = testdataFilenamePrefix;
        this.formatter = DateTimeFormatter.ofPattern(timeformat);
    }


    public Path generate() throws TestDataGeneratorException {
        return writeInFile(generateTestData());
    }

    private OBDS generateTestData() {
        OBDS obds = new OBDS();
        //TODO: Create test data
        return obds;
    }

    private Path writeInFile(OBDS testData) throws TestDataGeneratorException {
        Path result = Paths.get(temporalDirectory, generateFilename());
        writeInFile(result, testData);
        return result;
    }

    private String generateFilename() {
        return this.testdataFilenamePrefix + "-" + UUID.randomUUID() + "-" + fetchCurrentInstant() + ".xml";
    }

    private String fetchCurrentInstant() {
        return this.formatter.format(Instant.now().atZone(ZoneId.systemDefault()));
    }
    
    private void writeInFile(Path path, OBDS testData) throws TestDataGeneratorException {
        try {
            writeInFileWithoutExceptionHandling(path, testData);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void writeInFileWithoutExceptionHandling(Path path, OBDS testData) throws IOException {
        this.xmlMapper.writeValue(path.toFile(), testData);
    }

}
