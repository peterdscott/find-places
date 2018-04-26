/**
 * 
 */
package org.venuefinder.search.repository;

import org.venuefinder.search.domain.dto.VenuesDto;
import org.venuefinder.search.domain.searchrequest.FindVenuesSearchRequest;
import org.venuefinder.search.exception.FindVenuesSearchException;
import org.venuefinder.search.unmarshall.IUnmarshaller;


import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * Foursquare REST client
 * 
 * @author peterscott
 *
 */
@Repository
public class FoursquareFindVenues implements IFindVenuesRepository{

    private static final String HTTP_ERROR_RETURNED_FROM_FOURSQUARE = "HTTP error returned from Foursquare";
    
    /*
     * Members representing FourSquare query request parameters
     */
    @Value("${foursquare.client.id}")
    private String foursquareClientId;

    @Value("${foursquare.client.secret}")
    private String foursquareClientSecret;
  
    @Value("${foursquare.version.date}")
    private String foursquareVersionDate;
    
    /*
     * Members representing the FourSquare base URL and the venues explore
     * URL path components 
     */
    @Value("${foursquare.baseurl}")
    private String foursquareBaseUrl;
  
    @Value("${foursquare.venues.explore.uri}")
    private String foursquareVenuesExploreUri;
 
    @Autowired
    RestTemplate foursquareRestTemplate;
    
    @Autowired
    private IUnmarshaller foursquareMarshaller;
    
    /**
     * The base URL for FourSquare and the URL path components for the 
     * venues/explore request 
     */
    private String foursquareVenuesExploreUrlPath;
    
    /**
     * Build the base URL for FourSquare and the URL path components for the 
     * venues/explore request, this does not change during application 
     * execution so building this in this callback ensures it is only
     * built once during an application execution.
     */
    @PostConstruct
    public void init() {
        StringBuilder foursquareVenuesExploreUrlPathBuilder = new StringBuilder(foursquareBaseUrl);
        foursquareVenuesExploreUrlPath = foursquareVenuesExploreUrlPathBuilder
                .append(foursquareVenuesExploreUri)
                .toString();
    }
  
    /**
     * Build and send a venues explore request to FourSquare.
     * 
     * Please note an automatic mapping from the Foursquare response to a 
     * JsonProperty annotated POJO has not been implemented because the fields
     * required in the POJO are a small subset of the response JSON content.
     * 
     * @param FindVenuesSearchRequest, internal representation of search request
     * @return VenuesDto
     */
    public VenuesDto searchVenues(FindVenuesSearchRequest findVenuesSearchRequest) throws FindVenuesSearchException{
        /*
         * Build venues explore request to FourSquare for Foursquare
         */
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromHttpUrl(foursquareVenuesExploreUrlPath)
                .queryParam("client_id", foursquareClientId)
                .queryParam("client_secret", foursquareClientSecret)
                .queryParam("v", foursquareVersionDate);
        uriComponentsBuilder = mapPositionQueryParameters(uriComponentsBuilder, findVenuesSearchRequest)
                .queryParam("query", findVenuesSearchRequest.getQuery())
                .queryParam("radius", findVenuesSearchRequest.getRadius());
        
        ResponseEntity<String> foursquareResponse = foursquareRestTemplate.getForEntity(uriComponentsBuilder.build().encode().toUri(), String.class);
        /*
         * Check HTTP status returned and if it represents an error throw a
         * FindVenuesSearchException which is caught in the REST Controller
         */
        HttpStatus httpStatusCode = foursquareResponse.getStatusCode();
        if (httpStatusCode.isError()) {
            throw new FindVenuesSearchException(httpStatusCode, HTTP_ERROR_RETURNED_FROM_FOURSQUARE);
        }
        String responsePayload = foursquareResponse.getBody();
        VenuesDto venuesDto = foursquareMarshaller.unmarshall(responsePayload, findVenuesSearchRequest);
        return venuesDto;
    }
    
    /**
     * Map the position query parameters, If place name provided in request, 
     * use that as search criterion. Otherwise use latitude and longitude.
     * 
     * @param UriComponentsBuilder
     * @param FindVenuesSearchRequest, internal representation of search request
     * @return UriComponentsBuilder after position query parameter mapping
     */
    private UriComponentsBuilder mapPositionQueryParameters(UriComponentsBuilder uriComponentsBuilder, FindVenuesSearchRequest findVenuesSearchRequest) {
        if (!findVenuesSearchRequest.getPlaceName().equals("")) {
            uriComponentsBuilder.queryParam("near", findVenuesSearchRequest.getPlaceName());
        } else {
            StringBuilder coordinatesBuilder = new StringBuilder(findVenuesSearchRequest.getLatitude());
            coordinatesBuilder.append(",");
            coordinatesBuilder.append(findVenuesSearchRequest.getLongitude());
            uriComponentsBuilder.queryParam("ll", coordinatesBuilder.toString());
        }
        return uriComponentsBuilder;
        
    }
}
