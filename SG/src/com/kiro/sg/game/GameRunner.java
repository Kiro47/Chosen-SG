package com.kiro.sg.game;

import com.kiro.sg.Config;
import com.kiro.sg.SGMain;
import com.kiro.sg.utils.Chat;
import com.kiro.sg.utils.Msg;
import org.bukkit.ChatColor;
import org.bukkit.WorldBorder;
import org.bukkit.scheduler.BukkitRunnable;

public class GameRunner extends BukkitRunnable
{

	private final GameInstance gameInstance;
	private int timer;
	private int chestRefillTimer;

	public GameRunner(GameInstance instance)
	{
		gameInstance = instance;
		timer = 1;

		runTaskTimer(SGMain.getPlugin(), 20, 20);
	}

	public void setTimer(int time)
	{
		timer = time;
	}

	public void stop()
	{
		cancel();
	}

	@Override
	public void run()
	{
		timer--;
		gameInstance.getScoreboard().updateTimer(timer);

		if (chestRefillTimer++ >= Config.TIMER_CHEST_REFILL)
		{
			Msg.msgGame(Chat.center(ChatColor.RED + "The chests have been refilled!"), gameInstance, false);

			gameInstance.getCrates().refillCrates();
			chestRefillTimer = 0;
		}
		if (gameInstance.getState() == GameState.INIT)
		{
			if (timer == 0)
			{
				timer = Config.TIMER_STARTING_COUNTDOWN;
				gameInstance.start();
			}
		}
		else if (gameInstance.getState() == GameState.STARTING)
		{
			if (timer == 0)
			{
				// TODO: Add extra countdown timer thing (Maybe a boss bar?)
				timer = Config.TIMER_GAME_MAX_TIME;
				gameInstance.startMatch();
			}
			else
			{
				if (timer == 2)
				{

					WorldBorder border = gameInstance.getArena().getWorld().getWorldBorder();
					border.setCenter(gameInstance.getArena().getCenterPoint());
					border.setSize(2);
					border.setDamageAmount(0.0);
					border.setSize(500, 2);
					border.setDamageBuffer(0);
				}
				// 20s, 10s, 5s-
				if (timer == 20 || timer == 10 || timer <= 5)
				{
					Msg.msgGame(Chat.format("&c" + timer + " seconds till start!"), gameInstance, false);
				}
			}
		}
		else if (gameInstance.getState() == GameState.PLAYING)
		{
			if (timer == 0)
			{
				// TODO: Decrease time based on how many users died.
				timer = Config.TIMER_DEATHMATCH_MAX;
				gameInstance.deathmatch();
			}
			else
			{
				// 15m, 10m, 5m, 1m, 30s, 10s, 5s-
				if (timer == 60)
				{
					Msg.msgGame(Chat.format("&c1 minute remaining!"), gameInstance, false);
				}
				else if (timer == 300 || timer == 600 || timer == 900)
				{
					Msg.msgGame(Chat.format("&c" + timer / 60 + " minutes remaining!"), gameInstance, false);
				}
				else if (timer == 30 || timer == 10 || timer <= 5)
				{
					Msg.msgGame(Chat.format("&c" + timer + " seconds remaining!"), gameInstance, false);
				}
			}
		}
		else if (gameInstance.getState() == GameState.DEATHMATCH)
		{
			if (timer == 0)
			{
				gameInstance.end();
			}
		}
		else
		{
			// 15m, 10m, 5m, 1m, 30s, 10s, 5s-
			if (timer == 60)
			{
				Msg.msgGame(Chat.format("&c1 minute remaining!"), gameInstance, false);
			}
			else if (timer == 300 || timer == 240 || timer == 180 || timer == 120)
			{
				Msg.msgGame(Chat.format("&c" + timer / 60 + " minutes remaining!"), gameInstance, false);
			}
			else if (timer == 30 || timer == 10 || timer <= 5)
			{
				Msg.msgGame(Chat.format("&c" + timer + " seconds remaining!"), gameInstance, false);
			}
		}

	}
}
