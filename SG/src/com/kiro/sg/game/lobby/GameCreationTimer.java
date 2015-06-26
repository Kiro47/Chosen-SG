package com.kiro.sg.game.lobby;

import com.kiro.sg.SGMain;
import com.kiro.sg.config.Config;
import com.kiro.sg.scoreboard.LobbyScoreboard;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * Starts a new game every 2 minutes
 * If the queue has 20+ players waiting, it will drop the time to 30 seconds.
 * If the queue doesn't have more than the min count, timer will freeze.
 */
public class GameCreationTimer extends BukkitRunnable
{

	private final LobbyManager lobbyManager;
	private int countdown;
	private final LobbyScoreboard scoreboard;

	public GameCreationTimer(LobbyManager lobbyManager)
	{
		this.lobbyManager = lobbyManager;
		scoreboard = new LobbyScoreboard();
		runTaskTimer(SGMain.getPlugin(), 20, 20);
	}

	public LobbyScoreboard getScoreboard()
	{
		return scoreboard;
	}

	public void setTimeRemaining(int time)
	{
		countdown = time;
	}

	public int getTimeRemaining()
	{
		return countdown;
	}

	@Override
	public void run()
	{
		scoreboard.updatePlayers(lobbyManager.getQueueSize());
		scoreboard.updateTimer(countdown);
		scoreboard.updateOnlinePlayers();

		if (countdown == 0)
		{
			if (lobbyManager.createGame())
			{
			}
			countdown = 120;
		}
		else if (countdown > 30 && lobbyManager.getQueueSize() > 20)
		{
			countdown = 30;
		}

		if (lobbyManager.getQueueSize() < Config.MIN_PLAYER_COUNT)
		{
			countdown = 120;
		}

		countdown--;
	}
}
