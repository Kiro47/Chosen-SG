package com.kiro.sg.game.lobby.voting;

import com.kiro.sg.config.Config;
import com.kiro.sg.game.lobby.LobbyManager;
import com.kiro.sg.utils.chat.ChatUtils;
import com.kiro.sg.utils.chat.Msg;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Sign;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.map.MapView;

import java.util.*;
import java.util.regex.Pattern;

public class VotingMap
{
	private static final VotingMapRenderer mapRenderer = new VotingMapRenderer();
	private static final Pattern COMPILE = Pattern.compile(" ");
	public static List<VotingMap> VotingMaps = new ArrayList<>();
	public static Map<Location, VotingMap> Maps = new HashMap<>();
	private final List<MapView> mapViews;
	private final Location sign1;
	private final Location sign2;
	private VotingOption option;
	private String[] arenaName;

	public VotingMap(Location... maps)
	{
		VotingMaps.add(this);
		mapViews = new LinkedList<>();
		for (Location map : maps)
		{
			MapView view = Bukkit.createMap(map.getWorld());
			setMap(map, view);
			mapRenderer.applyTo(view);
			mapViews.add(view);
			Maps.put(map, this);
		}
		sign1 = maps[1].clone().add(0, 1, 0);
		sign2 = maps[0].clone().add(0, 1, 0);
	}

	public static VotingMap defineMap(Location location)
	{
		int posX = location.getBlockX();
		int posY = location.getBlockY();
		int posZ = location.getBlockZ();
		Entity[] entities = location.getChunk().getEntities();

		Location[] maps = new Location[4];
		maps[1] = location;
		maps[2] = location.clone().add(0, -1, 0);

		for (int offX : new int[]{-1, 0, 1})
		{
			if (maps[0] != null)
			{
				break;
			}
			for (int offZ : new int[]{-1, 0, 1})
			{
				if (offX == 0 && offZ == 0)
				{
					continue;
				}
				Location loc = new Location(location.getWorld(), posX + offX, posY, posZ + offZ);
				Entity[] entities1 = entities;
				if (!loc.getChunk().equals(location.getChunk()))
				{
					entities1 = loc.getChunk().getEntities();
				}
				for (Entity entity : entities1)
				{
					if (entity instanceof ItemFrame)
					{
						Location eloc = entity.getLocation();
						eloc = new Location(loc.getWorld(), eloc.getBlockX(), eloc.getBlockY(), eloc.getBlockZ());
						if (loc.equals(eloc))
						{
							maps[0] = loc;
							break;
						}
					}
				}
			}
		}
		maps[3] = maps[0].clone().add(0, -1, 0);
		saveMap(maps);
		return new VotingMap(maps);
	}

	public static void loadMaps()
	{
		YamlConfiguration config = YamlConfiguration.loadConfiguration(Config.VotingMapFile);
		String worldString = config.getString("world");
		System.out.println(worldString);
		World world = Bukkit.getWorld(worldString);

		ConfigurationSection sec = config.getConfigurationSection("maps");
		Set<String> keys = sec.getKeys(false);
		for (String key : keys)
		{
			ConfigurationSection mapValues = sec.getConfigurationSection(key);
			Location[] locations = {
					                       Config.getLocation(mapValues.getConfigurationSection("map1"), world),
					                       Config.getLocation(mapValues.getConfigurationSection("map2"), world),
					                       Config.getLocation(mapValues.getConfigurationSection("map3"), world),
					                       Config.getLocation(mapValues.getConfigurationSection("map4"), world)
			};

			new VotingMap(locations);

		}


	}

	public static void saveMap(Location... locs)
	{
		try
		{

			YamlConfiguration config = YamlConfiguration.loadConfiguration(Config.VotingMapFile);
			config.set("world", locs[0].getWorld().getName());

			ConfigurationSection sec = config.createSection("maps." + System.currentTimeMillis());
			Config.saveLocation(sec.createSection("map1"), locs[0]);
			Config.saveLocation(sec.createSection("map2"), locs[1]);
			Config.saveLocation(sec.createSection("map3"), locs[2]);
			Config.saveLocation(sec.createSection("map4"), locs[3]);

			config.save(Config.VotingMapFile);

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	private void setMap(Location loc, MapView view)
	{
		Chunk chunk = loc.getChunk();
		if (!chunk.isLoaded())
		{
			chunk.load();
		}

		for (Entity entity : chunk.getEntities())
		{
			Location l = entity.getLocation();
			l = new Location(l.getWorld(), l.getBlockX(), l.getBlockY(), l.getBlockZ());
			if (entity instanceof ItemFrame && l.equals(loc))
			{
				ItemFrame frame = (ItemFrame) entity;
				frame.setRotation(Rotation.NONE);
				ItemStack stack = new ItemStack(Material.MAP, 1, view.getId());
				frame.setItem(stack);

				BlockFace face = frame.getFacing();
				Location location = new Location(l.getWorld(), l.getBlockX() - face.getModX(), l.getBlockY() - face.getModY(), l.getBlockZ() - face.getModZ());
				Maps.put(location, this);


				break;
			}
		}

	}

	public List<MapView> getMapViews()
	{
		return Collections.unmodifiableList(mapViews);
	}

	public void setVotingOption(VotingOption option)
	{
		this.option = option;
		mapRenderer.loadImage(this.option == null ? null : this.option.getMapImageName(), this);
		arenaName = null;
		setSigns();

	}

	public void update()
	{
		if (option != null)
		{
			setSigns();
		}
	}

	private void setSigns()
	{
		if (option == null)
		{
			return;
		}
		Block block = sign1.getBlock();
		if (block.getState() != null && block.getState() instanceof Sign)
		{
			Sign sign = (Sign) block.getState();
			if (arenaName == null)
			{
				arenaName = new String[]{"", ""};
				String[] split = COMPILE.split(option.getDisplayName());

				for (String aSplit : split)
				{
					if (arenaName[0].length() + aSplit.length() <= 13)
					{
						arenaName[0] += aSplit + ' ';
					}
					else
					{
						arenaName[1] += aSplit + ' ';
					}

				}
				arenaName[0] = ChatColor.BOLD + arenaName[0];
				arenaName[1] = ChatColor.BOLD + arenaName[1];
			}

			sign.setLine(0, "===============");
			sign.setLine(1, arenaName[0]);
			sign.setLine(2, arenaName[1]);
			sign.setLine(3, "===============");
			sign.update();
		}
		block = sign2.getBlock();
		if (block.getState() != null && block.getState() instanceof Sign)
		{
			Sign sign = (Sign) block.getState();
			String prefix;
			try
			{
				if (option.isWinning())
				{
					prefix = "&a";
				}
				else
				{
					prefix = option.getVotes() > 0 ? "&b" : "&f";
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
				prefix = "&0";
			}

			prefix = ChatUtils.format(prefix) + ChatColor.BOLD;

			sign.setLine(0, "===============");
			sign.setLine(1, prefix + "Votes");
			sign.setLine(2, prefix + option.getVotes());
			sign.setLine(3, "===============");
			sign.update();
		}
	}

	public void onClick(Player player)
	{
		LobbyManager.getInstance().addToQueue(player);
		if (option != null)
		{
			Voting.voteFor(player, option.getOption());
		}
		else
		{
			Msg.msgPlayer(player, "&cYou cannot vote for this!");
		}
	}

}