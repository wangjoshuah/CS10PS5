import java.util.*;

/**
 * Actor class for use in Bacon Game
 * @author Alexander Tsu & Joshua Wang
 *
 */

public class Actor {
	private String name;
	private Set<Movie> movies;
	private Set<Actor> adjacentActors;
	private Actor prevActor;
	private Actor baconLink;
	private int baconNumber;
	private Map<Actor, Movie> movieLinks;
	private Movie baconMovie;
	
	/**
	 * Constructor for Actor
	 * @param person		Name of a given actor
	 */
	public Actor(String person)
	{
		name = person;
		movies = new HashSet<Movie>();
		prevActor = null;
		baconLink = null;
		movieLinks = new HashMap<Actor, Movie>();
		baconMovie = null;
		baconNumber = -1;
	}
	
	/**
	 * Get Name of Actor
	 * @return		Name of actor
	 */
	public String getName() {
		return name;
	}

	/**
	 * Get Movies actor was in
	 * @return		Set of movies the actor is in
	 */
	public Set<Movie> getMovies() {
		return movies;
	}
	
	/**
	 * Get previous actor in the bacon link
	 * @return		previous actor in bacon link
	 */
	public Actor getPrevActor() {
		return prevActor;
	}
	
	/**
	 * Get next actor in bacon link
	 * @return		next actor in bacon link
	 */
	public Actor getBaconLink() {
		return baconLink;
	}
	
	/**
	 * Get bacon number
	 * @return		bacon number
	 */
	public int getBaconNumber() {
		return baconNumber;
	}
	
	/**
	 * Get movie in bacon link
	 * @return		movie in the bacon link
	 */
	public Movie getBaconMovie() {
		return baconMovie;
	}

	/**
	 * Adds movie into the actor class
	 * @param m		movie to add into the Actor class
	 */
	public void addMovie(Movie m)
	{
		movies.add(m);
	}
	
	/**
	 * Adds co-star in
	 * @param film		movie the actors were in 
	 * @param coStar		co-star of the actor in the film
	 */
	public void addCoStar(Movie film, Actor coStar) {
		movieLinks.put(coStar, film);
	}
	
	/**
	 * Sets previous actor
	 * @param path		previous actor in the path
	 */
	public void setPrevActor(Actor path) {
		prevActor = path; //set our previous actor from the path we are coming from
	}
	
	/**
	 * Sets bacon link
	 * @param path		Actor used in the bacon link
	 */
	public void setBaconLink(Actor path) {
		baconLink = path; //set the bacon link
		baconMovie = movieLinks.get(baconLink); //set our bacon movie based on our bacon link
	}
	
	/**
	 * Sets bacon number
	 * @param distance		distance in the graph from KB
	 */
	public void setBaconNumber(int distance) {
		baconNumber = distance;
	}
		
	/**
	 * Gets adjacent actors
	 * @return		Set of actors adjacent to a given actor
	 */
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
	
	/**
	 * Name of actor 
	 * @return		Returns name of actor
	 */
	public String toString() {
		return name;
	}
}
