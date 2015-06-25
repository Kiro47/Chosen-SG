package com.kiro.sg.game.crates;

import org.bukkit.Location;

import java.util.HashMap;
import java.util.Map;

public class Crates
{

	private final Map<Location, SupplyCrate> crateMap;

	public Crates()
	{
		crateMap = new HashMap<>();
	}

	public SupplyCrate getCrate(Location location)
	{
		SupplyCrate crate = crateMap.get(location);
		if (crate == null)
		{
			crate = new SupplyCrate();
			crate.populate();

			crateMap.put(location, crate);
		}

		return crate;
	}


	public void refillCrates()
	{
		crateMap.clear();
	}

}
