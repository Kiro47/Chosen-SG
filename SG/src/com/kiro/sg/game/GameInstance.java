package com.kiro.sg.game;

import com.kiro.sg.SGMain;
import com.kiro.sg.config.Config;
import com.kiro.sg.custom.items.ItemCompass;
import com.kiro.sg.game.arena.SGArena;
import com.kiro.sg.game.crates.Crates;
import com.kiro.sg.game.lobby.voting.VotingMapRenderer;
import com.kiro.sg.scoreboard.GameScoreboard;
import com.kiro.sg.utils.Meta;
import com.kiro.sg.utils.chat.ChatUtils;
import com.kiro.sg.utils.chat.Msg;
import com.kiro.sg.utils.task.DamageTracker;
import com.kiro.sg.utils.task.FireworksTask;
import com.kiro.sg.utils.task.TeleportTask;
import com.kiro.sg.utils.task.TrackerTask;
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

	public boolean foreverNight = false;

	private GameState state;

	private GameScoreboard scoreboard;

	public GameInstance(List<Player> players, SGArena arena)
	{
		System.out.println("Game Start");
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

		for (PotionEffect effect : player.getActivePotionEffects())
		{
			player.removePotionEffect(effect.getType());
		}

		if ("Moon Base 9".equals(arena.getArenaName()))
		{
			foreverNight = true;
			player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 100000, 2));
		}

		for (Player player1 : remaining)
		{
			player.showPlayer(player1);
		}

		player.setGameMode(GameMode.SURVIVAL);
		player.setMaxHealth(20.0);
		player.setHealth(20.0);
		player.setFoodLevel(20);
		player.setAllowFlight(false);
		player.setScoreboard(scoreboard.getScoreboard());

		player.getInventory().setContents(new ItemStack[27]);
		player.getInventory().setArmorContents(new ItemStack[4]);

		new TrackerTask(player).runTaskTimer(SGMain.getPlugin(), 0L, 100L);
		player.updateInventory();
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

		player.setFireTicks(0);
		player.setFoodLevel(20);
		player.setMaxHealth(20.0);
		player.setHealth(20.0);
		player.setGameMode(GameMode.ADVENTURE);
		player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 20, 20), true);
		player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 20, 20), true);

		player.getInventory().setContents(new ItemStack[27]);
		player.getInventory().setArmorContents(new ItemStack[4]);
		player.updateInventory();


		scoreboard.setGhosts(player);
		player.setAllowFlight(true);
		player.setFlying(true);
	}

	public void playerDeath(Player player)
	{
		player.setFireTicks(0);

		Player killer = DamageTracker.get(player);
		if (killer != null)
		{
			Msg.msgGame(ChatUtils.format(String.format("&c%s &ehas been killed by &c%s", player.getDisplayName(), killer.getDisplayName())), this, false);
		}

		DamageTracker.remove(player);

		World world = player.getWorld();
		Location location = player.getLocation();
		world.strikeLightningEffect(location);

		PlayerInventory inv = player.getInventory();
		for (ItemStack stack : inv.getContents())
		{
			if (stack != null && stack.getType() != Material.AIR)
			{
				if (stack.getType() == Material.COMPASS)
				{
					stack = ItemCompass.reset(stack);
				}
				world.dropItemNaturally(location, stack);
			}
		}

		for (ItemStack stack : inv.getArmorContents())
		{
			if (stack != null && stack.getType() != Material.AIR)
			{
				world.dropItemNaturally(location, stack);
			}
		}

		inv.clear();
		setSpectator(player);

		if (remaining.size() == 1)
		{
			ending();
		}
		else if (remaining.isEmpty())
		{
			end();
		}
		else if (remaining.size() == 3)
		{
			deathmatch();
		}

	}

	public void removePlayer(Player player)
	{
		if (player.getGameMode() == GameMode.SURVIVAL)
		{
			playerDeath(player);
		}

		Meta.removeMetadata(player, "game");
		ItemCompass.remove(player);
		remaining.remove(player);
		spectators.remove(player);

		World mainWorld = Bukkit.getWorlds().get(0);
		player.teleport(mainWorld.getSpawnLocation());

		player.setFireTicks(0);
		player.setFlying(false);
		player.setAllowFlight(false);
		player.getInventory().setContents(new ItemStack[27]);
		player.getInventory().setArmorContents(new ItemStack[4]);

		player.setScoreboard(Bukkit.getScoreboardManager().getMainScoreboard());

		for (PotionEffect effect : player.getActivePotionEffects())
		{
			player.removePotionEffect(effect.getType());
		}

		player.updateInventory();
		VotingMapRenderer.sendToPlayer(player);
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
		Msg.msgGame(ChatColor.YELLOW + ChatUtils.fill("-"), this, false);
		Msg.msgGame(ChatColor.GOLD + ChatUtils.center("<>  Begin  <>"), this, false);
		Msg.msgGame(ChatColor.DARK_AQUA + ChatUtils.center("Those beside you will try to kill you."), this, false);
		Msg.msgGame(ChatColor.RED + ChatUtils.center("Kill them back."), this, false);
		Msg.msgGame(ChatColor.YELLOW + ChatUtils.fill("-"), this, false);
		arena.getWorld().setTime(0);
		setGameState(GameState.PLAYING);
	}

	public void deathmatch()
	{
		gameRunner.setTimer(Config.TIMER_DEATHMATCH_MAX);
		Msg.msgGame(ChatColor.YELLOW + ChatUtils.fill("-"), this, false);
		Msg.msgGame(ChatColor.RED + ChatUtils.center("<>  Deathmatch  <>"), this, false);
		Msg.msgGame(ChatColor.RED + ChatUtils.center("The Arena walls are caving in!"), this, false);
		Msg.msgGame(ChatColor.YELLOW + ChatUtils.fill("-"), this, false);
		setGameState(GameState.DEATHMATCH);
		WorldBorder border = arena.getWorld().getWorldBorder();
		border.setDamageAmount(4.0);
		border.setSize(60, 90);
	}

	public void ending()
	{
		Msg.msgGame(ChatColor.YELLOW + ChatUtils.fill("-"), this, false);
		Msg.msgGame(ChatColor.GOLD + ChatUtils.center("<>  Game Over  <>"), this, false);
		setGameState(GameState.ENDING);
		gameRunner.setTimer(10);

		if (remaining.size() == 1)
		{
			Player winner = remaining.get(0);
			Msg.msgGame(ChatUtils.center(ChatColor.GREEN + winner.getDisplayName() + ChatColor.AQUA + " has won the game!"), this, false);

			new FireworksTask(winner);
		}
		Msg.msgGame(ChatColor.YELLOW + ChatUtils.fill("-"), this, false);
	}

	public void end()
	{
		gameRunner.stop();

		for (Player player : arena.getWorld().getPlayers())
		{
			removePlayer(player);
		}

		arena.dispose();

	}

	public void start()
	{
		Msg.msgGame(ChatColor.YELLOW + ChatUtils.fill("-"), this, false);
		Msg.msgGame(ChatColor.GOLD + ChatUtils.center("<>  Starting  <>"), this, false);
		Msg.msgGame(ChatColor.YELLOW + ChatUtils.fill("-"), this, false);
		new TeleportTask(remaining, arena.getSpawns());
		setGameState(GameState.STARTING);
		arena.getWorld().setTime(15000);
	}

}
