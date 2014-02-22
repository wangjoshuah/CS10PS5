import java.util.ArrayList;
import java.util.*;

public class Actor {
	private String name;
	private ArrayList<Movie> movies;
	private int number; // distance from source
	private HashMap<Actor,Movie> myCoStars;
	private Actor prevActor;
	private Movie sharedMovie;
	
	public Actor(String person)
	{
		name = person;
		movies = new ArrayList<Movie>();
	}
	public String getName() {
		return name;
	}

	public ArrayList<Movie> getMovies() {
		return movies;
	}

	public void add(Movie m)
	{
		movies.add(m);
	}
	
	
	
	
}
