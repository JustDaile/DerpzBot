package justdaile.bot.cmd;

import justdaile.achievements.Achievement;
import justdaile.achievements.AchievementSystem;
import justdaile.bot.chan.Channel;
import justdaile.bot.chan.User;

import org.jibble.pircbot.PircBot;

public class AchievementCommand extends Command {

	public AchievementCommand() {
		super("achievements", "show your achievements for this channel");
	}

	@Override
	public void doCommand(String extra, PircBot bot, Channel channel, String user) {
		User u = channel.getUser(user);
		StringBuffer out = new StringBuffer();
		if(!u.getLogin().contentEquals(channel.getName())){
			int ta = u.getTotalAchievements();
			if(ta > 0){
				for(int i = 0; i < ta; i++){
					Achievement a = AchievementSystem.getAchievement(u.getAchievementId(i));
					out.append(a.getName() + " - " + a.getDescription());
					if(i != ta - 1){
						out.append(" > ");
					}
				}
			}else{
				out.append("you have no achievements in this channel");
			}
		}
		bot.sendMessage(channel.getName(), out.toString());
	}

}
