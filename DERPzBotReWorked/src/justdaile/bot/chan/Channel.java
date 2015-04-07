package justdaile.bot.chan;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;

import org.jibble.pircbot.PircBot;

import justdaile.bot.TwitchBotV2;
import justdaile.ini.INIFileParser;
import justdaile.ini.Key;

public class Channel {

	private File channelFile, channelSettingsFile;
	private ArrayList<User> users = new ArrayList<User>();
	private String welcomeMessage = "Welcome $name to $channel's channel.";
	private String twitter = "I'm sorry twitter account has not been set by the broadcaster";
	private String facebook = "I'm sorry facebook account has not been set by the broadcaster";
	private String gamertag = "I'm sorry gamertag has not been set by the broadcaster";
	private String psn = "I'm sorry psn username has not been set by the broadcaster";
	private String steam = "I'm sorry steam username has not been set by the broadcaster";
	private String name;
	private long startTime;
	private GameManager gm;
	
	public Channel(String name){
		this.name = name;
		this.channelFile = new File("res/channels/" + this.name + ".ini");
		this.channelSettingsFile = new File("res/channels/" + this.name + "_settings.ini");
		this.startTime = System.currentTimeMillis();
		this.gm = new GameManager();
	}
	
	public long getStartTime(){
		return this.startTime;
	}
	
	public void checkUsers(PircBot bot){ // check users status
		for(int i = 0; i < bot.getUsers(this.name).length; i++){
			if(!this.hasUser(bot.getUsers(this.name)[i].getNick()) && !bot.getUsers(this.name)[i].getNick().contentEquals(TwitchBotV2.NAME.toLowerCase()) && !bot.getUsers(this.name)[i].getNick().contentEquals(this.getName())){
				this.addUser(new User(bot.getUsers(this.name)[i].getNick()));
			}
		}
		for(int i = 0; i < this.users.size(); i++){
			User user = this.getUser(i);
			if(!user.getLogin().contentEquals(this.getName())){ // if user isn't owner
				if(this.userInList(bot, user) && !user.isOnline() && !user.getLogin().contentEquals(bot.getNick().toLowerCase())){ // if user is in chat list but isn't set online
					user.setOnline(true);
					user.addView();
				}else if(!this.userInList(bot, user) && user.isOnline()){
					user.setOnline(false);
					bot.sendMessage(this.name, user.getLogin() + " left");
				}
			}
		}
	}
	
	private boolean userInList(PircBot bot, User user){
		for(int i = 0; i < bot.getUsers(this.name).length; i++){
			if(bot.getUsers(this.name)[i].getNick().contentEquals(user.getLogin())){
				return true;
			}
		}
		return false;
	}
	
	public String getName(){
		return this.name;
	}
	
	/**
	 * @return
	 *        a count of the users in the ArrayList
	 */
	public int totalUsers(){
		return this.users.size();
	}
	
	/**
	 * @return
	 *        a count of the users in the ArrayList that are set online
	 */
	public int totalOnlineUsers(){
		int count = 0;
		for(int i = 0; i < users.size(); i++){
			if(this.users.get(i).isOnline()){
				count++;
			}
		}
		return count;
	}
	
	public User getTopCommenter(){
		User user = null;
		for(int i = 0; i < users.size(); i++){
			if(user == null || this.users.get(i).getTotalComments() > user.getTotalComments()){
				user = this.users.get(i);
			}
		}
		return user;
	}
	
	public User getTopScorer(){
		User user = null;
		for(int i = 0; i < this.users.size(); i++){
			if(user == null || this.users.get(i).getTotalPoints() > user.getTotalPoints()){
				user = this.users.get(i);
			}
		}
		return user;
	}
	
	public User getTopDonator(){
		User user = null;
		for(int i = 0; i < this.users.size(); i++){
			if(user == null || this.users.get(i).getTotalDonated() > user.getTotalDonated()){
				user = this.users.get(i);
			}
		}
		return user;
	}
	
	public boolean hasUser(String login) {
		return this.getUser(login) != null;
	}
	
	public void addUser(User user){
		if(!user.getLogin().toLowerCase().contentEquals("moobot")){
			this.users.add(user);
		}
	}
	
	public User getUser(int i){
		return this.users.get(i);
	}
	
	public User getUser(String login){
		for(int i = 0; i < this.totalUsers(); i++){
			if(this.getUser(i).getLogin().contentEquals(login)){
				return this.getUser(i);
			}
		}
		return null;
	}
	
