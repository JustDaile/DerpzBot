package justdaile.achievements;

import justdaile.bot.chan.Channel;
import justdaile.bot.chan.User;

public interface Unlockable {

	public boolean conditionMet(Channel channel, User user);
	
}
