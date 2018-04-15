package org.venuefinder.search.domain.dto;

/**
 * Represents a venue
 * 
 * @author peterscott
 *
 */
public class VenueDto {

    private String name;
    
    private String address;
    
    private String postalCode;


    public String getName() {
        return name;
    }


    public String getAddress() {
        return address;
    }


    public String getPostalCode() {
        return postalCode;
    }

    public VenueDto(String name,
            String address,
            String postalCode) {
        this.name = name;
        this.address = address;
        this.postalCode = postalCode;
    }

}
