package io.manpreet.moviecatalogservice.resources;

import org.springframework.web.client.RestTemplate;
import io.manpreet.moviecatalogservice.models.CatalogItem;
import io.manpreet.moviecatalogservice.models.Movie;
import io.manpreet.moviecatalogservice.models.Rating;
import io.manpreet.moviecatalogservice.models.UserRating;
import java.util.stream.Collectors;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.beans.factory.annotation.Autowired;
import java.lang.reflect.ParameterizedType;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogResource 
{
	@Autowired
	private RestTemplate restTemplate;	
	
	@Autowired
	private WebClient.Builder webClientBuilder;
	
	@RequestMapping("/{userId}")
	public List<CatalogItem> getCatalog(@PathVariable("userId") String userId)
	{
		
		//RestTemplate restTemplate=new RestTemplate();
		//WebClient.Builder builder = WebClient.builder();
		
		UserRating ratings = restTemplate.getForObject("http://ratings-data-service/ratingsdata/users/"+userId, UserRating.class );
		
		return ratings.getUserRating().stream().map(rating ->
			{
				Movie movie = restTemplate.getForObject("http://movie-info-service/movies/"+rating.getMovieId(),Movie.class);
				
				/*Movie movie = webClientBuilder.build()
												.get()
												.uri("http://localhost:8082/movies/"+rating.getMovieId())
												.retrieve()
												.bodyToMono(Movie.class)
												.block();*/
				
				return new CatalogItem(movie.getName(),movie.getDesc(),rating.getRating());
			}
				).collect(Collectors.toList());
		
	}
}
