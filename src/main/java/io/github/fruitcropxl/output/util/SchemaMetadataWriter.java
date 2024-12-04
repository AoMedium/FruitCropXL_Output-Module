package io.github.fruitcropxl.output.util;

import java.io.FileWriter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.jsonSchema.jakarta.JsonSchema;

import io.github.fruitcropxl.output.schema.ExtendedValidationSchemaFactoryWrapper;

/**
 * Handles writing the schema metadata of the given POJO type.
 * Each writer should only be responsible for writing to one file.
 */
public class SchemaMetadataWriter {

    /**
     * Path to the metadata file.
     */
    private String filePath;

    /**
     * The class of the POJO type to write out.
     * Specify class to avoid using generics as GroIMP does not support them.
     */
    private Class pojoType;

    /**
     * 
     * @param filePath Path to the metadata file.
     * @param pojoType The class of the POJO type to write out.
     */
    public SchemaMetadataWriter(String filePath, Class pojoType) {
        this.filePath = filePath;
        this.pojoType = pojoType;
    }

    public void writeMetadata() {
        ObjectMapper mapper = new ObjectMapper();
        ExtendedValidationSchemaFactoryWrapper schema = new ExtendedValidationSchemaFactoryWrapper();

        try (FileWriter fileWriter = new FileWriter(filePath)) {
            mapper.acceptJsonFormatVisitor(pojoType, schema);
            JsonSchema jsonSchema = schema.finalSchema();

            String schemaValue = mapper
                    .writerWithDefaultPrettyPrinter()
                    .writeValueAsString(jsonSchema);

            fileWriter.write(schemaValue);
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }
}
