package com.kiro.sg.sponsor;

import com.kiro.sg.sponsor.checks.ArmorCheck;
import com.kiro.sg.sponsor.checks.DefaultCheck;
import com.kiro.sg.sponsor.checks.HungerCheck;
import com.kiro.sg.sponsor.checks.WeaponCheck;
import com.kiro.sg.utils.chat.ChatUtils;
import com.kiro.sg.utils.chat.Msg;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;

import java.util.ArrayList;
import java.util.List;

public final class SmartSponsor
{

	private static final String BORDER_FILL = ChatUtils.fill("-");
	private static final String SMART_SPONSOR = ChatUtils.center("<> Smart Sponsor <>");

	private static final List<SponsorCheck> checks = new ArrayList<>();
	private static final int[] indecies;

	static
	{
		int weightSum = 0;
		checks.add(new DefaultCheck());
		checks.add(new ArmorCheck());
		checks.add(new WeaponCheck());
		checks.add(new HungerCheck());

		indecies = new int[weightSum];
		int cur = 0;
		for (int i = 0; i < checks.size(); i++)
		{
			int weight = checks.get(i).weight();
			for (int j = 0; j < weight; j++)
			{
				indecies[cur++] = i;
			}
		}
	}

	private SmartSponsor()
	{
	}

	@EventHandler
	public static void sponsor(Player player)
	{
		Msg.msgPlayer(player, ChatColor.GOLD + BORDER_FILL);
		Msg.msgPlayer(player, ChatColor.GOLD + SMART_SPONSOR);

		int last = 0;
		int count = 0;

		SponsorCheck check;
		do
		{
			if (count++ == 6)
			{
				check = checks.get(0);
				continue;
			}
			int index = indecies[(int) (Math.random() * indecies.length)];
			if (index != last)
			{
				check = checks.get(index);
				continue;
			}
			check = null;
		}
		while (check == null && !check.checkAndExecute(player));


		Msg.msgPlayer(player, ChatColor.GOLD + BORDER_FILL);
	}


}
