package com.kiro.sg.listeners.lobby;

import com.kiro.sg.PlayerStats;
import com.kiro.sg.game.GameInstance;
import com.kiro.sg.game.GameManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

/**
 * Created by Brandon on 6/28/2015.
 */
public class LoginListener implements Listener
{

	@EventHandler
	public void onLogin(PlayerJoinEvent event)
	{
		Player player = event.getPlayer();
		PlayerStats.getStats(player);
		for (Player p : Bukkit.getOnlinePlayers())
		{
			if (!p.equals(player))
			{
				GameInstance game = GameManager.getInstance(p);
				if (game != null)
				{
					if (p.canSee(player))
					{
						p.hidePlayer(player);
					}
					if (player.canSee(p))
					{
						player.hidePlayer(p);
					}
				}
				else
				{
					if (!p.canSee(player))
					{
						p.showPlayer(player);
					}
					if (!player.canSee(p))
					{
						player.showPlayer(p);
					}
				}
			}
		}
	}

}
