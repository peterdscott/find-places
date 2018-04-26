package org.venuefinder.search.exception;

import org.springframework.http.HttpStatus;

/**
 * Thrown if an error is encountered sending a request to an external location
 * data service or unmarshalling the response returned.
 * 
 * @author peterscott
 *
 */
public class FindVenuesSearchException extends Exception {

    private HttpStatus httpStatusCode;

    public HttpStatus getHttpStatusCode() {
        return httpStatusCode;
    }
    
    public FindVenuesSearchException(HttpStatus httpStatusCode, String message) {
        super(message);
        this.httpStatusCode = httpStatusCode;
    }
}
