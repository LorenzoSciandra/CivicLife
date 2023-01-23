package com.civiclife.oauthservice.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ErrorMessage implements Serializable {
    private ValidateCode code;
    private String requestedPath;
    private String method;

    public ValidateCode getCode() {
        return code;
    }

    public void setCode(ValidateCode code) {
        this.code = code;
    }

    public String getRequestedPath() {
        return requestedPath;
    }

    public void setRequestedPath(String requestedPath) {
        this.requestedPath = requestedPath;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }
}
