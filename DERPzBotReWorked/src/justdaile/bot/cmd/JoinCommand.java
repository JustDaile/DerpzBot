package justdaile.bot.cmd;

import org.jibble.pircbot.PircBot;

import justdaile.bot.chan.Channel;

public class JoinCommand extends Command{
	
	public JoinCommand() {
		super("join", "join the specified channel");
		this.expect("<channel>");
	}

	@Override
	public void doCommand(String extra, PircBot bot, Channel channel, String user) {
		if(extra.startsWith("#")){extra = extra.replace("#", "");} // remove hash if extra has it
		
		if(isChannelOwner(extra, user)){
			bot.joinChannel("#" + extra);
			bot.sendMessage(channel.getName(), "I have joined " + extra);
			bot.sendMessage("#" + extra, user + " invited bot to the channel");
		}else{
			bot.sendMessage(channel.getName(), "You can only ask bot to join a channel that you own.");
		}
	}

}
