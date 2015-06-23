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

	public void init()
	{
		gameRunner = new GameRunner(this);
		scoreboard = new GameScoreboard();
		arena.loadArena();

		for (Player player : remaining)
		{
			preparePlayer(player);
		}
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
		player.setGameMode(GameMode.ADVENTURE);
		player.setMaxHealth(20.0);
		player.setHealth(20.0);
		player.setAllowFlight(false);
		player.setScoreboard(scoreboard.getScoreboard());
		player.getInventory().clear();
		Meta.setMetadata(player, "game", this);
	}

	public void setSpectator(Player player)
	{
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

		player.setGameMode(GameMode.SURVIVAL);
		player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 2, 2));
		player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 2, 2));
		scoreboard.setGhosts(player);
	}

	public void playerDeath(Player player)
	{
		scoreboard.updatePlayers(remaining.size());
	}

	public void removePlayer(Player player)
	{
		Meta.removeMetadata(player, "game");
		if (player.getGameMode() == GameMode.ADVENTURE)
		{
			World world = player.getWorld();
			Location location = player.getLocation();
			world.strikeLightningEffect(location);

			PlayerInventory inv = player.getInventory();
			for (ItemStack stack : inv.getContents())
			{
				world.dropItemNaturally(location, stack);
			}

			for (ItemStack stack : inv.getArmorContents())
			{
				world.dropItemNaturally(location, stack);
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
		setGameState(GameState.PLAYING);
	}

	public void deathmatch()
	{
		Msg.msgGame(ChatColor.RED + "The Arena walls are caving in!", this, false);
		WorldBorder border = arena.getWorld().getWorldBorder();
		border.setDamageAmount(4.0);
		border.setSize(60, 90);
	}

	public void end()
	{
		setGameState(GameState.ENDING);
		gameRunner.stop();
	}

	public void start()
	{
		new TeleportTask(remaining, arena.getSpawns());
		setGameState(GameState.STARTING);


		WorldBorder border = arena.getWorld().getWorldBorder();
		border.setCenter(arena.getCenterPoint());
		border.setDamageAmount(0.0);
		border.setSize(500, 10);
		border.setDamageBuffer(0);
	}

}
