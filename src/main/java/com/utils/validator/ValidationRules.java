package com.utils.validator;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;


public class ValidationRules {

    private ValidationRules() {
        // No instanciar
    }

    public static Map<String, List<Consumer<JsonNode>>> createValidationRules() {
        return Map.of(
                "personalInfo.firstName", List.of(
                        ValidationUtils.validateNotNull(FieldConst.FIRST_NAME),
                        ValidationUtils.validateNotBlank(FieldConst.FIRST_NAME),
                        ValidationUtils.validateMaxLength(FieldConst.NAME_MAX_LENGTH, FieldConst.FIRST_NAME)
                ),
                "personalInfo.lastName", List.of(
                        ValidationUtils.validateLengthInRange(FieldConst.NAME_MIN_LENGTH, FieldConst.NAME_MAX_LENGTH, FieldConst.LAST_NAME)
                ),
                "age", List.of(
                        ValidationUtils.validatePositiveInteger(FieldConst.AGE)
                ),
                "contactInfo.address", List.of(
                        ValidationUtils.validateMaxLength(FieldConst.ADDRESS_MAX_LENGTH, FieldConst.ADDRESS)
                ),
                "contactInfo.phoneNumber.line1", List.of(
                        ValidationUtils.validateMaxLength(FieldConst.PHONE_MAX_LENGTH, FieldConst.PHONE_LINE1)
                ),
                "contactInfo.phoneNumber.line2", List.of(
                        ValidationUtils.validateMaxLength(FieldConst.PHONE_MAX_LENGTH, FieldConst.PHONE_LINE2)
                ),
                "contactInfo.email", List.of(
                        ValidationUtils.validateNotNull(FieldConst.EMAIL),
                        ValidationUtils.validateEmailFormat(FieldConst.EMAIL)
                )
        );
    }
}
