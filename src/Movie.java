import java.util.HashSet;
import java.util.Set;


public class Movie {
	private String name;
	private Set<Actor> actors;
	
	public Movie(String title)
	{
		name = title;
		actors = new HashSet<Actor>();
	}
	
	public String getName() {
		return name;
	}

	public Set<Actor> getActors() {
		return actors;
	}
	
	public void addActor(Actor person) {
		actors.add(person);
	}
	
	public void linkActors() {
		for (Actor star: actors) { //for each actor in the cast list
			for (Actor coStar: actors) { //get all other actors
				if (star != coStar) { //if they are different people
					star.addCoStar(this, coStar); //add their friendship through this movie
				}
			}
		}
	}

}
