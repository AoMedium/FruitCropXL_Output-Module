package io.github.fruitcropxl.output.util;

import static org.junit.Assert.fail;

import org.junit.Test;

import com.fasterxml.jackson.dataformat.csv.CsvMapper;

import io.github.fruitcropxl.output.model.Fruit;
import io.github.fruitcropxl.output.model.Iris;

public class CsvSteppingWriterTest {
    @Test
    public void testWriteInvalidPojo() {
        CsvSteppingWriter writer = new CsvSteppingWriter("target/invalid.csv", Iris.class);

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
            CsvSteppingWriter.deleteIf(i == 0, filePath);
            CsvSteppingWriter.write(new Fruit(i, i * i), filePath, mapper);
        }
    }
}
