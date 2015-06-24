package com.kiro.sg.voting;

import com.kiro.sg.arena.ArenaManager;
import com.kiro.sg.utils.Perms;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Voting
{

	public static List<VotingOption> selectedOptions;
	private static Map<Player, Integer> voted;

	private static VotingOption winning;
	private static int winning_votes;


	public static void reset()
	{
		voted = new HashMap<>();

		int size = Math.min(ArenaManager.getInstance().getArenaCount(), 3);
		selectedOptions = new ArrayList<>(4);
		if (size > 0)
		{
			do
			{
				VotingOption option = new VotingOption(ArenaManager.getInstance().selectRandomArena());
				if (!selectedOptions.contains(option))
				{
					option.setOption(selectedOptions.size());
					selectedOptions.add(option);
				}
			}
			while (selectedOptions.size() < size);

			VotingOption rand = new VotingOption(true);
			rand.setOption(selectedOptions.size());
			selectedOptions.add(rand);
		}

		for (int i = 0; i < VotingMap.VotingMaps.size(); i++)
		{
			if (selectedOptions.size() > i)
			{
				VotingMap.VotingMaps.get(i).setVotingOption(selectedOptions.get(i));
			}
			else
			{
				VotingMap.VotingMaps.get(i).setVotingOption(null);
			}
		}
	}

	public static void removeVote(Player player)
	{
		if (voted.containsKey(player))
		{
			int op = voted.get(player);
			VotingOption voteOption = selectedOptions.get(op);
			voteOption.removeVote();

			if (player.hasPermission(Perms.DONOR_VOTES))
			{
				voteOption.removeVote();
			}

			getMostVotes();


			for (VotingMap map : VotingMap.VotingMaps)
			{
				map.update();
			}
		}
	}

	public static void voteFor(Player player, int option)
	{

		if (voted.containsKey(player))
		{
			int op = voted.get(player);
			VotingOption voteOption = selectedOptions.get(op);
			voteOption.removeVote();

			if (player.hasPermission(Perms.DONOR_VOTES))
			{
				voteOption.removeVote();
			}
		}

		voted.put(player, option);
		VotingOption voteOption = selectedOptions.get(option);
		voteOption.addVote();

		if (player.hasMetadata("donor"))
		{
			voteOption.addVote();
		}
		player.sendMessage(String.format(ChatColor.AQUA + "You have voted for %s!", selectedOptions.get(option).getDisplayName()));

		getMostVotes();

		for (VotingMap map : VotingMap.VotingMaps)
		{
			map.update();
		}

	}

	public static VotingOption getMostVotes()
	{
		VotingOption mostVotes = null;

		for (VotingOption option : selectedOptions)
		{
			if (mostVotes == null || option.getVotes() > mostVotes.getVotes())
			{
				mostVotes = option;
			}
		}
		if (mostVotes != null)
		{
			if (winning != null)
			{
				winning.setWinning(false);
			}
			winning = mostVotes;
			winning_votes = mostVotes.getVotes();
			winning.setWinning(true);
		}
		return mostVotes;
	}

}
