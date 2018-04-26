package org.venuefinder.search.domain.response;

import org.springframework.http.HttpStatus;

/**
 * Error returned to client if an error is encountered sending a request to an 
 * external location data service or unmarshalling the response returned.
 * 
 * @author peterscott
 *
 */
public class FindVenuesErrorResponse extends AbstractServiceResponse{

    private String errorMessage;

    public String getErrorMessage() {
        return errorMessage;
    }

    public FindVenuesErrorResponse(HttpStatus httpStatusCode, String errorMessage) {
        setHttpStatusCode(httpStatusCode);
        this.errorMessage = errorMessage;
    }
}
