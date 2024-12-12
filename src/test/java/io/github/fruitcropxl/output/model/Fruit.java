package io.github.fruitcropxl.output.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonRootName;

import io.github.fruitcropxl.output.annotation.DefaultNumericValue;
import io.github.fruitcropxl.output.annotation.PropertyType;
import io.github.fruitcropxl.output.annotation.Tags;
import io.github.fruitcropxl.output.annotation.Unit;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@JsonRootName("fruit")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Fruit {
    @JsonProperty(value = "id", index = 0)
    @JsonPropertyDescription("The unique identifier")
    @NotNull
    @Min(0)
    @PropertyType("id")
    @DefaultNumericValue(0)
    @Tags({ "example_tag", "another_tag" })
    public int id = 0;

    @JsonProperty(value = "property1", index = 1)
    @JsonPropertyDescription("Property 1")
    @NotNull
    @Min(0)
    @Max(100)
    @Unit("cm")
    @PropertyType("state_variable")
    @DefaultNumericValue(0)
    @Tags({ "example_tag", "another_tag" })
    public double property1 = 0;

    public Fruit(int id, double property1) {
        this.id = id;
        this.property1 = property1;
    }
}
