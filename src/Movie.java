import java.util.ArrayList;


public class Movie {
	private String name;
	private ArrayList<Actor> actors;
	
	public Movie(String title, ArrayList<Actor> cast)
	{
		name = title;
		actors = cast;
	}
	
	public Movie(String title)
	{
		name = title;
		actors = new ArrayList<Actor>();
	}
	
	public String getName() {
		return name;
	}

	public ArrayList<Actor> getActors() {
		return actors;
	}
	
	public void addActor(Actor person) {
		actors.add(person);
	}

}
