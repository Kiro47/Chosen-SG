package com.kiro.sg.utils.effects;

import com.kiro.sg.utils.misc.VecUtils;
import net.minecraft.server.v1_8_R3.EnumParticle;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketPlayOutWorldParticles;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

public final class ParticleEffects
{
	private ParticleEffects()
	{
	}

	public static void playEffect(ParticleType effect, Location location, float speed, int count)
	{
		playEffect(effect, location, 0f, 0f, 0f, speed, count);
	}

	public static void playEffect(ParticleType effect, Location location, float offX, float offY, float offZ, float speed, int count)
	{

		Packet packet = new PacketPlayOutWorldParticles(EnumParticle.valueOf(effect.particleName), false, (float) location.getX(), (float) location.getY(), (float) location.getZ(), offX, offY, offZ, speed, count);

		for (Player player : Bukkit.getOnlinePlayers())
		{
			Location loc = player.getLocation();
			if (loc.distanceSquared(location) < 4096)
			{
				((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
			}
		}
	}

	public static void playEffect(ParticleType effect, double px, double py, double pz, float offX, float offY, float offZ, float speed, int count)
	{
		Packet packet = new PacketPlayOutWorldParticles(EnumParticle.valueOf(effect.particleName), false, (float) px, (float) py, (float) pz, offX, offY, offZ, speed, count);

		for (Player player : Bukkit.getOnlinePlayers())
		{
			Location loc = player.getLocation();
			if (VecUtils.getDistanceSquared(loc.getX(), loc.getZ(), px, pz) < 4096)
			{
				((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
			}
		}
	}

	public enum ParticleType
	{
		HUGE_EXPLOSION("hugeexplosion"),
		LARGE_EXPLODE("largeexplode"),
		FIREWORKS_SPARK("fireworksSpark"),
		BUBBLE("bubble"),
		SUSPEND("suspend"),
		DEPTH_SUSPEND("depthSuspend"),
		TOWN_AURA("townaura"),
		CRIT("crit"),
		MAGIC_CRIT("magicCrit"),
		MOB_SPELL("mobSpell"),
		MOB_SPELL_AMBIENT("mobSpellAmbient"),
		SPELL("spell"),
		INSTANT_SPELL("instantSpell"),
		WITCH_MAGIC("witchMagic"),
		NOTE("note"),
		PORTAL("portal"),
		ENCHANTMENT_TABLE("enchantmenttable"),
		EXPLODE("explode"),
		FLAME("flame"),
		LAVA("lava"),
		FOOTSTEP("footstep"),
		SPLASH("splash"),
		LARGE_SMOKE("largesmoke"),
		CLOUD("cloud"),
		RED_DUST("reddust"),
		SNOWBALL_POOF("snowballpoof"),
		DRIP_WATER("dripWater"),
		DRIP_LAVA("dripLava"),
		SNOW_SHOVEL("snowshovel"),
		SLIME("slime"),
		HEART("heart"),
		ANGRY_VILLAGER("angryVillager"),
		HAPPY_VILLAGER("happerVillager");

		public final String particleName;

		ParticleType(String particleName)
		{
			this.particleName = particleName;
		}
	}

}