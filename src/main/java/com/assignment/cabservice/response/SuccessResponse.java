package com.assignment.cabservice.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SuccessResponse {
    private boolean error;
    private String message;
    private String statusCode;
    private Object data;
}
