package com.kiro.sg.game;

import com.kiro.sg.PlayerStats;
import com.kiro.sg.SGMain;
import com.kiro.sg.config.Config;
import com.kiro.sg.custom.items.ItemCompass;
import com.kiro.sg.game.arena.SGArena;
import com.kiro.sg.game.crates.Crates;
import com.kiro.sg.game.spectators.CompassMenu;
import com.kiro.sg.lobby.GameSign;
import com.kiro.sg.lobby.voting.VotingMapRenderer;
import com.kiro.sg.mysql.Consumer;
import com.kiro.sg.mysql.query.queries.AddGameQuery;
import com.kiro.sg.mysql.query.queries.GetInsertIndex;
import com.kiro.sg.mysql.query.queries.UpdateGameQuery;
import com.kiro.sg.scoreboard.GameScoreboard;
import com.kiro.sg.sponsor.menu.SponsorMenu;
import com.kiro.sg.sponsor.smart.SmartSponsor;
import com.kiro.sg.utils.LastIDable;
import com.kiro.sg.utils.Meta;
import com.kiro.sg.utils.chat.ChatUtils;
import com.kiro.sg.utils.chat.Msg;
import com.kiro.sg.utils.misc.ItemUtils;
import com.kiro.sg.utils.task.DamageTracker;
import com.kiro.sg.utils.task.FireworksTask;
import com.kiro.sg.utils.task.TeleportTask;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class GameInstance implements LastIDable
{

	private final List<Player> remaining;
	private final List<Player> spectators;
	private final SGArena arena;
	private final Crates crates;
	private GameRunner gameRunner;

	private GameState state;

	private GameScoreboard scoreboard;

	private GameSign gameSign;
	private final CompassMenu compassMenu;

	private int gameID;

	private Player winner;

	public void setGameSign(GameSign sign)
	{
		gameSign = sign;
	}

	@Override
	public int getID()
	{
		return gameID;
	}

	@Override
	public void setID(int id)
	{
		gameID = id;
	}

	public GameInstance(List<Player> players, SGArena arena)
	{
		System.out.println("Game Start");
		remaining = players;
		spectators = new ArrayList<>();

		this.arena = arena.createNew();
		crates = new Crates();
		state = GameState.INIT;
		GameSign.setGame(this);
		compassMenu = new CompassMenu(this);
	}

	public SGArena getArena()
	{
		return arena;
	}

	public void init()
	{
		scoreboard = new GameScoreboard();
		arena.loadArena();

		for (Player player : remaining)
		{
			preparePlayer(player);
		}

		scoreboard.updatePlayers(remaining.size());
		compassMenu.update();

		Consumer.queue(new AddGameQuery(this));
		Consumer.queue(new GetInsertIndex(this));
		gameRunner = new GameRunner(this);
	}

	public void executeSmartSponsor()
	{
		for (Player player : remaining)
		{
			SmartSponsor.sponsor(player);
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
		if (state == GameState.JOINING || state == GameState.INIT)
		{
			for (PotionEffect effect : player.getActivePotionEffects())
			{
				player.removePotionEffect(effect.getType());
			}

			arena.getAttributes().setPotionEffects(player);

			for (Player p : Bukkit.getOnlinePlayers())
			{
				if (player.canSee(p))
				{
					player.hidePlayer(p);
				}
			}

			for (Player p : remaining)
			{
				if (!player.canSee(p))
				{
					player.showPlayer(p);
				}
			}
			scoreboard.addLiving(player);

			player.setLevel(0);
			player.setGameMode(GameMode.SURVIVAL);
			player.setMaxHealth(20.0);
			player.setHealth(20.0);
			player.setFoodLevel(20);
			player.setAllowFlight(false);

			player.getInventory().setContents(new ItemStack[27]);
			player.getInventory().setArmorContents(new ItemStack[4]);

			player.updateInventory();

			PlayerStats stats = PlayerStats.getStats(player);
			if (stats != null)
			{
				stats.addGame();
			}
		}
		else
		{
			Msg.msgPlayer(player, ChatColor.DARK_GREEN + "You have been added as a spectator");
			setSpectator(player);
		}

		player.setScoreboard(scoreboard.getScoreboard());
		Meta.setMetadata(player, "game", this);

	}

	public CompassMenu getCompassMenu()
	{
		return compassMenu;
	}

	public void setSpectator(Player player)
	{
		remaining.remove(player);
		spectators.add(player);

		scoreboard.removeLiving(player);
		scoreboard.updatePlayers(remaining.size());


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
		player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 40, 20), true);
		player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 40, 20), true);

		player.getInventory().setContents(new ItemStack[27]);
		player.getInventory().setArmorContents(new ItemStack[4]);
		player.updateInventory();

		player.getInventory().setItem(0, CompassMenu.getCompassItem());
		player.getInventory().setItem(4, SponsorMenu.SPONSOR_ITEM);
		player.getInventory().setItem(8, ItemUtils.nameItem(new ItemStack(Material.BED), ChatUtils.format("&4Leave Game!")));


		scoreboard.setGhosts(player);
		player.setAllowFlight(true);
		player.setFlying(true);
	}


	public void playerDeath(Player player)
	{
		player.setFireTicks(0);

		if (state != GameState.ENDING && state != GameState.ENDED)
		{
			player.sendMessage(ChatColor.RED + "You have died.");

			PlayerStats stats = PlayerStats.getStats(player);
			if (stats != null)
			{
				stats.addDeath();
			}

			Player killer = DamageTracker.get(player);
			if (killer != null)
			{
				killer.setLevel(killer.getLevel() + 2);
				stats = PlayerStats.getStats(killer);
				if (stats != null)
				{
					stats.addKill();
					stats.addPoints(5);
				}
				Msg.msgGame(ChatUtils.format(String.format("&c%s &ehas been killed by &c%s", player.getDisplayName(), killer.getDisplayName())), this, false);
			}
			else
			{
				Msg.msgGame(ChatUtils.format(String.format("&c%s &ehas been killed!", player.getDisplayName())), this, false);
			}

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

		gameSign.update();

		compassMenu.update();

		PlayerStats stats = PlayerStats.getStats(player);
		if (stats != null)
		{
			stats.update();
		}
	}

	public void removePlayer(final Player player)
	{
		if (player.getGameMode() == GameMode.SURVIVAL)
		{
			playerDeath(player);
		}

		ItemCompass.remove(player);
		remaining.remove(player);
		spectators.remove(player);
		scoreboard.removeLiving(player);

		//scoreboard.removeGhost(player);

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

		new BukkitRunnable()
		{
			@Override
			public void run()
			{
				if (player.isOnline())
				{
					VotingMapRenderer.sendToPlayer(player);
				}
			}

		}.runTaskLater(SGMain.getPlugin(), 40);

		player.updateInventory();

		Meta.removeMetadata(player, "game");
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
		gameSign.update();
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
		border.setDamageAmount(1.0);
		border.setSize(60, 120);
	}

	public void ending()
	{
		Msg.msgGame(ChatColor.YELLOW + ChatUtils.fill("-"), this, false);
		Msg.msgGame(ChatColor.GOLD + ChatUtils.center("<>  Game Over  <>"), this, false);
		setGameState(GameState.ENDING);
		gameRunner.setTimer(10);

		if (remaining.size() == 1)
		{
			winner = remaining.get(0);
			Msg.msgGame(ChatUtils.center(ChatColor.GREEN + winner.getDisplayName() + ChatColor.AQUA + " has won the game!"), this, false);

			PlayerStats stats = PlayerStats.getStats(winner);
			if (stats != null)
			{
				stats.addWin();
				stats.addPoints(25);
			}
			System.out.println("Player Stats");

			new FireworksTask(winner);
		}
		Msg.msgGame(ChatColor.YELLOW + ChatUtils.fill("-"), this, false);

		Consumer.queue(new UpdateGameQuery(this));

	}

	public Player getWinner()
	{
		return winner;
	}

	public void end()
	{
		gameRunner.stop();

		for (Player player : arena.getWorld().getPlayers())
		{
			removePlayer(player);
		}

		crates.clear();
		arena.dispose();
		spectators.clear();
		remaining.clear();
		setGameState(GameState.ENDED);

		compassMenu.dispose();
		gameSign.check();
	}

	public void start()
	{
		Msg.msgGame(ChatColor.YELLOW + ChatUtils.fill("-"), this, false);
		Msg.msgGame(ChatColor.GOLD + ChatUtils.center("<>  Starting  <>"), this, false);
		Msg.msgGame(ChatColor.GOLD + ChatUtils.center(arena.getArenaName()), this, false);
		Msg.msgGame(ChatColor.YELLOW + ChatUtils.fill("-"), this, false);
		new TeleportTask(remaining, arena.getSpawns());
		setGameState(GameState.STARTING);
		arena.getWorld().setTime(15000);
	}

}
