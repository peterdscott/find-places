# find-places

Find Places is a microservice, which returns venues near to a location specified by place name or latitude and longitude.

A summary is provided here, please see the Wiki attached to this repository for detailed information.

Build the *search* project in this repository, it is a Java 8 Spring Boot project built using Maven. Execute the JAR generated by the build and send GET requests as shown below using one of:
* Swagger UI, in your browser navigate to http://localhost:8080/swagger-ui.html and select **find-venues-controller** and follow the instructions.
* Postman or another REST client.

The base URL is:

```http://localhost:8080/findvenues```

The request parameters in the URI query string are:
* placeName, the name of the place of interest.
* latitude and longitude of the place of interest.
* query, the type of venue sought, one of *food*, *drinks*, *coffee*, *shops*, *arts*, *outdoors*, *sights*, *trending*, *nextVenues* (venues frequently visited after a given venue), or *topPicks*. If no value is specified, the default value *food* will be used.
* radius, the radius in metres from the selected location within which the search is to be performed. If no value is specified, the default value *250* metres will be used.

An example using SwaggerUI is shown below:

![GitHub Logo](/images/SwaggerUI-example-request.png)

This is the response that was received in this case:

![GitHub Logo](/images/SwaggerUI-example-response.png)


