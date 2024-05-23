package com.example.back.common;

public interface ResponseMessage {

    String SUCCESS = "SUCCESS.";

    String VALIDATION_FAIL = "Validation failed.";
    String DUPLICATE_ID = "Duplicate id.";
    String DUPLICATED_EMAIL = "Duplicate Email";
    String DUPLICATED_NICKNAME = "Duplicate Nickname";

    String SIGN_IN_FAIL = "Login information mismatch.";
    String CERTIFICATION_FAIL = "Certification failed.";

    String MAIL_FAIL = "Mail send failed";
    String DATABASE_ERROR = "Database error.";

    String NOT_EXISTED_USER = "Not Existed User";
    String NOT_EXISTED_BOARD = "Not Existed Board";
    String NOT_EXISTED_MUSIC = "Not Existed Music";

    String WRONG_PASSWORD = "Wrong Password";
    String DO_NOT_HAVE_PERMISSION = "Do Not Have Permission";
}
