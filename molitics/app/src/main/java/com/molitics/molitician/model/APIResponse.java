package com.molitics.molitician.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.molitics.molitician.ui.dashboard.register.UserModel;


import java.util.Map;

import javax.annotation.Nullable;

import okhttp3.MediaType;
import okhttp3.ResponseBody;
import okio.BufferedSource;

public class APIResponse extends ResponseBody {
    @SerializedName("status")
    @Expose
    private Integer status;

    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("error_code")
    @Expose
    private Integer errorCode;

    @SerializedName("error")
    @Expose
    private Map<String, String> error;

    @SerializedName("http_status")
    @Expose
    private Integer httpStatus;

    @SerializedName("data")
    @Expose
    private Data data;

    /**
     * @return The data
     */
    public Data getData() {
        return data;
    }

    /**
     * @param data The data
     */
    public void setData(Data data) {
        this.data = data;
    }

    /**
     * @return The success
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * @param status The success
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * @return The message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param message The message
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * @return The errorCode
     */
    public Integer getErrorCode() {
        return errorCode;
    }

    /**
     * @param errorCode The error_code
     */
    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

    /**
     * @return The error
     */
    public Map<String, String> getError() {
        return error;
    }

    /**
     * @param error The error
     */
    public void setError(Map<String, String> error) {
        this.error = error;
    }

    /**
     * @return The httpStatus
     */
    public Integer getHttpStatus() {
        return httpStatus;
    }

    /**
     * @param httpStatus The http_status
     */
    public void setHttpStatus(Integer httpStatus) {
        this.httpStatus = httpStatus;
    }


    @Nullable
    @Override
    public MediaType contentType() {
        return null;
    }

    @Override
    public long contentLength() {
        return 0;
    }

    @Override
    public BufferedSource source() {
        return null;
    }

}