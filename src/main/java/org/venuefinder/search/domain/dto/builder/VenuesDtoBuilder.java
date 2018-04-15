/**
 * 
 */
package org.venuefinder.search.domain.dto.builder;

import org.venuefinder.search.domain.dto.VenuesDto;

/**
 * Implements Gang of Four builder pattern to build a venues DTO

 * 
 * @author peterscott
 *
 */
public class VenuesDtoBuilder {

    private String latitude;
    
    private String longitude;

    private String placeName;
    
    public VenuesDtoBuilder setLatitude(String latitude) {
        this.latitude = latitude;
        return this;
    }

    public VenuesDtoBuilder setLongitude(String longitude) {
        this.longitude = longitude;
        return this;
    }

    public VenuesDtoBuilder setPlaceName(String placeName) {
        this.placeName = placeName;
        return this;
    }

    public VenuesDto buildVenuesDto() {
        VenuesDto venuesDto = new VenuesDto(latitude,
                longitude,
                placeName);
        return venuesDto;
    }
    
}