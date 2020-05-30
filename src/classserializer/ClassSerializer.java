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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Properties;
import java.lang.reflect.InvocationTargetException;
import java.beans.*;

/**
 * Provides a simple way of serializing and deserializing classes via properties
 * without any complicated dependencies like JAXB.
 *
 * It will serialize every property that has both a getter and a setter into a
 * Java properties file. By default it includes support for just primitives, but
 * it can easily be extended by adding classes that implement the interface
 * TypeSerializer to support additional types.
 *
 *
 * @author Ian Martinez
 */
public final class ClassSerializer {

    private boolean skipUnknownTypes = true;
    private boolean ignoreMissingValues = false;
    private final ArrayList<TypeSerializer> typeSerializers = new ArrayList<>();

    /**
     * Create a ClassSerializer to serialize a class.
     *
     * @param serializers any additional serializers you want to use
     */
    public ClassSerializer(TypeSerializer... serializers) {
        addSerializers(serializers);
    }

    /**
     * Read a properties file containing all of the serializable values of a
     * class and set all of the serializable properties in that class to the
     * values parsed from the properties file.
     *
     * @param targetClassType the type of the class
     * @param targetObject the instance of the class to be written to
     * @param fileName the location of the properties file to read
     */
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
                        if (value != null) {
                            writeMethod.invoke(targetObject, value);
                        }
                    }
                }
            }
        } catch (IntrospectionException | IOException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            throw new RuntimeException("Error reading class from file '" + e + "'", e);
        }
    }

    /**
     * Write a properties file containing all of the serializable values in a
     * given class instance.
     *
     * @param targetClassType the type of the class
     * @param targetObject the instance of the class whose values are to be
     * serialized
     * @param fileName the location to save the properties file
     */
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

    /**
     * If a property is serializable (i.e. has both a getter and a setter)
     *
     * @param descriptor the property descriptor
     *
     * @return true if the property is serializable, false if not
     */
    public boolean isSerializable(PropertyDescriptor descriptor) {
        return (descriptor.getReadMethod() != null) && (descriptor.getWriteMethod() != null);
    }

    /**
     * Get the deserialized value by a name in a properties list.
     *
     * @param properties the properties list
     * @param name the name of the property
     * @param type the type of the value to be returned
     *
     * @return the value that was parsed from the properties list
     */
    private Object getValue(Properties properties, String name, Class<?> type) {
        var value = properties.getProperty(name);

        if (value == null && !ignoreMissingValues) {
            throw new IllegalArgumentException("Missing value with name '" + name + "'");
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
            throw new IllegalArgumentException("Unknown type in class: '" + type.getName() + "'");
        }
    }

    /**
     * Set the value of a property in a properties list to a serialized value.
     *
     * @param properties the properties list
     * @param name the name of the property
     * @param type the type of value being serialized
     * @param value the value to serialize
     */
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
            throw new IllegalArgumentException("Unknown type in class '" + type.getName() + "'");
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

    /**
     * Get the available TypeSerializers.
     *
     * @return the TypeSerializers that can be used
     */
    public ArrayList<TypeSerializer> getTypeSerializers() {
        return typeSerializers;
    }

    /**
     * Add a set of serializers.
     *
     * @param serializers the serializers to add
     */
    public void addSerializers(TypeSerializer... serializers) {
        typeSerializers.addAll(Arrays.asList(serializers));
    }

    /**
     * Remove a set of serializers.
     *
     * @param serializers the serializers to remove
     */
    public void removeSerializers(TypeSerializer... serializers) {
        for (var serializer : serializers) {
            typeSerializers.remove(serializer);
        }
    }

    /**
     * Get a serializer for a given type.
     *
     * @param type the type to serialize
     *
     * @return the serializer, null if none could be found matching the type
     */
    public TypeSerializer getSerializerFor(Class<?> type) {
        for (var serializer : typeSerializers) {
            if (serializer.matches(type)) {
                return serializer;
            }
        }

        return null;
    }

}
