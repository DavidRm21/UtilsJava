package com.utils.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.utils.utils.DateUtils;
import com.utils.utils.EncoderUtils;
import com.utils.utils.JsonNodeValidator;
import com.utils.validator.JsonNodeValidator2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/utils")
public class UtilsController {

    private final JsonNodeValidator jsonNodeValidator;

    public UtilsController(JsonNodeValidator jsonNodeValidator) {
        this.jsonNodeValidator = jsonNodeValidator;
    }

    @PostMapping
    public ResponseEntity<String> validateFields(@RequestBody String request){
        /*
            {
  "personalInfo": {
    "firstName": null,
    "lastName": "Doe"
  },
  "contactInfo": {
    "email": "notanemail@mail.com",
    "phoneNumber": {
        "line1": 12345,
        "line2": 1234535456
    }
  },
  "age": 5
}
        */

        JsonNode jsonNode = EncoderUtils.toJsonNode(request);
        jsonNodeValidator.validate(jsonNode);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/validate")
    public ResponseEntity<String> validate(@RequestBody String request){
        ObjectNode objectNode = null;
        JsonNode jsonNode = EncoderUtils.toJsonNode(request);
        JsonNodeValidator2 validator = new JsonNodeValidator2();
        validator.validate(jsonNode);


        String date = Optional.ofNullable(jsonNode.get("date"))
                .map(JsonNode::textValue)
                .orElse(null);

        if (date != null){
            objectNode = (ObjectNode) jsonNode;
            objectNode.put("date", DateUtils.formatDateToMMYY(date) );
        }

        return ResponseEntity.ok(EncoderUtils.toJson( objectNode));
    }


}
