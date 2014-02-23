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
	 * 
	 */
	public void testGraphCreator() throws Exception{
		//create our test Graph		
		
		//input actors text
		BufferedReader actorInput = new BufferedReader(new FileReader("inputs/actorsTest.txt"));
		String line = new String();
		String[] parts = new String[2];
		while ((line = actorInput.readLine()) != null) {
		    parts = line.split("\\|");
		    addActor(parts[0], parts[1]);
		}
		
		//input movies
		BufferedReader movieInput = new BufferedReader(new FileReader("inputs/moviesTest.txt"));
		while ((line = movieInput.readLine()) != null) {
		    parts = line.split("\\|");
		    addMovie(parts[0], parts[1]);
		}
		
		//get their relations
		BufferedReader relationsInput = new BufferedReader(new FileReader("inputs/movie-actorsTest.txt"));
		while ((line = relationsInput.readLine()) != null) {
			parts = line.split("\\|");
			Movie film = movieIDs.get(parts[0]); //get the movie
			film.addActor(actorIDs.get(parts[1])); //add the actor to the cast list
		}

		System.out.println(this.actorIDs.size());
		System.out.println(this.movieIDs.size());
		System.out.println("end");
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		BaconGraph testGraph = new BaconGraph();
		try {
			testGraph.testGraphCreator();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String command = "";
		Scanner input = new Scanner(System.in);
		System.out.println("To quit the program, type return in answer to a question.");
		
		while (true) {
			System.out.println("Enter the name of an actor:");
			command = input.nextLine();
			if(testGraph.actorNames.containsKey(command)) {
				System.out.println("Found!");
			}
			if(command.equals("return")) {
				System.out.println("Bye");
				break;
			}
		}
	}

}
