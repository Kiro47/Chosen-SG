package com.kiro.sg.utils.task;

import com.kiro.sg.SGMain;
import com.kiro.sg.utils.effects.ParticleEffects;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.scheduler.BukkitRunnable;

public class ParticleSpiralTask extends BukkitRunnable
{

	private static final float POINTS_IN_CIRCLE = 360F;
	private final Entity entity;
	private final Location location;
	private final double radius;
	private final float angle_per_particle;
	private final int time;
	private final double amount_per_tick;
	private final int split;
	private final double height_shift;
	private final double angleOffsetSplit;
	private final ParticleEffects.ParticleType effect;
	private int ticks;
	private float angle;
	private double py;


	public ParticleSpiralTask(Entity entity, int time, int split, double wrapps, int height, int points, double radius, ParticleEffects.ParticleType effect)
	{
		this.location = null;
		this.effect = effect;
		this.radius = radius;
		this.entity = entity;
		angle = 0;
		this.time = time;

		this.split = split;

		angle_per_particle = POINTS_IN_CIRCLE / points;
		amount_per_tick = points * wrapps / time;

		height_shift = height / (points * wrapps);
		angleOffsetSplit = POINTS_IN_CIRCLE / this.split;

		runTaskTimer(SGMain.getPlugin(), 1, 1);

	}

	public ParticleSpiralTask(Location location, int time, int split, double wrapps, int height, int points, double radius, ParticleEffects.ParticleType effect)
	{
		this.entity = null;
		this.effect = effect;
		this.radius = radius;
		this.location = location;
		angle = 0;
		this.time = time;

		this.split = split;

		angle_per_particle = POINTS_IN_CIRCLE / points;
		amount_per_tick = points * wrapps / time;

		height_shift = height / (points * wrapps);
		angleOffsetSplit = POINTS_IN_CIRCLE / this.split;

		runTaskTimer(SGMain.getPlugin(), 1, 1);

	}

	@Override
	public void run()
	{
		Location loc;
		if (entity == null)
		{
			loc = location;
		}
		else
		{
			loc = entity.getLocation();
		}

		if (ticks++ == time)
		{
			cancel();
		}

		for (int i = 0; i < amount_per_tick; i++)
		{
			py += height_shift;
			for (int j = 0; j < split; j++)
			{
				angle += angle_per_particle;

				double px = loc.getX() + radius * Math.cos(Math.toRadians(angle + angleOffsetSplit * j));
				double pz = loc.getZ() + radius * -Math.sin(Math.toRadians(angle + angleOffsetSplit * j));

				Location l = new Location(loc.getWorld(), px, loc.getY() + py, pz);

				ParticleEffects.playEffect(effect, l, 0, 1);

			}
		}

	}
}
