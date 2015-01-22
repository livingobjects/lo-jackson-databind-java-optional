package org.zapodot.jackson.java8;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.afterburner.AfterburnerModule;
import org.junit.Before;
import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.*;

/**
 * @author zapodot
 */
public class AfterBurnerInteroperabilityTest {

    private ObjectMapper objectMapper;

    public static class Bean {

        public static final String PRESENT_VALUE = "present";
        @JsonProperty
        private Optional<String> empty = Optional.empty();

        @JsonProperty
        private Optional<String> notSet;

        @JsonProperty
        private Optional<String> present = Optional.of(PRESENT_VALUE);


    }

    @Before
    public void setUp() throws Exception {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new AfterburnerModule());
        objectMapper.registerModule(new JavaOptionalModule());
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    @Test
    public void testOptionalsWithAfterBurner() throws Exception {
        final String json = objectMapper.writeValueAsString(new Bean());
        assertEquals("{\"present\":\"present\"}", json);
        final Bean bean = objectMapper.readValue(json, Bean.class);

        assertFalse(bean.empty.isPresent());
        assertTrue(bean.present.isPresent());
        assertNull(bean.notSet);

    }
}
