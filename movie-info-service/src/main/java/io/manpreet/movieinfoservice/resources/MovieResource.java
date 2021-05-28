package io.manpreet.movieinfoservice.resources;

import io.manpreet.movieinfoservice.models.Movie;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.beans.factory.annotation.Autowired;
import io.manpreet.movieinfoservice.models.MovieSummary;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Value;

@RestController
@RequestMapping("/movies")
public class MovieResource 
{
	@Value("${api.key}")
	private String apiKey;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@RequestMapping("/{movieId}")
	public Movie getMovieInfo(@PathVariable("movieId") String movieId)
	{
		MovieSummary movieSummary=restTemplate.getForObject("https://api.themoviedb.org/3/movie/"+movieId+"?api_key="+apiKey,MovieSummary.class);
		
		return new Movie(movieId,movieSummary.getTitle(),movieSummary.getOverview());
	}
	
}
