package justdaile.bot;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.Timer;

import justdaile.achievements.*;
import justdaile.bot.chan.Channel;
import justdaile.bot.chan.ChannelSystem;
import justdaile.bot.chan.User;
import justdaile.bot.cmd.CommandSystem;
import justdaile.ini.INIFileParser;
import justdaile.ini.Key;

import org.jibble.pircbot.PircBot;

public class TwitchBotV2 extends PircBot {
	
	public static void main(String[] args){
		new TwitchBotV2(); // launch application
		new JOptionPane();
		JOptionPane.showMessageDialog(new JFrame(), "click to end", "bot", JOptionPane.OK_OPTION);
		System.exit(0);
	}
	
	public static final String DEVELOPER = "themightyderp91";
	public static String NAME;
	public static String HOST;
	public static String CHANNEL;
	public static String PASSWORD;
	public static String VERSION;
	public static int PORT;
		
	public TwitchBotV2(){
		// parse config.ini to get configuration
		INIFileParser config = new INIFileParser(new File("res/config.ini"));
		Key key = config.getKeyOrDie("config");
		TwitchBotV2.NAME = key.getSettingOrDie("name").value;
		TwitchBotV2.VERSION = key.getSettingOrDie("version").value;
		TwitchBotV2.HOST = key.getSettingOrDie("host").value;
		TwitchBotV2.PORT = Integer.parseInt(key.getSettingOrDie("port").value);
		TwitchBotV2.PASSWORD = key.getSettingOrDie("password").value;
		TwitchBotV2.CHANNEL = key.getSettingOrDie("channel").value;
		this.setVerbose(Boolean.parseBoolean(key.getSettingOrDie("verbose").value));
		this.setName(NAME);
		this.setVersion(VERSION);	
		this.setMessageDelay(60000 / 40); // 40 message per min maximum
		
		try {
			connect(TwitchBotV2.HOST, TwitchBotV2.PORT, TwitchBotV2.PASSWORD);
			if(!isConnected()){
				System.err.println("Connection Failed");
				System.exit(0); // exit if unable to connect
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.joinChannel(TwitchBotV2.CHANNEL); // join home channel by default
		
		new ChannelSystem(this);
		new AchievementSystem(this);
		new CommandSystem(this);
		
		// add achievements
		AchievementSystem.addAchievement(new NewbieAchievement());
		AchievementSystem.addAchievement(new JustLikeTVAchievement());
		AchievementSystem.addAchievement(new ChatMuchAchievement());
		AchievementSystem.addAchievement(new BigMouthAchievement());
		AchievementSystem.addAchievement(new DonaterAchievement());
		AchievementSystem.addAchievement(new LongTimeViewersClubAchievement());
		AchievementSystem.addAchievement(new DonaterAchievement());
		AchievementSystem.addAchievement(new BackForMoreAchievement());
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				new Timer(30000, new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						try{
							AchievementSystem.update();	
						}catch(Exception a){
							System.err.println("unexpected error, on achievements");
						}
					}
				}).start();
			}
		}).start();
	}
	
	@Override
	protected void onMessage(String channel, String sender, String login, String hostname, String message) {
		System.out.println(login + " said : " + message + " in " + channel);
		if(login.contentEquals("moobot")){return;} // ignore moobot & channel owner
		if(message.startsWith("!")){
			if(!CommandSystem.processCommand(message, channel, login)){
				sendMessage(channel, "unknown command " + message);
			}
		}else{
			Channel c = ChannelSystem.getChannel(channel);
			User u = c.getUser(login);
			u.addComment();
			c.saveChannel(); // save when anyone messages
			if(!u.welcomed()){
				u.welcome();
				this.sendMessage(channel, c.getWelcomeMessage(u.getLogin()));
			}
		}
		ChannelSystem.updateChannels();
	}
	
	@Override
	protected void onPart(String channel, String sender, String login, String hostname){
		System.out.println(login + " parted " + channel + " channel");
		if(login.contentEquals("moobot")){return;} // ignore moobot
		if(login.contentEquals(TwitchBotV2.CHANNEL)){
			ChannelSystem.closeChannel(channel);
		}else{
			ChannelSystem.updateChannels();
		}
	}

	@Override
	protected void onJoin(String channel, String sender, String login, String hostname) {
		System.out.println(login + " joined " + channel + " channel");
		ChannelSystem.updateChannels();
	}
	
	@Override	
	protected void onDisconnect() {
		super.onDisconnect();
		System.err.println("disconnected");
		try{
			System.err.println("attempting reconnection!");
			this.reconnect();
		}catch(Exception e){
			System.err.println("shutting down!");
			System.exit(0);
		}
	}
	
	@Override
	protected void onConnect() {
		super.onConnect();
		System.out.println("connected");
	}

}
