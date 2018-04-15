/**
 * 
 */
package org.venuefinder.search.domain.dto.builder;

import org.venuefinder.search.domain.dto.VenueDto;
import org.venuefinder.search.domain.dto.VenuesDto;

/**
 * Implements Gang of Four builder pattern to build a venue DTO
 * 
 * @author peterscott
 *
 */
public class VenueDtoBuilder {

    private String name;
    
    private String address;
    
    private String postalCode;

    public VenueDtoBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public VenueDtoBuilder setAddress(String address) {
        this.address = address;
        return this;
    }

    public VenueDtoBuilder setPostalCode(String postalCode) {
        this.postalCode = postalCode;
        return this;
    }

    public VenueDto buildVenueDto() {
        VenueDto venueDto = new VenueDto(name,
                address,
                postalCode);
        return venueDto;
    }
    

}
