package justdaile.achievements;

import justdaile.bot.chan.Channel;
import justdaile.bot.chan.User;

public class BigMouthAchievement extends Achievement {

	public BigMouthAchievement() {
		super("0003", "Big Mouth", "Post 100 or more comments", 50);
	}

	@Override
	public boolean conditionMet(Channel channel, User user) {
		return user.getTotalComments() >= 100;
	}


}
