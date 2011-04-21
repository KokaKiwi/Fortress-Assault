package org.kokakiwi.ssell.fortressassault.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.kokakiwi.ssell.fortressassault.FortressAssault;

public class TeamsCommand implements CommandExecutor {

	@SuppressWarnings("unused")
	private FortressAssault plugin;

	public TeamsCommand(FortressAssault fortressAssault) {
		plugin = fortressAssault;
	}

	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		sender.sendMessage(ChatColor.GOLD + "Coming soon...");
		
		return true;
	}

}
