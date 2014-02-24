import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

/**
 * Main Method for constructing a graph from the IMDb database of movies and actors and allows user to play Kevin Bacon game in said graph.
 * @author Alexander Tsu & Joshua Wang
 */

public class BaconGraph {

	private Map<String, String> actorNames; //maps actor names to their ids
	private Map<String, Actor> actorIDs; //our map of actors
	private Map<String, Movie> movieIDs; //our map of movies

	/**
	 * Constructor that creates 3 maps containing actors, movies, and matches actor names and ids
	 */
	public BaconGraph() {
		actorNames = new HashMap<String, String>();
		actorIDs = new HashMap<String, Actor>();
		movieIDs = new HashMap<String, Movie>();
	}

	/**
	 * Loops through each actor in the data set and uses Breadth Fist Search to construct a bacon number for each
	 */
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
				if (!visitedActors.contains(adjacency) && !searchQueue.contains(adjacency)) { //if we haven't looked at them yet and we don't plan on it
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
		Movie movie = new Movie(title); //create new Movie based on the title parameter
		movieIDs.put(idNumber, movie); //puts that movie into the map
	}

	/**
	 * Uses buffered readers to input data from the txt files. Then puts the data into our 3 HashMaps
	 * @throws Exception
	 */
	public void fullGraphCreator() throws Exception{
		//create our test Graph		

		//input actors text
		BufferedReader actorInput = new BufferedReader(new FileReader("inputs/actors.txt")); //Buffered reader is fed the txt file line by lin
		String line = new String(); //create a new string
		String[] parts = new String[2]; //create an array of two parts
		while ((line = actorInput.readLine()) != null) { //while the buffered reader continues reading
			parts = line.split("\\|"); //split each line in txt file into two parts, each assigned to an entry in the array
			addActor(parts[0], parts[1]); //put the two parts of the array into our map
		}

		//input movies. Same as previous while loop.
		BufferedReader movieInput = new BufferedReader(new FileReader("inputs/movies.txt"));
		while ((line = movieInput.readLine()) != null) {
			parts = line.split("\\|");
			addMovie(parts[0], parts[1]);
		}

		//get their relations. Same as previous while loop.
		BufferedReader relationsInput = new BufferedReader(new FileReader("inputs/movie-actors.txt"));
		while ((line = relationsInput.readLine()) != null) {
			parts = line.split("\\|");
			Movie film = movieIDs.get(parts[0]); //get the movie
			film.addActor(actorIDs.get(parts[1])); //add the actor to the cast list
		}

		//Calls the linkActors() method for each movie
		for (Movie film : movieIDs.values()) {
			film.linkActors(); //connect the actors to each other
		}
	}

	/**
	 * Prints the name and movie of each star in the Bacon Game
	 * @param name		name of the actor that the user inputs
	 */
	public void getBaconInfo(String name) { //print out the bacon info for a star
		Actor star = searchByName(name); //get the star
		if (star.getBaconNumber() == -1) { //if they are not connected
			System.out.println(star.getName() + " is not connected to Kevin Bacon");
		}
		else { //if they are
			System.out.println(star.getName() + "'s number is " + star.getBaconNumber());
			while(!star.getName().equals("Kevin Bacon")) { //print till we get KB
				Actor target = star.getBaconLink(); //get the next actor in the bacon link
				System.out.println(star.getName() + " appeared in " + star.getBaconMovie() + " with " + target.getName()); //print out the actor, the movie the actor and the next actor in the bacon link were in, and print the next actor in the bacon link
				star = target; //set the actor 
			}
		}
		System.out.println();
	}

	/**
	 * Our Main method. Inputs the data from the text files, calls the create createBaconGraph() method, and contains the user interface
	 * @param args
	 */
	public static void main(String[] args) {
		BaconGraph bGraph = new BaconGraph(); //create a new bacon graph
		try {
			bGraph.fullGraphCreator(); //call the graph creator. Loads the data into the maps
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		bGraph.createBaconGraph(); //set the bacon info for each actor
		
		String command = ""; //create empty string
		Scanner input = new Scanner(System.in); //create a scanner used for user input
		System.out.println("To quit the program, type return in answer to a question.");
		while (true) { //infinite loop that exits only when user types "return"
			System.out.println("Enter the name of an actor:");
			command = input.nextLine();
			if(bGraph.actorNames.containsKey(command)) { //if the user inputs the name of an actor within our Map
				bGraph.getBaconInfo(command); //return its bacon information
			}
			else if(command.equals("return")) { //if user types return
				System.out.println("Bye");
				break; //exit the while loop
			}
			else { //otherwise, let the user know the actor is not found
				System.out.println(command + " is not found in our list");
			}
		}
	}

}
