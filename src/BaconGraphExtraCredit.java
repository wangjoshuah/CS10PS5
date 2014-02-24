import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

/**
 * Main Method for constructing a graph from the IMDb database of movies and actors and allows user to play Kevin Bacon game in said graph.
 * The following Extra Credits are completed here:
 * 1) Near Match Reporting for inputted Actor Names
 * 2) Statistics on largest Bacon Number, average Bacon Number, and Bacon Distribution
 * 
 * @author Alexander Tsu & Joshua Wang
 */

public class BaconGraphExtraCredit {

	private Map<String, String> actorNames; //maps actor names to their ids
	private Map<String, Actor> actorIDs; //our map of actors
	private Map<String, Movie> movieIDs; //our map of movies
	private static int DISTANCE_LIMIT = 4; //maximum character difference between an incorrect input string and the closest actor name in our data set
	/**
	 * Constructor that creates 3 maps containing actors, movies, and matches actor names and ids
	 */
	public BaconGraphExtraCredit() {
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
	
	/** Calculates the distance between two strings based on the Levenshtein algorithm
	 * Found here: http://rosettacode.org/wiki/Levenshtein_distance#Java
	 * @param a first string
	 * @param b second string to compare against the first string
	 */
    public static int distance(String a, String b) {
        a = a.toLowerCase();
        b = b.toLowerCase();
        int [] costs = new int [b.length() + 1];
        for (int j = 0; j < costs.length; j++)
            costs[j] = j;
        for (int i = 1; i <= a.length(); i++) {
            costs[0] = i;
            int nw = i - 1;
            for (int j = 1; j <= b.length(); j++) {
                int cj = Math.min(1 + Math.min(costs[j], costs[j - 1]), a.charAt(i - 1) == b.charAt(j - 1) ? nw : nw + 1);
                nw = costs[j];
                costs[j] = cj;
            }
        }
        return costs[b.length()];
    }
	
    /**
     * Finds similar names to the name inputed by user using the distance method
     * @param bGraph		baconGraph
     * @param command		name of actor
     */
    public void nameMatcher(String command) {
		ArrayList<String> similarNames = new ArrayList<String>(); //create new Array List of Strings
		for (String actor : actorNames.keySet()) { //iterate through all actor names
			int temp = distance(command, actor); //get the distance between each actor name and the name input
			if(temp < DISTANCE_LIMIT) { //if the inputed string is sufficiently close to a given actor
				similarNames.add(actor); //add that actor to the arraylist
			}
		}
		if(!similarNames.isEmpty()) { //if the array list is not empty
			System.out.println("Name not found. Here are the closest matches:");
			for(int i = 0; i < similarNames.size(); i++) { //loop through all of the names that are sufficiently close
				int similarNamesIndex = i+1;
				System.out.println(similarNamesIndex + ") " + similarNames.get(i)); //print the index number and the name of that actor
			}
		}
		else { //if no actors are sufficiently similar to the inputed string, print this out
			System.out.println("Actor not found and no similar actor names found");
		}
    }
    
    /**
     * Calculates Kevin Bacon statistics based on the data set
     */
    public void baconStatistics () {
		Map<Integer, Set<Actor>> baconStats =  new HashMap<Integer, Set<Actor>>(); //create new map with kevin bacon number as key and a value that is a set of actors
		for(Actor a : actorIDs.values()) { //for each actor
			if(baconStats.containsKey(a.getBaconNumber())) { //if the actor is in the map
				Set<Actor> temp = baconStats.get(a.getBaconNumber()); 
				temp.add(a);
				baconStats.put(a.getBaconNumber(), temp); //add that actor to the set and put the set into the map
			}
			else { //if the actor is not in the map
				Set<Actor> temp = new HashSet<Actor>(); 
				temp.add(a);
				baconStats.put(a.getBaconNumber(), temp); //put the actor into a new set and add the set into the map
			}
		}
		
		//Bacon Distribution
		System.out.println("\nA) This is the distribution of Bacon Numbers in the data set");
		for(int i : baconStats.keySet()) { //for every key in baconStats
			if(i != -1 && i != 0) { //if the key isn't -1 or 0
				System.out.println("There are " + baconStats.get(i).size() + " actors with a Bacon Number of " + i); //print out the size of the set and i
			}
		}
		System.out.println("There are " + baconStats.get(-1).size() + " actors who are not connected to Kevin Bacon"); //for actors that aren't connected to Kevin Bacon (denoted by -1), print out the size of the set
			
		//Largest Bacon Number
		int largestKey = 0;
		for(int i : baconStats.keySet()) { //loop through each i within the keySet of the baconStat. i is equal to the largest BaconNumber within the map
			if(baconStats.containsKey(i) && i != -1) { 
				largestKey = i;
				i++;
			}
		}
		System.out.println("\nB) The largest Bacon Number in this data set is " + largestKey); //print the largest key
		System.out.println("The following actors have this Bacon Number:");
		int idx = 1;
		for(Actor a : baconStats.get(largestKey)) { //iterate through each actor within the set mapped to the largest index
			System.out.println(idx + ") " + a); //print that actor's name out with an index number
			idx++;
		}
		
		//Average Bacon Number
		double averageBaconNumber = 0;
		int denominator = 0;
		for(int i : baconStats.keySet()) { //loop through all the bacon numbers in the map
			if(i != -1 && i != 0) { //exclude any bacon numbers that are 0 or 9
				Set<Actor> temp = baconStats.get(i); //get the size of the set at any given index
				averageBaconNumber += temp.size()*i; //add the size of a given set and multiply it by the bacon number
				denominator += temp.size(); //add the size of each set to the denominator
			}
		}
		averageBaconNumber = averageBaconNumber/denominator; //calculate the average
		System.out.println("\nC) The average Bacon Number in this data set is " + averageBaconNumber + "\n"); //print it out
    }
    
	/**
	 * Our Main method. Inputs the data from the text files, calls the create createBaconGraph() method, and contains the user interface
	 * @param args
	 */
	public static void main(String[] args) {
		BaconGraphExtraCredit bGraph = new BaconGraphExtraCredit();
		try {
			bGraph.fullGraphCreator();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		bGraph.createBaconGraph(); //set the bacon info for each actor

		String command = "";
		Scanner input = new Scanner(System.in);
		System.out.println("To quit the program, type return in answer to a question. \nTo view statistics about this data set, type stats in answer to a question.");
		while (true) { //infinite loop that exits only when user types "return"
			System.out.println("Enter the name of an actor:");
			command = input.nextLine();
			if(bGraph.actorNames.containsKey(command)) { //if the name inputed is an actor name, call get bacon info
				bGraph.getBaconInfo(command);
			}
			else if(command.equals("stats")) { //if the name inputed is stats, call baconStatistics
				bGraph.baconStatistics();
			}
			else if(command.equals("return")) { //if the name inputed is return, exit the while loop
				System.out.println("Bye");
				break;
			}
			else {
				bGraph.nameMatcher(command); //otherwise, see if the nameMatcher method can match an incorrect input name with one of the actors in the list
			}
		}
	}

}
