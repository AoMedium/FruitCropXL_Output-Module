package io.github.fruitcropxl.output.schema;

import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.module.jsonSchema.jakarta.types.IntegerSchema;

import io.github.fruitcropxl.output.annotation.DefaultNumericValue;
import io.github.fruitcropxl.output.annotation.PropertyType;
import io.github.fruitcropxl.output.annotation.Tags;
import io.github.fruitcropxl.output.annotation.Unit;

class ExtendedIntegerSchema extends IntegerSchema {
    public String name;
    public String unit;
    public String propertyType;
    public double defaultValue;
    public String[] tags;

    @Override
    public void enrichWithBeanProperty(BeanProperty beanProperty) {
        super.enrichWithBeanProperty(beanProperty);
        Unit unit = beanProperty.getAnnotation(Unit.class);
        if (unit != null) {
            this.unit = unit.value();
        }

        PropertyType propertyType = beanProperty.getAnnotation(PropertyType.class);
        if (propertyType != null) {
            this.propertyType = propertyType.value();

        }

        DefaultNumericValue defaultValue = beanProperty.getAnnotation(DefaultNumericValue.class);
        if (defaultValue != null) {
            this.defaultValue = defaultValue.value();
        }

        Tags tags = beanProperty.getAnnotation(Tags.class);
        if (tags != null) {
            this.tags = tags.value();
        }

        this.name = beanProperty.getName();
    }
}