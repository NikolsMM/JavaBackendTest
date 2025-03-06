package com.nikols.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.Map;

public class JsonHelper {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static <T> T mergeJson(T originalObject, Map<String, Object> updates, Class<T> clazz) {
        try {
            // Convertimos el objeto original a un ObjectNode (funciona como JSON editable)
            ObjectNode objectNode = objectMapper.valueToTree(originalObject);

            // Fusionamos los valores del `updates` en el JSON a través de putPOJO que maneja los datos sin transformarlos como lo haria un put ( que trasnfiorma a strings )
            updates.forEach((key, value) -> {
                if (value != null) {
                    objectNode.putPOJO(key, value);
                } else {
                    objectNode.putNull(key);
                }
            });
            //Si mandamos algo que es null intencionadamente, se sobreescribe como null.
            //Es decir, { "name":"UWU" } sobreescribirá name |--> UWU y el resto campo |--> campo.
            //Sin embargo, {"name": ""/null} hará name |--> ""/null u el resto campo |--> campo

            // Convertimos de nuevo el JSON actualizado a un objeto del tipo `T`
            return objectMapper.treeToValue(objectNode, clazz);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error processing JSON: " + e.getMessage(), e);
        }
    }
}
