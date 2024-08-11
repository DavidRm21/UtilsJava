package com.utils.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.utils.enums.MessageHandler;
import com.utils.enums.StaticValue;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

@Slf4j
@Component
public class JsonNodeValidator {

    // Define las validaciones para cada campo
    private final Map<String, List<Consumer<JsonNode>>> validationRules = new HashMap<>();

    public JsonNodeValidator() {

        validationRules.put("personalInfo.firstName", Arrays.asList(
                node -> {
                    String value = node.asText();
                    if (node.isNull() || value.trim().isEmpty()) {
                        throw new RuntimeException("El campo firstName no puede estar vacío o ser nulo.");
                    }
                }
        ));

        validationRules.put("personalInfo.lastName", Arrays.asList(
                node -> {
                    String value = node.asText();
                    if (value.length() < 2 || value.length() > StaticValue.NAME_LENGTH) {
                        throw new RuntimeException("El campo lastName debe tener entre 2 y 50 caracteres.");
                    }
                }
        ));

        validationRules.put("age", Arrays.asList(
                node -> {
                    // Esta permitido que puede ser un valor nulo
                    if(node == null || node.isNull()) return;

                    // Si esta presente debe ser mayor a 0
                    if (node.asInt() <= 0 ) {
                        throw new RuntimeException("El campo age debe ser un número positivo.");
                    }
                }
        ));

        validationRules.put("contactInfo.address", Arrays.asList(
                node -> {
                    String value = node.asText();
                    if (value.length() > 50) {
                        throw new RuntimeException("El campo address no puede tener más de 50 caracteres.");
                    }
                }
        ));

        validationRules.put("contactInfo.phoneNumber.line1", Arrays.asList(
                node -> {
                    String value = node.asText();
                    if (value.length() > 6) {
                        throw new RuntimeException("El campo line1 no puede tener más de 6 caracteres.");
                    }
                }
        ));

        validationRules.put("contactInfo.phoneNumber.line2", Arrays.asList(
                node -> {
                    String value = node.asText();
                    if (value.length() > 6) {
                        throw new RuntimeException("El campo line2 no puede tener más de 12 caracteres.");
                    }
                }
        ));

        validationRules.put("contactInfo.email", Arrays.asList(
                node -> {
                    String value = node.asText();
                    if (!value.matches("^[A-Za-z0-9._%-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}$")) {
                        throw new RuntimeException("El campo email " + MessageHandler.FORMAT.getMessage());
                    }
                }
        ));
    }

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
                if (validationRules.containsKey(fullKey)) {
                    validationRules.get(fullKey).forEach(validator -> validator.accept(fieldNode));
                } else {
                    // Si el campo tiene hijos, validar los hijos
                    validateNode(fieldNode, fullKey);
                }
            });
        } else {
            // Validar si el nodo actual es un campo esperado
            if (validationRules.containsKey(parentKey)) {
                validationRules.get(parentKey).forEach(validator -> validator.accept(node));
            }
        }
    }
}
