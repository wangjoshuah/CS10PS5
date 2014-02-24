import java.util.*;

public class Actor {
	private String name;
	private Set<Movie> movies;
	private Set<Actor> adjacentActors;
	private Actor prevActor;
	private Actor baconLink;
	private int baconNumber;
	private Map<Actor, Movie> movieLinks;
	private Movie baconMovie;
	
	public Actor(String person)
	{
		name = person;
		movies = new HashSet<Movie>();
		prevActor = null;
		baconLink = null;
		movieLinks = new HashMap<Actor, Movie>();
		baconMovie = null;
	}
	
	public String getName() {
		return name;
	}

	public Set<Movie> getMovies() {
		return movies;
	}
	
	public Actor getPrevActor() {
		return prevActor;
	}
	
	public Actor getBaconLink() {
		return baconLink;
	}
	
	public int getBaconNumber() {
		return baconNumber;
	}
	
	public Movie getBaconMovie() {
		return baconMovie;
	}

	public void add(Movie m)
	{
		movies.add(m);
	}
	
	public void setPrevActor(Actor path) {
		prevActor = path; //set our previous actor from the path we are coming from
	}
	
	public void setBaconLink(Actor path) {
		baconLink = path; //set the bacon link
		baconMovie = movieLinks.get(baconLink); //set our bacon movie based on our bacon link
	}
	
	public void setBaconNumber(int distance) {
		baconNumber = distance;
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
