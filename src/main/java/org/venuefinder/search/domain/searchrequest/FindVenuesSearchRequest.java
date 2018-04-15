/**
 * 
 */
package org.venuefinder.search.domain.searchrequest;

/**
 * An instance of this class represent a venues search request.
 * 
 * An instance is built when a findvenues webs service GET request is received.
 * 
 * longitude and latitude are mapped directly from the corresponding findvenues
 * GET request parameters.
 * 
 * radius is optional, mapped if present in the request otherwise defaults to
 * 250 metres.
 * 
 * query, which represents the type of venue, is optional, mapped if present 
 * in the request otherwise defaults to "food"
 * 
 * @author peterscott
 *
 */
public class FindVenuesSearchRequest {
    
    private String placeName;

    private String latitude;
    
    private String longitude;
    
    private String query;
    
    private String radius;

    public String getPlaceName() {
        return placeName;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getQuery() {
        return query;
    }

    public String getRadius() {
        return radius;
    }

    public FindVenuesSearchRequest(String placeName, String latitude, String longitude, String query, String radius) {
        this.placeName = placeName;
        this.latitude = latitude;
        this.longitude = longitude;
        this.query = query;
        this.radius = radius;
    }
}
