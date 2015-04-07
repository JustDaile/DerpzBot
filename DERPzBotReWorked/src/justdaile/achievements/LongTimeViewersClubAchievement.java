package justdaile.achievements;

import justdaile.bot.chan.Channel;
import justdaile.bot.chan.User;

public class LongTimeViewersClubAchievement extends Achievement {

	public LongTimeViewersClubAchievement() {
		super("0005", "Long Time Viewers Club", "You watched for an hour or more", 100);
	}

	@Override
	public boolean conditionMet(Channel channel, User user) {
		return System.currentTimeMillis() - user.getLoginTime() >= (60000 * 60);
	}


}
