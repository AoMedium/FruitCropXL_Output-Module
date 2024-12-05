package io.github.fruitcropxl.output.util;

import java.io.FileWriter;
import java.io.IOException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

/**
 * Handles writing properties of POJO types to a CSV file each time step.
 * Each writer should only be responsible for writing to one file.
 */
public class StepwiseCsvWriter {

    /**
     * Unique identifier for this writer.
     */
    private String id;

    /**
     * Path to the CSV file.
     */
    private String filePath;

    /**
     * The class of the POJO type to write out.
     * Specify class to avoid using generics as GroIMP does not support them.
     */
    private Class pojoType;

    /**
     * The schema representing the POJO type.
     */
    private CsvSchema schema;

    /**
     * 
     * @param filePath Path to the CSV file.
     * @param pojoType The class of the POJO type to write out.
     */
    public StepwiseCsvWriter(String filePath, Class pojoType) {
        this(filePath, filePath, pojoType);
    }

    /**
     * 
     * @param id       Unique identifier for this writer.
     * @param filePath Path to the CSV file.
     * @param pojoType The class of the POJO type to write out.
     */
    public StepwiseCsvWriter(String id, String filePath, Class pojoType) {
        this.id = id;
        this.filePath = filePath;
        this.pojoType = pojoType;
        this.schema = new CsvMapper().schemaFor(pojoType);

        writeHeader();
    }

    public String getId() {
        return id;
    }

    public CsvSchema getSchema() {
        return schema;
    }

    public Class getPojoType() {
        return pojoType;
    }

    /**
     * Append the properties of the given object (which should have the same POJO
     * type as the type defined in the schema) to the CSV file.
     * 
     * @param object
     */
    public void append(Object object) {
        // Check if object is the appropriate type defined in the schema
        if (!pojoType.isInstance(object)) {
            throw new RuntimeException(object + " is not the correct class type: " + pojoType.getSimpleName());
        }

        try (FileWriter fileWriter = new FileWriter(filePath, true)) {
            fileWriter.append(getHeaderlessCsv(object));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void writeHeader() {
        try (FileWriter fileWriter = new FileWriter(filePath)) {
            fileWriter.write(getHeader()); // Write header
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getHeader() throws JsonProcessingException {
        String headers = new CsvMapper().writer(schema.withHeader()).writeValueAsString(null);
        schema.withoutHeader(); // Reset to no headers

        return headers;
    }

    private String getHeaderlessCsv(Object object) throws JsonProcessingException {
        return new CsvMapper().writer().with(schema.withoutHeader()).writeValueAsString(object);
    }
}
