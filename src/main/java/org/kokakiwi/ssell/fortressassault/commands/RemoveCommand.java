package org.kokakiwi.ssell.fortressassault.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.kokakiwi.ssell.fortressassault.FortressAssault;

public class RemoveCommand implements CommandExecutor {
	
	private FortressAssault plugin;

	public RemoveCommand(FortressAssault fortressAssault) {
		plugin = fortressAssault;
	}

	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		Player player = plugin.getServer().getPlayer(args[0]);
		plugin.getHandler().removePlayer(player);
		
		return true;
	}

}
