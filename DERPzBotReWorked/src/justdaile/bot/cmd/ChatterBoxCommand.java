package justdaile.bot.cmd;

import justdaile.bot.chan.Channel;
import justdaile.bot.chan.User;

import org.jibble.pircbot.PircBot;

public class ChatterBoxCommand extends Command {
	
	public ChatterBoxCommand(){
		super("chatterbox", "show user with the most comments");
	}

	@Override
	public void doCommand(String extra, PircBot bot, Channel channel, String user) {
		User tc = channel.getTopCommenter();
		bot.sendMessage(channel.getName(), "TopCommenter is " + tc.getLogin() + " " + tc.getTotalComments());
	}
	
}
