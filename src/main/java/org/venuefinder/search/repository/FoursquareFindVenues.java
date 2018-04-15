/**
 * 
 */
package org.venuefinder.search.repository;

import org.venuefinder.search.domain.dto.VenuesDto;
import org.venuefinder.search.domain.searchrequest.FindVenuesSearchRequest;
import org.venuefinder.search.unmarshall.IUnmarshaller;

import reactor.core.publisher.Mono;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * @author peterscott
 *
 */
@Repository
public class FoursquareFindVenues implements IFindVenuesRepository{

    @Value("${foursquare.client.id}")
    private String foursquareClientId;

    @Value("${foursquare.client.secret}")
    private String foursquareClientSecret;
  
    @Value("${foursquare.baseurl}")
    private String foursquareBaseUrl;
  
    @Value("${foursquare.venues.explore.uri}")
    private String foursquareVenuesExploreUri;
  
    @Value("${foursquare.version.date}")
    private String foursquareVersionDate;
    
    @Autowired
    private IUnmarshaller foursquareMarshaller;
  
    public VenuesDto searchVenues(FindVenuesSearchRequest findVenuesSearchRequest) {
        /*
         * Build request parameters query string for GET request
         */
        StringBuilder uriIncludingQueryParameters = new StringBuilder();
        uriIncludingQueryParameters.append(foursquareVenuesExploreUri);
        uriIncludingQueryParameters.append("?client_id=");
        uriIncludingQueryParameters.append(foursquareClientId);
        uriIncludingQueryParameters.append("&client_secret=");
        uriIncludingQueryParameters.append(foursquareClientSecret);
        uriIncludingQueryParameters.append("&v=");
        uriIncludingQueryParameters.append(foursquareVersionDate);
        // If place name provided in request, use that as search criterion
        if (!findVenuesSearchRequest.getPlaceName().equals("")) {
            uriIncludingQueryParameters.append("&near=");
            uriIncludingQueryParameters.append(findVenuesSearchRequest.getPlaceName());
        } else {
            uriIncludingQueryParameters.append("&ll=");
            uriIncludingQueryParameters.append(findVenuesSearchRequest.getLatitude());
            uriIncludingQueryParameters.append(",");
            uriIncludingQueryParameters.append(findVenuesSearchRequest.getLongitude());
        }
        uriIncludingQueryParameters.append("&query=");
        uriIncludingQueryParameters.append(findVenuesSearchRequest.getQuery());
        uriIncludingQueryParameters.append("&radius=");
        uriIncludingQueryParameters.append(findVenuesSearchRequest.getRadius());
        uriIncludingQueryParameters.append("&limit=10");
        String uriIncludingQueryParametersString = uriIncludingQueryParameters.toString();
        WebClient foursquareClient = WebClient.create(foursquareBaseUrl);
        Mono<ResponseEntity<String>> futureResult = foursquareClient.get()
                .uri(uriIncludingQueryParameters.toString()).accept(MediaType.APPLICATION_JSON)
                .exchange()
                .flatMap(response -> response.toEntity(String.class));
        ResponseEntity<String> foursquareResponse = futureResult.block();
        int statusCode = foursquareResponse.getStatusCodeValue();
        String responsePayload = foursquareResponse.getBody();
        VenuesDto venuesDto = foursquareMarshaller.unmarshall(responsePayload, findVenuesSearchRequest);
        return venuesDto;
    }
}
