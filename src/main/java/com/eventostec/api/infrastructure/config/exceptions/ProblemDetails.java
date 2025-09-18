package com.eventostec.api.infrastructure.config.exceptions;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Based on RFC7807(<a href="https://datatracker.ietf.org/doc/html/rfc7807">Problem Details for HTTP APIs</a>)
 */
@Getter
@Setter
@NoArgsConstructor
public class ProblemDetails {
    private String title;
    private Integer code;
    private String status;
    private String detail;
    private String instance;
    
    public ProblemDetails(String title, Integer code, String status, String detail, String instance) {
        this.title = title;
        this.code = code;
        this.status = status;
        this.detail = detail;
        this.instance = instance;
    }
}
