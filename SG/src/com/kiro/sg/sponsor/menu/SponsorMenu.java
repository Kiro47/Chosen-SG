package com.kiro.sg.sponsor.menu;

import com.kiro.sg.PlayerStats;
import com.kiro.sg.game.GameInstance;
import com.kiro.sg.game.crates.CrateUtils;
import com.kiro.sg.sponsor.smart.SmartSponsor;
import com.kiro.sg.utils.chat.ChatUtils;
import com.kiro.sg.utils.misc.ItemUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;

public class SponsorMenu implements Listener
{

	public static final ItemStack SPONSOR_ITEM = new ItemStack(Material.COOKIE);

	private static final String PLAYER_NAME = ChatUtils.format("[\"\",{\"text\":\"        &6%d &7| &2&l%s\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/sg sp %s\"},\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"\",\"extra\":[{\"text\":\"Select &a%s\"}]}}}]");
	private static final String ITEM_DISPALY = ChatUtils.format("[\"\",{\"text\":\"    &6%d &7| %s%3d &7| &3%s\",\"bold\":\"true\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/sg sp %s %d\"},\"hoverEvent\":{\"action\":\"show_item\",\"value\":\"%s\"}}]");
	private static final String BORDER = ChatColor.GOLD + ChatUtils.fill("-");
	private static final String PLAYER_CHOICE_TITLE = ChatColor.GOLD + ChatUtils.center("    <> Select Player <>");

	private static final String YOU_SPONSORED = ChatUtils.format("&2You have sponsored &a%s &2by giving them a &a%s!");
	private static final String BEEN_SPONSORED = ChatUtils.format("&2You have been sponsored by &a%s &2and recieved a &a%s!");

	private static final String ITEM_CHOICE_TITLE = ChatColor.GOLD + ChatUtils.center("<> Select Item (%s" + ChatColor.GOLD + ") <>");

	private static final String TELLRAW = "tellraw %s %s";

	private static final String[] SponsorNames = {"Player Tracker", "Hilt", "Diamond Blade", "Iron Blade", "Care Package",
			                                             "5x Cooked Beef", "Extra Heart", "Random Item", "Smart Sponsor"};

	private static final String[] SponsorDescriptions = {"tracks the nearest player", "Handle of a sword", "To make a diamond blade",
			                                                    "To make an Iron", "Acts as a higher tiered chest", "So you can grow big and strong!",
			                                                    "Can't complain about more health", "Can be just about anything.", "Determines the best item to give."};
	private static final String[] SponsorItemDisplays;
	private static final ItemStack[] SponsorItems = {new ItemStack(Material.COMPASS, 1),
			                                                new ItemStack(Material.STICK, 1),
			                                                new ItemStack(Material.DIAMOND, 1),
			                                                new ItemStack(Material.IRON_INGOT, 1),
			                                                new ItemStack(Material.ENDER_CHEST, 1),
			                                                new ItemStack(Material.COOKED_BEEF, 5),
			                                                new ItemStack(Material.REDSTONE, 1),
			                                                new ItemStack(Material.DRAGON_EGG, 1),
			                                                new ItemStack(Material.DRAGON_EGG, 1)};

	private static final int[] SponsorPrice = {100, 50, 250, 100, 75, 25, 25, 25, 120};

	private static final ItemStack[] randomItems = {new ItemStack(Material.RED_ROSE), new ItemStack(Material.YELLOW_FLOWER), new ItemStack(Material.COAL),
			                                               new ItemStack(Material.WHEAT, 5), new ItemStack(Material.COOKIE, 10), new ItemStack(Material.ARROW, 10), new ItemStack(Material.CLAY_BALL),
			                                               new ItemStack(Material.BOAT), new ItemStack(Material.DEAD_BUSH, 1), new ItemStack(Material.DIAMOND_BOOTS), new ItemStack(Material.GOLD_SWORD),
			                                               new ItemStack(Material.DIAMOND_SPADE), new ItemStack(Material.WOOD_SPADE), new ItemStack(Material.TORCH, 5), new ItemStack(Material.SKULL_ITEM),
			                                               new ItemStack(Material.POTION, 1), new ItemStack(Material.POTION, 1), new ItemStack(Material.POTION, 1)};

	static
	{

		ItemUtils.nameItem(SPONSOR_ITEM, ChatUtils.format("&bSponsor a player!"));

		List<String> lore;

		ItemUtils.nameItem(SponsorItems[0], ChatUtils.format("&aPlayer Tracker &4(Activate)"));
		ItemUtils.nameItem(SponsorItems[1], ChatUtils.format("&3Hilt"));
		ItemUtils.nameItem(SponsorItems[2], ChatUtils.format("&bDiamond Blade"));
		ItemUtils.nameItem(SponsorItems[3], ChatUtils.format("&7Iron Blade"));
		ItemUtils.nameItem(SponsorItems[4], ChatUtils.format("&dCare Package"));
		ItemUtils.nameItem(SponsorItems[5], ChatUtils.format("&c5x Cooked Beef"));
		ItemUtils.nameItem(SponsorItems[6], ChatUtils.format("&cExtra Heart"));
		ItemUtils.nameItem(SponsorItems[7], ChatUtils.format("&dRandom Item"));
		ItemUtils.nameItem(SponsorItems[8], ChatUtils.format("&4&k.&6Smart Package&4&k."));

		for (int i = 0; i < SponsorItems.length; i++)
		{
			ItemStack stack = SponsorItems[i];
			ItemMeta meta = stack.getItemMeta();
			lore = meta.getLore();
			if (lore == null)
			{
				lore = new ArrayList<>();
			}

			lore.add(SponsorDescriptions[i]);

			lore.add(" ");
			lore.add(ChatColor.GRAY.toString() + SponsorPrice[i] + " points");
			lore.add(ChatColor.GOLD + "Click to purchase");
			meta.setLore(lore);
			stack.setItemMeta(meta);
		}

		SponsorItemDisplays = new String[SponsorItems.length];
		for (int i = 0; i < SponsorItems.length; i++)
		{
			SponsorItemDisplays[i] = ItemUtils.toJSON(SponsorItems[i]);
		}
	}


