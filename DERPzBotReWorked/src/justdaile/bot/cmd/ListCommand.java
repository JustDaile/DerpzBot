package justdaile.bot.cmd;

import justdaile.bot.chan.Channel;

import org.jibble.pircbot.PircBot;

public class ListCommand extends Command {

	public ListCommand() {
		super("list", "show all commands");
	}

	@Override
	public void doCommand(String extra, PircBot bot, Channel channel, String user) {
		int cmds = CommandSystem.cmds.size();
		StringBuffer out = new StringBuffer("showing " + cmds + " commands ");
		for(int i = 0; i < cmds; i++){
			Command cmd = CommandSystem.cmds.get(i);
			out.append("!" + cmd.getId());
			if(cmd.hasExpectations()){
				for(int b = 0; b < cmd.totalExpectations(); b++){
					out.append(" " + cmd.getExpectation(b));
				}
			}
			out.append(" - " + cmd.getDescription());
			if(i != cmds - 1){
				out.append( " > ");
			}
		}
		bot.sendMessage(channel.getName(), out.toString());
	}

}
