package viktor.tsvetkov.conversations.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtils {

    private final ObjectMapper objectMapper = new ObjectMapper();

    public <T> T parseObject(String object, Class<T> className) throws JsonProcessingException {
        return objectMapper.readValue(object, className);
    }
}
