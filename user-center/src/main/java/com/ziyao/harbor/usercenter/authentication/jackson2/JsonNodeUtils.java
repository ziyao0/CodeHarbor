package com.ziyao.harbor.usercenter.authentication.jackson2;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.*;

/**
 * @author ziyao
 * @since 2024/06/14 10:47:30
 */
public abstract class JsonNodeUtils {


    static final TypeReference<Set<String>> STRING_SET = new TypeReference<>() {
    };

    static final TypeReference<Map<String, Object>> STRING_OBJECT_MAP = new TypeReference<>() {
    };

    static Long findLongValue(JsonNode jsonNode, String fieldName) {
        if (jsonNode == null) {
            return null;
        }
        JsonNode value = jsonNode.findValue(fieldName);
        return (value != null && value.isNumber()) ? value.asLong() : null;
    }

    static Integer findIntegerValue(JsonNode jsonNode, String fieldName) {
        if (jsonNode == null) {
            return null;
        }
        JsonNode value = jsonNode.findValue(fieldName);
        return (value != null && value.isNumber()) ? value.asInt() : null;
    }

    static String findStringValue(JsonNode jsonNode, String fieldName) {
        if (jsonNode == null) {
            return null;
        }
        JsonNode value = jsonNode.findValue(fieldName);
        return (value != null && value.isTextual()) ? value.asText() : null;
    }

    static List<String> findArrayValue(JsonNode jsonNode, String fieldName) {
        if (jsonNode == null) {
            return null;
        }
        JsonNode node = jsonNode.findValue(fieldName);
        if (node.isArray()) {
            List<String> result = new ArrayList<>();
            Iterator<JsonNode> elements = node.elements();
            if (elements.hasNext()) {

                JsonNode element = elements.next();
                result.add(element.asText());
            }
            return result;
        }
        return null;
    }

    static <T> T findValue(JsonNode jsonNode, String fieldName, TypeReference<T> valueTypeReference,
                           ObjectMapper mapper) {
        if (jsonNode == null) {
            return null;
        }
        JsonNode value = jsonNode.findValue(fieldName);
        return (value != null && value.isContainerNode()) ? mapper.convertValue(value, valueTypeReference) : null;
    }

    static JsonNode findObjectNode(JsonNode jsonNode, String fieldName) {
        if (jsonNode == null) {
            return null;
        }
        JsonNode value = jsonNode.findValue(fieldName);
        return (value != null && value.isObject()) ? value : null;
    }


    private JsonNodeUtils() {
    }
}
