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
		int baconNumber = 0; //set our bacon Number to be 0
		if (name.equals("Kevin Bacon")) { //if we are looking for Kevin Bacon (trivial case)
			System.out.println("Kevin Bacon's Bacon Number is 0."); //print our distance
		}
		else { //non trivial case
			Actor source = searchByName(name); //get the source actor
			Queue<Actor> searchQueue = new LinkedList<Actor>(); //queue of actors we need to search
			Set<Actor> visitedActors = new HashSet<Actor>(); //keep track of where we have been
			while (!searchQueue.isEmpty()) { //while the search queue isn't empty
				baconNumber ++; //increment our bacon number
				Actor query = searchQueue.poll(); //get the next actor we should look at in our queue

				Set<Actor> adjacencies = query.getAdjacentActors(); //get the adjacent actors to our source
				visitedActors.add(query); //add our source actor to the list of actors we have visited
				for (Actor adjacency : adjacencies) { // for all the adjacent actors,
					if (!visitedActors.contains(adjacency)) { //if we haven't visited the query yet
						adjacency.setPrevActor(query); //set the adjacent actors' path
						searchQueue.add(adjacency); //add each adjacency to the queue we need to search
					}
				}			
			}

		}
	}

	public void createBaconGraph() {
		Actor source  = searchByName("Kevin Bacon"); //get Kevin Bacon
		source.setBaconNumber(0); //set Kevin Bacon's bacon number to be 0
		source.setBaconLink(source); //Kevin Bacon is his own link
		
		Queue<Actor> searchQueue = new LinkedList<Actor>(); //queue of actors we need to search
		searchQueue.add(source); //add kevin bacon in
		Set<Actor> visitedActors = new HashSet<Actor>(); //create a list of actors we've visited
		while (!searchQueue.isEmpty()) { //until the search queue is empty
			Actor query = searchQueue.poll(); //get the next thing we need to look at
			visitedActors.add(query); //add it to our list of visited actors
			Set<Actor> adjacencies = query.getAdjacentActors(); //get the adjacencies
			for (Actor adjacency: adjacencies) { //for all adjacent actors
				if (!visitedActors.contains(adjacency)) { //if we haven't looked at them yet
					searchQueue.add(adjacency); //add them to our search queue
					adjacency.setBaconNumber(query.getBaconNumber() + 1); //set their bacon number
					adjacency.setBaconLink(query); //as well as their bacon path and movie
				}
			}
		}
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
	
	public void getBaconInfo(String name) { //print out the bacon info for a star
		Actor star = searchByName(name); //get the star
		System.out.println();
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
		
		testGraph.createBaconGraph(); //set the bacon info for each actor

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
