package com.kiro.sg.listeners;

import com.kiro.sg.PlayerStats;
import com.kiro.sg.game.GameInstance;
import com.kiro.sg.game.GameManager;
import com.kiro.sg.lobby.LobbyManager;
import com.kiro.sg.utils.chat.Msg;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class CommandListner implements Listener
{

	@EventHandler
	public void onCommand(PlayerCommandPreprocessEvent event)
	{
		String msg = event.getMessage().toLowerCase();
		if (msg.startsWith("/leave") || msg.startsWith("/quit"))
		{
			Player player = event.getPlayer();
			GameInstance instance = GameManager.getInstance(player);
			if (instance != null)
			{
				instance.removePlayer(player);
			}
			else
			{
				LobbyManager.getInstance().removeFromQueue(player);
			}
			event.setCancelled(true);
		}
		else if (msg.startsWith("/join"))
		{
			Player player = event.getPlayer();
			GameInstance instance = GameManager.getInstance(player);
			if (instance == null)
			{
				LobbyManager.getInstance().addToQueue(player);
			}
			event.setCancelled(true);
		}
		else if (msg.startsWith("/suicide"))
		{
			Player player = event.getPlayer();
			if (player.getGameMode() == GameMode.SURVIVAL)
			{
				GameInstance instance = GameManager.getInstance(player);
				if (instance != null)
				{
					Msg.msgPlayer(player, ChatColor.RED + "You have comitted Suicide!");
					instance.playerDeath(player);
				}
			}
			event.setCancelled(true);
		}
		else if (msg.startsWith("/bal"))
		{
			Player player = event.getPlayer();
			PlayerStats stats = PlayerStats.getStats(player);
			if (stats != null)
			{
				player.sendMessage(ChatColor.GREEN + "You have " + stats.getPoints() + " points!");
			}
			else
			{
				player.sendMessage(ChatColor.RED + "I don't know what happened, you don't seem to have stats.");
			}
		}
		else if (msg.startsWith("/stats"))
		{
			event.setMessage("/sg stats");
		}
	}

}
