package org.kokakiwi.ssell.fortressassault.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.kokakiwi.ssell.fortressassault.FortressAssault;

public class AddCommand implements CommandExecutor {
	
	private FortressAssault plugin;

	public AddCommand(FortressAssault fortressAssault) {
		plugin = fortressAssault;
	}

	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(args.length != 2)
			return false;
		
		String team = args[0];
		String player = args[1];
		plugin.getHandler().addPlayer(sender, team, player);
		return true;
	}

}
