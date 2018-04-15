/**
 * 
 */
package org.venuefinder.search.service;

import org.venuefinder.search.domain.dto.VenuesDto;
import org.venuefinder.search.domain.response.FindVenuesResponse;
import org.venuefinder.search.domain.searchrequest.FindVenuesSearchRequest;

/**
 * Find venues near to a specified location 
 * 
 * @author peterscott
 *
 */
public interface IFindVenuesService {

    public FindVenuesResponse searchVenues(FindVenuesSearchRequest findVenuesSearchRequest); 

    
}
