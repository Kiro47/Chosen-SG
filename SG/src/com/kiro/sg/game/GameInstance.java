package com.kiro.sg.game;

import com.kiro.sg.arena.SGArena;
import com.kiro.sg.crates.Crates;
import com.kiro.sg.scoreboard.GameScoreboard;
import com.kiro.sg.utils.Meta;
import com.kiro.sg.utils.Msg;
import com.kiro.sg.utils.TeleportTask;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;

public class GameInstance
{

	private final List<Player> remaining;
	private final List<Player> spectators;
	private final SGArena arena;
	private final Crates crates;
	private GameRunner gameRunner;

	private GameState state;

	private GameScoreboard scoreboard;

	public GameInstance(List<Player> players, SGArena arena)
	{
		remaining = players;
		spectators = new ArrayList<>();

		this.arena = arena.createNew();
		crates = new Crates();
		state = GameState.INIT;
	}

	public SGArena getArena()
	{
		return arena;
	}

	public void init()
	{
		gameRunner = new GameRunner(this);
		scoreboard = new GameScoreboard();
		arena.loadArena();

		for (Player player : remaining)
		{
			preparePlayer(player);
		}

		scoreboard.updatePlayers(remaining.size());
	}

	public List<Player> getRemaining()
	{
		return remaining;
	}

	public List<Player> getSpectators()
	{
		return spectators;
	}

	public GameScoreboard getScoreboard()
	{
		return scoreboard;
	}

	public GameRunner getGameRunner()
	{
		return gameRunner;
	}

	public void preparePlayer(Player player)
	{
		player.setGameMode(GameMode.SURVIVAL);
		player.setMaxHealth(20.0);
		player.setHealth(20.0);
		player.setFoodLevel(20);
		player.setAllowFlight(false);
		player.setScoreboard(scoreboard.getScoreboard());
		player.getInventory().clear();
		Meta.setMetadata(player, "game", this);
	}

	public void setSpectator(Player player)
	{
		remaining.remove(player);
		spectators.add(player);

		scoreboard.updatePlayers(remaining.size());

		player.sendMessage(ChatColor.RED + "You have died.");
		for (Player p : remaining)
		{
			p.hidePlayer(player);
			player.showPlayer(p);
		}

		for (Player p : spectators)
		{
			p.showPlayer(player);
			player.showPlayer(p);
		}
		player.setFoodLevel(20);
		player.setMaxHealth(20.0);
		player.setHealth(20.0);
		player.setGameMode(GameMode.ADVENTURE);
		player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 20, 20), true);
		player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 20, 20), true);


		scoreboard.setGhosts(player);
	}

	public void playerDeath(Player player)
	{
		setSpectator(player);


		World world = player.getWorld();
		Location location = player.getLocation();
		world.strikeLightningEffect(location);

		PlayerInventory inv = player.getInventory();
		for (ItemStack stack : inv.getContents())
		{
			if (stack != null)
			{
				world.dropItemNaturally(location, stack);
			}
		}

		for (ItemStack stack : inv.getArmorContents())
		{
			if (stack != null)
			{
				world.dropItemNaturally(location, stack);
			}
		}
	}

	public void removePlayer(Player player)
	{
		Meta.removeMetadata(player, "game");
		if (player.getGameMode() == GameMode.SURVIVAL)
		{
			World world = player.getWorld();
			Location location = player.getLocation();
			world.strikeLightningEffect(location);

			PlayerInventory inv = player.getInventory();
			for (ItemStack stack : inv.getContents())
			{
				if (stack != null)
				{
					world.dropItemNaturally(location, stack);
				}
			}

			for (ItemStack stack : inv.getArmorContents())
			{
				if (stack != null)
				{
					world.dropItemNaturally(location, stack);
				}
			}

		}
	}

	public Crates getCrates()
	{
		return crates;
	}

	public GameState getState()
	{
		return state;
	}

	public void setGameState(GameState state)
	{
		this.state = state;
	}

	public void startMatch()
	{
		arena.getWorld().setTime(0);
		setGameState(GameState.PLAYING);
	}

	public void deathmatch()
	{
		setGameState(GameState.DEATHMATCH);
		Msg.msgGame(ChatColor.RED + "The Arena walls are caving in!", this, false);
		WorldBorder border = arena.getWorld().getWorldBorder();
		border.setDamageAmount(4.0);
		border.setSize(60, 90);
	}

	public void end()
	{
		setGameState(GameState.ENDING);
		gameRunner.stop();

		World mainWorld = Bukkit.getWorlds().get(0);

		for (Player player : arena.getWorld().getPlayers())
		{
			player.teleport(mainWorld.getSpawnLocation());
		}

		arena.dispose();

	}

	public void start()
	{
		new TeleportTask(remaining, arena.getSpawns());
		setGameState(GameState.STARTING);
		arena.getWorld().setTime(15000);


	}

}
