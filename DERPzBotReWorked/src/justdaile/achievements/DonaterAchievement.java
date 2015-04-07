package justdaile.achievements;

import justdaile.bot.chan.Channel;
import justdaile.bot.chan.User;

public class DonaterAchievement extends Achievement {

	public DonaterAchievement() {
		super("0006", "Donator", "You donated to the channel", 100);
	}

	@Override
	public boolean conditionMet(Channel channel, User user) {
		return user.getTotalDonated() > 0.0;
	}


}
