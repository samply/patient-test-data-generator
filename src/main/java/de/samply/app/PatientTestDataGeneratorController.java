package de.samply.app;

import de.samply.generator.TestDataGenerator;
import de.samply.generator.TestDataGeneratorException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import utils.ProjectVersion;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@RestController
public class PatientTestDataGeneratorController {

    private final String projectVersion = ProjectVersion.getProjectVersion();
    private final TestDataGenerator testDataGenerator;
    private final String testDataFilename;
    private final int defaultNumberOfPatients;

    public PatientTestDataGeneratorController(
            TestDataGenerator testDataGenerator,
            @Value(PatientTestDataGeneratorConst.DEFAULT_NUMBER_OF_PATIENTS_SV) int defaultNumberOfPatients,
            @Value(PatientTestDataGeneratorConst.TEST_DATA_FILENAME_SV) String testDataFilename) {
        this.testDataGenerator = testDataGenerator;
        this.testDataFilename = testDataFilename;
        this.defaultNumberOfPatients = defaultNumberOfPatients;
    }

    @GetMapping(value = PatientTestDataGeneratorConst.INFO)
    public ResponseEntity<String> info() {
        return new ResponseEntity<>(projectVersion, HttpStatus.OK);
    }

    @GetMapping(value = PatientTestDataGeneratorConst.GENERATE + PatientTestDataGeneratorConst.NUMBER_OF_PATIENTS)
    public ResponseEntity<Resource> generate(@PathVariable (required = false) Integer numberOfPatients) throws IOException, TestDataGeneratorException {
        if (numberOfPatients == null){
            numberOfPatients = defaultNumberOfPatients;
        }
        return downloadDocument(testDataGenerator.generate(numberOfPatients));
    }

    private ResponseEntity<Resource> downloadDocument(Path filePath) throws IOException {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + testDataFilename);
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(filePath.toFile().length())
                .body(fetchResource(filePath));
    }

    private ByteArrayResource fetchResource(Path filePath) throws IOException {
        return new ByteArrayResource(Files.readAllBytes(filePath));
    }


}
