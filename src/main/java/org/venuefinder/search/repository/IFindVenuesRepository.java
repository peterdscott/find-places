/**
 * 
 */
package org.venuefinder.search.repository;

import org.venuefinder.search.domain.dto.VenuesDto;
import org.venuefinder.search.domain.searchrequest.FindVenuesSearchRequest;
import org.venuefinder.search.exception.FindVenuesSearchException;

/**
 * @author peterscott
 *
 */
public interface IFindVenuesRepository {

    /**
     * Searches for venues at the location and of the type specified in the
     * search request object parameter.
     * 
     * @param findVenuesSearchRequest
     * @return VenuesDto containing search results
     */
    public VenuesDto searchVenues(FindVenuesSearchRequest findVenuesSearchRequest) throws FindVenuesSearchException;
}
