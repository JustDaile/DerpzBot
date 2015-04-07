package justdaile.bot.cmd;

import justdaile.bot.chan.Channel;

import org.jibble.pircbot.PircBot;

public class TwitterCommand extends Command {

	public TwitterCommand() {
		super("twitter", "set or get broadcasters twitter");
	}

	@Override
	public void doCommand(String extra, PircBot bot, Channel channel, String user) {
		if(extra == null){
			bot.sendMessage(channel.getName(), channel.getTwitter());
		}else if(isChannelOwner(channel, user)){
			channel.setTwitter(extra);
			bot.sendMessage(channel.getName(), "set twitter " + extra);
		}
	}

}
