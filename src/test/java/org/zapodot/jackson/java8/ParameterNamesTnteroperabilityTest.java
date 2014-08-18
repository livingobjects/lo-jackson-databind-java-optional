package org.zapodot.jackson.java8;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import org.junit.Before;
import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.assertFalse;

/**
 * This source code is the property of NextGenTel AS
 *
 * @author sek
 */
public class ParameterNamesTnteroperabilityTest {

    private ObjectMapper objectMapper;

    public static class Bean {
        public final String property;
        public final Optional<String> anotherProperty;

        @JsonCreator
        public Bean(final String property, final Optional<String> anotherProperty) {
            this.property = property;
            this.anotherProperty = anotherProperty;
        }
    }

    public static class AnotherBean {
        public final Bean bean;
        public final Optional<String> description;

        @JsonCreator
        public AnotherBean(final Bean bean, final Optional<String> description) {
            this.bean = bean;
            this.description = description;
        }
    }

    @Before
    public void setUp() throws Exception {
        objectMapper = new ObjectMapper().registerModules(new ParameterNamesModule(), new JavaOptionalModule());
    }

    @Test
    public void testSimpleBean() throws Exception {
        final Bean beanInstance = objectMapper.readValue("{\"property\": \"value\"}", Bean.class);
        assertFalse(beanInstance.anotherProperty.isPresent());
    }

    @Test
    public void testBundledBean() throws Exception {
        final AnotherBean anotherBean = objectMapper.readValue("{\"bean\": {\"property\": \"value\"}}", AnotherBean.class);
        assertFalse(anotherBean.bean.anotherProperty.isPresent());

    }
}
