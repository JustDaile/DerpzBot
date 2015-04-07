package justdaile.bot.chan;

import java.util.ArrayList;

import justdaile.bot.TwitchBotV2;

import org.jibble.pircbot.PircBot;

public class ChannelSystem {

	private static PircBot bot;
	private static ArrayList<Channel> channels = new ArrayList<Channel>();
	
	public ChannelSystem(PircBot bot) {
		ChannelSystem.bot = bot;
	}
	
	public static int totalChannels(){
		return ChannelSystem.channels.size();
	}
	
	public static boolean hasChannel(String name){
		return ChannelSystem.getChannel(name) != null;
	}
	
	public static Channel getChannel(int i){
		return ChannelSystem.channels.get(i);
	}
	
	public static Channel getChannel(String name){
		for(int i = 0; i < ChannelSystem.totalChannels(); i++){
			if(ChannelSystem.getChannel(i).getName().contentEquals(name)){
				return ChannelSystem.getChannel(i);
			}
		}
		return null;
	}
	
	public static void addChannel(Channel channel){
		ChannelSystem.addChannel(channel.getName());
	}

	public static void addChannel(String name){
		ChannelSystem.channels.add(new Channel(name));
		ChannelSystem.getChannel(name).loadChannel();
	}
	
	public static void updateChannel(String channel) {
		ChannelSystem.getChannel(channel).checkUsers(ChannelSystem.bot);
	}
	
	public static void updateChannels(){
		for(int i = 0; i < ChannelSystem.bot.getChannels().length; i++){
			String channel = ChannelSystem.bot.getChannels()[i];
			if(!ChannelSystem.hasChannel(channel)){
				ChannelSystem.addChannel(channel);
			}
			if(!ChannelSystem.botInChannel(channel)){
				ChannelSystem.closeChannel(channel);
			}
		}
		for(int i = 0; i < channels.size(); i++){
			ChannelSystem.getChannel(i).checkUsers(ChannelSystem.bot);
		}
	}

	public static void closeChannel(String channel) {
		int remove = -1;
		for(int i = 0; i < ChannelSystem.totalChannels(); i++){
			Channel c = ChannelSystem.getChannel(i);
			if(c.getName().contentEquals(channel)){
				c.saveChannel();
				remove = i;
			}
		}
		if(remove != -1){
			ChannelSystem.bot.partChannel(ChannelSystem.getChannel(remove).getName());
			ChannelSystem.channels.remove(remove);
			System.out.println("closed channel " + remove);
		}
	}
	
	private static boolean botInChannel(String channel){
		for(int i = 0; i < ChannelSystem.bot.getChannels().length; i++){
			org.jibble.pircbot.User users[] = ChannelSystem.bot.getUsers(ChannelSystem.bot.getChannels()[i]);
			for(int x = 0; x < users.length; x++){
				if(users[i].getNick().contentEquals(TwitchBotV2.NAME.toLowerCase())){
					return true;
				}
			}
		}
		return false;
	}

}
