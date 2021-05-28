package io.manpreet.ratingdataservice.resources;

import io.manpreet.ratingdataservice.models.Rating;
import io.manpreet.ratingdataservice.models.UserRating;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/ratingsdata")
public class RatingsResource 
{
	@RequestMapping("/{movieId}")
	public Rating getRating(@PathVariable("movieId") String movieId)
	{
		return new Rating(movieId,4);
	}
	
	@RequestMapping("/users/{userId}")
	public UserRating getUserRating(@PathVariable("userId") String userId)
	{
		List<Rating> ratings = Arrays.asList(
					new Rating("100",4),
					new Rating("105",3)
				);
		
		UserRating userRating = new UserRating();
		userRating.setUserRating(ratings);
		
		return userRating;
	}
}
