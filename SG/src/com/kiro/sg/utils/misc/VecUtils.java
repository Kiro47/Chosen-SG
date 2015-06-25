package com.kiro.sg.utils.misc;

import com.kiro.sg.utils.Vector3D;
import org.bukkit.Location;
import org.bukkit.util.Vector;

public final class VecUtils
{
	private static final float EPSILON = 0.0001f;

	private VecUtils()
	{

	}

	public static void main(String[] args)
	{

		Vector dir = getDir(0, 0);
		Vector pos = new Vector(0, 0, 0);
		Vector pos2 = new Vector(-10, 0, 0);

		System.out.println(getAngle(dir, getUnitVector(pos, pos2)));
	}

	public static Vector getDir(float yaw, float pitch)
	{
		double radsY = Math.toRadians(yaw);
		double radsP = Math.toRadians(pitch);

		//		double cosP = Math.cos(radsP);

		double px = -Math.sin(radsY);
		double py = Math.sin(radsP);
		double pz = Math.cos(radsY);

		return new Vector(px, py, pz);
	}

	public static Vector getUnitVector(Vector v1, Vector v2)
	{
		return new Vector(v2.getX() - v1.getX(), v2.getY() - v1.getY(), v2.getZ() - v1.getZ());
	}

	public static double getAngle(Vector v1, Vector v2)
	{
		v1.setY(0.0);
		v2.setY(0.0);
		double cosA = v1.dot(v2) / (v1.length() * v2.length());

		//		return v1.angle(v2);

		return Math.toDegrees(Math.acos(cosA));
	}


	public static double getDistanceSquared(Vector v1, Vector v2)
	{
		return v1.distanceSquared(v2);
	}

	public static double getDistance(Vector v1, Vector v2)
	{
		return v1.distance(v2);
	}

	public static double getDistance(double x1, double y1, double x2, double y2)
	{
		return Math.sqrt(getDistanceSquared(x1, y1, x2, y2));
	}

	public static double getDistanceSquared(double x1, double y1, double x2, double y2)
	{
		return (x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2);
	}

	public static double getHorrizontalDistance(Vector v1, Vector v2)
	{
		return getDistance(v1.getX(), v1.getZ(), v2.getX(), v2.getZ());
	}

	public static double getHorrizontalDistance(Vector vector)
	{
		return getDistance(vector.getX(), vector.getZ(), 0, 0);
	}

	public static Vector normalizeHorrizontal(Vector vector)
	{
		double dist = getHorrizontalDistance(vector);
		if (dist == 0)
		{
			dist = EPSILON;
		}
		return vector.divide(new Vector(dist, 1, dist));
	}

	public static boolean locEqualsVec(Location loc, Vector vec)
	{
		return loc.getBlockX() == vec.getBlockX() && loc.getBlockY() == vec.getBlockY() && loc.getBlockZ() == vec.getBlockZ();
	}

	public static float getYaw(Vector v1, Vector v2)
	{
		return getYaw(v1.subtract(v2));
	}

	public static float getYaw(Vector vec)
	{
		double x = vec.getX();
		double z = vec.getZ();

		double yaw = Math.toDegrees(Math.atan(-x / z));
		if (z < 0.0D)
		{
			yaw += 180.0D;
		}
		return (float) yaw;
	}

	public static float getPitch(Vector v1, Vector v2)
	{
		return getPitch(v1.subtract(v2));
	}

	public static float getPitch(Vector vec)
	{
		double x = vec.getX();
		double y = vec.getY();
		double z = vec.getZ();
		double xz = Math.sqrt(x * x + z * z);

		double pitch = Math.toDegrees(Math.atan(xz / y));
		if (y <= 0.0D)
		{
			pitch += 90.0D;
		}
		else
		{
			pitch -= 90.0D;
		}
		return (float) pitch;
	}


	public static boolean hasIntersection(Vector point1, Vector point2, Vector min, Vector max)
	{
		return hasIntersection(Vector3D.wrap(point1), Vector3D.wrap(point2), Vector3D.wrap(min), Vector3D.wrap(max));
	}

	public static boolean hasIntersection(Vector3D point1, Vector3D point2, Vector3D min, Vector3D max)
	{
		Vector3D dist = point2.subtract(point1).multiply();
		Vector3D boxSize = max.subtract(min).multiply();
		Vector3D c = point1.add(dist).subtract(min.add(max).multiply());
		Vector3D off = dist.abs();

		return Math.abs(c.x) <= boxSize.x + off.x
				       && Math.abs(c.y) <= boxSize.y + off.y
				       && Math.abs(c.z) <= boxSize.z + off.z
				       && Math.abs(dist.y * c.z - dist.z * c.y) <= boxSize.y * off.z + boxSize.z * off.y + EPSILON
				       && Math.abs(dist.z * c.x - dist.x * c.z) <= boxSize.z * off.x + boxSize.x * off.z + EPSILON
				       && Math.abs(dist.x * c.y - dist.y * c.x) <= boxSize.x * off.y + boxSize.y * off.x + EPSILON;
	}

}
