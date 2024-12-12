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
public class ExtendedFruit extends Fruit {

    @JsonProperty(value = "property2", index = 2)
    @JsonPropertyDescription("Property 2")
    @NotNull
    @Min(0)
    @Max(100)
    @Unit("cm")
    @PropertyType("state_variable")
    @DefaultNumericValue(0)
    @Tags({ "example_tag", "another_tag" })
    public double property2 = 0;

    public ExtendedFruit(int id, double property1, double property2) {
        super(id, property1);
        this.property1 = property2;
    }
}
