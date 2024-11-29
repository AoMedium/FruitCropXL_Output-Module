package io.github.fruitcropxl.util;

import java.io.FileWriter;
import java.io.IOException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

public class StepwiseCsvWriter {

    private String id;

    private String filePath;

    private CsvSchema schema;

    public StepwiseCsvWriter(String filePath, CsvSchema schema) {
        this(filePath, filePath, schema);
    }

    public StepwiseCsvWriter(String id, String filePath, CsvSchema schema) {
        this.id = id;
        this.filePath = filePath;
        this.schema = schema;

        setupFile();
    }

    public String getId() {
        return id;
    }

    public void writeStep(Object o) {
        try (FileWriter fileWriter = new FileWriter(filePath, true)) {
            fileWriter.append(getHeaderlessCsv(o));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void setupFile() {
        try (FileWriter fileWriter = new FileWriter(filePath)) {
            fileWriter.append(getHeaders()); // Write header
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getHeaders() {
        try {
            String headers = new CsvMapper().writer(schema.withHeader()).writeValueAsString(null);
            schema.withoutHeader(); // Reset to no headers

            return headers;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String getHeaderlessCsv(Object object) {
        try {
            return new CsvMapper().writer().with(schema.withoutHeader()).writeValueAsString(object);
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }
}
