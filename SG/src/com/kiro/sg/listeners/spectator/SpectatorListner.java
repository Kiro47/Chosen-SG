package com.kiro.sg.listeners.spectator;

import com.kiro.sg.custom.events.PlayerDamageByPlayerEvent;
import com.kiro.sg.custom.items.ItemCompass;
import com.kiro.sg.game.GameInstance;
import com.kiro.sg.game.GameManager;
import com.kiro.sg.game.spectators.CompassMenu;
import com.kiro.sg.sponsor.menu.SponsorMenu;
import com.kiro.sg.utils.chat.Msg;
import com.kiro.sg.utils.misc.EventUtils;
import com.kiro.sg.utils.misc.ItemUtils;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.entity.EntityType;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;

public class SpectatorListner implements Listener
{

	@EventHandler
	public void onItemPickup(PlayerPickupItemEvent event)
	{
		if (event.getPlayer().getGameMode() == GameMode.ADVENTURE)
		{
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void onEntityDamageByEntity(PlayerDamageByPlayerEvent event)
	{
		if (event.getPlayer().getGameMode() != event.getDamager().getGameMode())
		{
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void onItemDrop(PlayerDropItemEvent event)
	{
		if (event.getPlayer().getGameMode() == GameMode.ADVENTURE)
		{
			event.setCancelled(true);
		}
		else
		{
			ItemStack stack = event.getItemDrop().getItemStack();
			if (stack.getType() == Material.COMPASS)
			{
				stack = ItemCompass.reset(stack);
				event.getItemDrop().setItemStack(stack);
			}
		}
	}

	@EventHandler(priority = EventPriority.HIGH)
	public void onPlayerInteract(PlayerInteractEvent event)
	{

		if (event.getPlayer().getGameMode() == GameMode.ADVENTURE)
		{
			if (event.getAction() == Action.PHYSICAL)
			{
				event.setCancelled(true);
			}
			else if (event.getAction() == Action.RIGHT_CLICK_BLOCK)
			{
				Block block = event.getClickedBlock();
				Material type = block.getType();
				if (type == Material.LEVER || type == Material.STONE_BUTTON || type == Material.WOOD_BUTTON || type == Material.TRAP_DOOR)
				{
					event.setCancelled(true);
				}
				else if (ItemUtils.isDoor(type))
				{
					event.setCancelled(true);

					BlockState state = block.getState();
					MaterialData materialData = state.getData();
					byte data = materialData.getData();
					if (ItemUtils.isTopHalf(data))
					{
						Location loc1 = block.getLocation();
						loc1.subtract(0, 1, 0);
						block = loc1.getBlock();
						if (block != null)
						{
							state = block.getState();
							materialData = state.getData();
							if (materialData != null)
							{
								data = materialData.getData();
							}
							else
							{
								return;
							}
						}
						else
						{
							return;
						}
					}

					Location loc = block.getLocation();
					boolean flag = true;

					BlockFace face = event.getBlockFace();
					BlockFace doorDir = ItemUtils.getDoorDirection(data);
					if (face == doorDir.getOppositeFace())
					{
						face = face.getOppositeFace();
						loc.add(face.getModX(), face.getModY(), face.getModZ());
						flag = false;
					}
					block = loc.getBlock();
					if (block == null || !block.getType().isSolid() || flag || block.getType() == Material.LADDER)
					{
						loc.setDirection(event.getPlayer().getEyeLocation().getDirection());
						loc.add(0.5, 0, 0.5);
						event.getPlayer().teleport(loc);
					}
				}
			}

			if (event.hasItem() && EventUtils.isRightClick(event.getAction()))
			{
				ItemStack stack = event.getItem();
				if (stack.getType() == Material.COMPASS)
				{
					GameInstance instance = GameManager.getInstance(event.getPlayer());
					if (instance != null)
					{
						CompassMenu menu = instance.getCompassMenu();
						menu.onOpen(event.getPlayer());
					}

				}
				else if (stack.getType() == Material.BED)
				{
					GameInstance instance = GameManager.getInstance(event.getPlayer());
					if (instance != null)
					{
						instance.removePlayer(event.getPlayer());
						Msg.msgPlayer(event.getPlayer(), ChatColor.DARK_GREEN + "You have left the game!");
					}

				}
				else if (stack.getType() == Material.COOKIE)
				{
					GameInstance instance = GameManager.getInstance(event.getPlayer());
					if (instance != null)
					{
						event.setCancelled(true);
						event.setUseItemInHand(Event.Result.DENY);
						SponsorMenu.displayPlayerChoiceMenu(instance, event.getPlayer());
					}
				}
			}
		}
	}

	@EventHandler
	public void onDamage(EntityDamageEvent event)
	{
		//		if (event.getEntityType() == EntityType.PLAYER)
		//		{
		//			Player player = (Player) event.getEntity();
		//			if (player.getGameMode() == GameMode.ADVENTURE)
		//			{
		//				event.setCancelled(true);
		//				if (event.getCause() == EntityDamageEvent.DamageCause.SUFFOCATION)
		//				{
		//					Location location = player.getLocation();
		//					while (location.getBlock().getType() != Material.AIR)
		//					{
		//						location.add(0, 1, 0);
		//						if (location.getY() > 250)
		//						{
		//							GameInstance instance = GameManager.getInstance(player);
		//							if (instance != null)
		//							{
		//								location = instance.getArena().getCenterPoint();
		//							}
		//						}
		//					}
		//
		//					player.teleport(location);
		//				}
		//			}
		//		}
	}


	@EventHandler
	public void onHungerDamage(FoodLevelChangeEvent event)
	{
		if (event.getEntityType() == EntityType.PLAYER)
		{
			if (event.getEntity().getGameMode() == GameMode.ADVENTURE)
			{
				event.setFoodLevel(20);
			}
		}
	}

}
