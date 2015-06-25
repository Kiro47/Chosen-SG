package com.kiro.sg.commands.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.kiro.sg.SettingsManager;


public class ReloadBroadcastConfig implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender s, Command cmd, String label,	String[] args) {
		
		if (!(s.hasPermission("sg.bcreload"))) {
			s.sendMessage(ChatColor.RED + "You don't have permission for this!");
			return true;
		}
		else {
		SettingsManager.getBroadcastConfig().reloadFile();
		s.sendMessage("broadcastConfig.yml reloaded!");
		return true;
		}
	}

	
	

}
