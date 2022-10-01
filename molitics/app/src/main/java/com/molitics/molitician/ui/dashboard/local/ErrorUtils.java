package com.molitics.molitician.ui.dashboard.local;

import com.molitics.molitician.httpapi.RetrofitRestClient;

import java.io.IOException;
import java.lang.annotation.Annotation;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Response;

/**
 * Created by Rahul on 10/20/2016.
 */

public class ErrorUtils {

    public static LocalError parseError(Response<?> response) {
        Converter<ResponseBody, LocalError> converter = RetrofitRestClient.retrofit
                .responseBodyConverter(LocalError.class, new Annotation[0]);

        LocalError error;

        try {

            error = converter.convert(response.errorBody());
        } catch (IOException e) {
            return new LocalError();
        }

        return error;
    }
}