	public synchronized void loadChannel(){
		INIFileParser channelData = new INIFileParser(this.channelFile);
		if(channelData.countKeys() >= 1){
			for(int i = 0; i < channelData.countKeys(); i++){
				Key k = channelData.getKey(i);
				String login = k.name;
				String comments = k.getSettingOrDie("comments").value;
				String points = k.getSettingOrDie("points").value;
				String views = k.getSettingOrDie("views").value;
				String donated = k.getSettingOrDie("donated").value;
				String achievements = k.getSettingOrDie("achievements").value;
				
				if(login.contentEquals(this.getName())){continue;} // don't save owners data
				User user = new User(login);
				user.setComments(Integer.parseInt(comments));
				user.setPoints(Integer.parseInt(points));
				user.setViews(Integer.parseInt(views));
				user.setDonated(Float.parseFloat(donated));
				user.setAchievements(achievements);
				if(!this.hasUser(user.getLogin())){
					this.addUser(user);
				}
			}
		}
		
		INIFileParser channelSettings = new INIFileParser(this.channelSettingsFile);
		if(channelSettings.hasKey("settings")){
			Key k = channelSettings.getKey("settings");
			String welcome = k.getSettingOrDie("welcome").value;
			String twitter = k.getSettingOrDie("twitter").value;
			String facebook = k.getSettingOrDie("facebook").value;
			String steam = k.getSettingOrDie("steam").value;
			String gamertag = k.getSettingOrDie("gamertag").value;
			String psn = k.getSettingOrDie("psn").value;
			setWelcomeMessage(welcome);
			setTwitter(twitter);
			setFacebook(facebook);
			setSteam(steam);
			setGamertag(gamertag);
			setPSN(psn);
		}
		
		if(channelSettings.hasKey("gamelist")){
			Key k = channelSettings.getKey("gamelist");
			for(int i = 0; i < k.countSettings(); i++){
				this.getGameManager().addGame(k.getSetting(i).name + "&" + k.getSetting(i).value);
			}
		}
		
	}

	public synchronized void saveChannel(){
		try{
			FileWriter w = new FileWriter(this.channelFile);
			for(int i = 0; i < this.totalUsers(); i++){
				User user = this.getUser(i);
				w.write("[" + user.getLogin() + "]\n");
				w.write("comments=" + user.getTotalComments() + "\n");
				w.write("points=" + user.getTotalPoints() + "\n");
				w.write("views=" + user.getViews() + "\n");
				w.write("donated=" + user.getTotalDonated() + "\n");
				w.write("achievements=" + user.getAchievementList() + "\n");
			}
			w.close();
			
			// load custom settings
			w = new FileWriter(this.channelSettingsFile);
			w.write("[settings]\n");
			w.write("welcome=" + this.welcomeMessage + "\n");
			w.write("twitter=" + getTwitter() + "\n");
			w.write("facebook=" + getFacebook() + "\n");
			w.write("steam=" + getSteam() + "\n");
			w.write("gamertag=" + getGametag() + "\n");
			w.write("psn=" + getPSN() + "\n");
			
			w.write("[gamelist]\n");
			GameManager gm = this.getGameManager();
			for(int i = 0; i < gm.totalGames(); i++){
				w.write(gm.getGame(i).getName() + "=" + gm.getGame(i).getPlatform() + "\n");
			}
			w.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void setWelcomeMessage(String message){
		this.welcomeMessage = message;
	}

	public String getWelcomeMessage(String login) {
		return this.welcomeMessage.replace("$name", login).replace("$channel", this.name);
	}
	
	public void setTwitter(String twitter){
		this.twitter = twitter;
	}

	public String getTwitter() {
		return this.twitter;
	}
	
	public void setFacebook(String facebook){
		this.facebook = facebook;
	}
	
	public String getFacebook(){
		return this.facebook;
	}
	
	public void setGamertag(String gamertag){
		this.gamertag = gamertag;
	}

	public String getGametag(){
		return this.gamertag;
	}
	
	public void setPSN(String psn){
		this.psn = psn;
	}
	
	public String getPSN(){
		return this.psn;
	}
	
	public void setSteam(String steam){
		this.steam = steam;
	}
	
	public String getSteam() {
		return this.steam;
	}

	public GameManager getGameManager() {
		return gm;
	}
	
}
