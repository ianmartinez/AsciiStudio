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
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Properties;

/**
 * Provide a simple way of serializing and deserializing classes without any
 * complicated dependencies like JAXB.
 *
 * @author Ian Martinez
 */
public class ClassSerializer {

    private boolean skipUnknownTypes = false;
    private ArrayList<TypeSerializer> typeSerializers = new ArrayList<>();

    public ClassSerializer() {

    }

    public void read(Class<?> targetClassType, Object targetObject, String fileName) {
        try {
            var properties = new Properties();
            try (var propertyStream = new FileInputStream(fileName)) {
                properties.load(propertyStream);
            }
            
            for (Field field : targetClassType.getDeclaredFields()) {
                if (!Modifier.isStatic(field.getModifiers())) {                   
                    field.set(targetObject, getValue(properties, field.getName(), field.getType()));
                }
            }
        } catch (IOException | IllegalAccessException | IllegalArgumentException | SecurityException e) {
            throw new RuntimeException("Error loading class from file: " + e, e);
        }
    }

    private Object getValue(Properties properties, String name, Class<?> type) {
        var value = properties.getProperty(name);

        if (value == null) {
            throw new IllegalArgumentException("Missing configuration value: " + name);
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

        if (skipUnknownTypes) {
            return null;
        } else {
            throw new IllegalArgumentException("Unknown type in class: " + type.getName());
        }
    }
    
    private void setValue(Properties properties, String name, Class<?> type, Object value) {
    }

    public void write(Class<?> sourceClass, String fileName) {

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

    public ArrayList<TypeSerializer> getTypeSerializers() {
        return typeSerializers;
    }

    public void addSerializer(TypeSerializer... serializers) {
        typeSerializers.addAll(Arrays.asList(serializers));
    }

    public void removeSerializer(TypeSerializer... serializers) {
        for(var serializer: serializers) {
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
