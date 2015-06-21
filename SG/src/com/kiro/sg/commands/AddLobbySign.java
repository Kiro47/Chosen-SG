package com.kiro.sg.commands;

import java.util.Set;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;

import com.kiro.sg.Arena;
import com.kiro.sg.ArenaManager;
import com.kiro.sg.LobbySign;
import com.kiro.sg.SettingsManager;

@CommandInfo (description = "Add a lobby status sign." , usage = "<arenaName>" , aliases = {"addlobbysign", "addsign", "als" }, op = true)
public class AddLobbySign extends GameCommand{

	@Override
	public void onCommand(Player p, String[] args) {
		
		
		if (args.length == 0) {
			p.sendMessage(ChatColor.RED + "You must specify an arena to which you want to bind this sign!");
			return;
		}
		
		Arena a = ArenaManager.getInstance().getArena(args[0]);
		
		if (a == null) {
			p.sendMessage(ChatColor.RED + "An arena with that naem doesn't exist!");
			return;
		}
		
		Block target = p.getTargetBlock((Set<Material>) null,10);
		
		if (target == null) {
			p.sendMessage(ChatColor.RED + "You are not looking at a block1");
			return;
		}
		
		if (!(target.getState() instanceof Sign)) {
			p.sendMessage(ChatColor.RED + "You are not looking at a sign!");
			return;
		}
		
		new LobbySign(target.getLocation(), a).save(SettingsManager.getSigns().createSection
				(String.valueOf(SettingsManager.getSigns().getKeys().size())));
		
		// New sign isn't in SignManagers
		
		p.sendMessage(ChatColor.GREEN + " New Sign Added at: " + target.getLocation().getBlockX() + ", " + target.getLocation().getBlockY() + ", " +
		target.getLocation().getBlockZ() + " in world: " + target.getLocation().getWorld().getName());
	}
}
