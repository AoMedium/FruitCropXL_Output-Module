package io.github.fruitcropxl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.fasterxml.jackson.module.jsonSchema.jakarta.JsonSchema;

import io.github.fruitcropxl.model.Fruit;
import io.github.fruitcropxl.model.Iris;
import io.github.fruitcropxl.output.schema.ExtendedValidationSchemaFactoryWrapper;
import io.github.fruitcropxl.output.util.StepwiseCsvWriter;

public class OutputModuleTest {

    @Test
    public void testPrint() {
        int max = 20;

        List<Iris> irisList = new ArrayList<>();
        for (int i = 0; i < max; i++) {
            Iris irisInstance = new Iris(
                    i, i + 1, i + 2, i + 3, i + 4);
            irisList.add(irisInstance);
        }

        List<Fruit> fruitList = new ArrayList<>();
        for (int i = 0; i < max; i++) {
            Fruit fruitInstance = new Fruit(
                    i, i + 1);
            fruitList.add(fruitInstance);
        }

        printJson(irisList);
        printCsv(irisList);

        System.out.println("============ SINGLE CSV\n");

        String irisFilePath = "target/test-output/iris.csv";
        String fruitFilePath = "target/test-output/fruit.csv";

        Map<String, StepwiseCsvWriter> csvWriters = new HashMap<>();

        csvWriters.put(irisFilePath, new StepwiseCsvWriter(irisFilePath, Iris.class));
        csvWriters.put(fruitFilePath, new StepwiseCsvWriter(fruitFilePath, Fruit.class));

        StepwiseCsvWriter irisWriter = csvWriters.get(irisFilePath);
        irisWriter.getSchema()
                .withQuoteChar('\"')
                .withColumnSeparator(',');

        StepwiseCsvWriter fruitWriter = csvWriters.get(fruitFilePath);
        fruitWriter.getSchema()
                .withQuoteChar('\"')
                .withColumnSeparator(';');

        for (Iris iris : irisList) {
            irisWriter.append(iris);
        }

        for (Fruit fruit : fruitList) {
            fruitWriter.append(fruit);
        }
    }

    protected static void printJson(List<Iris> irisList) {
        ObjectMapper mapper = new ObjectMapper();
        ExtendedValidationSchemaFactoryWrapper schema = new ExtendedValidationSchemaFactoryWrapper();

        try {
            mapper.acceptJsonFormatVisitor(Iris.class, schema);
            JsonSchema jsonSchema = schema.finalSchema();
            String schemaValue = mapper
                    .writerWithDefaultPrettyPrinter()
                    .writeValueAsString(jsonSchema);
            System.out.println("JSON");
            System.out.println(schemaValue);
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    protected static void printCsv(List<Iris> irisList) {
        CsvMapper mapper = new CsvMapper();
        CsvSchema schema = mapper.schemaFor(Iris.class)
                .withHeader()
                .withQuoteChar('\"')
                .withColumnSeparator(',');

        try {
            String csvValues = mapper.writer().with(schema).writeValueAsString(irisList);
            System.out.println("CSV");
            System.out.println(csvValues);
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

}
