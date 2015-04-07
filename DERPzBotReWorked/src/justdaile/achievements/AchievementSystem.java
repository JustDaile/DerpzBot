package justdaile.achievements;

import java.util.ArrayList;

import org.jibble.pircbot.PircBot;

import justdaile.bot.chan.Channel;
import justdaile.bot.chan.ChannelSystem;
import justdaile.bot.chan.User;

public class AchievementSystem {

	private static ArrayList<Achievement> achievements = new ArrayList<Achievement>();
	private static PircBot bot;
	
	public AchievementSystem(PircBot bot){
		AchievementSystem.bot = bot;
	}
	
	public static void addAchievement(Achievement achievement){
		AchievementSystem.achievements.add(achievement);
	}
	
	public static void update(){
		for(int i = 0; i < ChannelSystem.totalChannels(); i++){
			Channel channel = ChannelSystem.getChannel(i);
			for(int x = 0; x < channel.totalOnlineUsers(); x++){
				User user = channel.getUser(x);
				for(int a = 0; a < AchievementSystem.achievements.size(); a++){
					if(AchievementSystem.achievements.get(a).conditionMet(channel, user) && !user.hasAchievement(AchievementSystem.achievements.get(a)) && user.isOnline()){
						user.unlockAchievement(AchievementSystem.achievements.get(a));
						bot.sendMessage(channel.getName(), user.getLogin() + " unlocked " + AchievementSystem.achievements.get(a).getName() + " - " + AchievementSystem.achievements.get(a).getDescription());
						user.addPoints(AchievementSystem.achievements.get(i).getPoints());
						channel.saveChannel();
					}
				}
			}
		}
	}

	public static Achievement getAchievement(String achievementId) {
		for(int i = 0; i < AchievementSystem.achievements.size(); i++){
			if(AchievementSystem.achievements.get(i).getId().contentEquals(achievementId)){
				return AchievementSystem.achievements.get(i);
			}
		}
		return null;
	}
	
}
