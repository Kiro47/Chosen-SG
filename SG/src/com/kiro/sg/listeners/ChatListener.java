package com.kiro.sg.listeners;

import com.kiro.sg.game.GameInstance;
import com.kiro.sg.game.GameManager;
import com.kiro.sg.utils.chat.ChatUtils;
import com.kiro.sg.utils.chat.Msg;
import net.milkbowl.vault.chat.Chat;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatListener implements Listener
{

	private static final String LOBBY_FORMAT = ChatUtils.format("&7[%s&7] &f%s&7: &2%s");
	private static final String GAME_FORMAT = ChatUtils.format("&7[%s&7] &f%s&7: &2%s");
	private static final String SPECTATOR_FORMAT = ChatUtils.format("&c[SPECTATOR] &7[%s&7] &f%s&7: &2%s");

	private Chat chat;

	@EventHandler
	public void onChatMessage(AsyncPlayerChatEvent event)
	{
		event.setCancelled(true);
		if (checkChatInit() != null)
		{
			Player player = event.getPlayer();
			String prefix = ChatUtils.format(chat.getPlayerPrefix(player));
			String name = player.getDisplayName();
			String msg = event.getMessage();
			GameInstance game = GameManager.getInstance(player);
			if (game != null)
			{
				if (player.getGameMode() == GameMode.SURVIVAL)
				{
					String message = String.format(GAME_FORMAT, prefix, name, msg);
					Msg.msgGame(message, game, false);
				}
				else
				{
					String message = String.format(SPECTATOR_FORMAT, prefix, name, msg);
					Msg.msgGame(message, game, true);
				}
			}
			else
			{
				String message = String.format(LOBBY_FORMAT, prefix, name, msg);
				Msg.msgWorld(player.getWorld(), message);
			}

		}

	}

	private Chat checkChatInit()
	{
		if (chat == null)
		{
			chat = Bukkit.getServicesManager().getRegistration(Chat.class).getProvider();
		}
		return chat;
	}
}
