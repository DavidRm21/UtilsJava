package com.utils.validator;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

@Component
public class JsonNodeValidator2 {

    private final Map<String, List<Consumer<JsonNode>>> validationRules = ValidationRules.createValidationRules();

    public void validate(JsonNode jsonNode) {
        validateNode(jsonNode, "");
    }

    private void validateNode(JsonNode node, String parentKey) {
        if (node.isObject()) {
            node.fields().forEachRemaining(entry -> {
                String fieldName = entry.getKey();
                JsonNode fieldNode = entry.getValue();
                String fullKey = parentKey.isEmpty() ? fieldName : parentKey + "." + fieldName;

                // Validar el campo si tiene validaciones definidas
                validationRules.getOrDefault(fullKey, List.of())
                        .forEach(validator -> validator.accept(fieldNode));

                // Si el campo tiene hijos, validar los hijos
                validateNode(fieldNode, fullKey);
            });
        } else {
            // Validar si el nodo actual es un campo esperado
            validationRules.getOrDefault(parentKey, List.of())
                    .forEach(validator -> validator.accept(node));
        }
    }
}
