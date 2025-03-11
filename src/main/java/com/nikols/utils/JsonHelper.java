package com.nikols.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.Map;

public class JsonHelper {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static <T> void mergeJsonIntoObject(T targetObject, Map<String, Object> updates) {
        try {
            ObjectNode objectNode = (ObjectNode) objectMapper.valueToTree(targetObject);

            updates.forEach((key, value) -> {
                if (objectNode.has(key)) { // Solo actualizar claves existentes
                    if (value != null) {
                        if (value instanceof Number) {
                            // Convertir números a su tipo correcto
                            if (value instanceof Integer) {
                                objectNode.put(key, (Integer) value);
                            } else if (value instanceof Double) {
                                objectNode.put(key, (Double) value);
                            } else {
                                objectNode.put(key, ((Number) value).doubleValue());
                            }
                        } else {
                            objectNode.putPOJO(key, value);
                        }
                    } else {
                        objectNode.putNull(key);
                    }
                }
            });

            objectMapper.readerForUpdating(targetObject).readValue(objectNode.toString());

        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error processing JSON: " + e.getMessage(), e);
        }
    }


}
