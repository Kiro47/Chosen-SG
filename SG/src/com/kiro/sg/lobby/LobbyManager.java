package com.kiro.sg.lobby;

import com.kiro.sg.Config;
import com.kiro.sg.game.GameInstance;
import com.kiro.sg.game.GameManager;
import com.kiro.sg.utils.Perms;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Manages the players who have not yet been assigned to a game.
 */
public class LobbyManager
{

	public static LobbyManager instance;

	public static LobbyManager getInstance()
	{
		return instance;
	}

	private final LinkedList<Player> playerQueue;
	private int donorCount = 0;

	public LobbyManager()
	{
		instance = this;
		playerQueue = new LinkedList<>();
		new GameCreationTimer(this);
	}

	/**
	 * add a player to the queue to join a game.
	 *
	 * @return the position the player is in when added to the queue (to notify the user)
	 */
	public int addToQueue(Player player)
	{
		if (player.hasPermission(Perms.DONOR_QUEUE))
		{
			// Donors will fill every OTHER slot in the queue to give normal players a chance to get into a game.
			int index = Math.min(donorCount * 2, playerQueue.size());
			playerQueue.add(index, player);
			donorCount++;

			return index + 1;
		}
		else
		{
			playerQueue.add(player);
			return playerQueue.size();
		}
	}

	/**
	 * Remove a player from the queue
	 */
	public void removeFromQueue(Player player)
	{
		if (player.hasPermission(Perms.DONOR_QUEUE))
		{
			donorCount--;
		}
		playerQueue.remove(player);
	}

	/**
	 * @return The next user in the list (thinking while a game is in the
	 * countdown timer, we can fill the slots if a player leaves or just not full)
	 */
	public Player pullPlayer()
	{
		return playerQueue.poll();
	}

	/**
	 * @return the amount of players waiting in the queue for a game to start.
	 */
	public int getQueueSize()
	{
		return playerQueue.size();
	}

	/**
	 * Takes the top 24 players from the queue to create a game.
	 *
	 * @return whether or not it successfully created a game
	 */
	public boolean createGame(boolean force)
	{
		List<Player> players = new ArrayList<>();

		int count = Math.min(24, playerQueue.size());
		if (count >= Config.MIN_PLAYER_COUNT || force)
		{
			boolean flag = true;
			while (players.size() < count)
			{
				Player player = playerQueue.poll();
				if (player.hasPermission(Perms.DONOR_QUEUE))
				{
					donorCount--;
				}

				if (player != null)
				{
					players.add(player);
				}

				flag = !flag;
			}
			// TODO: Add Voting to load arena.
			GameInstance gameInstance = new GameInstance(players, null);
			GameManager.registerGame(gameInstance);

			gameInstance.init();

			return true;
		}
		else
		{
			return false;
		}

	}

	public boolean createGame()
	{
		return createGame(false);
	}

}
