package org.venuefinder.search.domain.response;

import org.springframework.http.HttpStatus;

/**
 * Base class for response to client
 * 
 * @author peterscott
 *
 */
public abstract class AbstractServiceResponse {

    private HttpStatus httpStatusCode;

    public HttpStatus getHttpStatusCode() {
        return httpStatusCode;
    }

    public void setHttpStatusCode(HttpStatus httpStatusCode) {
        this.httpStatusCode = httpStatusCode;
    }
    
}
