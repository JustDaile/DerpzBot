package justdaile.achievements;

import justdaile.bot.chan.Channel;
import justdaile.bot.chan.User;

public class JustLikeTVAchievement extends Achievement{

	public JustLikeTVAchievement() {
		super("0001", "Just Like TV", "Watch 30 mins of the broadcast you have commented on 5 times or more", 30);
	}

	@Override
	public boolean conditionMet(Channel channel, User user) {
		return System.currentTimeMillis() - user.getLoginTime() >= (60000 * 30) && user.getTotalComments() >= 5;
	}

}
