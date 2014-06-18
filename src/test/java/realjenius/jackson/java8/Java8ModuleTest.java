package realjenius.jackson.java8;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class Java8ModuleTest {

    public static class Bean {

        public static final String PRESENT_VALUE = "present";
        @JsonProperty
        private Optional<String> empty = Optional.empty();

        @JsonProperty
        private Optional<String> notSet;

        @JsonProperty
        private Optional<String> present = Optional.of(PRESENT_VALUE);


    }

    @Test
    public void testAutoDetect() throws Exception {
        assertEquals(1L, ObjectMapper.findModules().stream().filter(m -> m instanceof Java8Module).count());

    }

    @Test
    public void testSerialize() throws Exception {
        final Bean bean = new Bean();
        final ObjectMapper mapper = new ObjectMapper().findAndRegisterModules();
        final JsonNode node = mapper.readTree(mapper.writeValueAsString(bean));
        assertTrue(node.get("empty").isNull());
        assertTrue(node.get("notSet").isNull());
        assertEquals(Bean.PRESENT_VALUE, node.get("present").asText());
    }

    @Test
    public void testDeserialize() throws Exception {
        final Bean bean = new ObjectMapper().findAndRegisterModules()
                                            .readValue("{\"empty\":null,\"notSet\":null}", Bean.class);
        assertNotNull(bean.empty);
        assertEquals(Optional.empty(), bean.empty);
        assertNotNull(bean.notSet);
        assertEquals(Optional.empty(), bean.notSet);
        assertEquals(Optional.of(Bean.PRESENT_VALUE), bean.present);

    }
}