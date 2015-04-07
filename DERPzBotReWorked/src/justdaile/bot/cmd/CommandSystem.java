package justdaile.bot.cmd;

import java.util.ArrayList;

import org.jibble.pircbot.PircBot;

public class CommandSystem {

	public static ArrayList<Command> cmds = new ArrayList<Command>();
	private static PircBot bot;
	
	public CommandSystem(PircBot bot) {
		CommandSystem.bot = bot;

		// add commands
		CommandSystem.cmds.add(new ListCommand());
		CommandSystem.cmds.add(new WelcomeCommand());
		CommandSystem.cmds.add(new JoinCommand());
		CommandSystem.cmds.add(new LeaveCommand());
		CommandSystem.cmds.add(new TopDogCommand());
		CommandSystem.cmds.add(new ChatterBoxCommand());
		CommandSystem.cmds.add(new AchievementCommand());
		CommandSystem.cmds.add(new GameCommand());
		CommandSystem.cmds.add(new GameListCommand());
		CommandSystem.cmds.add(new TwitterCommand());
		CommandSystem.cmds.add(new FacebookCommand());
		CommandSystem.cmds.add(new SteamCommand());
		CommandSystem.cmds.add(new GamertagCommand());
		CommandSystem.cmds.add(new PsnCommand());
	}

	public static boolean processCommand(String command, String channel, String user) {
		int a = command.indexOf("!");
		int b = 0;
		if(command.contains(" ")){
			b = command.indexOf(" ");
		}else{
			b = command.length();
		}
		
		String cmd = command.substring(a + 1, b);
		for(int i = 0; i < CommandSystem.cmds.size(); i++){
			Command currentCmd = CommandSystem.cmds.get(i);
			if(currentCmd.getId().contentEquals(cmd)){
				if(b == command.length()){
					currentCmd.prepareCommand(null, bot, channel, user);
				}else{
					currentCmd.prepareCommand(command.substring(b + 1, command.length()), bot, channel, user);
				}
				return true;
			}
		}
		return false;
	}
	
}
