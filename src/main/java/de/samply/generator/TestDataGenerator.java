package de.samply.generator;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import de.samply.app.PatientTestDataGeneratorConst;
import de.samply.model.OBDS;
import de.samply.random.RandomValuesGenerator;
import de.samply.serializer.XmlMapperConfiguration;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;
import javax.xml.datatype.DatatypeConfigurationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;



@Component
public class TestDataGenerator {

  private final String temporalDirectory;
  private final String testdataFilenamePrefix;
  private final DateTimeFormatter formatter;
  private final PatientGenerator patientGenerator;
  private int numberOfPatients;
  private final Scanner input = new Scanner(System.in);
  private XmlMapper xmlMapper = (XmlMapper) XmlMapperConfiguration.createXmlMapper().enable(
      SerializationFeature.INDENT_OUTPUT);


  public TestDataGenerator(
      @Value(PatientTestDataGeneratorConst.TEMPORAL_DIRECTORY_SV) String temporalDirectory,
      @Value(PatientTestDataGeneratorConst.TEST_DATA_FILENAME_PREFIX_SV) String testdataFilenamePrefix,
      @Value(PatientTestDataGeneratorConst.TEST_DATA_FILENAME_TIME_FORMAT_SV) String timeformat,
      RandomValuesGenerator randomValuesGenerator, PatientGenerator patientGenerator) {
    this.temporalDirectory = temporalDirectory;
    this.testdataFilenamePrefix = testdataFilenamePrefix;
    this.formatter = DateTimeFormatter.ofPattern(timeformat);
    this.patientGenerator = patientGenerator;
  }


  public Path generate(Integer numberOfPatients) throws TestDataGeneratorException {
    this.numberOfPatients = numberOfPatients;
    return writeInFile(generateTestData());
  }

  private OBDS generateTestData() {
    OBDS obds = new OBDS();
    obds.setMengePatient(createMengePatient());
    //TODO: Create test data
    return obds;
  }

  private OBDS.MengePatient createMengePatient() {
    OBDS.MengePatient mengePatient = new OBDS.MengePatient();
    List<OBDS.MengePatient.Patient> patientList = mengePatient.getPatient();
    for (int i = 0; i < numberOfPatients; i++) {
      patientList.add(createPatient());
    }
    return mengePatient;
  }

  private OBDS.MengePatient.Patient createPatient(){
    return patientGenerator.createPatient();
  }

  private Path writeInFile(OBDS testData) throws TestDataGeneratorException {
    Path result = Paths.get(temporalDirectory, generateFilename());
    writeInFile(result, testData);
    return result;
  }

  private String generateFilename() {
    return this.testdataFilenamePrefix + "-" + UUID.randomUUID() + "-" + fetchCurrentInstant()
        + ".xml";
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