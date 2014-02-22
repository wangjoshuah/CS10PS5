import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*
;


public class BaconGraph {

	private Map<String, String> actorNames; //maps actor names to their ids
	private Map<String, Actor> actorIDs; //our map of actors
	private Map<String, Movie> movieIDs; //our map of movies

	public BaconGraph() {
		actorNames = new HashMap<String, String>();
		actorIDs = new HashMap<String, Actor>();
		movieIDs = new HashMap<String, Movie>();
	}
	
	
	
	public void getBaconNumber(String name) {
		

	}

	/**
	 * Add an actor to our map of actors
	 * @param idNumber 	use the id number from the data
	 * @param name		take in the actor's name
	 */
	public void addActor(String idNumber, String name) {
		Actor actor = new Actor(name); //create a new actor class
		actorNames.put(name, idNumber); //put the actor's name in a map getting the id value
		actorIDs.put(idNumber, actor); //get the actor from their id value
	}
	
	/**
	 * searches for an actor by their name instead of the id number
	 * @param name		name of the actor
	 * @return		returns the actor
	 */
	public Actor searchByName(String name) {
		String idNumber = actorNames.get(name); //get the id number from that array of id numbers
		return actorIDs.get(idNumber); //return the actor instance from id number
	}

	/**
	 * add a moivie to our map of movies
	 * @param idNumber	use the id number from the data
	 * @param title		name of the movie we are going to add
	 */
	public void addMovie(String idNumber, String title) {
		Movie movie = new Movie(title);
		movieIDs.put(idNumber, movie);
	}

	/**
	 * processes txt documents
	 * @param args
	 */
	public void fileReader() throws Exception{
		BufferedReader reader = new BufferedReader(new FileReader("/inputs/actors.txt"));
		String line = null;
		while ((line = reader.readLine()) != null) {
		    String[] parts = line.split("|");
		    addActor(parts[0], parts[1]);
		}
		BufferedReader reader2 = new BufferedReader(new FileReader("/inputs/movies.txt"));
		String line2 = null;
		while ((line2 = reader2.readLine()) != null) {
		    String[] parts = line2.split("|");
		    addMovie(parts[0], parts[1]);
		}
		BufferedReader reader3 = new BufferedReader(new FileReader("/inputs/movies-actors.txt"));
		String line3 = null;
		while ((line3 = reader3.readLine()) != null) {
			String[] parts = line3.split("|");
			
		}
	}

	/**
	 * 
	 */
	public void graphCreator() {
		for(String actor: actorNames.keySet()) {
			
		}
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
	}

}
