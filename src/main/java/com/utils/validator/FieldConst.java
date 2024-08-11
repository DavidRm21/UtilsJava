package com.utils.validator;

public class FieldConst {

    // Patrones Regex
    public static final String EMAIL_PATTERN = "^[A-Za-z0-9._%-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}$";

    // Nombres de los campos
    public static final String FIRST_NAME = "firstName";
    public static final String LAST_NAME = "lastName";
    public static final String AGE = "age";
    public static final String ADDRESS = "address";
    public static final String PHONE_LINE1 = "line1";
    public static final String PHONE_LINE2 = "line2";
    public static final String EMAIL = "email";

    // Reglas de longitud
    public static final byte NAME_MAX_LENGTH = 50;
    public static final byte NAME_MIN_LENGTH = 3;
    public static final byte ADDRESS_MAX_LENGTH = 50;
    public static final byte PHONE_MAX_LENGTH = 12;
}
