package org.kokakiwi.ssell.fortressassault.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.kokakiwi.ssell.fortressassault.FortressAssault;

public class StopCommand implements CommandExecutor {

	private FortressAssault plugin;

	public StopCommand(FortressAssault fortressAssault) {
		plugin = fortressAssault;
	}

	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		plugin.getHandler().stopEvent((Player) sender);
		
		return true;
	}

}
