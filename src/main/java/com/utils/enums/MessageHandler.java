package com.utils.enums;

public enum MessageHandler {
    NOT_NULL(" no puede ser nulo."),
    NOT_EMPTY(" no puede estar vacio."),
    MAX_CHAR(" tiene más caracteres."),
    NUMBER(" tiene que ser un numero positivo."),
    FORMAT(" tiene el formato incorrecto");

    private String message;

    MessageHandler(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
