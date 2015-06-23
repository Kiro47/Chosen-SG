package com.kiro.sg.commands;

import com.kiro.sg.arena.SGArena;
import com.kiro.sg.utils.Meta;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;

import java.util.Set;


@CommandInfo(description = "Add a chest to an arena.", usage = "<arenaName> <tier>", aliases = {"addchest"}, op = true)
public class AddChest extends GameCommand
{

	@Override
	public void onCommand(Player p, String[] args)
	{

		if (!Meta.has(p, "defineArena"))
		{
			p.sendMessage(ChatColor.RED + "You have to define an arena first! /createarena <world name> <arena name>");
			return;
		}

		SGArena arena = (SGArena) Meta.getMetadata(p, "defineArena");

		//		if (args.length < 2) {
		//			p.sendMessage(ChatColor.RED + "you must specify the arena to which the chest will be added and the tier of it!");
		//			return;
		//		}
		//
		//		Arena a = ArenaManager.getInstance().getArena(args[0]);
		//
		//		if (a == null) {
		//			p.sendMessage(ChatColor.RED + "That arena does not exist!");
		//			return;
		//		}
		//
		//		int tier;
		//
		//		try {
		//			tier = Integer.parseInt(args[1]);
		//		}
		//
		//		catch (Exception e){
		//			p.sendMessage(ChatColor.RED + "That is not a number!");
		//			return;
		//		}
		//
		//		if (tier !=1 && tier !=2 && tier !=3) {
		//			p.sendMessage(ChatColor.RED + " Invalid Tier, Tier Values = [1-3]");
		//			return;
		//		}

		Block target = p.getTargetBlock((Set<Material>) null, 10);

		if (target == null)
		{
			p.sendMessage(ChatColor.RED + "You are not looking at a block!");
			return;
		}


		// implements crate method
		if (!(target.getState() instanceof Chest || target.getState() instanceof Chest))
		{
			p.sendMessage(ChatColor.RED + "You are not looking at a Chest/Crate!");
			return;
		}

		//Chest chest = (Chest) target.getState();
		arena.addCornChest(target.getLocation());
		//
		//		if (!a.getBounds().contains(chest.getLocation()))
		//		{
		//			p.sendMessage(ChatColor.RED + "That Chest/Crate is outside of the arena!");
		//			return;
		//		}
		//
		//		a.addChest(chest, tier);
		//
		//
		//		if (!SettingsManager.getArenas().contains(a.getID() + ".chests"))
		//		{
		//			SettingsManager.getArenas().createSection(a.getID() + ".chests");
		//		}
		//
		//		new GameChest(chest, tier).save(SettingsManager.getArenas().createSection(a.getID() + ".chests." +
		//				                                                                          SettingsManager.getArenas().<ConfigurationSection>get(a.getID() + ".chests").getKeys(false).size()));
		//
		//		SettingsManager.getArenas().save();
		//
		p.sendMessage(ChatColor.GREEN + "Added a cornicopia chest! :: " + arena.getCornChests().size());
	}
}