	public static void displayPlayerChoiceMenu(GameInstance game, Player sponsor)
	{
		sponsor.sendMessage(BORDER);
		sponsor.sendMessage(PLAYER_CHOICE_TITLE);

		int i = 0;
		for (Player player : game.getRemaining())
		{
			i++;
			String s = String.format(PLAYER_NAME, i, player.getDisplayName(), player.getName(), player.getDisplayName());
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), String.format(TELLRAW, sponsor.getName(), s));
			//player.sendRawMessage(s);
		}

		sponsor.sendMessage(BORDER);
	}

	public static void displayItemMenu(GameInstance game, Player sponsor, Player player)
	{
		double mul = 1;
		if (player.equals(sponsor))
		{
			mul = 1.5;
		}

		sponsor.sendMessage(ChatColor.DARK_GREEN + "You have selected to sponsor " + ChatColor.GREEN + player.getName());
		sponsor.sendMessage(BORDER);
		sponsor.sendMessage(String.format(ITEM_CHOICE_TITLE, player.getDisplayName()));

		PlayerStats stats = PlayerStats.getStats(sponsor);
		int bal = stats.getPoints();

		sponsor.sendMessage(ChatUtils.center(ChatColor.DARK_GREEN + "You have " + bal + " points"));
		sponsor.sendMessage(" ");

		ChatColor color;


		for (int i = 0; i < SponsorItems.length; i++)
		{
			int price = (int) (SponsorPrice[i] * mul);

			if (bal > price)
			{
				color = ChatColor.GREEN;
			}
			else
			{
				color = ChatColor.RED;
			}

			String s = String.format(ITEM_DISPALY, i + 1, color.toString(), price, SponsorNames[i], player.getName(), i, SponsorItemDisplays[i]);
			s = String.format(TELLRAW, sponsor.getName(), s).replace("  ", "    ");
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), s);
			System.out.println(s);
		}

		sponsor.sendMessage(BORDER);

	}

	public static void process(GameInstance game, Player sponsor, Player player, int item)
	{
		PlayerStats stats = PlayerStats.getStats(sponsor);

		int price = SponsorPrice[item];
		if (sponsor.equals(player))
		{
			price *= 1.5;
		}
		if (stats.getPoints() >= price)
		{
			stats.addPoints(-price);
			stats.update();
			String name;
			if (item == 7)
			{
				ItemStack stack;
				if ((int) (Math.random() * 3) == 1)
				{
					do
					{
						stack = CrateUtils.getChestItem();
					}
					while (stack == null);
				}
				else
				{
					stack = randomItems[(int) (Math.random() * randomItems.length)];
					if (stack.getType() == Material.POTION)
					{
						ItemMeta meta = stack.getItemMeta();
						PotionMeta potionMeta = (PotionMeta) meta;
						PotionEffectType type = PotionEffectType.values()[(int) (Math.random() * PotionEffectType.values().length)];
						potionMeta.setMainEffect(type);
						int eff = (int) (Math.random() * 2) + 1;
						int dur = (int) (Math.random() * 60) + 5;
						potionMeta.addCustomEffect(new PotionEffect(type, eff, dur), true);

						meta.setDisplayName(type.getName() + "(" + eff + ") [0:" + dur + ']');
						stack.setItemMeta(meta);
					}
				}
				ItemMeta meta = stack.getItemMeta();
				if (meta.getDisplayName() != null)
				{
					name = meta.getDisplayName();
				}
				else
				{
					name = stack.getType().name();
				}

				player.getInventory().addItem(stack);

			}
			else if (item == 8)
			{
				SmartSponsor.sponsor(player);
				name = "Smart Sponsor";
			}
			else
			{
				ItemStack stack = SponsorItems[item].clone();
				ItemMeta meta = stack.getItemMeta();
				meta.setLore(new ArrayList<String>());
				stack.setItemMeta(meta);

				player.getInventory().addItem(stack);
				name = SponsorNames[item];
			}

			if (player.equals(sponsor))
			{
				player.sendMessage(String.format(BEEN_SPONSORED, "Yourself", name));

			}
			else
			{
				sponsor.sendMessage(String.format(YOU_SPONSORED, player.getDisplayName(), name));
				player.sendMessage(String.format(BEEN_SPONSORED, sponsor.getDisplayName(), name));
			}
		}
		else
		{
			sponsor.sendMessage(ChatColor.RED + "Sorry, You can't afford this item.");
		}
	}

}
