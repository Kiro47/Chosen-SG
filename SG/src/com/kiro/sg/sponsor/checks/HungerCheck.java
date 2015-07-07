package com.kiro.sg.sponsor.checks;

import com.kiro.sg.sponsor.SponsorCheck;
import com.kiro.sg.utils.chat.Msg;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class HungerCheck implements SponsorCheck
{
	@Override
	public int weight()
	{
		return 5;
	}

	@Override
	public boolean checkAndExecute(Player player)
	{
		if (player.getFoodLevel() < 10)
		{
			PlayerInventory inventory = player.getInventory();
			ItemStack[] contents = inventory.getContents();
			int fCount = 0;
			for (ItemStack stack : contents)
			{
				if (stack != null)
				{
					if (stack.getType().isEdible())
					{
						fCount++;
					}
				}
			}

			if (fCount < 4)
			{
				inventory.addItem(new ItemStack(Material.GRILLED_PORK, 5));
				Msg.msgPlayer(player, ChatColor.DARK_GREEN + "you have been given " + ChatColor.GREEN + "5 Grilled Porkchop");
				return true;
			}

		}
		return false;
	}
}
