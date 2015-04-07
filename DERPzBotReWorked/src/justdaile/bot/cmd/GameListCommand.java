package justdaile.bot.cmd;

import justdaile.bot.chan.Channel;
import justdaile.bot.chan.GameManager;
import justdaile.bot.chan.GameManager.Game;

import org.jibble.pircbot.PircBot;

public class GameListCommand extends Command {

	public GameListCommand() {
		super("gamelist", "list games the broadcaster plays");
	}

	@Override
	public void doCommand(String extra, PircBot bot, Channel channel, String user) {
		GameManager gm = channel.getGameManager();
		int tg = gm.totalGames();
		StringBuffer out = new StringBuffer();
		if(tg > 0){
			out.append("showing " + tg + " set games ");
			for(int i = 0; i < tg; i++){
				Game g = gm.getGame(i);
				out.append(g.getName() + " on " + g.getPlatform());
				if(i < tg - 1){
					out.append(" > ");
				}
			}
		}else{
			out.append("Broadcaster has not set any games.");
		}
		bot.sendMessage(channel.getName(), out.toString());
	}

}
