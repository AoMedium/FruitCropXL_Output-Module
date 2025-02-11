package io.github.fruitcropxl.output.util;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.github.fruitcropxl.output.model.Fruit;
import io.github.fruitcropxl.output.model.Iris;

public class SchemaMetadataWriterTest {
    @Test
    public void testWrite() {
        String irisMetadataFilePath = "target/iris.metadata.json";
        String fruitMetadataFilePath = "target/fruit.metadata.json";

        ObjectMapper mapper = new ObjectMapper();

        SchemaMetadataWriter irisMetadataWriter = new SchemaMetadataWriter(irisMetadataFilePath, Iris.class, mapper);
        SchemaMetadataWriter fruitMetadataWriter = new SchemaMetadataWriter(fruitMetadataFilePath, Fruit.class, mapper);

        irisMetadataWriter.writeMetadata();
        fruitMetadataWriter.writeMetadata();
    }
}
