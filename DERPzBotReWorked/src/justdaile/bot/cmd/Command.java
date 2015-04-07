package justdaile.bot.cmd;

import org.jibble.pircbot.PircBot;

import justdaile.bot.TwitchBotV2;
import justdaile.bot.chan.Channel;
import justdaile.bot.chan.ChannelSystem;

public abstract class Command {
	
	private String id;
	private String description;
	private String[] expectations;
	
	public Command(String id, String description){
		this.id = id;
		this.description = description;
	}
	
	public boolean isChannelOwner(Channel channel, String user){
		return this.isChannelOwner(channel.getName(), user);
	}
	
	public boolean isChannelOwner(String channel, String user){
		return channel.contentEquals(user.toLowerCase()) || user.contentEquals(TwitchBotV2.DEVELOPER.toLowerCase());
	}
	
	public void expect(String...expectations){
		this.expectations = expectations;
	}
	
	public int totalExpectations() {
		return this.expectations.length;
	}
	
	public String getExpectation(int index){
		return this.expectations[index];
	}
	
	public boolean hasExpectations(){
		return expectations != null;
	}
	
	public String getId(){
		return this.id;
	}
	
	public String getDescription() {
		return this.description;
	}
	
	/**
	 * @param extra
	 *             an assignment or extension to the called command
	 * @param channel
	 *             channel name command was called in
	 * @param bot 
	 *             
	 * @param user
	 *             users name who called command
	 */
	public void prepareCommand(String extra, PircBot bot, String channel, String user){
		String out = "";
		if(this.hasExpectations() && extra == null){
			out += this.id + " command expects parameter(s) " ;
			for(int i = 0; i < this.expectations.length; i++){
				out += this.expectations[i] + " ";
			}
			bot.sendMessage(channel, out);
		}else{
			doCommand(extra, bot, ChannelSystem.getChannel(channel), user);
		}
		ChannelSystem.getChannel(channel).saveChannel();
	}
	
	/**
	 * @param extra
	 *             an assignment or extension to the called command
	 * @param channel
	 *             channel command was called in
	 * @param bot 
	 *             
	 * @param user
	 *             user who called command
	 */
	public abstract void doCommand(String extra, PircBot bot, Channel channel, String user);
	
}
