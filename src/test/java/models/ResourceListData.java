package models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ResourceListData {

    private int id, year;
    private String color, name;

    @JsonProperty("pantone_value")
    private String pantoneValue;

}
