import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;
import java.util.*
;


public class BaconGraph {

	private Map<String, Actor> actors; //our map of actors
	private Map<String, Movie> movies; //our map of movies

	public BaconGraph() {
		actors = new HashMap<String, Actor>();
		movies = new HashMap<String, Movie>();
	}
	
	public void getBaconNumber(String name) {
		Actor actor = actors.get(name);

	}

	/**
	 * Add an actor to our map of actors
	 * @param idNumber 	use the id number from the data
	 * @param name		take in the actor's name
	 */
	public void addActor(String idNumber, String name) {
		Actor actor = new Actor(name);
		actors.put(idNumber, actor);
	}

	/**
	 * add a moivie to our map of movies
	 * @param idNumber	use the id number from the data
	 * @param title		name of the movie we are going to add
	 */
	public void addMovie(String idNumber, String title) {
		Movie movie = new Movie(title);
		movies.put(idNumber, movie);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
	}

}
