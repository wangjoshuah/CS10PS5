import java.util.HashSet;
import java.util.Set;

/**
 * Movie class for use in Bacon Game
 * @author Alexander Tsu & Joshua Wang
 *
 */

public class Movie {
	private String name;
	private Set<Actor> actors;
	
	/**
	 * Construct for Movie Class
	 * @param title		name of movie
	 */
	public Movie(String title)
	{
		name = title;
		actors = new HashSet<Actor>();
	}
	
	/**
	 * Get name of movie
	 * @return		name of movie
	 */
	public String getName() {
		return name;
	}

	/**
	 * Get actors in a movie
	 * @return		set of actors in a given movie
	 */
	public Set<Actor> getActors() {
		return actors;
	}
	
	/**
	 * Add an actor into a movie
	 * @param person		actor to add into a movie
	 */
	public void addActor(Actor person) {
		actors.add(person);
	}
	
	/**
	 * Link actors in the same movie together
	 */
	public void linkActors() {
		for (Actor star: actors) { //for each actor in the cast list
			star.addMovie(this); //add this movie in the stars' movies
			for (Actor coStar: actors) { //get all other actors
				if (star != coStar) { //if they are different people
					star.addCoStar(this, coStar); //add their friendship through this movie
				}
			}
		}
	}
	
	/**
	 * Get name of movie
	 * @return		Name of Movie
	 */
	public String toString() {
		return name;
	}

}
