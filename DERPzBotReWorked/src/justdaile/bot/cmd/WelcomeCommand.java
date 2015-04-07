package justdaile.bot.cmd;

import justdaile.bot.chan.Channel;

import org.jibble.pircbot.PircBot;

public class WelcomeCommand extends Command {

	public WelcomeCommand() {
		super("welcome", "set welcome message use $name for users name and $channel for channel name");
		this.expect("<message>");
	}

	@Override
	public void doCommand(String extra, PircBot bot, Channel channel, String user) {
		if(isChannelOwner(channel, user)){
			channel.setWelcomeMessage(extra);
			bot.sendMessage(channel.getName(), "set message : " + extra);
		}
	}

}
