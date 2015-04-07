package justdaile.bot.cmd;

import justdaile.bot.chan.Channel;

import org.jibble.pircbot.PircBot;

public class GameCommand extends Command {

	public GameCommand() {
		super("game", "add a game to the game list");
		expect("<game>", "<&>", "<platform>");
	}

	@Override
	public void doCommand(String extra, PircBot bot, Channel channel, String user) {
		if(isChannelOwner(channel, user)){
			channel.getGameManager().addGame(new String(extra.trim()));
			bot.sendMessage(channel.getName(), "added game");
		}
	}

}
