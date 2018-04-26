/**
 * 
 */
package org.venuefinder.search.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.venuefinder.search.domain.response.AbstractServiceResponse;
import org.venuefinder.search.domain.response.FindVenuesErrorResponse;
import org.venuefinder.search.domain.response.FindVenuesResponse;
import org.venuefinder.search.domain.searchrequest.FindVenuesSearchRequest;
import org.venuefinder.search.exception.FindVenuesSearchException;
import org.venuefinder.search.service.IFindVenuesService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ApiResponse;


/**
 * This is the web service endpoint for GET findvenues requests
 * 
 * @author peterscott
 *
 */
@RestController
@Api(value = "/findvenues", description = "Find places of interest near a place")
public class FindVenuesController {

    private final static String DEFAULT_RADIUS = "250";
    private final static String DEFAULT_QUERY = "food";
    private final static Double NORTH_POLE = 90.0;
    private final static Double SOUTH_POLE = -90.0;
    private final static Double FURTHEST_WEST = -180.0;
    private final static Double FURTHEST_EAST = 180.0;

    
    @Autowired
    IFindVenuesService findVenuesService;

    /**
     * Fulfil a GET findvenues request, see Swagger http://localhost:port/swagger-ui.html
     * 
     * @param placeName
     * @param latitude
     * @param longitude
     * @param radius
     * @param query
     * @return
     */
    @GetMapping(value="/findvenues")
    @ApiOperation(
            value = "Find venues by place name", notes = "The latitude and longitude can be provided instead. If the place name, latitude and longitude are all provided, the place name is used as the search criterion. If the radius is not set, a default of 250 metres is used. If the query is not set, a default of food is used. ",
            response = FindVenuesResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Error encountered fetching location data from remote service"),
            @ApiResponse(code = 404, message = "Place not found, check place name or latitude and longitude")}
    )
    public ResponseEntity<? extends AbstractServiceResponse> findServices(@RequestParam(value="placeName", required=false, defaultValue="") String placeName,
            @RequestParam(value="latitude", required=false, defaultValue="") String latitude,
            @RequestParam(value="longitude", required=false, defaultValue="") String longitude,
            @RequestParam(value="radius", required=false, defaultValue="") String radius,
            @RequestParam(value="query", required=false, defaultValue="") String query) {
        FindVenuesResponse findVenuesResponse = null;;
        ResponseEntity<FindVenuesResponse> responseEntity;
        String radiusToRequest = (radius.equals("") ? DEFAULT_RADIUS : radius);
        String queryToRequest = (query.equals("") ? DEFAULT_QUERY : query);
        if (areLocationParametersValid(placeName, latitude, longitude)) {
            FindVenuesSearchRequest findVenuesSearchRequest = new FindVenuesSearchRequest(placeName, latitude, longitude, queryToRequest, radiusToRequest);
            try {
                findVenuesResponse = findVenuesService.searchVenues(findVenuesSearchRequest);
            } catch(FindVenuesSearchException fvse) {
                FindVenuesErrorResponse findVenuesErrorResponse = new FindVenuesErrorResponse(fvse.getHttpStatusCode(), fvse.getMessage());
                ResponseEntity<FindVenuesErrorResponse> errorResponseEntity = new ResponseEntity<>(findVenuesErrorResponse, fvse.getHttpStatusCode());
                return errorResponseEntity;
            }
            findVenuesResponse.setHttpStatusCode(HttpStatus.OK);
            responseEntity = new ResponseEntity<FindVenuesResponse>(findVenuesResponse,HttpStatus.OK);
        } else {
            responseEntity = new ResponseEntity<FindVenuesResponse>(new FindVenuesResponse(HttpStatus.NOT_FOUND),HttpStatus.NOT_FOUND);
        }
        return responseEntity;
    }
    
    /**
     * Determine whether either a place name or valid latitude and longitude 
     * have been passed.
     * 
     * @param placeName
     * @param latitude
     * @param longitude
     * @return
     */
    private boolean areLocationParametersValid(String placeName, String latitude, String longitude) {
        boolean result = false;
        // Place name or latitude and longitude must be present
        if (placeName.equals("")) {
            if (!latitude.equals("") && !longitude.equals("")) {
                Double latitudeAsDouble = Double.parseDouble(latitude);
                Double longitudeAsDouble = Double.parseDouble(longitude);
                if ((latitudeAsDouble.compareTo(SOUTH_POLE) >= 0) && (latitudeAsDouble.compareTo(NORTH_POLE) <= 0)
                        && (longitudeAsDouble.compareTo(FURTHEST_WEST) >= 0) && (longitudeAsDouble.compareTo(FURTHEST_EAST) <= 0)) {
                    // Valid latitude and longitude present
                    result = true;
                }
            } else {
                result = false;
            }
        } else {
            // There is a place name
            result = true;
        }
        
        return result;
    }
}