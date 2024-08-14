package com.utils.validator;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;

@Component
public class JsonNodeValidator2 {

    private Map<String, List<Consumer<JsonNode>>> validationRules;

    public void validate(JsonNode jsonNode, String strategy) {
        this.validationRules = ValidationRules.createValidationRules(strategy);
        validateNode(jsonNode, "");
    }

    private void validateNode(JsonNode node, String parentKey) {
        validationRules.forEach((fullKey, validators) -> {
            String[] keyParts = fullKey.split("\\.");
            JsonNode currentNode = node;
            StringBuilder currentKey = new StringBuilder();

            for (int i = 0; i < keyParts.length; i++) {
                String keyPart = keyParts[i];
                if (!currentNode.has(keyPart)) {
                    ((ObjectNode) currentNode).putNull(keyPart);
//                    throw new RuntimeException("El campo requerido " + fullKey + " no está presente.");
                }
                currentNode = currentNode.get(keyPart);
                currentKey.append(keyPart);

                // Si hemos llegado al último nivel, validamos
                if (i == keyParts.length - 1) {
                    for (Consumer<JsonNode> validator : validators) {
                        validator.accept(currentNode);
                    }
                }

                // Agregar un punto si no es la última clave
                if (i < keyParts.length - 1) {
                    currentKey.append(".");
                }
            }
        });
    }
}
