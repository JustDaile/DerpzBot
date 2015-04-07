package justdaile.bot.chan;

import java.util.ArrayList;

import justdaile.achievements.Achievement;

public class User {
	
	private ArrayList<String> achievementIDs = new ArrayList<String>();
	private String login;
	private boolean online, welcomed;
	private int comment, point, views;
	private float donate;
	private long loginTime;
	
	public User(String login){
		this.login = login;
		this.online = false;
		this.comment = 0;
		this.point = 0;
		this.donate = 0.0f;
	}

	public void setOnline(boolean online) {
		this.online = online;
		if(online){
			this.loginTime = System.currentTimeMillis();
		}
	}
	
	public long getLoginTime(){
		return this.loginTime;
	}
	
	public void setViews(int views){
		this.views = views;
	}
	
	public void addView(){
		this.setViews(this.getViews() + 1);
	}
	
	public int getViews(){
		return this.views;
	}

	public void setComments(int comment) {
		this.comment = comment;
	}
	
	public void addComment(){
		this.setComments(this.getTotalComments() + 1);
	}

	public void setPoints(int point) {
		this.point = point;
	}
	
	public void addPoints(int points){
		this.setPoints(this.getTotalPoints() + points);
	}

	public void setDonated(float donate) {
		this.donate = donate;
	}
	
	public void addDonated(float donate){
		this.setDonated(this.getTotalDonated() + donate);
	}

	public boolean isOnline() {
		return this.online;
	}

	public int getTotalComments() {
		return this.comment;
	}

	public int getTotalPoints() {
		return this.point;
	}

	public float getTotalDonated() {
		return this.donate;
	}

	public String getLogin() {
		return this.login;
	}

	public boolean hasAchievement(Achievement achievement){
		return this.hasAchievement(achievement.getId());
	}
	
	public boolean hasAchievement(String id){
		for(int i = 0; i < this.achievementIDs.size(); i++){
			if(this.achievementIDs.get(i).contentEquals(id)){
				return true;
			}
		}
		return false;
	}
	
	public void unlockAchievement(Achievement achievement) {
		this.unlockAchievement(achievement.getId());
	}
	
	public void unlockAchievement(String id){
		this.achievementIDs.add(id);
	}
	
	public void setAchievements(String ids){
		int start = 0, end = 0;
		if(ids.startsWith(",")){
			ids = ids.substring(1, ids.length());
		}
		if(ids.contains(",")){
			end = ids.indexOf(",");
		}else{
			end = ids.length();
		}
		String segment = ids.substring(start, end);
		ids = ids.replace(segment, "").trim();
		this.achievementIDs.add(segment);
		if(ids.length() > 1){
			setAchievements(ids); // use recurrsion
		}
	}

	public String getAchievementList() {
		String list = "";
		for(int i = 0; i < achievementIDs.size(); i++){
			list += achievementIDs.get(i);
			if(i < achievementIDs.size() - 1){
				list += ",";
			}
		}
		return list;
	}
	
	public int getTotalAchievements(){
		return this.achievementIDs.size();
	}
	
	public String getAchievementId(int index){
		return this.achievementIDs.get(index);
	}

	// methods below are used by the bot when a user comments in chat. 
	public boolean welcomed() {
		return welcomed;
	}
	
	public void welcome(){
		this.welcomed = true;
	}

}
