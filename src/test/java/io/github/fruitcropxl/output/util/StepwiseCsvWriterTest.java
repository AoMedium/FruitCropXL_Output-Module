package io.github.fruitcropxl.output.util;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;

import io.github.fruitcropxl.output.model.Fruit;
import io.github.fruitcropxl.output.model.Iris;

public class StepwiseCsvWriterTest {
    @Test
    public void testBasicWrite() {
        CsvMapper mapper = new CsvMapper();

        for (int i = 0; i < 5; i++) {
            (new StepwiseCsvWriter("target/fruit-basic.csv", mapper))
                    .deleteFileIf(i == 0)
                    .write(new Fruit(i, i * i));
        }

        for (int i = 0; i < 5; i++) {
            (new StepwiseCsvWriter("target/iris-basic.csv", mapper))
                    .deleteFileIf(i == 0)
                    .write(new Iris(i, i * 1, i * 2, i * 3, i * 4));
        }
    }

    @Test
    public void testSchemaConfiguration() {
        CsvMapper mapper = new CsvMapper();
        mapper.setVisibility(PropertyAccessor.ALL, Visibility.NONE);
        mapper.setVisibility(PropertyAccessor.FIELD, Visibility.DEFAULT);

        String filePath = "target/static-write-config.csv";

        StepwiseCsvWriter writer = new StepwiseCsvWriter(filePath, mapper)
                .setSchema(mapper.schemaFor(Fruit.class)
                        .withQuoteChar('/')
                        .withComments()
                        .withLineSeparator(";"));

        for (int i = 0; i < 5; i++) {
            writer.deleteFileIf(i == 0)
                    .write(new Fruit(i, i * i));
        }

        writer.setSchema(mapper.schemaFor(Fruit.class)
                .withQuoteChar('/')
                .withComments()
                .withLineSeparator("\n"));

        for (int i = 0; i < 5; i++) {
            writer.write(new Fruit(i, i * i));
        }
    }

    @Test
    public void testWriteList() {
        List<Fruit> list = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            list.add(new Fruit(i, i * i));
        }

        CsvMapper mapper = new CsvMapper();
        mapper.setVisibility(PropertyAccessor.ALL, Visibility.NONE);
        mapper.setVisibility(PropertyAccessor.FIELD, Visibility.DEFAULT);

        String filePath = "target/write-list.csv";

        (new StepwiseCsvWriter(filePath, mapper)).deleteFileIf(true).writeList(list);
    }

    @Test
    public void testWriteArray() {
        Fruit[] list = {
                new Fruit(0, 0),
                new Fruit(1, 0),
                new Fruit(2, 0),
                new Fruit(3, 0)
        };

        CsvMapper mapper = new CsvMapper();
        mapper.setVisibility(PropertyAccessor.ALL, Visibility.NONE);
        mapper.setVisibility(PropertyAccessor.FIELD, Visibility.DEFAULT);

        String filePath = "target/write-array.csv";

        (new StepwiseCsvWriter(filePath, mapper)).deleteFileIf(true).writeArray(list);
    }

    @Test
    public void testWriteEmptyArray() {
        Fruit[] list = {};

        CsvMapper mapper = new CsvMapper();
        mapper.setVisibility(PropertyAccessor.ALL, Visibility.NONE);
        mapper.setVisibility(PropertyAccessor.FIELD, Visibility.DEFAULT);

        String filePath = "target/write-empty-array.csv";

        (new StepwiseCsvWriter(filePath, mapper)).deleteFileIf(true).writeArray(list);
    }
}
