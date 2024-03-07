package de.samply.app;

public class PatientTestDataGeneratorConst {

    public final static String APP_NAME = "Patient Test Data Generator";


    // REST Services
    public final static String INFO = "/info";
    public final static String GENERATE = "/generate";

    // Environment Variables
    public final static String TEST_DATA_FILENAME = "TEST_DATA_FILENAME";

    // Spring Values (SV)
    public final static String HEAD_SV = "${";
    public final static String BOTTOM_SV = "}";
    public final static String TEST_DATA_FILENAME_SV = HEAD_SV + TEST_DATA_FILENAME + BOTTOM_SV;


}
