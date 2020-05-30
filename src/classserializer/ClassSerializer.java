/*
 * Copyright (C) 2020 Ian Martinez
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package classserializer;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Properties;
import java.beans.*;
import java.lang.reflect.InvocationTargetException;

/**
 * Provide a simple way of serializing and deserializing classes via properties
 * without any complicated dependencies like JAXB.
 *
 * @author Ian Martinez
 */
public class ClassSerializer {

    private boolean skipUnknownTypes = true;
    private boolean ignoreMissingValues = false;
    private final ArrayList<TypeSerializer> typeSerializers = new ArrayList<>();

    public ClassSerializer() {

    }

    public void read(Class<?> targetClassType, Object targetObject, String fileName) {
        try {
            var properties = new Properties();
            try (var propertyStream = new FileInputStream(fileName)) {
                properties.load(propertyStream);
            }
            
            var propertyDescriptors = Introspector.getBeanInfo(targetClassType).getPropertyDescriptors();
            for (var descriptor : propertyDescriptors) {
                if (descriptor.getReadMethod() != null && !descriptor.getName().equals("class")) {
                    var writeMethod = descriptor.getWriteMethod();
                    if (isSerializable(descriptor)) {
                        var value = getValue(properties, descriptor.getName(), descriptor.getPropertyType());
                        if(value != null)
                            writeMethod.invoke(targetObject, value);
                    }
                }
            }
        } catch (IntrospectionException | IOException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            throw new RuntimeException("Error reading class from file '" + e + "'", e);
        }
    }

    public void write(Class<?> targetClassType, Object targetObject, String fileName) {
        try {
            var properties = new Properties();
            var propertyDescriptors = Introspector.getBeanInfo(targetClassType).getPropertyDescriptors();

            for (var descriptor : propertyDescriptors) {
                if (descriptor.getReadMethod() != null && !descriptor.getName().equals("class")) {
                    var readMethod = descriptor.getReadMethod();
                    if (isSerializable(descriptor)) {
                        setValue(properties, descriptor.getName(), descriptor.getPropertyType(), readMethod.invoke(targetObject));
                    }
                }
            }

            try (var propertyStream = new FileOutputStream(fileName)) {
                properties.store(propertyStream, "Serialized: " + targetClassType.getName());
            }
        } catch (IntrospectionException | IOException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            throw new RuntimeException("Error writing class '" + targetClassType.getName() + "' to file '" + e + "'", e);
        }
    }
    
    private boolean isSerializable(PropertyDescriptor descriptor) {
        return (descriptor.getReadMethod() != null) && (descriptor.getWriteMethod() != null);
    }

    private Object getValue(Properties properties, String name, Class<?> type) {
        var value = properties.getProperty(name);

        if (value == null && !ignoreMissingValues) {
            throw new IllegalArgumentException("Missing value with name: " + name);
        } else if (type == String.class) {
            return value;
        } else if (type == boolean.class) {
            return Boolean.parseBoolean(value);
        } else if (type == int.class) {
            return Integer.parseInt(value);
        } else if (type == float.class) {
            return Float.parseFloat(value);
        } else { // Try to find a serializer for the type and parse it
            for (var serializer : typeSerializers) {
                if (serializer.matches(type)) {
                    return serializer.parse(value);
                }
            }
        }

        if (skipUnknownTypes || value == null) {
            return null;
        } else {
            throw new IllegalArgumentException("Unknown type in class: " + type.getName());
        }
    }

    private void setValue(Properties properties, String name, Class<?> type, Object value) {
        String serializedValue = null;

        if (type == String.class || type == boolean.class || type == int.class || type == float.class) {
            serializedValue = value.toString();
        } else { // Try to find a serializer for the type and parse it
            for (var serializer : typeSerializers) {
                if (serializer.matches(type)) {
                    serializedValue = serializer.serialize(value);
                }
            }
        }

        if (serializedValue != null) {
            properties.setProperty(name, serializedValue);
        } else if (!skipUnknownTypes) {
            throw new IllegalArgumentException("Unknown type in class: " + type.getName());
        }
    }

    /**
     * @return if unknown types should be skipped
     */
    public boolean skipsUnknownTypes() {
        return skipUnknownTypes;
    }

    /**
     * @param skipUnknownTypes if unknown types should be skipped
     */
    public void setSkipUnknownTypes(boolean skipUnknownTypes) {
        this.skipUnknownTypes = skipUnknownTypes;
    }

    /**
     * @return if missing values should be ignored when reading
     */
    public boolean ignoresMissingValues() {
        return ignoreMissingValues;
    }

    /**
     * @param ignoreMissingValues if missing values should be ignored when
     * reading
     */
    public void setIgnoreMissingValues(boolean ignoreMissingValues) {
        this.ignoreMissingValues = ignoreMissingValues;
    }

    public ArrayList<TypeSerializer> getTypeSerializers() {
        return typeSerializers;
    }

    public void addSerializer(TypeSerializer... serializers) {
        typeSerializers.addAll(Arrays.asList(serializers));
    }

    public void removeSerializer(TypeSerializer... serializers) {
        for (var serializer : serializers) {
            typeSerializers.remove(serializer);
        }
    }

    public TypeSerializer getSerializerFor(Class<?> type) {
        for (var serializer : typeSerializers) {
            if (serializer.matches(type)) {
                return serializer;
            }
        }

        return null;
    }
}