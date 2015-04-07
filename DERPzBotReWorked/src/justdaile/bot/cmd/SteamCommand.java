package justdaile.bot.cmd;

import justdaile.bot.chan.Channel;

import org.jibble.pircbot.PircBot;

public class SteamCommand extends Command {

	public SteamCommand() {
		super("steam", "set or get broadcasters steam");
	}

	@Override
	public void doCommand(String extra, PircBot bot, Channel channel, String user) {
		if(extra == null){
			bot.sendMessage(channel.getName(), channel.getSteam());
		}else if(isChannelOwner(channel, user)){
			channel.setSteam(extra);
			bot.sendMessage(channel.getName(), "set steam " + extra);
		}
	}

}
