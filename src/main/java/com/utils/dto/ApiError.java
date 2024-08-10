package com.utils.dto;

import lombok.Builder;

@Builder
public record ApiError(
        String code,
        String message
) {
    @Override
    public String toString() {
        return "ApiError{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
