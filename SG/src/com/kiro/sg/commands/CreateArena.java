package com.kiro.sg.commands;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.kiro.sg.ArenaManager;
import com.kiro.sg.SGMain;
import com.kiro.sg.SettingsManager;
import com.sk89q.worldedit.bukkit.selections.Selection;

@CommandInfo (description = "Create an Arena!" , usage = "<arenaName>", aliases = {"createarena", "ca"}, op = true)
public class CreateArena extends GameCommand{

	
	@Override
		public void onCommand(Player p, String[] args) {
		
		if (args.length == 0 ) {
			p.sendMessage(ChatColor.RED + "You must specify a name for the arena!");
			return;
		}
		
		String name = args[0];
		
		if (ArenaManager.getInstance().getArena(name) !=null) {
			p.sendMessage(ChatColor.RED + "Arena with that name already exists!");
			return;
		}
		
		Selection s = SGMain.getWorldEdit().getSelection(p);
		
		
		SettingsManager.getArenas().set(name + ".world", s.getWorld().getName());
		
		SGMain.saveLocation(s.getMinimumPoint(), SettingsManager.getArenas().createSection(name + ".cornerA"));
		SGMain.saveLocation(s.getMaximumPoint(), SettingsManager.getArenas().createSection(name + ".cornerB"));
		
		SettingsManager.getArenas().save();
		
		ArenaManager.getInstance().setup();
		
		p.sendMessage(ChatColor.GREEN + "Created arena " + name + ".");
	}
}
