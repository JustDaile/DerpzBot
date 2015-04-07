package justdaile.bot.cmd;

import justdaile.bot.chan.Channel;

import org.jibble.pircbot.PircBot;

public class LeaveCommand extends Command {

	public LeaveCommand() {
		super("leave", "leave the channel");
	}

	@Override
	public void doCommand(String extra, PircBot bot, Channel channel, String user) {
		if(isChannelOwner(channel, user)){
			bot.sendMessage(channel.getName(), "Thank you for using DERPzBot");
			bot.partChannel(channel.getName());
		}
	}

}
