package com.molitics.molitician.error;

/**
 * Created by rohitkumar on 9/30/16.
 */
public class ApiException {

    int code;
    String message;

    public ApiException(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

}
