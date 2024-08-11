package com.utils.validator;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.regex.Pattern;

public class ValidationUtils {

    private static final Pattern EMAIL_PATTERN = Pattern.compile(FieldConst.EMAIL_PATTERN);



    public static Consumer<JsonNode> validateNotBlank(String fieldName) {
        return node -> {
            String value = Optional.ofNullable(node)
                    .map(JsonNode::asText)
                    .orElse("");
            if (value.trim().isEmpty() || node.isNull()) {
                throw new RuntimeException("El campo " + fieldName + " no puede estar vacío.");
            }
        };
    }

    public static Consumer<JsonNode> validateNotNull(String fieldName) {
        return node -> {
            if (node.isNull()) {
                throw new RuntimeException("El campo " + fieldName + " no puede ser nulo.");
            }
        };
    }

    public static Consumer<JsonNode> validateLengthInRange(int min, int max, String fieldName) {
        return node -> {
            if (node.isNull()) return;

            String value = Optional.ofNullable(node)
                    .map(JsonNode::asText)
                    .orElse("");
            if (value.length() < min || value.length() > max) {
                throw new RuntimeException("El campo " + fieldName + " debe tener entre " + min + " y " + max + " caracteres.");
            }

        };
    }

    public static Consumer<JsonNode> validatePositiveInteger(String fieldName) {
        return node -> {
            if (node.isNull()) return;

            Optional.ofNullable(node)
                    .filter(JsonNode::isValueNode)
                    .map(JsonNode::asInt)
                    .filter(value -> value > 0)
                    .orElseThrow(() -> new RuntimeException("El campo " + fieldName + " debe ser un número positivo."));
        };
    }

    public static Consumer<JsonNode> validateMaxLength(int maxLength, String fieldName) {
        return node -> {
            if (node.isNull()) return;

            String value = Optional.ofNullable(node)
                    .map(JsonNode::asText)
                    .orElse("");
            if (value.length() > maxLength) {
                throw new RuntimeException("El campo " + fieldName + " no puede tener más de " + maxLength + " caracteres.");
            }
        };
    }

    public static Consumer<JsonNode> validateEmailFormat(String fieldName) {
        return node -> {
            String value = Optional.ofNullable(node)
                    .map(JsonNode::asText)
                    .orElse("");
            if (!EMAIL_PATTERN.matcher(value).matches()) {
                throw new RuntimeException("El campo " + fieldName + " no tiene un formato de correo electrónico válido.");
            }
        };
    }
}
