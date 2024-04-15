package de.samply.probabilities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * Probability: Represents a ...
 *
 * Individuals Values:
 *
 * [{"enum-value": "MALE", "probability" : "0,4"}, {"enum-value": "FEMALE", "probability" : "0,4"}, {"enum-value": "OTHER", "probability" : "0,2"}]
 *
 * Intervals:
 * Integer interval
 *
 *
 * double interval
 *
 *
 */

@Data
public class Probability {
    // Individual values
    @JsonProperty(value = "enum-value")
    private String enumValue;

    @JsonProperty(value = "integer-value")
    private Integer integerValue;

    @JsonProperty(value = "double-value")
    private Double doubleValue;

    // Integer interval
    @JsonProperty(value = "min-integer-value")
    private Integer minIntegerValue;

    @JsonProperty(value = "max-integer-value")
    private Integer maxIntegerValue;

    // Double interval
    @JsonProperty(value = "min-double-value")
    private Double minDoubleValue;

    @JsonProperty(value = "max-double-value")
    private Double maxDoubleValue;

    // Probability
    @JsonProperty(value = "probability", required = true)
    private Double probability;
}
