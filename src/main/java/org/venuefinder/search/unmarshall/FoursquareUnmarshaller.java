/**
 * 
 */
package org.venuefinder.search.unmarshall;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.venuefinder.search.domain.dto.VenueDto;
import org.venuefinder.search.domain.dto.VenuesDto;
import org.venuefinder.search.domain.dto.builder.VenueDtoBuilder;
import org.venuefinder.search.domain.dto.builder.VenuesDtoBuilder;
import org.venuefinder.search.domain.searchrequest.FindVenuesSearchRequest;

import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.ReadContext;

/**
 * Unmarshall fields from Foursquare response JSON into DTO for the service
 * layer
 * 
 * @author peterscott
 *
 */
public class FoursquareUnmarshaller implements IUnmarshaller {

    private static final String ITEMS_JSON_KEY = "items";
    private static final String VENUE_JSON_KEY = "venue";
    private static final String NAME_JSON_KEY = "name";
    private static final String LOCATION_JSON_KEY = "location";
    private static final String ADDRESS_JSON_KEY = "address";
    private static final String POSTALCODE_JSON_KEY = "postalCode";

    /**
     * Unmarshall fields from Foursquare response JSON into a Venues DTO, which
     * contains details of the requested location and a list of Venue DTOs, one for
     * each venue returned in the Foursquare response.
     * 
     * @param String
     *            JSON Foursquare response payload
     * @FindVenuesSearchRequest the original search request built by the controller
     * @return VenuesDto the DTO to return to the service tier from which the
     *         response to the client will be mapped
     */
    public VenuesDto unmarshall(String payload, FindVenuesSearchRequest findVenuesSearchRequest) {
        VenuesDtoBuilder venuesDtoBuilder = new VenuesDtoBuilder();
        ReadContext readContext = JsonPath.parse(payload);
        venuesDtoBuilder = venuesDtoBuilder.setLatitude(findVenuesSearchRequest.getLatitude())
                .setLongitude(findVenuesSearchRequest.getLongitude())
                .setPlaceName(readContext.read("$.response.headerLocation"));
        VenuesDto venuesDto = venuesDtoBuilder.buildVenuesDto();
        List<Object> items = readContext.read("$.response.groups[*]");
        Map<String, Object> groupsMap = (Map<String, Object>) items.get(0);
        List<Map<String, Object>> itemsList = (List<Map<String, Object>>) groupsMap.get(ITEMS_JSON_KEY);
        /*
         * Build and map a venue DTO for each venue returned in the Foursquare response
         * and add it into the venue DTO list in the venues DTO
         */
        List<VenueDto> venueDtoList = itemsList.stream().map(itemMap -> {
            Map<String, Object> venueMap = (Map<String, Object>) itemMap.get(VENUE_JSON_KEY);
            Map<String, Object> locationMap = (Map<String, Object>) venueMap.get(LOCATION_JSON_KEY);
            VenueDtoBuilder venueDtoBuilder = new VenueDtoBuilder().setName((String) venueMap.get(NAME_JSON_KEY))
                    .setAddress((String) locationMap.get(ADDRESS_JSON_KEY))
                    .setPostalCode((String) locationMap.get(POSTALCODE_JSON_KEY));
            VenueDto venueDto = venueDtoBuilder.buildVenueDto();
            venuesDto.addVenue(venueDto);
            return venueDto;
        }).collect(Collectors.toList());
        return venuesDto;
    }

}
