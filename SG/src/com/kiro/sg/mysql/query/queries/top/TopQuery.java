package com.kiro.sg.mysql.query.queries.top;

import com.kiro.sg.SGMain;
import com.kiro.sg.lobby.leaderboards.LeaderBoard;
import com.kiro.sg.lobby.leaderboards.TopEntry;
import com.kiro.sg.mysql.query.ExecuteQuery;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public abstract class TopQuery implements ExecuteQuery
{
	protected final LeaderBoard.LeaderboardEntry entry;

	public TopQuery(LeaderBoard.LeaderboardEntry entry)
	{
		this.entry = entry;
	}


	@Override
	public void execute(ResultSet set) throws SQLException
	{

		while (set.next())
		{
			TopEntry entry = new TopEntry();
			entry.name = set.getString(1);
			entry.uuid = UUID.fromString(set.getString(2));
			entry.value = set.getFloat(3);

			this.entry.addEntry(entry);

		}

		new BukkitRunnable()
		{
			@Override
			public void run()
			{
				entry.doneLoading();
			}

		}.runTask(SGMain.getPlugin());

	}

}
