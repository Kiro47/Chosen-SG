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

	public SupplyCrate getCrate(Location location, int mod)
	{
		SupplyCrate crate = crateMap.get(location);
		if (crate == null)
		{
			crate = new SupplyCrate();
			crate.populate(mod);

			crateMap.put(location, crate);
		}

		return crate;
	}


	public void clear()
	{
		crateMap.clear();
	}

}
