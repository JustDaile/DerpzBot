package justdaile.bot.chan;

import java.util.ArrayList;

public class GameManager {

	private ArrayList<Game> games = new ArrayList<Game>();
	
	public void addGame(String game){
		this.addGame(new Game(game));
	}
	
	public void addGame(Game game){
		this.games.add(game);
	}
	
	public void removeGame(String name) {
		int remove = -1;
		for(int i = 0; i < this.games.size(); i++){
			if(this.games.get(i).getName().contentEquals(name)){
				remove = i;
			}
		}
		if(remove != -1){
			this.games.remove(remove);
		}
	}
	
	public int totalGames(){
		return this.games.size();
	}
	
	public Game getGame(int index){
		return this.games.get(index);
	}
	
	public boolean hasGame(String name){
		for(int i = 0; i < this.games.size(); i++){
			if(this.games.get(i).getName().contentEquals(name)){
				return true;
			}
		}
		return false;
	}
	
	public class Game {
		
		private String name;
		private String platform;
		
		public Game(String name){
			if(!name.contains("&")){
				this.name = name;
				this.platform = "unspecified";
			}else{
				this.name = name.substring(0, name.indexOf("&"));
				this.platform = name.substring(name.indexOf("&") + 1, name.length());
			}
		}
		
		public String getName(){
			return this.name;
		}
		
		public String getPlatform(){
			return this.platform;
		}
		
	}
	
}
