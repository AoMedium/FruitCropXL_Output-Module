package io.github.fruitcropxl.output.util;

import static org.junit.Assert.fail;

import java.io.File;

import org.junit.Test;

import com.fasterxml.jackson.dataformat.csv.CsvMapper;

import io.github.fruitcropxl.output.model.Fruit;
import io.github.fruitcropxl.output.model.Iris;
import io.github.fruitcropxl.output.util.StepwiseCsvWriter;

public class StepwiseCsvWriterTest {
    @Test
    public void testWriteInvalidPojo() {
        StepwiseCsvWriter writer = new StepwiseCsvWriter("target/invalid.csv", Iris.class);

        try {
            writer.append(new Fruit(0, 0));
            fail("Should have produced RuntimeException from writing Fruit out as Iris");
        } catch (RuntimeException e) {
        }
    }

    @Test
    public void testStaticWrite() {
        CsvMapper mapper = new CsvMapper();

        String filePath = "target/static-write.csv";

        for (int i = 0; i < 5; i++) {
            StepwiseCsvWriter.deleteIf(i == 0, filePath);
            StepwiseCsvWriter.write(new Fruit(i, i * i), filePath, mapper);
        }
    }
}
