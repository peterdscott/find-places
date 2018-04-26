/**
 * 
 */
package org.venuefinder.search.service;

import org.venuefinder.search.domain.dto.VenuesDto;
import org.venuefinder.search.domain.response.FindVenuesResponse;
import org.venuefinder.search.domain.searchrequest.FindVenuesSearchRequest;
import org.venuefinder.search.exception.FindVenuesSearchException;
import org.venuefinder.search.repository.IFindVenuesRepository;
import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author peterscott
 *
 */
@Service
public class FindVenuesService implements IFindVenuesService {
    
    @Autowired
    private IFindVenuesRepository foursquareFindVenues;
    
    @Autowired
    private DozerBeanMapper dozerBeanMapper;
    
    public FindVenuesResponse searchVenues(FindVenuesSearchRequest findVenuesSearchRequest) throws FindVenuesSearchException{
        FindVenuesResponse findVenuesResponse;
        VenuesDto venuesDto = foursquareFindVenues.searchVenues(findVenuesSearchRequest);
        findVenuesResponse = dozerBeanMapper.map(venuesDto, FindVenuesResponse.class);
        return findVenuesResponse;
    }; 

}
