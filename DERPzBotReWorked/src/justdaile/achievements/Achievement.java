package justdaile.achievements;

public abstract class Achievement implements Unlockable{
	
	private String id;
	private String name;
	private String description;
	private int points;
	
	public Achievement(String id, String name, String description, int points){
		this.id = id;
		this.name = name;
		this.description = description;
		this.points = points;
	}
	
	public String getId(){
		return this.id;
	}
	
	public String getName(){
		return this.name;
	}
	
	public String getDescription(){
		return this.description;
	}
	
	public int getPoints(){
		return this.points;
	}
	
}
