/**
 * 
 */
package org.venuefinder.search.config;

import org.dozer.DozerBeanMapper;
import org.dozer.loader.api.BeanMappingBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.venuefinder.search.domain.dto.VenueDto;
import org.venuefinder.search.domain.dto.VenuesDto;
import org.venuefinder.search.domain.response.FindVenuesResponse;
import org.venuefinder.search.domain.response.VenueResponseElement;
import org.venuefinder.search.repository.FoursquareFindVenues;
import org.venuefinder.search.repository.IFindVenuesRepository;
import org.venuefinder.search.service.FindVenuesService;
import org.venuefinder.search.service.IFindVenuesService;
import org.venuefinder.search.unmarshall.FoursquareUnmarshaller;
import org.venuefinder.search.unmarshall.IUnmarshaller;


/**
 * @author peterscott
 *
 */
@Configuration
public class ApplicationConfig {

    @Bean
     public IFindVenuesService findVenuesService() {
        return new FindVenuesService();    
    }
    
    @Bean
    public IFindVenuesRepository foursquareFindVenues() {
       return new FoursquareFindVenues();    
   }
    
    @Bean IUnmarshaller foursquareMarshaller() {
        return new FoursquareUnmarshaller();
    }
    
    @Bean
    public DozerBeanMapper dozerBeanMapper() throws Exception {
        DozerBeanMapper mapper = new DozerBeanMapper();
        mapper.addMapping( new BeanMappingBuilder() {
            @Override
            protected void configure() {
                mapping(VenueDto.class, VenueResponseElement.class);
                mapping(VenuesDto.class, FindVenuesResponse.class).fields("venues", "venues");                  
            }
        });
        return mapper;
    }
}
