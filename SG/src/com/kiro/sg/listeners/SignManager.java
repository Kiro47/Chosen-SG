package com.kiro.sg.listeners;

import com.kiro.sg.LobbySign;
import com.kiro.sg.lobby.LobbyManager;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.ArrayList;

public class SignManager implements Listener
{

	private ArrayList<LobbySign> signs;

	public SignManager()
	{
		//		signs = new ArrayList<LobbySign>();
		//
		//		for (String key : SettingsManager.getSigns().getKeys())
		//		{
		//
		//			ConfigurationSection s = SettingsManager.getSigns().get(key);
		//
		//			Location loc = SGMain.loadLocation(s.getConfigurationSection("location"));
		//			Arena arena = ArenaManager.getInstance().getArena(s.getString("arena"));
		//
		//			if (loc.getBlock() == null || !(loc.getBlock().getState() instanceof Sign))
		//			{
		//				SettingsManager.getSigns().set(key, null);
		//				System.err.println("removed broken sign at location: " + loc);
		//				continue;
		//			}
		//
		//
		//			if (arena == null)
		//			{
		//				SettingsManager.getSigns().set(key, null);
		//				System.err.println("Removed Sign For nonexisting arena at location: " + loc);
		//				continue;
		//			}
		//
		//			signs.add(new LobbySign(loc, arena));
		//		}
		//
		//
		//		Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(SGMain.getPlugin(), new Runnable()
		//		{
		//			public void run()
		//			{
		//				for (LobbySign sign : signs)
		//				{
		//					sign.update();
		//				}
		//			}
		//		}, 0, 20);
	}


	@EventHandler

	public void onPlayerLobbySignClick(PlayerInteractEvent e)
	{

		if (e.getAction() != Action.RIGHT_CLICK_BLOCK)
		{
			return;
		}


		Block b = e.getClickedBlock();

		if (b.getType() != Material.SIGN && b.getType() != Material.WALL_SIGN && b.getType() != Material.SIGN_POST)
		{
			return;
		}

		// TODO: do voting here

		LobbyManager.getInstance().addToQueue(e.getPlayer());


		//		for (LobbySign sign : signs)
		//		{
		//
		//			if (sign.getLocation().equals(b.getLocation()))
		//			{
		//				sign.getArena().addPlayer(e.getPlayer());
		//				break;
		//			}
		//		}


	}

}
