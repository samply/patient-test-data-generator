package de.samply.generator;

import de.samply.model.OBDS;
import org.springframework.stereotype.Component;

import java.nio.file.Path;

@Component
public class TestDataGenerator {

    public Path generate() {
        return writeInFile(generateTestData());
    }

    private OBDS generateTestData(){
        OBDS obds = new OBDS();
        //TODO: Create test data
        return obds;
    }

    private Path writeInFile(OBDS testData){
        Path result = null;
        //TODO: Serialize testData in file and save it in temporal directory
        return result;
    }

}
