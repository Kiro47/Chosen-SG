package com.kiro.sg.game;

import com.kiro.sg.SGMain;
import com.kiro.sg.config.Config;
import com.kiro.sg.game.arena.SGArena;
import com.kiro.sg.utils.chat.ChatUtils;
import com.kiro.sg.utils.chat.Msg;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.WorldBorder;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class GameRunner extends BukkitRunnable
{

	private final GameInstance gameInstance;
	private int timer;
	private int chestRefillTimer;
	private int dayNightDriver;

	public GameRunner(GameInstance instance)
	{
		gameInstance = instance;
		timer = 1;
		dayNightDriver = 0;

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
		if (!gameInstance.foreverNight)
		{
			dayNightDriver += 80;
			if (dayNightDriver >= 24000)
			{
				dayNightDriver = 0;
			}
			gameInstance.getArena().getWorld().setTime(dayNightDriver);
		}
		else
		{
			gameInstance.getArena().getWorld().setTime(17000);
		}

		timer--;
		gameInstance.getScoreboard().updateTimer(gameInstance.getState(), timer);

		if (chestRefillTimer++ >= Config.TIMER_CHEST_REFILL)
		{
			Msg.msgGame(ChatUtils.center(ChatColor.RED + "The chests have been refilled!"), gameInstance, false);

			gameInstance.getCrates().refillCrates();
			chestRefillTimer = 0;
		}
		if (gameInstance.getState() == GameState.INIT)
		{
			if (timer <= 0)
			{
				timer = Config.TIMER_STARTING_COUNTDOWN;
				gameInstance.start();
			}
		}
		else if (gameInstance.getState() == GameState.STARTING)
		{
			if (timer <= 0)
			{
				// TODO: Add extra countdown timer thing (Maybe a boss bar?)
				timer = Config.TIMER_GAME_MAX_TIME;
				gameInstance.startMatch();

				WorldBorder border = gameInstance.getArena().getWorld().getWorldBorder();
				border.setCenter(gameInstance.getArena().getCenterPoint());
				border.setSize(2);
				border.setDamageAmount(0.0);
				border.setSize(800, 2);
				border.setDamageBuffer(0);
			}
			else
			{

				// 20s, 10s, 5s-
				if (timer == 20 || timer == 10 || timer <= 5)
				{
					Msg.msgGame(ChatUtils.format("&c" + timer + " seconds till start!"), gameInstance, false);
					SGArena arena = gameInstance.getArena();
					World world = arena.getWorld();
					for (Player player : world.getPlayers())
					{
						player.playSound(player.getLocation(), Sound.NOTE_PLING, 1f, 1f);
					}
				}
			}
		}
		else if (gameInstance.getState() == GameState.PLAYING)
		{
			if (timer <= 0)
			{
				timer = Config.TIMER_DEATHMATCH_MAX;
				gameInstance.deathmatch();
			}
			else
			{
				if (timer == Config.TIMER_GAME_MAX_TIME - 1)
				{
					for (Player player : gameInstance.getRemaining())
					{
						player.setHealth(20.0);
					}
				}
				// 15m, 10m, 5m, 1m, 30s, 10s, 5s-
				if (timer == 60)
				{
					Msg.msgGame(ChatUtils.format("&c1 minute remaining!"), gameInstance, false);
				}
				else if (timer == 300 || timer == 600 || timer == 900)
				{
					Msg.msgGame(ChatUtils.format("&c" + timer / 60 + " minutes remaining!"), gameInstance, false);
				}
				else if (timer == 30 || timer == 10 || timer <= 5)
				{
					Msg.msgGame(ChatUtils.format("&c" + timer + " seconds remaining!"), gameInstance, false);
				}
			}
		}
		else if (gameInstance.getState() == GameState.DEATHMATCH)
		{
			if (timer <= 0)
			{
				gameInstance.ending();
				timer = 5;
			}
			else
			{
				// 15m, 10m, 5m, 1m, 30s, 10s, 5s-
				if (timer == 60)
				{
					Msg.msgGame(ChatUtils.format("&c1 minute remaining!"), gameInstance, false);
				}
				else if (timer == 300 || timer == 240 || timer == 180 || timer == 120)
				{
					Msg.msgGame(ChatUtils.format("&c" + timer / 60 + " minutes remaining!"), gameInstance, false);
				}
				else if (timer == 30 || timer == 10 || timer <= 5)
				{
					Msg.msgGame(ChatUtils.format("&c" + timer + " seconds remaining!"), gameInstance, false);
				}
			}
		}
		else if (gameInstance.getState() == GameState.ENDING)
		{
			if (timer <= 0)
			{
				gameInstance.end();
			}
		}

	}
}
