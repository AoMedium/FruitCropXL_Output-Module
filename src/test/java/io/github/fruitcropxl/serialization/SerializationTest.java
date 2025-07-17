package io.github.fruitcropxl.serialization;

import static org.junit.Assert.fail;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;

import io.github.fruitcropxl.output.model.Iris;
import io.github.fruitcropxl.output.util.SchemaMetadataWriter;
import io.github.fruitcropxl.output.util.StepwiseCsvWriter;

public class SerializationTest {

    @Test
    public void testStepwiseCsvWriter() {
        try {
            CsvMapper mapper = new CsvMapper();
            StepwiseCsvWriter writer = new StepwiseCsvWriter("target/fruit-basic.csv", mapper);

            File file = serialize(writer);
            deserialize(file, StepwiseCsvWriter.class);
        } catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }

    @Test
    public void testSchemaMetadataWriter() {
        try {
            String irisMetadataFilePath = "target/iris.metadata.json";
            ObjectMapper mapper = new ObjectMapper();
            SchemaMetadataWriter writer = new SchemaMetadataWriter(irisMetadataFilePath, Iris.class, mapper);

            File file = serialize(writer);
            deserialize(file, SchemaMetadataWriter.class);
        } catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }

    private <T> File serialize(Object o) throws IOException {
        final String outFilePath = "serialized.txt";
        File outFile = new File(outFilePath);

        FileOutputStream fileOutputStream = new FileOutputStream(outFile,
                false);

        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(
                fileOutputStream)) {
            objectOutputStream.writeObject(o);
        }
        return outFile;
    }

    private <T> boolean deserialize(File file, Class<T> clazz)
            throws ClassNotFoundException, IOException {
        return deserialize(file, clazz, true);
    }

    private <T> boolean deserialize(File file, Class<T> clazz,
            boolean deleteFile) throws IOException, ClassNotFoundException {
        FileInputStream fileInputStream = new FileInputStream(file);

        try (ObjectInputStream objectInputStream = new ObjectInputStream(
                fileInputStream)) {
            T serializedConfig = clazz.cast(objectInputStream.readObject());

            System.out.println(serializedConfig);

            // TODO: tests to determine if return false
        }

        if (deleteFile) {
            file.delete();
        }
        return true;
    }
}