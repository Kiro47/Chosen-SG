package com.kiro.sg.commands;

import java.util.Set;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import com.kiro.sg.Arena;
import com.kiro.sg.ArenaManager;
import com.kiro.sg.GameChest;
import com.kiro.sg.SettingsManager;


@CommandInfo (description = "Add a chest to an arena.", usage = "<arenaName> <tier>", aliases = { "addchest"}, op = true)
public class AddChest extends GameCommand{

	@Override
	public void onCommand(Player p, String[] args) {
		
		
		if (args.length < 2) {
			p.sendMessage(ChatColor.RED + "you must specify the arena to which the chest will be added and the tier of it!");
			return;
		}
		
		Arena a = ArenaManager.getInstance().getArena(args[0]);
		
		if (a == null) {
			p.sendMessage(ChatColor.RED + "That arena does not exist!");
			return;
		}
		
		int tier;
		
		try {
			tier = Integer.parseInt(args[1]);
		}
		
		catch (Exception e){
			p.sendMessage(ChatColor.RED + "That is not a number!");
			return;
		}
		
		if (tier !=1 && tier !=2 && tier !=3) {
			p.sendMessage(ChatColor.RED + " Invalid Tier, Tier Values = [1-3]");
			return;
		}
		
		Block target = p.getTargetBlock((Set<Material>)null, 10);
		
		if (target == null) {
			p.sendMessage(ChatColor.RED + "You are not looking at a block!");
			return;
		}
		
		
		// implements crate method
		if (!(target.getState() instanceof Chest)) {
			p.sendMessage(ChatColor.RED + "You are not looking at a Chest/Crate!");
			return;
		}
		
		Chest chest = (Chest) target.getState();
		
		if (!a.getBounds().contains(chest.getLocation())) {
			p.sendMessage(ChatColor.RED + "That Chest/Crate is outside of the arena!");
			return;
		}
		
		a.addChest(chest, tier);
		
		
		if (!SettingsManager.getArenas().contains(a.getID() + ".chests")) {
			SettingsManager.getArenas().createSection(a.getID() + ".chests");
		}
		
		new GameChest(chest, tier).save(SettingsManager.getArenas().createSection(a.getID() + ".chests." + 
				SettingsManager.getArenas().<ConfigurationSection>get(a.getID() + ".chests").getKeys(false).size()));
		
		SettingsManager.getArenas().save();
		
		p.sendMessage(ChatColor.GREEN + "Tier: " + tier + " chest added to " + a.getID() + " at " + chest.getLocation().getBlockX() + ", "
				+ chest.getLocation().getBlockY() + ", " + chest.getLocation().getBlockZ() +" in world: " + chest.getLocation().getWorld().getName() );
	}
}
