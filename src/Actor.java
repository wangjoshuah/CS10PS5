import java.util.*;

public class Actor {
	private String name;
	private Set<Movie> movies;
	private Set<Actor> adjacentActors;
	
	public Actor(String person)
	{
		name = person;
		movies = new HashSet<Movie>();
	}
	public String getName() {
		return name;
	}

	public Set<Movie> getMovies() {
		return movies;
	}

	public void add(Movie m)
	{
		movies.add(m);
	}
	
	public Set<Actor> getAdjacentActors() {
		if (adjacentActors == null) { //if we don't have a set yet
			adjacentActors = new HashSet<Actor>(); //instantiate the set
			for (Movie film : movies) { //for all the movies this actor has been in
				Set<Actor> actors = film.getActors(); //get the cast list
				for (Actor star : actors) { //for all stars on that list 
					if (!star.equals(this)) { //if we are not that star
						if (!adjacentActors.contains(star)) { //if the star is not already in there
							adjacentActors.add(star); //add the star to our friends list
						}
					}
				}
			}
		}
		return adjacentActors; //return our list of adjacent actors
	}
	
	
	
	
	
}
