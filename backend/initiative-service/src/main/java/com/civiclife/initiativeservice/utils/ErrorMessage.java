package com.civiclife.initiativeservice.utils;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class ErrorMessage implements Serializable {
    private ValidateCode code;
    private String requestedPath;
    private String method;

    @Override
    public String toString() {
        return "ErrorMessage{" +
                "code=" + code +
                ", requestedPath='" + requestedPath + '\'' +
                ", method='" + method + '\'' +
                '}';
    }
}
