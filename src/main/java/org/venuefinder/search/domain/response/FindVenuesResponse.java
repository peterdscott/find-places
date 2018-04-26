/**
 * 
 */
package org.venuefinder.search.domain.response;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.venuefinder.search.domain.dto.VenueDto;
import org.venuefinder.search.domain.searchrequest.FindVenuesSearchRequest;

/**
 * @author peterscott
 *
 */
public class FindVenuesResponse extends AbstractServiceResponse{
    private String latitude;
    
    private String longitude;
    
    private String placeName;
    
    private String totalFound;
    
    private List<VenueResponseElement> venues;

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
    
    public String getTotalFound() {
        return totalFound;
    }

    public void setTotalFound(String totalFound) {
        this.totalFound = totalFound;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public List<VenueResponseElement> getVenues() {
        return venues;
    }

    public void setVenues(List<VenueResponseElement> venues) {
        this.venues = venues;
    }
    
    /*
     * Required by Dozer Mapper
     */
    public FindVenuesResponse() {
        super();        
    }
    
    public FindVenuesResponse(HttpStatus httpStatusCode) {
        setHttpStatusCode(httpStatusCode);
    }
}
