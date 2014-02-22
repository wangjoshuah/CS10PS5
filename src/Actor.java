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
		number = Integer.MAX_VALUE;
		myCoStars = null;
		prevActor = null;
		sharedMovie = null;
	}
	public String getName() {
		return name;
	}

	public ArrayList<Movie> getMovies() {
		return movies;
	}

	public HashMap<Actor, Movie> coStars() {
		if (myCoStars != null)
			return myCoStars;
		myCoStars = new HashMap<Actor,Movie>();
		for (Movie m: movies)
		{
			for (Actor a: m.getActors())
			{
				if (a != this)
				{
					myCoStars.put(a, m);
				}
			}
		}
		return myCoStars;
	}
	public void add(Movie m)
	{
		movies.add(m);
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int n) {
		if (n < number)
			number = n;
		
	}
	public Actor getPrevActor() {
		return prevActor;
	}
	public void setPrevActor(Actor prevActor) {
		this.prevActor = prevActor;
	}
	public int hashCode()
	{
		return name.hashCode();
	}
	public Movie getSharedMovie() {
		return sharedMovie;
	}
	public void setSharedMovie(Movie sharedMovie) {
		this.sharedMovie = sharedMovie;
	}
	
	
}
