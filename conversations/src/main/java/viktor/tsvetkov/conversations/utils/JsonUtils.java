package viktor.tsvetkov.conversations.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

public class JsonUtils {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final ObjectWriter objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();

    public <T> T parseObject(String object, Class<T> className) throws JsonProcessingException {
        return objectMapper.readValue(object, className);
    }

    public String toJson(Object object) throws JsonProcessingException {
        return objectWriter.writeValueAsString(object);
    }
}
