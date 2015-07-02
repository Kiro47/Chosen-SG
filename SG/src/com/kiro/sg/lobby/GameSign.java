package com.kiro.sg.lobby;

import com.kiro.sg.config.Config;
import com.kiro.sg.game.GameInstance;
import com.kiro.sg.game.GameState;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.util.Set;

public class GameSign
{
	private static GameSign first;

	public static GameSign getSign(Location loc)
	{
		GameSign other = first;
		while (true)
		{
			if (other != null)
			{
				if (other.location.equals(loc))
				{
					return other;
				}
				other = other.next;
				continue;
			}
			return null;
		}
	}

	public static void updateSigns()
	{
		GameSign other = first;
		while (true)
		{
			if (other != null)
			{
				other.update();
				other = other.next;
				continue;
			}
			return;
		}
	}

	public static void add(GameSign sign)
	{
		if (first == null)
		{
			first = sign;
		}
		else
		{
			first.addSign(sign);
		}
	}

	public static void setGame(GameInstance instance)
	{
		first.addGame(instance);
	}

	public static void addSign(Location location)
	{
		try
		{
			YamlConfiguration configuration = YamlConfiguration.loadConfiguration(Config.GameSignFile);
			ConfigurationSection section = configuration.createSection(String.valueOf(System.currentTimeMillis()));
			Config.saveLocation(section, location);

			configuration.save(Config.GameSignFile);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public static void loadConfig()
	{
		YamlConfiguration configuration = YamlConfiguration.loadConfiguration(Config.GameSignFile);
		Set<String> keys = configuration.getKeys(false);
		World world = Bukkit.getWorlds().get(0);
		for (String key : keys)
		{
			System.out.println("Sign Added");
			ConfigurationSection section = configuration.getConfigurationSection(key);
			Location location = Config.getLocation(section, world);

			add(new GameSign(location));

		}
	}

	private GameSign next;
	private GameInstance game;
	private final Location location;

	public GameSign(Location location)
	{
		this.location = location;
	}

	public void addSign(GameSign sign)
	{
		if (next == null)
		{
			next = sign;
		}
		else
		{
			next.addSign(sign);
		}
	}


	public boolean addGame(GameInstance game)
	{
		GameSign other = this;
		while (true)
		{
			if (other.game != null)
			{
				if (other.next == null)
				{
					return false;
				}
				other = other.next;
				continue;
			}
			other.game = game;
			game.setGameSign(other);
			return true;
		}
	}

	public void shiftGame()
	{
		if (next != null)
		{
			game = next.game;
			if (game != null)
			{
				game.setGameSign(this);
				next.shiftGame();
			}
		}
		else
		{
			game = null;
		}
	}

	public void check()
	{
		if (game != null)
		{
			if (game.getState() == GameState.ENDED)
			{
				game = null;
				update();
			}
		}
		if (next != null)
		{
			next.check();
		}
	}

	public void onClick(Player player)
	{
		System.out.println("GameSign.onClick");
		if (game != null)
		{
			if (game.getState() != GameState.ENDING)
			{
				game.preparePlayer(player);
				player.teleport(game.getArena().getCenterPoint());
				LobbyManager.getInstance().removeFromQueue(player);
			}
		}
	}

	public void update()
	{
		String[] lines = new String[4];
		if (game != null)
		{
			lines[0] = "";
			lines[1] = "";

			String name = game.getArena().getArenaName();
			String[] split = name.split(" ");
			for (String aSplit : split)
			{
				if (lines[0].length() + aSplit.length() < 13)
				{
					lines[0] += aSplit + ' ';
				}
				else
				{
					lines[1] += aSplit + ' ';
				}
			}

			lines[0] = ChatColor.BOLD + lines[0];
			lines[1] = ChatColor.BOLD + lines[1];

			int gameSize = game.getRemaining().size();
			ChatColor color;
			if (gameSize < 5)
			{
				color = ChatColor.RED;
			}
			else if (gameSize < 10)
			{
				color = ChatColor.YELLOW;
			}
			else
			{
				color = ChatColor.GREEN;
			}

			lines[2] = color.toString() + ChatColor.BOLD + game.getRemaining().size() + ChatColor.GRAY + '/' + ChatColor.GREEN + "24";
			lines[3] = game.getState().disp;
		}
		else
		{
			lines[0] = "";
			lines[1] = ChatColor.RED + "No Game";
			lines[2] = ChatColor.RED + "Available";
			lines[3] = "";
		}

		Block block = location.getBlock();
		if (!block.getChunk().isLoaded())
		{
			block.getChunk().load();
		}
		BlockState state = block.getState();
		if (state instanceof Sign)
		{
			Sign sign = (Sign) state;
			for (int i = 0; i < 4; i++)
			{
				sign.setLine(i, lines[i]);
			}
			sign.update();
		}

	}

}
