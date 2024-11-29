package io.github.fruitcropxl.util;

import static org.junit.Assert.fail;

import org.junit.Test;

import io.github.fruitcropxl.model.Fruit;
import io.github.fruitcropxl.model.Iris;
import io.github.fruitcropxl.output.util.StepwiseCsvWriter;

public class StepwiseCsvWriterTest {
    @Test
    public void testWriteInvalidPojo() {
        StepwiseCsvWriter writer = new StepwiseCsvWriter("target/test-output/invalid.csv", Iris.class);

        try {
            writer.append(new Fruit(0, 0));
            fail("Should have produced RuntimeException from writing Fruit out as Iris");
        } catch (RuntimeException e) {
        }
    }
}
