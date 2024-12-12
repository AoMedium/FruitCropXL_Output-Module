package io.github.fruitcropxl.output.util;

import java.io.File;
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
     * The CsvMapper used for writing the output.
     */
    private CsvMapper mapper;

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
        this.mapper = new CsvMapper();
        this.setMapper(this.mapper);
    }

    /**
     * Write object to CSV without needing to setup the writer.
     * 
     * @param object
     * @param filePath
     * @param mapper
     */
    public static void write(Object object, String filePath, CsvMapper mapper) {

        File file = new File(filePath);
        boolean fileExist = file.isFile(); // Check here as FileWriter creates a file

        CsvSchema schema = mapper.schemaFor(object.getClass());

        try (FileWriter fileWriter = new FileWriter(filePath, true)) {
            if (!fileExist) { // Write header if writing to a new file
                fileWriter.write(StepwiseCsvWriter.getHeader(mapper, schema)); // Write header
            }

            fileWriter.append(StepwiseCsvWriter.getHeaderlessCsv(object, mapper, schema));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Delete file if the condition is satisfied. Useful for clearing
     * data at the start of a simulation.
     * 
     * @param condition
     * @param filePath
     */
    public static void deleteIf(boolean condition, String filePath) {
        if (condition) {
            File file = new File(filePath);
            file.delete();
        }
    }

    public String getId() {
        return id;
    }

    /**
     * Mapper may have its visibility updated, which requires the schema to be
     * recreated.
     */
    public void setMapper(CsvMapper mapper) {
        this.mapper = mapper;
        schema = this.mapper.schemaFor(pojoType);

        writeHeader();
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
        checkValidObject(object, pojoType);

        try (FileWriter fileWriter = new FileWriter(filePath)) {
            fileWriter.append(StepwiseCsvWriter.getHeaderlessCsv(object, mapper, schema));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Check if object is the appropriate type defined in the schema.
     * 
     * @param object
     * @param pojoType
     */
    private static void checkValidObject(Object object, Class pojoType) {
        if (!pojoType.isInstance(object)) {
            throw new RuntimeException(object + " is not the correct class type: " + pojoType.getSimpleName());
        }
    }

    private void writeHeader() {
        try (FileWriter fileWriter = new FileWriter(filePath)) {
            fileWriter.write(StepwiseCsvWriter.getHeader(mapper, schema)); // Write header
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String getHeader(CsvMapper mapper, CsvSchema schema) throws JsonProcessingException {
        String headers = mapper.writer(schema.withHeader()).writeValueAsString(null);
        schema.withoutHeader(); // Reset to no headers

        return headers;
    }

    private static String getHeaderlessCsv(Object object, CsvMapper mapper, CsvSchema schema)
            throws JsonProcessingException {
        return mapper.writer().with(schema.withoutHeader()).writeValueAsString(object);
    }
}
