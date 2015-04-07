package justdaile.bot.cmd;

import justdaile.bot.chan.Channel;

import org.jibble.pircbot.PircBot;

public class GamertagCommand extends Command {

	public GamertagCommand() {
		super("gamertag", "set or get broadcasters gamertag");
	}

	@Override
	public void doCommand(String extra, PircBot bot, Channel channel, String user) {
		if(extra == null){
			bot.sendMessage(channel.getName(), channel.getGametag());
		}else if(isChannelOwner(channel, user)){
			channel.setGamertag(extra);
			bot.sendMessage(channel.getName(), "set gamertag " + extra);
		}
	}

}
