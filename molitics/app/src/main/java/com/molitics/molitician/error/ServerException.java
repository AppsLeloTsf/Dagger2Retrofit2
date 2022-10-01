package com.molitics.molitician.error;

/**
 * Created by rohitkumar on 10/24/16.
 */

public class ServerException {

    int code;
    String message;


    public ServerException(int code, String message) {
        this.code=code;
        this.message=message;

    }


}
