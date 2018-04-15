/**
 * 
 */
package org.venuefinder.search.unmarshall;

import org.venuefinder.search.domain.dto.VenuesDto;
import org.venuefinder.search.domain.searchrequest.FindVenuesSearchRequest;

/**
 * @author peterscott
 *
 */
public interface IUnmarshaller {

    public VenuesDto unmarshall(String payload, FindVenuesSearchRequest findVenuesSearchRequest);
}
