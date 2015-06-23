package com.kiro.sg.crates;


import com.kiro.sg.Config;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class CrateUtils
{


	private static int[] indexes;
	private static ItemStack[][] stacks;

	public static void loadLoots()
	{

		File file = Config.LootConfigFile;

		YamlConfiguration config = new YamlConfiguration();
		try
		{
			config.load(file);

			int count = config.getInt("loot.count");
			stacks = new ItemStack[count][];

			int total = 0;
			int[] weights = new int[count];
			for (int i = 0; i < count; i++)
			{
				int weight = config.getInt("loot.v" + i + ".weight");
				weights[i] = weight;
				total += weight;
			}
			indexes = new int[total];
			int ind = 0;
			for (int i = 0; i < count; i++)
			{
				List<String> list = config.getStringList("loot.v" + i + ".loots");
				stacks[i] = new ItemStack[list.size()];
				for (int j = 0; j < weights[i]; j++)
				{
					indexes[ind++] = i;
				}
				int index2 = 0;
				for (String s : list)
				{
					String[] args = s.split(",");
					Material material = Material.valueOf(args[0]);
					short damage = Short.valueOf(args[1]);
					int amount = Integer.valueOf(args[2]);

					ItemStack stack = new ItemStack(material, amount, damage);
					if (args.length > 3)
					{
						for (int j = 3; j < args.length; j++)
						{
							String factor = args[j];
							if (factor.startsWith("n:"))
							{
								factor = factor.substring(factor.indexOf(':') + 1);
								ItemMeta meta = stack.getItemMeta();
								meta.setDisplayName(factor);
								stack.setItemMeta(meta);
							}
							else if (factor.startsWith("e:"))
							{
								factor = factor.substring(factor.indexOf(':') + 1);
								String[] ags = factor.split(";");
								ItemMeta meta = stack.getItemMeta();
								Enchantment enchant = Enchantment.getByName(ags[0]);
								meta.addEnchant(enchant, Integer.valueOf(ags[1]), true);
								stack.setItemMeta(meta);
							}
						}
					}
					stacks[i][index2++] = stack;
				}

			}

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

	}

	public static ItemStack getChestItem()
	{
		int i = (int) (Math.random() * indexes.length);
		int index = indexes[i];
		int i2 = (int) (Math.random() * stacks[index].length);

		return stacks[index][i2];

	}

	public static ItemStack getWeightedItem(int mod)
	{
		if (mod > stacks.length)
		{
			mod = stacks.length - 1;
		}

		int index;
		if (mod != stacks.length)
		{
			do
			{
				int i = (int) (Math.random() * indexes.length);
				index = indexes[i] + mod;
			}
			while (index >= stacks.length);
		}
		else
		{
			index = stacks.length;
		}
		int i2 = (int) (Math.random() * stacks[index].length);

		return stacks[index][i2];
	}

	public static ItemStack[] createContents(int size)
	{
		ItemStack[] contents = new ItemStack[size];
		int i = 0;

		List<Material> used = new ArrayList<>();

		do
		{
			try
			{
				ItemStack stack = getChestItem();
				if (!used.contains(stack.getType()))
				{
					contents[(int) (Math.random() * size)] = stack;
					used.add(stack.getType());
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		while (++i < 5);

		return contents;

	}
}
