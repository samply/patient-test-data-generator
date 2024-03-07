package de.samply.app;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import utils.ProjectVersion;

@RestController
public class PatientTestDataGeneratorController {

    private final String projectVersion = ProjectVersion.getProjectVersion();

    @GetMapping(value = PatientTestDataGeneratorConst.INFO)
    public ResponseEntity<String> info() {
        return new ResponseEntity<>(projectVersion, HttpStatus.OK);
    }

    @GetMapping(value = PatientTestDataGeneratorConst.GENERATE)
    public ResponseEntity<String> generate() {
        //TODO
        return new ResponseEntity<>("Hello World!", HttpStatus.OK);
    }


}
