/**
 * 
 */
package org.venuefinder.search.domain.response;

/**
 * Represents a venue property and its nested content in the JSON response 
 * returned to the caller
 * 
 * @author peterscott
 *
 */
public class VenueResponseElement {

    private String name;
    
    private String address;
    
    private String postalCode;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }
}
