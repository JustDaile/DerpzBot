package justdaile.bot.cmd;

import justdaile.bot.chan.Channel;

import org.jibble.pircbot.PircBot;

public class PsnCommand extends Command {

	public PsnCommand() {
		super("psn", "set or get broadcasters psn");
	}

	@Override
	public void doCommand(String extra, PircBot bot, Channel channel, String user) {
		if(extra == null){
			bot.sendMessage(channel.getName(), channel.getPSN());
		}else if(isChannelOwner(channel, user)){
			channel.setPSN(extra);
			bot.sendMessage(channel.getName(), "set psn " + extra);
		}
	}

}
