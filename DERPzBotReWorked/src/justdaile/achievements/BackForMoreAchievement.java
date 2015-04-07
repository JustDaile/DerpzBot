package justdaile.achievements;

import justdaile.bot.chan.Channel;
import justdaile.bot.chan.User;

public class BackForMoreAchievement extends Achievement {

	public BackForMoreAchievement() {
		super("0007", "Can't get rid of you", "10 or more views on this channel", 50);
	}

	@Override
	public boolean conditionMet(Channel channel, User user) {
		return user.getViews() >= 10;
	}


}
