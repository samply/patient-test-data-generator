package de.samply.app;

public class PatientTestDataGeneratorConst {

    public final static String APP_NAME = "Patient Test Data Generator";


    // REST Services
    public final static String INFO = "/info";
    public final static String GENERATE = "/generate";

    // Environment Variables
    public final static String TEST_DATA_FILENAME = "TEST_DATA_FILENAME";
    public final static String TEMPORAL_DIRECTORY = "TEMPORAL_DIRECTORY";
    public final static String TEST_DATA_FILENAME_TIME_FORMAT = "TEST_DATA_FILENAME_TIME_FORMAT";
    public final static String TEST_DATA_FILENAME_PREFIX = "TEST_DATA_FILENAME_PREFIX";

    // Spring Values (SV)
    public final static String HEAD_SV = "${";
    public final static String BOTTOM_SV = "}";
    public final static String TEST_DATA_FILENAME_SV = HEAD_SV + TEST_DATA_FILENAME + ":testdata.xml" + BOTTOM_SV;
    public final static String TEMPORAL_DIRECTORY_SV = HEAD_SV + TEMPORAL_DIRECTORY + ":./temporal" + BOTTOM_SV;
    public final static String TEST_DATA_FILENAME_TIME_FORMAT_SV = HEAD_SV + TEST_DATA_FILENAME_TIME_FORMAT + ":yyyy-MM-dd_HH-mm-ss" + BOTTOM_SV;
    public final static String TEST_DATA_FILENAME_PREFIX_SV = HEAD_SV + TEST_DATA_FILENAME_PREFIX + ":obds" + BOTTOM_SV;


}
