package io.github.fruitcropxl.output;

import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.fasterxml.jackson.module.jsonSchema.jakarta.JsonSchema;

import io.github.fruitcropxl.output.model.ExtendedFruit;
import io.github.fruitcropxl.output.model.Fruit;
import io.github.fruitcropxl.output.model.Iris;
import io.github.fruitcropxl.output.schema.ExtendedValidationSchemaFactoryWrapper;

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

        List<ExtendedFruit> fruit2List = new ArrayList<>();
        for (int i = 0; i < max; i++) {
            ExtendedFruit fruit2Instance = new ExtendedFruit(
                    i, i + 1, i + 2);
            fruit2List.add(fruit2Instance);
        }

        printJson(irisList);
        printCsv(irisList);
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
