package com.kiro.sg.sponsor;

import org.bukkit.entity.Player;

public interface SponsorCheck
{

	int weight();

	boolean checkAndExecute(Player player);


}
