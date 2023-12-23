package com.assignment.cabservice.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(value = JsonInclude.Include.NON_DEFAULT)
public class ErrorResponse {
    private boolean error;
    private String message;
    private String statusCode;
    private Object data;
}
