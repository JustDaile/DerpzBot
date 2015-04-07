package justdaile.achievements;

import justdaile.bot.chan.Channel;
import justdaile.bot.chan.User;

public class NewbieAchievement extends Achievement{

	public NewbieAchievement(){
		super("0000", "Newbie", "Bot welcomed you to the channel", 10);
	}
	
	public boolean conditionMet(Channel channel, User user){
		return user.welcomed();
	}
	
}
