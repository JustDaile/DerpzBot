package justdaile.bot.cmd;

import justdaile.bot.chan.Channel;
import justdaile.bot.chan.User;

import org.jibble.pircbot.PircBot;

public class TopDogCommand extends Command {

	public TopDogCommand() {
		super("topdog", "show user with highest score");
	}

	@Override
	public void doCommand(String extra, PircBot bot, Channel channel, String user) {
		User ts = channel.getTopScorer();
		bot.sendMessage(channel.getName(), "TopScorer is " + ts.getLogin() + " " + ts.getTotalPoints());
	}

}
