package org.zapodot.jackson.java8;

import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.ser.Serializers;

import java.util.Optional;

/**
 * @author R.J. Lorimer [rj@realjenius.com]
 */
public class JavaOptionalSerializers extends Serializers.Base {
    @Override
    public JsonSerializer<?> findSerializer(SerializationConfig config, JavaType type, BeanDescription beanDesc) {
        Class<?> raw = type.getRawClass();
        if(Optional.class.isAssignableFrom(raw)) {
            return new JavaOptionalSerializer(type);
        }
        return super.findSerializer(config, type, beanDesc);
    }
}
