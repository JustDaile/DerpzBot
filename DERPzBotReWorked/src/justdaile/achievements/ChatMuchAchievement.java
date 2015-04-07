package justdaile.achievements;

import justdaile.bot.chan.Channel;
import justdaile.bot.chan.User;

public class ChatMuchAchievement extends Achievement {

	public ChatMuchAchievement() {
		super("0002", "Chat Much", "Post 50 or more comments", 25);
	}

	@Override
	public boolean conditionMet(Channel channel, User user) {
		return user.getTotalComments() >= 50;
	}

}
