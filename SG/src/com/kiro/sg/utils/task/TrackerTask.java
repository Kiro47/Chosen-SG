package com.kiro.sg.utils.task;

//import net.minecraft.server.v1_8_R3.ChatSerializer;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class TrackerTask extends BukkitRunnable {
	
	/*
	 * How to use:
	 * 
	 * Give player compass then use this method:
	 * 
	 * 		new TrackerTask (player).runTaskTimer(plugin, 0L, 100L);
	 * 
	 */
	
	private Player player;
	
	public TrackerTask(Player player) {
		this.player = player;
	}
	
	
	@Override 
	public void run() {
//		String message = "" + ChatColor.RED + ChatColor.BOLD + "NO TARGET FOUND!";
//
//		ArrayList<Player> players = new ArrayList<Player> ();
//
//		for (Player p : players) {
////			if (!(p.getUniqueId().equals(player.getUniqueId()))  || ((player.canSee(p)))
////					//Check to see if player is not a  spectator			&& !()   )
////					) {
////				players.add(p);
////				return;
////			}
////
////			Collections.sort(players, new CompassTracker(player));
////					Player nearest = null;
////
////
////					if (players.size() > 0 ) {
////						nearest =  players.get(0);
////						message = "" + ChatColor.GREEN + ChatColor.BOLD + "TARGET: " + ChatColor.GOLD + nearest.getName() +
////								" " + ChatColor.GREEN + "DISTANCE: " + ChatColor.GOLD + ""
////								+ nearest.getLocation().distance(player.getLocation());
////
////
////						// Change compass's title
////					}
////
////					IChatBaseComponent comp = ChatSerializer.a("{\"text\": \"" + (
////							player.getItemInHand().getType().equals (Material.COMPASS) ? message :
////						" ") + "\"}");
////
////					PacketPlayOutChat packet = new PacketPlayOutChat(comp, (byte) 2);
////					((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
//				try {
//					player.setCompassTarget(nearest !=null ? nearest.getLocation() : null);
//				}
//				catch (NullPointerException ignore) {
//
//				}
//		}
		
		
	}

}
