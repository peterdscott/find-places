/**
 * 
 */
package org.venuefinder.search.domain.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * The location details and a list of nearby venues
 * 
 * @author peterscott
 *
 */
public class VenuesDto {

    private String latitude;
    
    private String longitude;

    private String placeName;
    
    private String totalFound;
    
    private List<VenueDto> venues = new ArrayList<>();
    
    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getPlaceName() {
        return placeName;
    }

    /**
     * Return the number of venues found near this location
     * 
     * @return
     */
    public String getTotalFound() {
        return totalFound;
    }

    public List<VenueDto> getVenues() {
        return venues;
    }

    public void setVenues(List<VenueDto> venues) {
        this.venues = venues;
    }

    /**
     * Add a venue to the venues list
     *  
     * @param venueDto
     */
    public void addVenue(VenueDto venueDto) {
        venues.add(venueDto);
        totalFound = new Integer(venues.size()).toString();
    }
    
    public VenuesDto() {
        super();
   }
    
    public VenuesDto(String latitude,
            String longitude,
            String placeName) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.placeName = placeName;
    }
}