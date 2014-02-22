import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;
import java.util.*
;


public class Bacon {
	
	
	// name -> Movies that actor has been in
	// name -> Actor object
	private HashMap<String,Actor> actors;
	private HashMap<String,Movie> movies;
	
	public Bacon() {
		actors = new HashMap<String,Actor>();
		movies = new HashMap<String,Movie>();
	}
	
	/**
	 * Given that the actors and movies maps are properly
	 * initialized from readFile. Explore all of the actors
	 * starting from Kevin Bacon in a breadth-first manner.
	 * After completing traverse, all Actors connected to
	 * Kevin Bacon (by one or more links) should have 
	 * their Bacon numbers set.
	 */
	public void traverse() {
		int vis = 0;
		Actor start  = actors.get("Bacon, Kevin");
		start.setNumber(0);
		start.setPrevActor(null);
		
		Queue<Actor> fringe = new LinkedList<Actor>();
		fringe.add(start);
		HashSet<Actor> visited = new HashSet<Actor>();
		while (!fringe.isEmpty())
		{
			Actor curr = fringe.poll();
			visited.add(curr);
			if (vis++ % 1000 == 0)
				System.out.print("#");
			Map<Actor,Movie> coStars = curr.coStars();
			
			// for every actor who was a costar with curr
			for (Actor a: coStars.keySet())
			{
				// add to queue
				if (!visited.contains(a))
				{
					fringe.add(a);
					a.setNumber(curr.getNumber() + 1);
					a.setPrevActor(curr);
					a.setSharedMovie(coStars.get(a));
				}
			}
			
		}
	}
	
	public void baconNumber(String name) {
		
	}
	
	public void printTable()
	{
		
	}
	
	/**
	 * Print the chain from Kevin Bacon to specified
	 * actor or actress. If no such actor or actress. print
	 * error message
	 * Actor Name has a Bacon number of X
	 * Actor Name appeared in Movie Nam with Actor 2 Name
	 * ...
	 * Actor Z Name appeared in Movie Z Name with Kevin Bacon
	 * @param name for actor or actress. 
	 */
	public void printChain(String name)
	{
		Actor start  = actors.get("Bacon, Kevin");
		Actor dest = actors.get(name);
		
		if (dest == null)
			System.out.println("No such actor " + name);
		if (dest.getNumber() == Integer.MAX_VALUE)
		{
			System.out.println(dest.getName() + "has a Bacon number of infinity");
			return;
		}
		System.out.println(dest.getName() + " has a Bacon number of " + 
				dest.getNumber());
		
		while (dest != start)
		{
			System.out.println(dest.getName() + " was in " +
					dest.getSharedMovie().getName() + " with " + 
					dest.getPrevActor().getName());
			dest = dest.getPrevActor();
		}
		
	}
	
	public void readFile(Scanner in)
	{
		long begin = System.currentTimeMillis();
		// Each line of file 
		// movie name/actor 1/actor 2/ .... / actress n
		while (in.hasNext())
		{
			String line = in.nextLine();
			String[] elems = line.split("|");
			if (elems.length == 0)
			{
				System.out.println("BAD line in input!");
				continue;
			}
			// create movie
			Movie moov = new Movie(elems[0]);
			// add movie to movie map
			movies.put(elems[0], moov);
			// loop through elems 
			for (int k = 1; k < elems.length; k++)
			{
				//  create an actor and add to actors map and
				//    to movie's list of actors
				Actor person = actors.get(elems[k]);
				if (person == null)
				{ // new actor
					 person = new Actor(elems[k]);
					 actors.put(elems[k], person);
				}
				person.add(moov);
				moov.addActor(person);
				
			}
		
			
		}
		System.out.println("File read. Time elapsed: " +
				(System.currentTimeMillis() - begin) * .001 + "s");
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String fileName = "inputs/actors.txt";
		Scanner f = null;;
		Bacon bfs = new Bacon();
		if (args.length > 0) // filename is intial argument
			fileName = args[0];
		// create Scanner for fileName
		try {
			f = new Scanner(new File(fileName));
		} catch (FileNotFoundException e) {
			System.out.println("File not found " + fileName);
			e.printStackTrace();
		}
		bfs.readFile(f);
		bfs.traverse();
		bfs.printChain("Bacon, Kevin");
		bfs.printChain("Sinatra, Frank");
	}

}
