package com.kiro.sg.utils.task;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;

import com.kiro.sg.SGMain;
import com.kiro.sg.config.SettingsManager;

public class BroadcasterTask {
	
	final private static double repeat = SettingsManager.getBroadcastConfig().get("OcccurenceOfMessages");
	
	static int ticks = (int) (repeat * 60 * 20);

	public static void broadcastTask() {
		
		
		Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(SGMain.getPlugin(), new Runnable() {
			
			public void run() {
				String msg = null;
				
	//		for
				
				
					Bukkit.getServer().broadcastMessage(msg);
			}
		}, 20, ticks);
	}
	
	
	private static List<String> msglist = Arrays.asList("Message 1", "Message 2", "Message 3", "Message 4", "Message 5");
	
	public static void broadcastConfigBuild() {
		SettingsManager.getBroadcastConfig().createSection("OcccurenceOfMessages").addDefault("OccurenceOfMessages.time", 3600);
		SettingsManager.getBroadcastConfig().createSection("Messages").addDefault("Messages", msglist);
	}
}
