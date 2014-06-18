package org.zapodot.jackson.java8;

import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.deser.Deserializers;

import java.util.Optional;

/**
 * @author R.J. Lorimer [rj@realjenius.com]
 */
public class JavaOptionalDeserializers extends Deserializers.Base {

    @Override
    public JsonDeserializer<?> findBeanDeserializer(JavaType type, DeserializationConfig config, BeanDescription beanDesc) throws JsonMappingException {
        Class<?> raw = type.getRawClass();
        if(Optional.class.isAssignableFrom(raw)){
            return new JavaOptionalDeserializer(type);
        }
        return super.findBeanDeserializer(type, config, beanDesc);
    }
}