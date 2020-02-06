package org.nil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping(value = "api")
public class GeoCodingApiController {

	private static final Logger log = LoggerFactory.getLogger(GeoCodingApiController.class);
	private static final String GEOCODING_URI = "https://maps.googleapis.com/maps/api/geocode/json";
	
	@Autowired
	private Environment env;

	@RequestMapping("/getGeoCoding/{address}")
	public GeoCoding getGeoCodingForLoc(@PathVariable(value = "address") String address) {
		
		address = address.replaceAll("\\s","");
		log.info(address);
		RestTemplate restTemplate = new RestTemplate();
		UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(GEOCODING_URI).queryParam("address", address)
				.queryParam("key", env.getProperty("apiKey")).queryParam("sensor", true);
			
		log.info("Calling geocoding api with: " + builder.toUriString());
		
		GeoCoding geoCoding = restTemplate.getForObject(builder.toUriString(), GeoCoding.class);
		log.info(geoCoding.toString());

		
		return geoCoding;
	}
}
