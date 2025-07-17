package io.github.fruitcropxl.output.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

/**
 * Handles writing properties of POJO types to a CSV file each time step.
 * Each writer instance should only be responsible for one file, utilizing one
 * mapper.
 */
public class StepwiseCsvWriter implements Serializable {
    /**
     * The file which the CSV output is written to.
     */
    private File file;

    private CsvMapper mapper;

    private CsvSchema schema;

    /**
     * @param filePath Path to the CSV file.
     * @param mapper   Mapper to use for reading objects to CSV.
     */
    public StepwiseCsvWriter(String filePath, CsvMapper mapper) {
        this.file = new File(filePath);
        this.mapper = mapper;
    }

    /**
     * Change schema configuration.
     * 
     * @param schema
     * @return
     */
    public StepwiseCsvWriter setSchema(CsvSchema schema) {
        this.schema = schema;
        return this;
    }

    /**
     * Delete file if the condition is satisfied. Useful for clearing
     * data at the start of a simulation.
     * 
     * @param condition
     */
    public StepwiseCsvWriter deleteFileIf(boolean condition) {
        if (condition) {
            file.delete();
        }
        return this;
    }

    public void writeArray(Object[] object) {
        writeList(Arrays.asList(object)); // Convert and write as List
    }

    public void writeList(List<?> list) {
        if (list.isEmpty()) {
            try {
                throw new Exception("Writing from an empty list");
            } catch (Exception e) {
                e.printStackTrace();
            }
            return;
        }

        schema = mapper.schemaFor(list.get(0).getClass());
        write(list);
    }

    /**
     * Write object to CSV.
     * 
     * @param object
     */
    public void write(Object object) {
        if (schema == null) {
            schema = mapper.schemaFor(object.getClass());
        }

        boolean fileExist = file.isFile(); // Check here as FileWriter creates a file

        try (FileWriter fileWriter = new FileWriter(file.getPath(), true)) {
            if (!fileExist) { // Write header if writing to a new file
                fileWriter.write(getHeader()); // Write header
            }

            fileWriter.append(getHeaderlessCsv(object));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns the CSV header for the schema.
     * 
     * @return
     * @throws JsonProcessingException
     */
    private String getHeader() throws JsonProcessingException {
        String headers = mapper.writer(schema.withHeader()).writeValueAsString(null);
        schema.withoutHeader(); // Reset to no headers

        return headers;
    }

    /**
     * Returns the object as CSV without a header row.
     * 
     * @param object
     * @return
     * @throws JsonProcessingException
     */
    private String getHeaderlessCsv(Object object)
            throws JsonProcessingException {
        return mapper.writer().with(schema.withoutHeader()).writeValueAsString(object);
    }
}
