package com.kiro.sg.listeners.items;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Slowball implements Listener {
	
	public static ItemStack slowball() {
		
		ItemStack SlowballItemStack = new ItemStack(Material.SNOW_BALL);
		
		ItemMeta sm = SlowballItemStack.getItemMeta();
		
		sm.setDisplayName(ChatColor.AQUA + "" + ChatColor.ITALIC + "Slowball");
		
		List<String> lore = new ArrayList<> ();
		lore.add(ChatColor.GOLD + "SLOW YOUR FOES!");
		lore.add("");
		lore.add(ChatColor.RED + "Inflicts Slowness 3 on the");
		lore.add(ChatColor.RED + "entity you hit for 6 seconds!");
		sm.setLore(lore);
		
		SlowballItemStack.setItemMeta(sm);

		SlowballItemStack.addEnchantment(Enchantment.THORNS, 1);
		
		return SlowballItemStack  ;
		
	}
	

}
