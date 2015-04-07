package justdaile.bot.cmd;

import justdaile.bot.chan.Channel;

import org.jibble.pircbot.PircBot;

public class FacebookCommand extends Command {

	public FacebookCommand() {
		super("facebook", "set or get broadcasters facebook");
	}

	@Override
	public void doCommand(String extra, PircBot bot, Channel channel, String user) {
		if(extra == null){
			bot.sendMessage(channel.getName(), channel.getFacebook());
		}else if(isChannelOwner(channel, user)){
			channel.setFacebook(extra);
			bot.sendMessage(channel.getName(), "set facebook " + extra);
		}
	}

}
